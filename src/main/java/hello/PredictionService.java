package hello;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PredictionService {

    private final DarkSkyService darkSkyService;
    private final ServiceHelper serviceHelper;

    @Autowired
    public PredictionService(DarkSkyService darkSkyService, ServiceHelper serviceHelper) {
        this.darkSkyService = darkSkyService;
        this.serviceHelper = serviceHelper;
    }

    public String performPrediction(Double temperature) throws Exception {
        serviceHelper.performRequest();
        List<Double> rates = serviceHelper.getRates();
        List<Long> dates = darkSkyService.getDatesAsLongValues(serviceHelper.getDates());
        List<Double> temperatures = darkSkyService.getWeatherDataForDates(serviceHelper.getDates());
        PredictionModel.fit(temperatures, rates);
        return PredictionModel.predict(temperature).toString();
    }
}
