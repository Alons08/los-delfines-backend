package Service;

import Entity.Room;
import Entity.RoomState;

import java.util.List;
import java.util.Optional;

public interface IRoomService {

    List<Room> findAll();
    Optional<Room> findById(Long id);
    Room save(Room room);
    Room update(Long id, Room roomDetails);
    void delete(Long id);

    List<Room> findByState(RoomState state);
    List<Room> findByRoomType(Long roomTypeId);
}
