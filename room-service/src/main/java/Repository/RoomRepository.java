package Repository;

import java.util.List;
import Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByState(RoomState state);

    List<Room> findByRoomType_Id(Long roomTypeId);
}