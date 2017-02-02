package traindeparture;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @version 31.10.2016
 * @author Daniel Peters
 */
public class TrainDepartureAdmin {

    /**
     *
     */
    private final String[] menuItems = {
        "Suche nach Abfahrtszeit",
        "Suche nach Gleis und Abfahrtszeit",
        "Suche alle Abfahrten nach Stadt",
        "Beenden"
    };

    /**
     *
     */
    private DataLoader loader;

    /**
     * Format for Windows because of German special chars
     */
    private final Scanner scan = new Scanner(System.in, "ISO-8859-1");

    /**
     *
     */
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    /**
     * Store all Departure objects from file here
     */
    private List<Departure> departures;

    /**
     * Default Constructor
     */
    public TrainDepartureAdmin() {
        this.loader = new FileLoader();
        this.departures = loader.loadList();
        Collections.sort(departures, Collections.reverseOrder());
    }

    /**
     * Reads user input
     *
     * @return user input as string
     */
    public String scanUserInput() {
        String input = this.scan.nextLine();
        return input;
    }

    /**
     * Pauses the program until enter is pressed
     */
    public void pause() {
        try {
            System.out.println("Mit Eingabetaste zurück zum Menü.");
            System.in.read();
        } catch (IOException ex) {
            System.out.println("Fehler im programm");
        }
    }

    /**
     * Displays the menu
     */
    public void showMenuItems() {
        for (int i = 0; i < this.menuItems.length; i++) {
            System.out.println((i + 1) + ": " + this.menuItems[i]);
        }
    }

    /**
     * Shows all infos of departure objects in an ArrayList
     *
     * @param depart ArrayList with Departure objects
     */
    private void showDepartures(List<Departure> depart) {
        depart.forEach((departure) -> departure.showAllInfos());
    }

    /**
     * Searches for departures with departure times greater than the user
     * specified departure time and return max. 20 departure objects in an
     * ArrayList
     *
     * @param time user specified time
     * @return ArrayList with filtered departures
     */
    public List<Departure> getDepartures(String time) {
        ArrayList<Departure> dep = new ArrayList();
        Date searchTime;
        try {
            searchTime = this.format.parse(time);
            this.departures.forEach((departure) -> {
                try {
                    Date depTime = this.format.parse(departure.getDepartureTime());
                    if (depTime.after(searchTime) && dep.size() < 20) {
                        dep.add(departure);
                    }
                } catch (ParseException ex) {
                    System.out.println("Ungültiges Zeitformat");
                }
            });
        } catch (ParseException ex) {
            System.out.println("Ungültiges Zeitformat");
        }

        return dep;
    }

    /**
     * Returns two departure objects which leave on a specified platform and
     * after the specified time
     *
     * @param platform user specified platform name
     * @param time user specified time
     * @return ArrayList with filtered departures
     */
    public List<Departure> getPlatformDepartures(String platform, String time) {
        ArrayList<Departure> dep = new ArrayList();
        Date searchTime;
        try {
            searchTime = this.format.parse(time);
            this.departures.forEach((departure) -> {
                try {
                    Date depTime = this.format.parse(departure.getDepartureTime());
                    if (depTime.after(searchTime) && departure.hasPlatform(platform) && dep.size() < 2) {
                        dep.add(departure);
                    }
                } catch (ParseException ex) {
                    System.out.println("Ungültiges Zeitformat");
                }
            });
        } catch (ParseException ex) {
            System.out.println("Ungültiges Zeitformat");
        }

        return dep;
    }

    /**
     * Returns all Departure objects which stop at the user specified city as an
     * ArrayList
     *
     * @param city user specified city name
     * @return ArrayList with filtered departures
     */
    public List<Departure> getDeparturesToCity(String city) {

        List<Departure> depToCity = new ArrayList();

        this.departures.forEach((departure) -> {

            if (departure.hasDestination(city)) {
                depToCity.add(departure);
            } else if (departure.hasVia(city)) {
                depToCity.add(departure);
            }

        });

        return depToCity;
    }

    /**
     *
     */
    public void departuresByTime() {
        List<Departure> dep;
        String input;
        System.out.println("Bitte geben sie eine Abfahrtszeit an (z.B. 8:30):");
        input = scanUserInput();
        dep = getDepartures(input);
        showDepartures(dep);
        pause();
    }

    /**
     *
     */
    public void departuresByPlatform() {

        String input;
        String time;
        List<Departure> dep;

        System.out.println("Bitte geben sie ein Gleis an (z.B. 43):");
        input = scanUserInput();
        System.out.println("Bitte geben sie eine Zeit an (z.B. 7:30):");
        time = scanUserInput();
        dep = getPlatformDepartures(input, time);
        showDepartures(dep);
        pause();
    }

    /**
     *
     */
    public void departuresToLocation() {

        String input;
        List<Departure> dep;

        System.out.println("Bitte geben sie einen Zielort an (z.B. Zürich HB):");
        input = scanUserInput();
        dep = getDeparturesToCity(input);
        showDepartures(dep);
        pause();
    }

    /**
     * Starts the menu and reacts on user input. Also shows hints to user
     */
    public void runMenu() {

        String input;
        boolean running = true;

        // Run until user inputs exit command
        while (running) {
            System.out.println("Bitte wählen sie eine Aktion aus "
                    + "(Aktionsnummer eingeben):");
            showMenuItems();
            input = scanUserInput();
            // User selects action
            switch (input) {
                case "1":
                    departuresByTime();
                    break;
                case "2":
                    departuresByPlatform();
                    break;
                case "3":
                    departuresToLocation();
                    break;
                case "4":
                    System.out.println("Adieu");
                    running = false;
                    break;
                default:
                    System.out.println("Befehl nicht gefunden!");
                    pause();
                    break;
            }
        }
    }

    /**
     * Start the Program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TrainDepartureAdmin admin = new TrainDepartureAdmin();
        admin.runMenu();
    }
}
