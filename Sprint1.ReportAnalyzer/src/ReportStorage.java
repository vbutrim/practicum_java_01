/**
 * @author butrim
 */
public class ReportStorage {
    static Report report;

    static Report getInstance() {
        if (report == null) {
            report = new Report();
        }
        return report;
    }
}
