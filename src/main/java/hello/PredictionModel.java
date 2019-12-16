package hello;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class PredictionModel {
    private static Double coefA = 0.;
    private static Double coefB = 0.;

    public static Double predict(Double temperature) throws IOException, ParseException {
        Double answer = coefA + temperature * coefB;
        return answer > 0 ? answer : 0.0;
    }

    public static void fit(List<Double> temperatures, List<Double> rates) {
         coefB = findB(temperatures, rates);
         coefA = findA(temperatures, rates, coefB);
    }

    private static Double getMean(List<Double> arr) {
        Double sumArr = 0.;
        Double elementCount = 0.;
        for (Double elem : arr) {
            sumArr += elem;
            elementCount++;
        }
        return sumArr / elementCount;
    }

    private static Double findB(List<Double> tempList, List<Double> rateList) {
        Double up = 0.;
        Double down = 0.;
        for (int i = 0; i < tempList.size(); i++) {
            up += (tempList.get(i) - getMean(tempList)) * (rateList.get(i) - getMean(rateList));
            down += Math.pow(tempList.get(i) - getMean(tempList), 2);
        }
        return up / down;
    }

    private static Double findA(List<Double> tempList, List<Double> rateList, Double b) {
        return getMean(rateList) - b * getMean(tempList);
    }
}
