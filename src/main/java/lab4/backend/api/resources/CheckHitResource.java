package lab4.backend.api.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lab4.backend.dto.CheckHitResultDTO;
import lab4.backend.dto.DotDTO;
import lab4.backend.services.DotService;
import lombok.extern.java.Log;

import java.math.BigDecimal;

@Path("/checkHit")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Log
public class CheckHitResource {
    @EJB
    private DotService dotService;

    @POST
    public CheckHitResultDTO postCheckHit(DotDTO dotDTO) {
        return dotService.checkHit(dotDTO);
    }

}
