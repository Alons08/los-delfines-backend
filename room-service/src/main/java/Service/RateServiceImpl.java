package Service;

import Entity.Rate;
import Repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RateServiceImpl implements IRateService {

    private final RateRepository rateRepository;

    @Override
    public List<Rate> findAll() {
        return rateRepository.findAll();
    }

    @Override
    public Optional<Rate> findById(Long id) {
        return rateRepository.findById(id);
    }

    @Override
    public Rate save(Rate rate) {
        return rateRepository.save(rate);
    }

    @Override
    public Rate update(Long id, Rate rateDetails) {
        return rateRepository.findById(id)
                .map(rate -> {
                    rate.setMode(rateDetails.getMode());
                    rate.setBasePrice(rateDetails.getBasePrice());
                    rate.setCardPrice(rateDetails.getCardPrice());
                    return rateRepository.save(rate);
                }).orElseThrow(() -> new RuntimeException("Rate not found"));
    }

    @Override
    public void delete(Long id) {
       rateRepository.deleteById(id);
    }
}
