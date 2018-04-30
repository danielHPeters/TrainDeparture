package ch.d.peters.swisstraintimetable.interfaces;

public interface IAbstractFactory<T> {
  T getInstance();

  T getInstance(String string);
}
