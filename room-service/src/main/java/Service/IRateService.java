package Service;

import Entity.Rate;
import java.util.List;
import java.util.Optional;

public interface IRateService {

    List<Rate> findAll();

    Optional<Rate> findById(Long id);

    Rate save(Rate rate);

    Rate update(Long id, Rate rateDetails);

    void delete(Long id);

}
