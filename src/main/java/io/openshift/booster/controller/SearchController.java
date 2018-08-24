package io.openshift.booster.controller;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class SearchController {
    
    // Enter a valid subscription key.
static String subscriptionKey = "Ocp-Apim-Subscription-Key";
static String subscriptionVal = "";

/*
 * If you encounter unexpected authorization errors, double-check these values
 * against the endpoint for your Bing Web search instance in your Azure
 * dashboard.
 */
static String host = "https://api.cognitive.microsoft.com";
static String path = "/bing/v7.0/search";
static String searchTerm = "Microsoft Cognitive Services";

@RequestMapping("/search")
public  SearchResults searchWeb(String searchQuery){
    SearchResults results =null;
    try{
        RestTemplate restTemplate=new RestTemplate();
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add(subscriptionKey, subscriptionVal);
        HttpHeaders headers = new HttpHeaders();
        headers.add(subscriptionKey, subscriptionVal);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response =restTemplate.getForEntity(host+path+"?q="+searchTerm, String.class,entity);
        results = new SearchResults(new HashMap<String, String>(), response.toString());

    }catch(Exception e){
        e.printStackTrace();
    }
    return results;
}

    
    
    
}

class SearchResults{
    HashMap<String, String> relevantHeaders;
    String jsonResponse;
    SearchResults(HashMap<String, String> headers, String json) {
        relevantHeaders = headers;
        jsonResponse = json;
    }
}
