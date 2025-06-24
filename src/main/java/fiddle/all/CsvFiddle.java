package fiddle.all;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class CsvFiddle {
  public static void main(String[] args) {
    String csvFilePath = "/test.csv";
    try (CSVReader csvReader =
        new CSVReader(
            new InputStreamReader(
                Objects.requireNonNull(CsvFiddle.class.getResourceAsStream(csvFilePath))))) {
      List<String[]> data = csvReader.readAll();
      String[][] dataArray = new String[data.size()][];
      dataArray = data.toArray(dataArray);
      for (String[] row : dataArray) {
        for (String cell : row) {
          System.out.print(cell + " ");
        }
        System.out.println();
      }
    } catch (IOException | CsvException e) {
      e.printStackTrace();
    }
  }
}
