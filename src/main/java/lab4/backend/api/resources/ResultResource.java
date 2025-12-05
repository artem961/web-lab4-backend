package lab4.backend.api.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import lab4.backend.api.utils.filters.auth.AuthorizedOnly;
import lab4.backend.api.models.result.request.DotRequestModel;
import lab4.backend.api.models.result.response.ResultResponseModel;
import lab4.backend.api.utils.mapping.ResultMapper;
import lab4.backend.dto.DotDTO;
import lab4.backend.dto.ResultDTO;
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.UserDTO;
import lab4.backend.services.AuthService;
import lab4.backend.services.ResultService;
import lombok.extern.java.Log;

import java.util.List;
import java.util.stream.Collectors;

@Path("/dots")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AuthorizedOnly
@Log
public class ResultResource {
    @EJB
    private ResultService resultService;

    @EJB
    private AuthService authService;

    @POST
    @Path("/check")
    public ResultResponseModel checkHit(DotRequestModel requestModel, @HeaderParam("Authorization") String header) {
        DotDTO dotDTO = ResultMapper.dotRequestModelToDotDTO(requestModel);
        UserDTO userDTO = getUserFromHeader(header);
        ResultDTO resultDTO = resultService.checkHit(dotDTO, userDTO);
        return ResultMapper.resultDtoToResultResponseModel(resultDTO);
    }

    @GET
        @Path("/all")
    public List<ResultResponseModel> getAllResults(@HeaderParam("Authorization") String header) {
        UserDTO userDTO = getUserFromHeader(header);
        return resultService.getAllResultsForUser(userDTO).stream()
                .map(ResultMapper::resultDtoToResultResponseModel)
                .collect(Collectors.toList());
    }

    @DELETE
    @Path("/all")
    public void deleteAllResults(@HeaderParam("Authorization") String header) {
        UserDTO userDTO = getUserFromHeader(header);
        resultService.deleteAllResultsForUser(userDTO);
    }

    private UserDTO getUserFromHeader(String header) {
        String token = header.split(" ")[1];
        return authService.getUserByToken(TokenDTO.builder().token(token).build());
    }
}
