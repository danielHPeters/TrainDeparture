package ch.d.peters.swisstraintimetable.model

import java.io.Serializable
import java.util.Date

/**
 * @author Daniel Peters
 * @version 1.2
 */
data class Departure(
    var trainNo: String,
    var time: Date,
    var destination: String,
    var via: String,
    val platform: String
) : Comparable<Departure>, Serializable {

  fun showAllInfos() {
    val info = "Abfahrtinfo\nZug: $trainNo\nAbfahrt: $time\nZiel: $destination\nVia: $via\nPlatform: $platform\n"
    System.out.println(info)
  }

  /**
   * Compares the departure time of this Departure object to another
   *
   * @param other Departure object to be compared to
   * @return integer for sorting purposes
   */
  override fun compareTo(other: Departure): Int {
    return other.time.compareTo(time)
  }
}
