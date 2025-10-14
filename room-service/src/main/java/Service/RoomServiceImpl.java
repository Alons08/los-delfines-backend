package Service;

import Entity.Room;
import Entity.RoomState;
import Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room update(Long id, Room roomDetails) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setNumber(roomDetails.getNumber());
                    room.setCapacity(roomDetails.getCapacity());
                    room.setDescription(roomDetails.getDescription());
                    room.setState(roomDetails.getState());
                    room.setRoomType(roomDetails.getRoomType());
                    return roomRepository.save(room);
                }).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Override
    public void delete(Long id) {
      roomRepository.deleteById(id);
    }

    @Override
    public List<Room> findByState(RoomState state) {
        return roomRepository.findByState(state);
    }

    @Override
    public List<Room> findByRoomType(Long roomTypeId) {
        return roomRepository.findByRoomType_Id(roomTypeId);
    }
}
