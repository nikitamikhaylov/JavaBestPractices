package sbt.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DateWeatherRepository extends CrudRepository<DateWeather, Integer> {
    Optional<DateWeather> findByDate(String dateString);
}