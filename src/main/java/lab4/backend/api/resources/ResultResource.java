package lab4.backend.api.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab4.backend.api.utils.filters.auth.AuthorizedOnly;
import lab4.backend.api.models.result.request.DotRequestModel;
import lab4.backend.api.models.result.response.ResultResponseModel;
import lab4.backend.api.utils.mapping.ResultMapper;
import lab4.backend.dto.DotDTO;
import lab4.backend.dto.ResultDTO;
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

    @POST
    @Path("/check")
    public ResultResponseModel checkHit(DotRequestModel requestModel) {
        DotDTO dotDTO = DotDTO.builder()
                .x(requestModel.getX())
                .y(requestModel.getY())
                .r(requestModel.getR())
                .build();

        ResultDTO resultDTO = resultService.checkHit(dotDTO);
        return ResultMapper.resultDtoToResponseModel(resultDTO);
    }

    @GET
    @Path("/all")
    public List<ResultResponseModel> getAllResults() {
        return resultService.getAllResults().stream()
                .map(ResultMapper::resultDtoToResponseModel)
                .collect(Collectors.toList());
    }

    @DELETE
    @Path("/all")
    public void deleteAllResults() {
        resultService.deleteAllResults();
    }

}
