package Mooving.MUgituApi.api;

import Mooving.MUgituApi.dao.bici.BiciDao;
import Mooving.MUgituApi.dao.user.UsuarioDao;
import Mooving.MUgituApi.dao.utilizacion.UtilizacionDao;
import Mooving.MUgituApi.entities.Utilizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/utilizar")
public class UtilizacionApi {

    final UtilizacionDao utilizacionDao;
    final BiciDao biciDao;
    final UsuarioDao usuarioDao;

    public UtilizacionApi(UtilizacionDao utilizacionDao, BiciDao biciDao, UsuarioDao usuarioDao) {
        this.utilizacionDao = utilizacionDao;
        this.biciDao = biciDao;
        this.usuarioDao = usuarioDao;
    }

    @PutMapping(path = "/create/{biciId}/{userId}")
    public void createUtilizacion(@PathVariable("biciId") long biciId, @PathVariable("userId") long userId){
        Utilizacion u = new Utilizacion();
        u.setBici(biciDao.getBici(biciId));
        u.setFechaInicio(new Date());
        u.setUser(usuarioDao.getUser(userId));
        u = utilizacionDao.addUtilizacion(u);
    }
}
