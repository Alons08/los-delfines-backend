package Service;

import Entity.RoomType;

import java.util.List;
import java.util.Optional;

public interface IRoomTypeService {
    List<RoomType> findAll();
    Optional<RoomType> findById(Long id);
    RoomType save(RoomType roomType);
    RoomType update(Long id, RoomType roomTypeDetails);
    void delete(Long id);
}
