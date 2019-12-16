package hello.services;

import hello.PredictionModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PredictionService {

    private final DarkSkyService darkSkyService;
    private final ResponseRBKService responseRBKService;

    @Autowired
    public PredictionService(DarkSkyService darkSkyService, ResponseRBKService responseRBKService)
    {
        this.darkSkyService = darkSkyService;
        this.responseRBKService = responseRBKService;
    }

    public String performPrediction(Double temperature) throws Exception
    {
        responseRBKService.performRequest();
        List<Double> rates = responseRBKService.getRates();
        List<Long> dates = darkSkyService.getDatesAsLongValues(responseRBKService.getDates());
        List<Double> temperatures = darkSkyService.getWeatherDataForDates(responseRBKService.getDates());
        PredictionModel.fit(temperatures, rates);
        return PredictionModel.predict(temperature).toString();
    }
}
