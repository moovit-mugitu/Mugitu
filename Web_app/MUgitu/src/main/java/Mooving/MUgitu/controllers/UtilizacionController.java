package Mooving.MUgitu.controllers;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(path = "/utilizar")
public class UtilizacionController {

    @PostMapping(path = "/create/{biciId}/{userId}")
    @ResponseBody
    public String createUtilizacion(WebRequest request) {
        long biciId = Long.parseLong(request.getParameter("biciId"));
        long userId = Long.parseLong(request.getParameter("userId"));

        ResponseEntity<Void> response = RestRequests.RestRequestWithHeaders("/utilizar/create/" + biciId + "/" + userId,
                HttpMethod.PUT, RestRequests.ACCESSTOKEN, Void.class);
        return "created";
    }
}
