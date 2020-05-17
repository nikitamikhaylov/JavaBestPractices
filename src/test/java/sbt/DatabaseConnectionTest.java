//package sbt;
//
//import sbt.database.DateWeather;
//import sbt.database.DateWeatherRepository;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Calendar;
//import java.util.Date;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class DatabaseConnectionTest {
//
//    @Autowired
//    private DateWeatherRepository dateWeatherRepository;
//
//    @Test
//    public void test() {
//        Date date = Calendar.getInstance().getTime();
//        Double temperature = 42.0;
//        dateWeatherRepository.save(new DateWeather(date, temperature.toString()));
//        Assert.assertTrue(dateWeatherRepository.findByDate(DateWeather.dateFormat(date))
//                .map(DateWeather::getTemperature).isPresent());
//        Assert.assertEquals(dateWeatherRepository.findByDate(DateWeather.dateFormat(date))
//                .map(DateWeather::getTemperature).get(), temperature.toString());
//    }
//}