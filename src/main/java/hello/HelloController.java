package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/maxrate")
    public String getMaxRate() {
        ServiceHelper helper = new ServiceHelper("http://export.rbc.ru/free/selt.0/free.fcgi?period=DAILY&tickers=USD000000TOD&separator=,&data_format=BROWSER&lastdays=");
        return helper.getMaxRateForLastMonth();
    }

    @RequestMapping("/anime")
    public String anime() {
        return "Anime";
    }
}