package ch.d.peters.swisstraintimetable

import ch.d.peters.swisstraintimetable.config.Constants
import ch.d.peters.swisstraintimetable.factory.DepartureFactory
import ch.d.peters.swisstraintimetable.model.Departure
import ch.d.peters.swisstraintimetable.storage.CsvHandler

import java.util.Date
import java.util.Scanner
import java.text.ParseException

/**
 * @author Daniel Peters
 * @version 1.2
 */
class App {
  companion object {
    /**
     * Start the Program
     *
     * @param args the command line arguments
     */
    @JvmStatic
    fun main(args: Array<String>) {
      val admin = App()
      admin.run()
    }
  }

  private enum class Commands {
    TIME, PLATFORM, CITY, HELP, QUIT, ILLEGAL
  }

  private val menu = mapOf(
      Commands.TIME to "Suche nach Abfahrtszeit",
      Commands.PLATFORM to "Suche nach Gleis und Abfahrtszeit",
      Commands.CITY to "Suche alle Abfahrten nach Stadt",
      Commands.HELP to "Diesen hilfe text anzeigen",
      Commands.QUIT to "Beenden"
  )
  private val loader = CsvHandler("resources/abfahrten_zhb", DepartureFactory())
  private val scan = Scanner(System.`in`)
  private var departures: List<Departure>
  private var running = true

  init {
    departures = loader.get()
  }

  /**
   * Reads user input
   *
   * @return user input as string
   */
  private fun scanUserInput(): String {
    return scan.nextLine()
  }

  /**
   * Displays the menu
   */
  private fun showHelp() {
    menu.forEach { key, value -> System.out.println(key.toString() + " - " + value) }
  }

  /**
   * Shows all infos of departure objects in an ArrayList
   *
   * @param depart ArrayList with Departure objects
   */
  private fun showDepartures(depart: List<Departure>) {
    depart.forEach(Departure::showAllInfos)
  }

  /**
   * Searches for departures with departure times greater than the user
   * specified departure time and return max. 20 departure objects in an
   * ArrayList
   *
   * @param time user specified time
   * @return ArrayList with filtered departures
   */
  private fun getDepartures(time: Date): List<Departure> {
    return departures.filter { departure -> departure.time.after(time) }.take(20)
  }

  /**
   * Returns two departure objects which leave on a specified platform and
   * after the specified time
   *
   * @param platform user specified platform name
   * @param time     user specified time
   * @return ArrayList with filtered departures
   */
  private fun getPlatformDepartures(platform: String, time: Date): List<Departure> {
    val plat = platform.trim().toLowerCase()
    return departures
        .filter { departure ->
          departure.platform.toLowerCase().contains(plat)
              && departure.time.after(time)
        }.take(2)
  }

  /**
   * Returns two Departure objects which stop at the user specified city as an
   * ArrayList
   *
   * @param city user specified city name
   * @return ArrayList with filtered departures
   */
  private fun getDeparturesToCity(city: String): List<Departure> {
    val cit = city.trim().toLowerCase()
    return departures
        .filter { departure ->
          departure.via.toLowerCase().contains(cit)
              || departure.destination.toLowerCase() == cit
        }.take(2)
  }

  private fun departuresByTime() {
    System.out.println("Bitte geben sie eine Abfahrtszeit an (z.B. 8:30):")

    val input = scanUserInput()
    try {
      val dep = getDepartures(Constants.DATE_FORMAT.parse(input))
      showDepartures(dep)
    } catch (e: ParseException) {
      System.out.println("Ungültiges format.")
    }
  }

  private fun departuresByPlatform() {
    System.out.println("Bitte geben sie ein Gleis an (z.B. 43):")
    val input = scanUserInput()
    System.out.println("Bitte geben sie eine Zeit an (z.B. 7:30):")
    val time = scanUserInput()
    try {
      val dep = getPlatformDepartures(input, Constants.DATE_FORMAT.parse(time))
      showDepartures(dep)
    } catch (e: ParseException) {
      System.out.println("Ungüliges Format.")
    }
  }

  private fun departuresToLocation() {
    System.out.println("Bitte geben sie einen Zielort an (z.B. Zürich HB):")
    val input = scanUserInput()

    showDepartures(getDeparturesToCity(input))
  }

  private fun quit() {
    System.out.println("Adieu")
    running = false
  }

  /**
   * Starts the menu and reacts on user input. Also shows hints to user
   */
  private fun run() {
    System.out.println("Bitte geben sie einen Befehl ein.\nMit 'HELP' erhalten sie die Liste der Befehle.")
    // Run until user inputs exit command
    while (running) {

      val command = try {
        Commands.valueOf(scanUserInput().trim().toUpperCase())
      } catch (e: IllegalArgumentException) {
        Commands.ILLEGAL
      }

      // User selects action
      when (command) {
        Commands.TIME -> departuresByTime()
        Commands.PLATFORM -> departuresByPlatform()
        Commands.CITY -> departuresToLocation()
        Commands.HELP -> showHelp()
        Commands.QUIT -> quit()
        else -> System.out.println("Befehl nicht gefunden!")
      }
    }
  }
}
