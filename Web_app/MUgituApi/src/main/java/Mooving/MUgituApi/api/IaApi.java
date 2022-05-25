package Mooving.MUgituApi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/ia")
public class IaApi {

    @GetMapping(path = "/biciEstacion")
    public Map<Long, Integer> getBicisPorEstaciones(){
        //Request a IA
        //REVISAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRR//////////////
        //Obtener mapa de (Estacion - plazasLibres)
        //Restar por plazas - libres
        //////////////////////////////////////////////////////////
        return null;
    }
}
