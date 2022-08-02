package external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.time.Instant;

public class ReservationDao {

    /**
     * @return reservationId if success
     * @throws AlreadyExistException if not
     */
    public Reservation save(int garageId, Instant from, Instant to) throws AlreadyExistException {
        return new Reservation(0, garageId, from, to);
    }

    public Reservation findById(int reservationId) {
        return new Reservation(0, 0, Instant.EPOCH, Instant.EPOCH);
    }

    public Reservation update(Reservation reservation) {
        return reservation;
    }

    public static final class AlreadyExistException extends Exception {

    }

    @AllArgsConstructor
    @Getter
    public static class Reservation {
        private final int id;
        private final int garageId;
        private final Instant from;
        private final Instant to;
    }
}
