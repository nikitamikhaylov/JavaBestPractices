package hello;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceHelper {

    private final String URL;
    private final RestTemplate restTemplate;

    public ServiceHelper(String URL) {
        this.URL = URL;
        this.restTemplate = new RestTemplate();
    }

    public ServiceHelper(String URL, RestTemplate restTemplate) {
        this.URL = URL;
        this.restTemplate = restTemplate;
    }

    public String getMaxRateForLastMonth() {
        Double maxRate = getMax(parseResponseAndGetRates(getRateForLastMonth()));
        return "Max USD v.s. RUB rate for the last month is " + maxRate;
    }

    public String[] getRateForLastMonth() {
        ResponseEntity<String> response = restTemplate.getForEntity(URL + "30", String.class);
        assert (response.getStatusCode().equals(HttpStatus.OK));
        return Objects.requireNonNull(response.getBody()).split("\n");
    }

    public static List<Double> parseResponseAndGetRates(String[] lines) {
        List<Double> answer = new ArrayList<Double>();
        for (String line1 : lines) {
            String[] line = line1.split(",");
            answer.add(Double.parseDouble(line[line.length - 1]));
        }
        return answer;
    }

    public static Double getMax(List<Double> rates) {
        Double maxRate = 0.;
        for (Double current: rates) {
            maxRate = Math.max(maxRate, current);
        }
        return maxRate;
    }
}
