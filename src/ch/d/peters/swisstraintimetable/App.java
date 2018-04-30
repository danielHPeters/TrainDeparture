package ch.d.peters.swisstraintimetable;

import ch.d.peters.swisstraintimetable.config.Constants;
import ch.d.peters.swisstraintimetable.factory.DepartureFactory;
import ch.d.peters.swisstraintimetable.model.Departure;
import ch.d.peters.swisstraintimetable.storage.CsvHandler;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Daniel Peters
 * @version 1.2
 */
public class App {
  private enum Commands {
    TIME, PLATFORM, CITY, HELP, QUIT, ILLEGAL
  }

  private final Map<Commands, String> menu = Map.of(
      Commands.TIME, "Suche nach Abfahrtszeit",
      Commands.PLATFORM, "Suche nach Gleis und Abfahrtszeit",
      Commands.CITY, "Suche alle Abfahrten nach Stadt",
      Commands.HELP, "Diesen hilfe text anzeigen",
      Commands.QUIT, "Beenden"
  );

  private final Scanner scan = new Scanner(System.in);
  private List<Departure> departures;

  private App() {
    var loader = new CsvHandler<>("resources/abfahrten_zhb", new DepartureFactory());
    departures = loader.get();
    departures.sort(Collections.reverseOrder());
  }

  /**
   * Reads user input
   *
   * @return user input as string
   */
  private String scanUserInput() {
    return scan.nextLine();
  }

  /**
   * Displays the menu
   */
  private void showMenu() {
    menu.forEach((key, value) -> System.out.println(key + " - " + value));
  }

  /**
   * Shows all infos of departure objects in an ArrayList
   *
   * @param depart ArrayList with Departure objects
   */
  private void showDepartures(List<Departure> depart) {
    depart.forEach(Departure::showAllInfos);
  }

  /**
   * Searches for departures with departure times greater than the user
   * specified departure time and return max. 20 departure objects in an
   * ArrayList
   *
   * @param time user specified time
   * @return ArrayList with filtered departures
   */
  private List<Departure> getDepartures(Date time) {
    return departures.stream()
        .filter(departure -> departure.getTime().after(time))
        .limit(20)
        .collect(Collectors.toList());
  }

  /**
   * Returns two departure objects which leave on a specified platform and
   * after the specified time
   *
   * @param platform user specified platform name
   * @param time     user specified time
   * @return ArrayList with filtered departures
   */
  private List<Departure> getPlatformDepartures(String platform, Date time) {
    var parsedPlatform = platform.trim().toLowerCase();
    return departures.stream()
        .filter(departure -> departure.getPlatform().toLowerCase().contains(parsedPlatform)
            && departure.getTime().after(time))
        .collect(Collectors.toList());
  }

  /**
   * Returns all Departure objects which stop at the user specified city as an
   * ArrayList
   *
   * @param city user specified city name
   * @return ArrayList with filtered departures
   */
  private List<Departure> getDeparturesToCity(String city) {
    var parsedCity = city.trim().toLowerCase();
    return departures.stream()
        .filter(departure ->
            departure.getVia().toLowerCase().contains(parsedCity)
                || departure.getDestination().toLowerCase().equals(parsedCity))
        .collect(Collectors.toList());
  }

  private void departuresByTime() {
    System.out.println("Bitte geben sie eine Abfahrtszeit an (z.B. 8:30):");

    var input = scanUserInput();
    try {
      var dep = getDepartures(Constants.DATE_FORMAT.parse(input));
      showDepartures(dep);
    } catch (ParseException e) {
      System.out.println("Ungültiges format.");
    }
  }

  private void departuresByPlatform() {
    String input;
    String time;
    List<Departure> dep;

    System.out.println("Bitte geben sie ein Gleis an (z.B. 43):");
    input = scanUserInput();
    System.out.println("Bitte geben sie eine Zeit an (z.B. 7:30):");
    time = scanUserInput();
    try {
      dep = getPlatformDepartures(input, Constants.DATE_FORMAT.parse(time));
      showDepartures(dep);
    } catch (ParseException e) {
      System.out.println("Ungüliges Format.");
    }
  }

  private void departuresToLocation() {
    String input;
    List<Departure> dep;

    System.out.println("Bitte geben sie einen Zielort an (z.B. Zürich HB):");
    input = scanUserInput();
    dep = getDeparturesToCity(input);
    showDepartures(dep);
  }

  /**
   * Starts the menu and reacts on user input. Also shows hints to user
   */
  private void runMenu() {
    boolean running = true;
    System.out.println("Bitte geben sie einen Befehl ein.\nMit 'HELP' erhalten sie die Liste der Befehle.");
    // Run until user inputs exit command
    while (running) {

      Commands command;
      try {
        command = Commands.valueOf(scanUserInput().trim().toUpperCase());
      } catch (IllegalArgumentException e) {
        command = Commands.ILLEGAL;
      }

      // User selects action
      switch (command) {
        case TIME:
          departuresByTime();
          break;
        case PLATFORM:
          departuresByPlatform();
          break;
        case CITY:
          departuresToLocation();
          break;
        case HELP:
          showMenu();
          break;
        case QUIT:
          System.out.println("Adieu");
          running = false;
          break;
        default:
          System.out.println("Befehl nicht gefunden!");
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
    var admin = new App();
    admin.runMenu();
  }
}
