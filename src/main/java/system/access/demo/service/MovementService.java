package system.access.demo.service;

import org.springframework.http.ResponseEntity;
import system.access.demo.model.Movement;

public interface MovementService {

    ResponseEntity<?> enter(Movement movement);

    ResponseEntity<?> exit(Movement movement);
}
