package hello.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.database.DateWeather;
import hello.presentation.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.Date;

@Service
@Component
public class DarkSkyService
{
    private static final String key = "c905b24e84e551d675fc3c5aaa3eb81e";
    private static final String latitude = "55.0000";
    private static final String longitude = "37.5000";
    private String getUrl(Long date)
    {
        return "https://api.darksky.net/forecast/" + key + "/" + latitude + "," +
                longitude + "," + date +  "?units=auto&exclude=currently,hourly,flags";
    }
    private static final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));

    private final RestTemplate restTemplate;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    public DarkSkyService()
    {
        this.restTemplate = new RestTemplate();
    }

    private Double getTemperatureFromResponse(ResponseEntity<String> responseEntity) throws IOException
    {
        Example example = new ObjectMapper().readValue(responseEntity.getBody(), Example.class);
        return example.getDaily().getData().get(0).getTemperatureHigh();
    }


    @Transactional
    public List<Double> getWeatherDataForDates(List<Date> dates) throws IOException
    {
        List<Double> temperatures = new ArrayList<>();
        for (Date date : dates) {
            Optional<DateWeather> cached = databaseService.getRowByDate(date);
            if (cached.isPresent())
            {
                System.out.println("Reading temperature for " + date + " from cache.");
                temperatures.add(Double.parseDouble(cached.get().getTemperature()));
            }
            else
            {
                System.out.println("Requesting DarkSkyService for " + date);
                ResponseEntity<String> response = restTemplate.getForEntity(getUrl(getDateAsLongValue(date)), String.class);
                assert (response.getStatusCode().equals(HttpStatus.OK));
                Double temperature = getTemperatureFromResponse(response);
                temperatures.add(temperature);
                databaseService.saveOrUpdate(new DateWeather(date, temperature.toString()));
            }
        }
        return temperatures;
    }

    public List<Long> getDatesAsLongValues(List<Date> dates)
    {
        List<Long> transformed_dates = new ArrayList<>();
        for (Date date: dates)
        {
            transformed_dates.add(getDateAsLongValue(date));
        }
        return transformed_dates;
    }

    private Long getDateAsLongValue(Date date)
    {
        calendar.setTime(date);
        return calendar.getTimeInMillis() / 1000L;
    }
}
