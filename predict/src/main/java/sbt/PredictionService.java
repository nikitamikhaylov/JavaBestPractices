package sbt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class PredictionService
{
    public static void main(String[] args) {
        SpringApplication.run(PredictionService.class, args);
    }

    public String performPrediction(Double temperature) throws Exception
    {
        RestTemplate restTemplate = new RestTemplate();

        System.out.println("performPrediction: " + temperature.toString());

        String darkspy = "http://darkspy:8081/getWeatherDataForDates";
        ResponseEntity<String> darkspy_response = restTemplate.getForEntity(darkspy, String.class);
        String weather = darkspy_response.getBody();

        String rbk = "http://rbk:8082/getRates";
        ResponseEntity<String> rbk_response = restTemplate.getForEntity(rbk, String.class);
        String rates = rbk_response.getBody();

        return weather + "\n" + rates;
    }
}
