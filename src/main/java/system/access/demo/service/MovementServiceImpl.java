package system.access.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import system.access.demo.repository.MovementRepository;
import system.access.demo.model.Movement;

import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService {

    private final Logger LOGGER = LoggerFactory.getLogger(MovementServiceImpl.class);

    private final MovementRepository repository;

    @Autowired
    public MovementServiceImpl(MovementRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<?> enter(Movement movement) {
        if (isAbleToMove(movement)) {
            repository.save(movement);
            LOGGER.info("You have entered to the room which number is: [{}]", movement.getRoomId());
            return ResponseEntity.ok().build();
        } else {
            LOGGER.error("You are not able to make a movement");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    public ResponseEntity<?> exit(Movement movement) {
        if (isAbleToMove(movement)) {
            movement.setRoomId(0);
            repository.save(movement);
            LOGGER.info("You have exited from the room");
            return ResponseEntity.ok().build();
        } else {
            LOGGER.error("You are not able to make a movement");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private boolean isAbleToMove(Movement movement) {
        Optional<Movement> optionalMovement;

        if (movement.getEntrance()) {
            optionalMovement = repository.findById(movement.getKeyId());
            return optionalMovement.map(value -> value.getRoomId().equals(0)).orElse(true);
        } else {
            optionalMovement = repository.findByKeyIdAndRoomId(movement.getKeyId(), movement.getRoomId());
            return optionalMovement.isPresent();
        }
    }
}