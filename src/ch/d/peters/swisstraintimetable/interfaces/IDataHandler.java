package ch.d.peters.swisstraintimetable.interfaces;

import java.util.List;

/**
 * Data
 *
 * @author Daniel Peters
 * @version 1.0
 */
public interface IDataHandler<T> {
  List<T> get();

  void put(List<T> element);

  void put(T element);
}
