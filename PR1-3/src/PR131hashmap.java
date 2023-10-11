import java.io.Serializable;
import java.util.HashMap;

public class PR131hashmap implements Serializable {
    HashMap<String, String> hsmp = new HashMap<String, String>();
    
    PR131hashmap(String key, String value) {
        for (int i = 1; i != 7; i++) {
            hsmp.put(key+"_"+i, value+"_"+i);
        }
    }
}
