package Mooving.MUgitu.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestRequests {

    public static <T> T RESTgetRequest(String requestUrl, Class<T> returnClass) {
        RestTemplate restTemplate = new RestTemplate();

        String url = HomeController.BASE_PATH+requestUrl;
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, returnClass);
        return  responseEntity.getBody();
    }

    public static <T, G> G RESTpostRequest(String requestUrl, T objToSend, Class<G> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> requestEntity = new HttpEntity<>(objToSend, headers);

        String url = HomeController.BASE_PATH+requestUrl;
        ResponseEntity<G> responseEntity = restTemplate.postForEntity(url, requestEntity, returnClass);
        return  responseEntity.getBody();
    }

}
