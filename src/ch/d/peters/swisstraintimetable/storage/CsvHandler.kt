package ch.d.peters.swisstraintimetable.storage

import ch.d.peters.swisstraintimetable.interfaces.IAbstractFactory
import ch.d.peters.swisstraintimetable.interfaces.IDataHandler

import java.io.BufferedReader
import java.io.FileReader

/**
 * @author Daniel Peters
 * @version 1.2
 */
class CsvHandler<T>(
    private val file: String,
    private val factory: IAbstractFactory<T>
) : IDataHandler<T> {
  companion object {
    const val EXTENSION = ".csv"
  }


  override fun get(): List<T> {
    val list = ArrayList<T>()
    BufferedReader(FileReader(file + EXTENSION)).use {
      it.lines().forEach { line -> list.add(factory.getInstance(line)) }
    }
    return list
  }

  override fun put(elements: List<T>) {

  }

  override fun put(element: T) {

  }
}
