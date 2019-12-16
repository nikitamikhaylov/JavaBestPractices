package hello;

import hello.services.DarkSkyService;
import hello.services.PredictionService;
import hello.services.ResponseRBKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
public class HelloController
{
    private static final String url =
            "http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&separator=,&data_format=BROWSER&lastdays=";

    private DarkSkyService darkSkyService;
    private ResponseRBKService helper = new ResponseRBKService(url);

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
        return "Max USD v.s. RUB rate for the last month is " + helper.getMaxRateForLastMonth().toString();
    }

    @RequestMapping("/temperatures")
    public String getTemperature() throws Exception {
        helper.performRequest();
        List<Double> temperatures = darkSkyService.getWeatherDataForDates(helper.getDates());
        StringBuilder answer = new StringBuilder();
        for (Double temp: temperatures)
        {
            answer.append(temp.toString()).append('\n');
        }
        return answer.toString();
    }

    @RequestMapping(value = "/predict")
    String getIdByValue(@RequestParam("temperature") Double temperature) throws Exception {
        PredictionService predictionService = new PredictionService(darkSkyService, helper);
        return predictionService.performPrediction(temperature).toString();
    }

    @RequestMapping("/anime")
    public String anime() {
        return "Anime";
    }
}