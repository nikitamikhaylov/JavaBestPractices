package sbt.database;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="Data")
public class DateWeather {
    private final static DateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String date;
    private String temperature;

    public DateWeather() {}

    public DateWeather(Date date, String temperature) {
        this.date = dateFormater.format(date);
        this.temperature = temperature;
    }

    public static String dateFormat(Date date) {
        return dateFormater.format(date);
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    @Entity
    @Table(name="DateMaxRate")
    public static class DateMaxRate {

        private final static DateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");

        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        private int id;
        private Double maxRate;
        private String date;

        protected DateMaxRate() {}

        public DateMaxRate(Date date, Double maxRate)
        {
            this.maxRate = maxRate;
            this.date = dateFormater.format(date);
        }

        public static String dateFormat(Date date)
        {
            return dateFormater.format(date);
        }

        public Double getMaxRate()
        {
            return maxRate;
        }
    }
}