package hello.services;

import hello.database.DateWeather;
import hello.database.DateWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class DatabaseService
{
    @Autowired
    DateWeatherRepository dateWeatherRepository;

    public List<DateWeather> getAllData()
    {
        List<DateWeather> rows = new ArrayList<DateWeather>();
        dateWeatherRepository.findAll().forEach(rows::add);
        return rows;
    }

    @Transactional
    public DateWeather getById(int id)
    {
        return dateWeatherRepository.findById(id).get();
    }

    @Transactional
    public void saveOrUpdate(DateWeather row)
    {
        dateWeatherRepository.save(row);
    }

    @Transactional
    public void delete(int id)
    {
        dateWeatherRepository.deleteById(id);
    }

    @Transactional
    public Optional<DateWeather> getRowByDate(Date date)
    {
        return dateWeatherRepository.findByDate(DateWeather.dateFormat(date));
    }
}