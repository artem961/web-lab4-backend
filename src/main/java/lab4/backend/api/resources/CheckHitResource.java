package lab4.backend.api.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lab4.backend.dto.ResultDTO;
import lab4.backend.dto.DotDTO;
import lab4.backend.services.ResultService;
import lombok.extern.java.Log;

import java.util.List;

@Path("/dots")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Log
public class CheckHitResource {
    @EJB
    private ResultService resultService;

    @POST
    @Path("/check")
    public ResultDTO postCheckHit(DotDTO dotDTO) {
        return resultService.checkHit(dotDTO);
    }

    @GET
    @Path("/all")
    public List<ResultDTO> getAllResults() {
        return resultService.getAllResults();
    }

    @DELETE
    @Path("/all")
    public void deleteAllResults() {
        resultService.deleteAllResults();
    }

}
