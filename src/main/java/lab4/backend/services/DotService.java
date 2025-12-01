package lab4.backend.services;

import jakarta.ejb.Stateless;
import lab4.backend.dto.CheckHitResultDTO;
import lab4.backend.dto.DotDTO;
import lab4.backend.utils.HitChecker;

@Stateless
public class DotService {
    public CheckHitResultDTO checkHit(DotDTO dotDTO){
        return HitChecker.checkHit(dotDTO);
    }
}
