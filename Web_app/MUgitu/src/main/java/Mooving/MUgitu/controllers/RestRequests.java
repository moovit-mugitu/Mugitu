package Mooving.MUgitu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class RestRequests {
    public final static String BASE_PATH = "http://localhost:8000/MUgitu/REST/api";
    public final static String TokenPrefix = "Bearer ";

    public static HttpSession getSession(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }

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

    public static <T, G> ResponseEntity<T> RestRequestWithHeaders(String requestUrl, HttpMethod method, G sendEntity, HttpHeaders headers, Class<T> returnClass){
        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_PATH+requestUrl;
        HttpEntity<G> requestEntity = new HttpEntity<>(sendEntity,headers);
        ResponseEntity<T> response = restTemplate.exchange(
                url, method, requestEntity, returnClass);
        return response;
    }
    public static <T, G> ResponseEntity<T> RestRequestWithHeaders(String requestUrl, HttpMethod method, HttpHeaders headers, Class<T> returnClass){
        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_PATH+requestUrl;
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<T> response = restTemplate.exchange(
                url, method, requestEntity, returnClass);
        return response;
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
    public static <T, G> ResponseEntity<T> localPostRequestForm(String requestUrl, MultiValueMap<String, G> map, Class<T> returnClass) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, G>> request = new HttpEntity<>(map, headers);

        String url = "http://localhost:8080"+requestUrl;
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, request, returnClass);
        return  responseEntity;
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
