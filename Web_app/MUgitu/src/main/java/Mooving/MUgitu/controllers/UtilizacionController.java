package Mooving.MUgitu.controllers;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@RestController
@RequestMapping(path = "/utilizar")
public class UtilizacionController {

    @PostMapping(path = "/create")
    @ResponseBody
    public String createUtilizacion(WebRequest request) {
        long biciId = Long.parseLong(Objects.requireNonNull(request.getParameter("biciId")));
        long userId = Long.parseLong(Objects.requireNonNull(request.getParameter("userId")));

        ResponseEntity<Void> response = RestRequests.RestRequestWithHeaders("/utilizar/create/" + biciId + "/" + userId,
                HttpMethod.PUT, RestRequests.getToken(RestRequests.ACCESSTOKEN), Void.class);
        return "created";
    }
}
