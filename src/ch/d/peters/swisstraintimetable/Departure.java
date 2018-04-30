package ch.d.peters.swisstraintimetable;

import java.util.Date;

/**
 * @author Daniel Peters
 * @version 1.2
 */
public class Departure implements Comparable<Departure> {
  private String trainNo;
  private Date time;
  private String destination;
  private String via;
  private String platform;

  /**
   * Default constructor.
   * Initializes all object attributes
   *
   * @param trainNo     initial train number
   * @param time        initial departure time
   * @param destination initial destination
   * @param via         inital via
   * @param platform    initial platform
   */
  public Departure(String trainNo, Date time, String destination, String via, String platform) {
    this.trainNo = trainNo;
    this.time = time;
    this.destination = destination;
    this.via = via;
    this.platform = platform;
  }

  public String getTrainNo() {
    return trainNo;
  }

  public void setTrainNo(String trainNo) {
    this.trainNo = trainNo;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getVia() {
    return via;
  }

  public void setVia(String via) {
    this.via = via;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public void showAllInfos() {
    String departure = "Abfahrtinfo\n"
        + "Zug: " + trainNo + "\n"
        + "Abfahrt: " + time + "\n"
        + "Ziel: " + destination + "\n"
        + "Via: " + via + "\n"
        + "Platform: " + platform + "\n";
    System.out.println(departure);
  }

  /**
   * Compares the departure time of this Departure object to another
   *
   * @param o Departure object to be compared to
   * @return integer for sorting purposes
   */
  @Override
  public int compareTo(Departure o) {
    return o.getTime().compareTo(time);
  }

  @Override
  public String toString() {
    return "Departure{" +
        "trainNo='" + trainNo + '\'' +
        ", time=" + time +
        ", destination='" + destination + '\'' +
        ", via='" + via + '\'' +
        ", platform='" + platform + '\'' +
        '}';
  }
}
