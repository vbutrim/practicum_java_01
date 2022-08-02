import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joda.time.Instant;

@AllArgsConstructor
@Getter
public class ReservationRequest {
    private final int garageId;
    private final Instant from;
    private final Instant to;
}
