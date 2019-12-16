package hello.database;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name="DateMaxRate")
public class DateMaxRate {

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
