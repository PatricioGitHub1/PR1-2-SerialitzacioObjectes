import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;


public class PR134estudiantsManager {
    private static final int ID_SIZE = 4; // bytes
    private static final int CHAR_SIZE = 2; // bytes per caràcter en UTF-16
    private static final int NAME_SIZE = 20; // Longitud màxima en caràcters del nom
    private static final int GRADE_SIZE = 4; //bytes

    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile("./data/estudiants.dat", "rw")) {
            // Resetear el documento y poner los valores base
            restartDataFile(raf);

            // Afegim nou registre
            afegirEstudiant(raf, 4, "Antonio Perez", (float) 5.00);

            // Consultar i mostrar els estudiants afegits
            mostrarEstudiant(raf, 1, "Original");
            mostrarEstudiant(raf, 2, "Original");
            mostrarEstudiant(raf, 3, "Original");
            mostrarEstudiant(raf, 4, "Original");

            // Actualitzar els noms dels estudiants
            actualitzarNotaEstudiant(raf, 1, (float) 5.50);
            actualitzarNotaEstudiant(raf, 4, (float) 10.00);


            // Consultar i mostrar els estudiants actualitzats
            mostrarEstudiant(raf, 1, "Canvi a la nota");
            mostrarEstudiant(raf, 4, "Canvi a la nota");

            // Caso en donde el estudiante no existe
            mostrarEstudiant(raf, 30, "Estudiant no existeix");

        } catch (EOFException e){
            System.out.println("Aquest estudiant no existeix");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restartDataFile(RandomAccessFile raf) {
        String basePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "estudiants.dat";
        File newFile = new File(basePath);
        // Crear o en su defecto vaciar el archivo de datos
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
                System.out.println("Se ha creado el archivo de datos");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(basePath, false);
                fileWriter.close();
                System.out.println("Disponible archivo de datos");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Introducir los casos base
        try {
            afegirEstudiant(raf, 1, "Patricio Rojas", (float) 7.50);
            afegirEstudiant(raf, 2, "John Doe", (float) 4.99);
            afegirEstudiant(raf, 3, "Jane Doe", (float) 8.73);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void afegirEstudiant(RandomAccessFile raf, int id, String nom, float nota) throws Exception {
        raf.seek(raf.length());
        raf.writeInt(id);
        raf.writeChars(getPaddedName(nom));
        raf.writeFloat(nota);
    }


    public static String consultarEstudiante(RandomAccessFile raf, int id) throws Exception {
        raf.seek(getSeekPosition(id));
        raf.readInt();
        char[] chars = new char[NAME_SIZE];
        for (int i = 0; i < NAME_SIZE; i++) {
            chars[i] = raf.readChar();
        }
        
        return new String(chars).trim() + " - "+ Float.toString(raf.readFloat()) ;
    }


    public static void actualitzarNotaEstudiant(RandomAccessFile raf, int id, float novaNota) throws Exception {
        raf.seek(getSeekPosition(id) + ID_SIZE + NAME_SIZE * CHAR_SIZE);
        raf.writeFloat(novaNota);
    }


    public static void mostrarEstudiant(RandomAccessFile raf, int id, String msg) throws Exception {
        System.out.println(msg + " " + id + ": " + consultarEstudiante(raf, id));
    }


    /**
     * Calcula la posició (offset) dins del fitxer on s'inicia el registre del videojoc amb l'ID especificat.
     *
     * @param id L'identificador del videojoc.
     * @return La posició dins del fitxer on s'inicia el registre del videojoc.
     */
    private static long getSeekPosition(int id) {
        // L'operació (id - 1) serveix per obtenir un índex basat en 0.
        // (ID_SIZE + NAME_SIZE * CHAR_SIZE) calcula la mida total en bytes d'un registre de videojoc.
        // ID_SIZE representa la mida en bytes de l'ID del videojoc.
        // NAME_SIZE * CHAR_SIZE representa la mida total en bytes del nom del videojoc.
        return (id - 1) * (ID_SIZE + NAME_SIZE * CHAR_SIZE + GRADE_SIZE);
    }


    /**
     * Retorna una versió del nom del videojoc que sempre té una longitud fixa (NAME_SIZE).
     * Si el nom és més llarg que NAME_SIZE, es trunca. Si és més curt, s'omple amb espais en blanc.
     *
     * @param name El nom original del videojoc.
     * @return El nom amb una longitud fixa de NAME_SIZE caràcters.
     */
    private static String getPaddedName(String name) {
        // Si el nom és més llarg que la mida màxima permesa (NAME_SIZE),
        // es trunca per ajustar-se a aquesta mida.
        if (name.length() > NAME_SIZE) {
            return name.substring(0, NAME_SIZE);
        }
        // Si el nom és més curt que NAME_SIZE, s'omple amb espais en blanc fins a assolir aquesta mida.
        // String.format amb "%1$-" + NAME_SIZE + "s" assegura que la cadena resultant tingui una longitud fixa.
        return String.format("%1$-" + NAME_SIZE + "s", name);
    }
}



