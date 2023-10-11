import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class PR131mainLlegeix {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir") + "/data/";
        String filePath = basePath + "PR131HashMapData.ser";

        // Crear la carpeta 'data' si no existeix
        File dir = new File(basePath);
        if (!dir.exists()){
            if(!dir.mkdirs()) {
                System.out.println("Error en la creació de la carpeta 'data'");
            }
        }

        System.out.println("");

        try {
			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);

            PR131hashmap obj0 = (PR131hashmap) ois.readObject();

            System.out.println("Datos del HashMap\n=================");
            HashMap<String, String> hm = obj0.hsmp;
            
            for (Map.Entry<String, String> set :
             hm.entrySet()) {
                System.out.println(set.getKey() + " = " + set.getValue());
            }

			ois.close();
			fis.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
    }
}
