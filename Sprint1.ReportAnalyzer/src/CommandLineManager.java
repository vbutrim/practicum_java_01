import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author butrim
 */
class CommandLineManager {
    static final int EXIT_COMMAND = 0;
    static final Path REPORT_PATH = Path.of("./resources/report");

    Scanner scanner;

    CommandLineManager() {
        this.scanner = new Scanner(System.in);
    }

    void printMenuAndHandleCommand() {
        while (true) {
            printMenu();

            int command = scanner.nextInt();

            if (EXIT_COMMAND != command) {
                handleCommand(command);
            } else {
                break;
            }
        }
    }

    void printMenu() {
        System.out.println("---------------------------------------------------------");
        System.out.println("Введите комманду");
        System.out.println("1 -- прочитать отчет");
        System.out.println("2 -- найти публикацию с максимальным количеством показов");
        System.out.println(EXIT_COMMAND + " -- выход");
        System.out.println("---------------------------------------------------------");
    }

    void handleCommand(int command) {
        if (command == 1) {
            ReportBuilder.readReportTo(REPORT_PATH, ReportStorage.getInstance());
            System.out.println("Отчет успешно подгружен");
        } else if (command == 2) {
            int publicationWithMaxImpressionsId = ReportStorage.getInstance().getPublicationWithMaxImpressionsIds();

            if (publicationWithMaxImpressionsId == -1) {
                System.out.println("Отчет не подгружен");
            } else {
                System.out.println("ID публикации с максимальным кол-вом показов: " + publicationWithMaxImpressionsId);
            }
        } else {
            System.out.println("Неизвестная комманда " + command);
        }
    }
}
