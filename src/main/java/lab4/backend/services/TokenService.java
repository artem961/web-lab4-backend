package lab4.backend.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import lab4.backend.configuration.JWTConfig;
import lab4.backend.data.repositories.token.postgres.TokenRepository;
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.TokenPairDTO;
import lab4.backend.dto.TokenPayloadDTO;
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
    private TokenRepository tokenRepository;

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

        tokenRepository.saveToken(TokenMapper.dtoToEntity(refreshToken));

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
            Claims claims = extractClaimsFromToken(token);

            return TokenPayloadDTO.builder()
                    .userId((Integer) claims.get("userId"))
                    .username((String) claims.get("username"))
                    .build();
        } catch (JwtException e) {
            throw new ServiceException("Invalid token: " + e.getMessage());
        }
    }

    public Claims extractClaimsFromToken(TokenDTO token) {
        try {
            Claims claims = jwtParser.parseClaimsJws(token.getToken()).getBody();
            return claims;
        } catch (JwtException e) {
            throw new ServiceException("Invalid token: " + e.getMessage());
        }
    }

    public TokenPairDTO refreshToken(TokenDTO refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new ServiceException("Invalid token");
        }

        if (!isRefreshToken(refreshToken)) {
            throw new ServiceException("Token is not a refresh token");
        }

        if (tokenRepository.existsByToken(TokenMapper.dtoToEntity(refreshToken))) {
            tokenRepository.delete(TokenMapper.dtoToEntity(refreshToken));
            return generateTokenPair(extractPayloadFromToken(refreshToken));
        } else {
            throw new ServiceException("Token does not exist");
        }
    }

    public void revokeToken(TokenDTO refreshToken) {
        tokenRepository.delete(TokenMapper.dtoToEntity(refreshToken));
    }

    private TokenDTO generateAccessToken(TokenPayloadDTO payload) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "access");
        claims.put("userId", payload.getUserId());
        claims.put("username", payload.getUsername());

        Date expires = Date.from(Instant.now().plus(jwtConfig.getAccessTokenExpiration()));

        String token = Jwts.builder()
                .setClaims(claims)
                .issuer(jwtConfig.getIssuer())
                .setSubject(payload.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expires)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();


        return TokenDTO.builder()
                .token(token)
                .expires(expires.getTime() / 1000)
                .build();
    }

    private TokenDTO generateRefreshToken(TokenPayloadDTO payload) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "refresh");
        claims.put("userId", payload.getUserId());
        claims.put("username", payload.getUsername());

        Date expires = Date.from(Instant.now().plus(jwtConfig.getAccessTokenExpiration()));

        String token = Jwts.builder()
                .setClaims(claims)
                .issuer(jwtConfig.getIssuer())
                .setSubject(payload.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expires)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();


        return TokenDTO.builder()
                .token(token)
                .expires(expires.getTime() / 1000)
                .build();
    }

    public boolean isRefreshToken(TokenDTO token) {
        try {
            Claims claims = extractClaimsFromToken(token);
            return "refresh".equals(claims.get("token_type"));
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isAccessToken(TokenDTO token) {
        try {
            Claims claims = extractClaimsFromToken(token);
            return "access".equals(claims.get("token_type"));
        } catch (JwtException e) {
            return false;
        }
    }
}