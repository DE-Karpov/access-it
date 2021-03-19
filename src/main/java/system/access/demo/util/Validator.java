package system.access.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.Map;

public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    public static boolean validate(Map<String, String> requestParams) {
        if (isInboundParametersValid(requestParams)) {
            int roomId = Integer.parseInt(requestParams.get("roomId"));
            int keyId = Integer.parseInt(requestParams.get("keyId"));
            if (isInboundRoomIdAndKeyIdInLimit(roomId, keyId)) {
                if (isExactDivision(roomId, keyId)) {
                    return true;
                } else {
                    LOGGER.error("keyId is not divided properly");
                }
            } else {
                LOGGER.error("keyId: [{}] or roomId: [{}] is out of range", keyId, roomId);
            }
        }
        return false;
    }

    private static boolean isInboundParametersValid(Map<String, String> requestParams) {
        try {
            Integer.parseInt(requestParams.get("roomId"));
            Integer.parseInt(requestParams.get("keyId"));
            String entrance = requestParams.get("entrance");

            if (!(entrance.equals("true") || entrance.equals("false"))) {
                throw new InvalidParameterException();
            }
            return true;
        } catch (NullPointerException e) {
            LOGGER.error("One or more parameters is null");
            return false;
        } catch (NumberFormatException e) {
            LOGGER.error("Either roomId: [{}] or keyId: [{}] is not a number", requestParams.get("roomId"), requestParams.get("keyId"));
            return false;
        } catch (InvalidParameterException e) {
            LOGGER.error("Entrance parameter is not a boolean type: [{}]", requestParams.get("entrance"));
            return false;
        }
    }

    private static boolean isInboundRoomIdAndKeyIdInLimit(int roomId, int keyId) {
        return roomId <= 5 && roomId >= 1 && keyId <= 10000 && keyId >= 1;
    }

    private static boolean isExactDivision(int roomId, int keyId) {
        return keyId % roomId == 0;
    }

}
