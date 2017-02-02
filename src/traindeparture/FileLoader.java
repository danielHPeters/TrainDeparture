package traindeparture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @version 31.10.20164
 * @author Daniel Peters
 */
public class FileLoader implements DataLoader {

    /**
     *
     * @return
     */
    @Override
    public List<Departure> loadList() {

        List<Departure> departures = new ArrayList<>();
        Departure dep;
        BufferedReader reader;
        File file;
        String fileName, line;
        String[] items;

        fileName = "abfahrten_zhb.csv";
        try {
            file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {

                items = line.split(";");
                dep = new Departure(items[0], items[1], items[2], items[3], items[4]);
                departures.add(dep);

            }

        } catch (IOException e) {
            System.out.println("IO error");
        }
        return departures;
    }

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
