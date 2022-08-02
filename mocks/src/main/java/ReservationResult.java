import external.GarageDao;
import external.ReservationDao;
import lombok.Getter;

@Getter
public abstract class ReservationResult {
    private final GarageDao.Garage garage;

    private ReservationResult(GarageDao.Garage garage) {
        this.garage = garage;
    }

    @Getter
    public static class Success extends ReservationResult {
        private final ReservationDao.Reservation reservation;

        Success(GarageDao.Garage garage, ReservationDao.Reservation reservation) {
            super(garage);
            this.reservation = reservation;
        }
    }

    @Getter
    public static class AlreadyBooked extends ReservationResult {
        private final ReservationRequest reservationRequest;

        AlreadyBooked(GarageDao.Garage garage, ReservationRequest reservationRequest) {
            super(garage);
            this.reservationRequest = reservationRequest;
        }
    }
}
