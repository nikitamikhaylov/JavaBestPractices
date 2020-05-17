package sbt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sbt.database.DateWeather;
import sbt.presentation.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@SpringBootApplication
@Component
public class DarkSkyService
{
    public static void main(String[] args) {
        SpringApplication.run(DarkSkyService.class, args);
    }

    public static List<Date> getDefaultDates() throws ParseException {
        List<Date> default_dates = new ArrayList<>();
        for (String strDate : Arrays.asList("2019-09-25", "2019-09-26", "2019-09-27", "2019-09-30",
                "2019-10-01", "2019-10-02", "2019-10-03", "2019-10-04",
                "2019-10-07", "2019-10-08", "2019-10-09", "2019-10-10", "2019-10-11",
                "2019-10-15", "2019-10-16", "2019-10-17", "2019-10-18", "2019-10-21", "2019-10-22", "2019-10-23"))
        {
            default_dates.add(new SimpleDateFormat("yyyy-MM-dd").parse(strDate));
        }
        return default_dates;
    }

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
                System.out.println("Requesting sbt.DarkSkyService for " + date);
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
