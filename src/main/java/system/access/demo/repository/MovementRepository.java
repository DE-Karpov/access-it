package system.access.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import system.access.demo.model.Movement;


import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Integer> {

    Optional<Movement> findByKeyIdAndRoomId(Integer keyId, Integer roomId);
}
