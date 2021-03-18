package system.access.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import system.access.demo.service.MovementService;
import system.access.demo.model.Movement;
import system.access.demo.util.Validator;

import java.util.Map;

@RestController
public class MovementController {

    private final Logger LOGGER = LoggerFactory.getLogger(MovementController.class);

    public final MovementService movementService;

    @Autowired
    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestParam Map<String, String> requestParams) {
        if (!Validator.validate(requestParams)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Movement movement = Movement.builder().
                roomId(Integer.parseInt(requestParams.get("roomId"))).
                keyId(Integer.parseInt(requestParams.get("keyId"))).
                entrance(Boolean.parseBoolean(requestParams.get("entrance"))).
                build();


        LOGGER.info("Movement is [{}]", movement);

        return movement.getEntrance() ? movementService.enter(movement) : movementService.exit(movement);

    }
}