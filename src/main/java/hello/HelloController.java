package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class HelloController {

    private static final String url = "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&separator=,&data_format=BROWSER&lastdays=";

    private DarkSkyService darkSkyService;

    @Autowired
    public HelloController(DarkSkyService darkSkyService) {
        this.darkSkyService = darkSkyService;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/maxrate")
    public String getMaxRate() throws Exception {
        ServiceHelper helper = new ServiceHelper(url);
        return helper.getMaxRateForLastMonth();
    }

    @RequestMapping("/temperature")
    public String getTemperature() throws Exception {
        ServiceHelper helper = new ServiceHelper(url);
        helper.performRequest();
        List<Double> temperature = darkSkyService.getWeatherDataForDates(helper.getDates());
        return "OK";
    }

    @RequestMapping(value = "/predict")
    String getIdByValue(@RequestParam("temperature") Double temperature) throws Exception {
        ServiceHelper helper = new ServiceHelper(url);
        PredictionService predictionService = new PredictionService(darkSkyService, helper);
        return predictionService.performPrediction(temperature).toString();
    }

    @RequestMapping("/anime")
    public String anime() {
        return "Anime";
    }
}