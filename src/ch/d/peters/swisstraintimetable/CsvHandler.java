package ch.d.peters.swisstraintimetable;

import ch.d.peters.swisstraintimetable.interfaces.IAbstractFactory;
import ch.d.peters.swisstraintimetable.interfaces.IDataHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Peters
 * @version 1.2
 */
public class CsvHandler<T> implements IDataHandler<T> {
  private static final String EXTENSION = ".csv";
  private String file;
  private IAbstractFactory<T> factory;

  public CsvHandler(String fileName, IAbstractFactory<T> abstractFactory) {
    file = fileName;
    factory = abstractFactory;
  }

  @Override
  public List<T> get() {
    var list = new ArrayList<T>();
    try (var reader = new BufferedReader(new FileReader(file + EXTENSION))) {
      reader.lines().forEach(line -> list.add(factory.getInstance(line)));
    } catch (IOException e) {
      System.out.println("IO error");
    }
    return list;
  }

  @Override
  public void put(List<T> element) {

  }

  @Override
  public void put(T element) {

  }
}
