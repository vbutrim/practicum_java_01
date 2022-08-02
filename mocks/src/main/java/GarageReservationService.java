import external.GarageDao;
import external.ReservationDao;
import lombok.AllArgsConstructor;
import org.joda.time.Instant;

@AllArgsConstructor
public class GarageReservationService {
    private final GarageDao garageDao;
    private final ReservationDao reservationDao;

    public ReservationResult makeReservation(ReservationRequest request) {
        GarageDao.Garage garage = garageDao.findGarageById(request.getGarageId());

        try {
            ReservationDao.Reservation reservation = reservationDao
                    .save(garage.getId(), request.getFrom(), request.getTo());
            return new ReservationResult.Success(garage, reservation);
        } catch (ReservationDao.AlreadyExistException e) {
            return new ReservationResult.AlreadyBooked(garage, request);
        }
    }

    public boolean extendReservation(int reservationId, Instant until) {
        ReservationDao.Reservation reservation = reservationDao.findById(reservationId);
        // check that we can reserve until
        reservationDao.update(reservation);
        return true;
    }
}
