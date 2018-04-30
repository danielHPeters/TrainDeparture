package ch.d.peters.swisstraintimetable.interfaces

/**
 * Data handler interface.
 *
 * @author Daniel Peters
 * @version 1.0
 */
interface IDataHandler<T> {
  fun get(): List<T>

  fun put(elements: List<T>)

  fun put(element: T)
}
