package Mooving.MUgitu.controllers;

import Mooving.MUgitu.entities.Usuario;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RestRequests {
    public final static String BASE_PATH = "https://mugitu.eus/MUgitu/REST/api";
    public final static String TokenPrefix = "Bearer ";
    public final static String ACCESSTOKEN = "accessToken";
    public final static String REFRESHTOKEN = "refreshToken";

    public static HttpSession getSession(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }
    public static String getToken(String tokenId){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        return (String) session.getAttribute(tokenId);
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

    //Headers and send something PROBABLY POST
    public static <T, G> ResponseEntity<T> RestRequestWithHeaders(String requestUrl, HttpMethod method, G sendEntity, String token, Class<T> returnClass){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, TokenPrefix + token);

        String url = BASE_PATH+requestUrl;
        HttpEntity<G> requestEntity = new HttpEntity<>(sendEntity,headers);
        ResponseEntity<T> response = null;
        try {
            response = restTemplate.exchange(
                    url, method, requestEntity, returnClass);
        }
        catch (HttpClientErrorException e){
            String responseString = RestRequests.manageException(e);
            if(responseString.equals("login")){
                throw new ResponseStatusException(
                        HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "Login needed, refresh token expired", e);
            }else{
                response = RestRequestWithHeaders(requestUrl,method,responseString,returnClass);
            }
        }

        return response;
    }
    //Headers and send nothing PROBABLY GET
    public static <T> ResponseEntity<T> RestRequestWithHeaders(String requestUrl, HttpMethod method, String token, Class<T> returnClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, TokenPrefix + token);

        String url = BASE_PATH+requestUrl;
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<T> response = null;
        try {
            response = restTemplate.exchange(
                    url, method, requestEntity, returnClass);
        }
        catch (HttpClientErrorException e){
            String responseString = RestRequests.manageException(e);
            if(responseString.equals("login")){
                throw new ResponseStatusException(
                        HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "Login needed, refresh token expired", e);
            }else{
                response = RestRequestWithHeaders(requestUrl,method,responseString,returnClass);
            }
        }
        return response;
    }

    private static String manageException(HttpClientErrorException e) {
        if(Objects.requireNonNull(e.getMessage()).startsWith("403 : \"{\"error_message\":\"Access token expired:")){
            ResponseEntity<String> response = RestRequests.RestRequestWithHeaders(
                    "/token/refresh", HttpMethod.GET, getToken(REFRESHTOKEN), String.class);

            JSONObject jsonObj;
            String accessToken ="", refreshToken = "";
            try {
                jsonObj = new JSONObject(response.getBody());
                accessToken = jsonObj.get(RestRequests.ACCESSTOKEN).toString();
                refreshToken = jsonObj.get(RestRequests.REFRESHTOKEN).toString();
            } catch (JSONException eJson) {
                e.printStackTrace();
            }
            if(refreshToken.equals(getToken(REFRESHTOKEN))){
                getSession().setAttribute(ACCESSTOKEN, accessToken);
                return accessToken;
            }
        }
        else if(e.getMessage().startsWith("403 : \"{\"error_message\":\"Refresh token expired:")){
            return "login";
        }
        else{
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
        return null;
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
