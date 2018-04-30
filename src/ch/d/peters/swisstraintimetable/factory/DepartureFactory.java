package ch.d.peters.swisstraintimetable.factory;

import ch.d.peters.swisstraintimetable.model.Departure;
import ch.d.peters.swisstraintimetable.config.Constants;
import ch.d.peters.swisstraintimetable.interfaces.IAbstractFactory;

import java.text.ParseException;
import java.util.Date;

public class DepartureFactory implements IAbstractFactory<Departure> {
  @Override
  public Departure getInstance() {
    return null;
  }

  @Override
  public Departure getInstance(String string) {
    var items = string.split(";");
    Date date;
    try {
      date = Constants.DATE_FORMAT.parse(items[1]);
    } catch (ParseException e) {
      date = new Date();
    }
    return new Departure(items[0], date, items[2], items[3], items[4]);
  }
}
