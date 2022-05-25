package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.bici.BiciDao;
import Mooving.MUgituApi.dao.estacionar.EstacionarDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.dao.utilizacion.UtilizacionDao;
import Mooving.MUgituApi.entities.Utilizacion;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/utilizar")
public class UtilizacionApi {

    final UtilizacionDao utilizacionDao;
    final BiciDao biciDao;
    final UsuarioDao usuarioDao;
    final EstacionarDao estacionarDao;

    public UtilizacionApi(UtilizacionDao utilizacionDao, BiciDao biciDao, UsuarioDao usuarioDao, EstacionarDao estacionarDao) {
        this.utilizacionDao = utilizacionDao;
        this.biciDao = biciDao;
        this.usuarioDao = usuarioDao;
        this.estacionarDao = estacionarDao;
    }

    @PutMapping(path = "/create/{biciId}/{userId}")
    public Utilizacion createUtilizacion(@PathVariable("biciId") long biciId, @PathVariable("userId") long userId,
                                         HttpServletResponse response, Authentication authentication) {
        if(UserApi.getPrincipal(usuarioDao, authentication).getUserId() != userId){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        Utilizacion u = new Utilizacion();
        u.setBici(biciDao.getBici(biciId));
        u.setFechaInicio(new Date());
        u.setUser(usuarioDao.getUser(userId));
        try{
            estacionarDao.finishEstacionar(biciId);
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        u = utilizacionDao.addUtilizacion(u);
        return u;
    }
}
