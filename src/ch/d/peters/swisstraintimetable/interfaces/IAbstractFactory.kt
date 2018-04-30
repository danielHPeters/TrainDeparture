package ch.d.peters.swisstraintimetable.interfaces

/**
 * Abstract factory interface.
 *
 * @author Daniel Peters
 * @version 1.0
 */
interface IAbstractFactory<T> {
  fun getInstance(): T

  fun getInstance(string: String): T
}
