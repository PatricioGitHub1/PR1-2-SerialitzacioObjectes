import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PR132main {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + "/data/";
        String filePath = basePath + "PR132people.dat";

        // Crear la carpeta 'data' si no existeix
        File dir = new File(basePath);
        if (!dir.exists()){
            if(!dir.mkdirs()) {
                System.out.println("Error en la creació de la carpeta 'data'");
            }
        }

        System.out.println("");

        try {
            // Escribir los objetos
			FileOutputStream fos = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);


			oos.writeObject(new PR132persona("Maria", "López", 36));
            System.out.println("Objeto escrito");

            oos.writeObject(new PR132persona("Gustavo", "Ponts", 63));
            System.out.println("Objeto escrito");

            oos.writeObject(new PR132persona("Irene", "Sales", 54));
            System.out.println("Objeto escrito");

			oos.close();
			fos.close();

            System.out.println("Todos los objetos escritos en PR132people.dat\n");

            // Momento de leer los objetos

            try {
                System.out.println("Mostrando los objetos desde 132people.dat...");
                FileInputStream fis = new FileInputStream(filePath);
                ObjectInputStream ois = new ObjectInputStream(fis);
    
                PR132persona obj0 = (PR132persona) ois.readObject();
                PR132persona obj1 = (PR132persona) ois.readObject();
                PR132persona obj2 = (PR132persona) ois.readObject();
    
                System.out.println(obj0);
                System.out.println(obj1);
                System.out.println(obj2);
    
                ois.close();
                fis.close();
    
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) { e.printStackTrace(); }

		} catch (IOException e) { e.printStackTrace(); }
    }
}
