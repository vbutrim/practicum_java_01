import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author butrim
 */
class ReportBuilder {

    static void readReportTo(
            Path path,
            Report report)
    {
        String sourceStr = readSourceAsString(path);
        String[] lines = sourceStr.split(System.lineSeparator());

        ArrayList<ReportRow> rows = new ArrayList<>();
        for (int i = 1; i < lines.length; ++i) {
            String nextLine = lines[i];
            String[] cells = nextLine.split(",");
            ReportRow nextRow = new ReportRow(
                    Integer.parseInt(cells[0]),
                    Long.parseLong(cells[1])
            );
            rows.add(nextRow);
        }
        report.initializeWith(rows);
    }

    private static String readSourceAsString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
