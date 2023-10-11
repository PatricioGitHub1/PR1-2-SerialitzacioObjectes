import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class PR130mainPersonesHashmap {
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    
    public static void main(String[] args) {
        PR130mainPersonesHashmap people = new PR130mainPersonesHashmap();
        people.map.put("Jhon", 19);
        people.map.put("Lia", 3);
        people.map.put("Dana", 2);
        people.map.put("Christian", 45);
        people.map.put("Andrew", 29);
        
        people.escripturaDadesPrimitives();
        people.lecturaDadesPrimitives();
    }

    void escripturaDadesPrimitives() {
        String basePath = System.getProperty("user.dir") + "/data/";
        String filePath = basePath + "PR130persones.dat";

        // Crear la carpeta 'data' si no existeix
        File dir = new File(basePath);
        if (!dir.exists()){
            if(!dir.mkdirs()) {
                System.out.println("Error en la creació de la carpeta 'data'");
            }
        } else {
            System.out.println("La carpeta 'Data' existeix");
        }

        // Escriptura en l'arxiu 'PR130persones.dat'
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            DataOutputStream dos = new DataOutputStream(fos);

            // Iterar sobre el HashMap per treure les dades
            for (Iterator i = this.map.keySet().iterator(); i.hasNext();) {
                String name = (String) i.next();
                int age = (int) this.map.get(name);
                
                dos.writeUTF(name);
                dos.writeInt(age);
                System.out.println("Entrada añadida");
            }

            // Importante, al acabar usar el flush() para subir la informacion y cerrar funciones
            dos.flush();
            fos.close();
            dos.close();
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
         catch ( IOException e2) {
            e2.printStackTrace();
        } 
    }

    void lecturaDadesPrimitives() {
        // Conseguim direccions arxiu
        String basePath = System.getProperty("user.dir") + "/data/";
        String filePath = basePath + "PR130persones.dat";

        try {
            // Declarem les instancies necessaries
            FileInputStream fis = new FileInputStream(filePath);
            DataInputStream dis = new DataInputStream(fis);

            // Iterem en funcio del HashMap que hem creat i mostrem les dades
            for (int i = 0; i != this.map.size(); i++) {
                System.out.println("Nombre = "+dis.readUTF()+" | Edat = "+dis.readInt());
            }

            // Tanquem les funciones
            dis.close();
            fis.close();
        }   catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } 
    }
}
