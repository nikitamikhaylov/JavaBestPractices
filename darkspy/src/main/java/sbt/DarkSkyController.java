package sbt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class DarkSkyController
{
    @Autowired
    private DarkSkyService darkSkyService;

    @RequestMapping("/temperatures")
    public String getTemperature() throws Exception {
        List<Double> temperatures = darkSkyService.getWeatherDataForDates(DarkSkyService.getDefaultDates());
        StringBuilder answer = new StringBuilder();
        for (Double temp: temperatures)
        {
            answer.append(temp.toString()).append('\n');
        }
        return answer.toString();
    }

    @RequestMapping("/getWeatherDataForDates")
    public String getWeatherDataForDates() throws Exception {
        return darkSkyService.getWeatherDataForDates(DarkSkyService.getDefaultDates()).toString();
    }
}