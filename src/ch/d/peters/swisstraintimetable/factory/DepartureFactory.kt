package ch.d.peters.swisstraintimetable.factory

import ch.d.peters.swisstraintimetable.model.Departure
import ch.d.peters.swisstraintimetable.config.Constants
import ch.d.peters.swisstraintimetable.interfaces.IAbstractFactory

import java.text.ParseException
import java.util.Date

/**
 * Concrete factory for departures.
 *
 * @author Daniel Peters
 * @version 1.2
 */
class DepartureFactory : IAbstractFactory<Departure> {
  override fun getInstance(): Departure {
    TODO("Not implemented")
  }

  override fun getInstance(string: String): Departure {
    val items = string.split(";")
    val date = try {
      Constants.DATE_FORMAT.parse(items[1])
    } catch (e: ParseException) {
      Date()
    }
    return Departure(items[0], date, items[2], items[3], items[4])
  }
}
