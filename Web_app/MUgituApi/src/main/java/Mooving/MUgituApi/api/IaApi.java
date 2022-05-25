package Mooving.MUgituApi.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/ia")
public class IaApi {

    @GetMapping(path = "/biciEstacion")
    public Map<Long, Integer[]> getBicisPorEstaciones(){
        //Request a IA
        //REVISAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRR//////////////
        //Obtener mapa de (Estacion - plazasLibres)
        //Restar por plazas - libres
        //////////////////////////////////////////////////////////
        Map<Long, Integer[]> map = new HashMap<>();
        map.put(1L, new Integer[] { 3, 10 });
        map.put(2L, new Integer[] { 10, 15 });
        map.put(3L, new Integer[] { 25, 30 });
        map.put(4L, new Integer[] { 3, 20 });

        return map;
    }
}
