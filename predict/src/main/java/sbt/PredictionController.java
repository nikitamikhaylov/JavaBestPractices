package sbt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class PredictionController
{
    @Autowired
    private PredictionService predictionService;

    @RequestMapping(value = "/predict")
    String getIdByValue(@RequestParam("temperature") Double temperature) throws Exception {
        return predictionService.performPrediction(temperature);
    }

}