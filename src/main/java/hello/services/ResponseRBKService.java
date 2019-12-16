package hello.services;

import hello.database.DateMaxRate;
import hello.database.DateMaxRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ResponseRBKService {

    @Autowired
    private DateMaxRateRepository dateMaxRateRepository;

    private String URL;
    private RestTemplate restTemplate;

    private final List<Double> rates = new ArrayList<>();
    private final List<Date> dates = new ArrayList<>();

    public ResponseRBKService() {}

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

    @Transactional
    public Double getMaxRateForLastMonth() throws Exception
    {
        Double maxRate = .0;
        Date now = Calendar.getInstance().getTime();
        Optional<DateMaxRate> optionalDateMaxRate = dateMaxRateRepository.findByDate(DateMaxRate.dateFormat(now));
        if (optionalDateMaxRate.isPresent())
        {
            System.out.println("Reading maximum rate value for" + now + " from cache");
            maxRate = optionalDateMaxRate.get().getMaxRate();
        }
        else
        {
            System.out.println("Performing maximum rate value request for " + now);
            performRequest();
            maxRate = getMax(getRates());
            dateMaxRateRepository.save(new DateMaxRate(now, maxRate));
        }
        return maxRate;
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
        rates.clear();
        dates.clear();
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
