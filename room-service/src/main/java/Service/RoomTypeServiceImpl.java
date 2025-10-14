package Service;

import Entity.RoomType;
import Repository.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements  IRoomTypeService{

    private final RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    @Override
    public Optional<RoomType> findById(Long id) {
        return roomTypeRepository.findById(id);
    }

    @Override
    public RoomType save(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    @Override
    public RoomType update(Long id, RoomType roomTypeDetails) {
        return roomTypeRepository.findById(id)
                .map(roomType -> {
                    roomType.setName(roomTypeDetails.getName());
                    roomType.setRateId(roomTypeDetails.getRateId());
                    return roomTypeRepository.save(roomType);
                }).orElseThrow(() -> new RuntimeException("RoomType not found"));
    }

    @Override
    public void delete(Long id) {
        roomTypeRepository.deleteById(id);
    }
}
