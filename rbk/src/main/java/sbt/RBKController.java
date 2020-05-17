package sbt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class RBKController
{
    private ResponseRBKService responseRBKService;

    @Autowired
    public RBKController(ResponseRBKService responseRBKService)
    {
        this.responseRBKService = responseRBKService;
    }

    @RequestMapping("/maxrate")
    public String getMaxRate() throws Exception {
        return "Max USD v.s. RUB rate for the last month is " + responseRBKService.getMaxRateForLastMonth().toString();
    }


    @RequestMapping("/getDates")
    public String getDates() throws Exception {
        return responseRBKService.getDates().toString();
    }

    @RequestMapping("/getRates")
    public String getRates() throws Exception {
        return responseRBKService.getRates().toString();
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/anime")
    public String anime() {
        return "Anime";
    }

}