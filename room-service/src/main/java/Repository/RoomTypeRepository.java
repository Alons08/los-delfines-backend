package Repository;

import Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.Entity;

@Entity
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}
