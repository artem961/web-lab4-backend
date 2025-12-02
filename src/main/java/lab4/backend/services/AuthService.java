package lab4.backend.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.TokenPairDTO;
import lab4.backend.dto.UserDTO;
import lab4.backend.utils.mapping.UserMapper;

@Singleton
public class AuthService {
    @EJB
    private UserService userService;
    @EJB
    private TokenService tokenService;


    public TokenPairDTO authenticate(UserDTO userDTO) {
        UserDTO bdUser = userService.getUserByName(userDTO.getUsername());
        if (userDTO.getPassword().equals(bdUser.getPassword())){
            return tokenService.generateTokenPair(UserMapper.userDTOToTokenPayloadDTO(bdUser));
        } else{
            throw new RuntimeException("Invalid username or password");
        }
    }

    public TokenPairDTO register(UserDTO userDTO) {
        userDTO = userService.createUser(userDTO);
        return tokenService.generateTokenPair(UserMapper.userDTOToTokenPayloadDTO(userDTO));
    }

    public TokenPairDTO refreshToken(TokenDTO refreshToken) {
        return tokenService.refreshToken(refreshToken);
    }

    public void logout(TokenDTO refreshToken) {
        tokenService.revokeToken(refreshToken);
    }
}
