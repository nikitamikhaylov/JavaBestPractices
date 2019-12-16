package hello.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResponseRBKService {

    private final String URL;
    private final RestTemplate restTemplate;

    private final List<Double> rates = new ArrayList<>();
    private final List<Date> dates = new ArrayList<>();

    public ResponseRBKService(String URL)
    {
        this.URL = URL;
        this.restTemplate = new RestTemplate();
    }

    public ResponseRBKService(String URL, RestTemplate restTemplate)
    {
        this.URL = URL;
        this.restTemplate = restTemplate;
    }

    public void performRequest() throws Exception
    {
        parseResponseAndFillRatesAndDates(getRateForLastMonth());
    }

    public String getMaxRateForLastMonth() throws Exception
    {
        performRequest();
        Double maxRate = getMax(getRates());
        return "Max USD v.s. RUB rate for the last month is " + maxRate;
    }

    public String[] getRateForLastMonth()
    {
        ResponseEntity<String> response = restTemplate.getForEntity(URL + "30", String.class);
        assert (response.getStatusCode().equals(HttpStatus.OK));
        System.out.println(response.getBody());
        return Objects.requireNonNull(response.getBody()).split("\n");
    }

    public void parseResponseAndFillRatesAndDates(String[] lines) throws ParseException
    {
        for (String line : lines) {
            String[] splitted_line = line.split(",");
            Double rate = Double.parseDouble(splitted_line[splitted_line.length - 1]);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(splitted_line[1]);
            rates.add(rate);
            dates.add(date);
        }
    }

    public Double getMax(List<Double> rates)
    {
        Double maxRate = 0.;
        for (Double current: rates) {
            maxRate = Math.max(maxRate, current);
        }
        return maxRate;
    }

    public List<Double> getRates() {
        return rates;
    }

    public List<Date> getDates() {
        return dates;
    }


}
