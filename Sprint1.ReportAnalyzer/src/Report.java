import java.util.ArrayList;

/**
 * @author butrim
 */
public class Report {
    ArrayList<ReportRow> rows;

    Report() {
    }

    void initializeWith(ArrayList<ReportRow> rows) {
        this.rows = rows;
    }

    /**
     * @return -1 if report is not initialized
     */
    int getPublicationWithMaxImpressionsIds() {
        if (rows == null) {
            return -1;
        }

        int publicationId = -1;
        long maxImpressions = 0;

        for (ReportRow row : rows) {
            if (row.impressions > maxImpressions) {
                maxImpressions = row.impressions;
                publicationId = row.publicationId;
            }
        }

        return publicationId;
    }
}
