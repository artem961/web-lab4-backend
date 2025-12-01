package lab4.backend.api.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab4.backend.api.models.request.result.DotRequestModel;
import lab4.backend.api.models.response.result.ResultResponseModel;
import lab4.backend.dto.ResultDTO;
import lab4.backend.services.ResultService;
import lab4.backend.utils.mapping.DotMapper;
import lab4.backend.utils.mapping.ResultMapper;
import lombok.extern.java.Log;

import java.util.List;
import java.util.stream.Collectors;

@Path("/dots")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Log
public class ResultResource {
    @EJB
    private ResultService resultService;

    @POST
    @Path("/check")
    public ResultResponseModel checkHit(DotRequestModel requestModel) {
        ResultDTO resultDTO = resultService.checkHit(DotMapper.requestModelToDTO(requestModel));
        return ResultMapper.dtoToResponseModel(resultDTO);
    }

    @GET
    @Path("/all")
    public List<ResultResponseModel> getAllResults() {
        return resultService.getAllResults().stream()
                .map(ResultMapper::dtoToResponseModel)
                .collect(Collectors.toList());
    }

    @DELETE
    @Path("/all")
    public void deleteAllResults() {
        resultService.deleteAllResults();
    }

}
