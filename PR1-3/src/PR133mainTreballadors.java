import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class PR133mainTreballadors {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + "/data/";
        String fileName = "PR133treballadors.csv";
        String filePath = basePath + fileName;

        System.out.println("");

        // Array amb cada linia del document
        List<String> csv = UtilsCSV.read(filePath);

        // Scanner lee ID
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("ID del treballador: ");

            String id = sc.next();
            int numLiniaEmpleado = UtilsCSV.getLineNumber(csv, "Id", id);
            
            if (numLiniaEmpleado == -1) {
                throw new Exception("User not found");
            } else {
                sc.nextLine();
                // Sacar campos
                String[] columnes = UtilsCSV.getKeys(csv);

                System.out.print("Camp a canviar: ");
                String camp_canviar = sc.next();

                // Comprovar que existe el campo
                boolean campExists = false;
                for (String cmp: columnes) {
                    if (cmp.equals(camp_canviar)) {
                        campExists = true;
                        break;
                    }
                }

                if (!campExists) {
                    throw new Exception("Camp not found");
                }
                sc.nextLine();
                System.out.print("Nou valor: ");
                String new_camp = sc.next();
                // Hacer modificaciones
                UtilsCSV.update(csv, numLiniaEmpleado, camp_canviar, new_camp);                
                UtilsCSV.write(filePath, csv);
                System.out.println("Archivo modificado");

                System.out.println("\nDades del CSV:");
                UtilsCSV.list(csv);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

}

class UtilsCSV {
    // Reads a CSV file into List<String>
    static List<String> read (String filePath) {
        List<String> result = null;
        try {
            result = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) { e.printStackTrace(); }
        return result;
    }

    // Writes a CSV file from List<String>
    static void write(String path, List<String> csvLines) {
        Path out = Paths.get(path);
        try {
            Files.write(out, csvLines, Charset.defaultCharset());
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Transforms a comma separated string into an array
    static String[] getLineArray (String line) {
        return line.split(",");
    }

    // Returns the keys in the first line of the CSV data
    static String[] getKeys(List<String> csvLines) {
        return getLineArray(csvLines.get(0));
    }

    // Gets the column position of one key
    static int csvGetColumnPosition(List<String> csvLines, String column) {
        String[] keys = getKeys(csvLines);
        return Arrays.asList(keys).indexOf(column);
    }

    // Gets all the column data of one key
    static String[] getColumnData(List<String> csvLines, String column) {
        String[] result = new String[csvLines.size()];
        int columnPosition = csvGetColumnPosition(csvLines, column);
        for (int cnt = 0; cnt < csvLines.size(); cnt = cnt + 1) {
            String[] lineArray = getLineArray(csvLines.get(cnt));
            result[cnt] = lineArray[columnPosition];
        }
        return result;
    }

    // Gets the first line number of a given key and value combination
    static int getLineNumber(List<String> csvLines, String column, String value) {
        int result = -1;
        int columnPosition = csvGetColumnPosition(csvLines, column);
        for (int cnt = 0; cnt < csvLines.size(); cnt = cnt + 1) {
            String[] lineArray = getLineArray(csvLines.get(cnt));
            String cellValue = lineArray[columnPosition];
            if (cellValue.compareTo(value) == 0) {
                result = cnt;
                break;
            }
        }
        return result;
    }

    // Updates CSV data
    static void update (List<String> csvLines, int line, String column, String value) {
        String[] lineArray = getLineArray(csvLines.get(line));
        int columnPosition = csvGetColumnPosition(csvLines, column);
        lineArray[columnPosition] = value;
        csvLines.set(line, String.join(",", lineArray));
    }

    // Shows CSV data
    static void list (List<String> csvLines) {
        for (int cnt = 0; cnt < csvLines.size(); cnt = cnt + 1) {
            System.out.println(csvLines.get(cnt));
        }
    }
}
