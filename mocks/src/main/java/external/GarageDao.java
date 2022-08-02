package external;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class GarageDao {

    public Garage findGarageById(int id) {
        return new Garage(id);
    }

    @AllArgsConstructor
    @Getter
    public static class Garage {
        private final int id;
    }
}
