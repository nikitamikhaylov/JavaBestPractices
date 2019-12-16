package hello.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DateMaxRateRepository extends CrudRepository<DateMaxRate, Integer> {
    Optional<DateMaxRate> findByDate(String dateString);
}