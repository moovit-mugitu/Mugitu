package Mooving.MUgitu.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestRequests {
    public final static String BASE_PATH = "http://localhost:8000/MUgitu/REST/api";

    public static <T> ResponseEntity<T> RESTgetRequestResponseEntity(String requestUrl, Class<T> returnClass) {
        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_PATH+requestUrl;
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, returnClass);
        return  responseEntity;
    }

    public static <T> T RESTgetRequest(String requestUrl, Class<T> returnClass) {
        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_PATH+requestUrl;
        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, returnClass);
        return  responseEntity.getBody();
    }

    public static <T, G> T RESTpostRequestForm(String requestUrl, MultiValueMap<String, G> map, Class<T> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, G>> request = new HttpEntity<>(map, headers);

        String url = BASE_PATH+requestUrl;
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, request, returnClass);
        return  responseEntity.getBody();
    }
    public static <T, G> T localPostRequestForm(String requestUrl, MultiValueMap<String, G> map, Class<T> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, G>> request = new HttpEntity<>(map, headers);

        String url = "http://localhost:8080"+requestUrl;
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, request, returnClass);
        return  responseEntity.getBody();
    }

    public static <T, G> G RESTpostRequest(String requestUrl, T objToSend, Class<G> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> requestEntity = new HttpEntity<>(objToSend, headers);

        String url = BASE_PATH+requestUrl;
        ResponseEntity<G> responseEntity = restTemplate.postForEntity(url, requestEntity, returnClass);
        return  responseEntity.getBody();
    }

}
