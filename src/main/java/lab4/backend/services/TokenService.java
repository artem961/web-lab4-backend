package lab4.backend.services;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import lab4.backend.configuration.JWTConfig;
import lab4.backend.data.repositories.token.postgres.PostgresTokenRepository;
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.TokenPairDTO;
import lab4.backend.dto.TokenPayloadDTO;
import lab4.backend.dto.UserDTO;
import lab4.backend.services.exceptions.ServiceException;
import lab4.backend.services.utils.annotations.ExceptionMessage;
import lab4.backend.services.utils.annotations.WrapWithServiceException;
import lab4.backend.utils.mapping.TokenMapper;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Singleton
@WrapWithServiceException
@ExceptionMessage
public class TokenService {
    @Inject
    private JWTConfig jwtConfig;

    @EJB
    private PostgresTokenRepository postgresTokenRepository;

    private Key signingKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
        this.jwtParser = Jwts.parser()
                .setSigningKey(signingKey)
                .requireIssuer(jwtConfig.getIssuer())
                .build();
    }

    public TokenPairDTO generateTokenPair(TokenPayloadDTO payload) {
        TokenDTO accessToken = generateAccessToken(payload);
        TokenDTO refreshToken = generateRefreshToken(payload);

        postgresTokenRepository.saveToken(TokenMapper.dtoToEntity(refreshToken));

        return TokenPairDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    public Boolean validateToken(TokenDTO token) {
        try {
            jwtParser.parseClaimsJws(token.getToken());
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public TokenPayloadDTO extractPayloadFromToken(TokenDTO token) {
        try {
            var claims = jwtParser.parseClaimsJws(token.getToken()).getBody();

            return TokenPayloadDTO.builder()
                    .userId((Integer) claims.get("userId"))
                    .username((String) claims.get("username"))
                    .build();
        } catch (JwtException e) {
            throw new ServiceException("Invalid token: " + e.getMessage());
        }
    }

    public TokenPairDTO refreshToken(TokenDTO refreshToken) {
        if (!isRefreshToken(refreshToken)) {
            throw new ServiceException("Token is not a refresh token");
        }

        if (!validateToken(refreshToken)) {
            throw new ServiceException("Invalid token");
        }

        if (postgresTokenRepository.existsByToken(TokenMapper.dtoToEntity(refreshToken))) {
            postgresTokenRepository.delete(TokenMapper.dtoToEntity(refreshToken));
            return generateTokenPair(extractPayloadFromToken(refreshToken));
        }else{
            throw new ServiceException("Token does not exist");
        }
    }

    public void revokeToken(TokenDTO refreshToken) {
        postgresTokenRepository.delete(TokenMapper.dtoToEntity(refreshToken));
    }

    private TokenDTO generateAccessToken(TokenPayloadDTO payload) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "access");
        claims.put("userId", payload.getUserId());
        claims.put("username", payload.getUsername());

        String token = Jwts.builder()
                .setClaims(claims)
                .issuer(jwtConfig.getIssuer())
                .setSubject(payload.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
                        Instant.now().plus(jwtConfig.getAccessTokenExpiration())
                ))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();


        return TokenDTO.builder()
                .token(token)
                .build();
    }

    private TokenDTO generateRefreshToken(TokenPayloadDTO payload) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "refresh");
        claims.put("userId", payload.getUserId());
        claims.put("username", payload.getUsername());

        String token = Jwts.builder()
                .setClaims(claims)
                .issuer(jwtConfig.getIssuer())
                .setSubject(payload.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
                        Instant.now().plus(jwtConfig.getRefreshTokenExpiration())
                ))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();


        return TokenDTO.builder()
                .token(token)
                .build();
    }

    public boolean isRefreshToken(TokenDTO token) {
        try {
            var claims = jwtParser.parseClaimsJws(token.getToken()).getBody();
            return "refresh".equals(claims.get("token_type"));
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isAccessToken(TokenDTO token) {
        try {
            var claims = jwtParser.parseClaimsJws(token.getToken()).getBody();
            return "access".equals(claims.get("token_type"));
        } catch (JwtException e) {
            return false;
        }
    }
}
