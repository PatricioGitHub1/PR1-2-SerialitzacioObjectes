import java.io.Serializable;

public class PR132persona implements Serializable{
    String nom;
    String cognom;
    int edat;
    
    public PR132persona(String nom, String cognom, int edat) {
        this.nom = nom;
        this.cognom = cognom;
        this.edat = edat;
    }

    @Override
    public String toString() {
        return "Nom = " + nom + " | Cognom = " + cognom + " | Edat = " + edat;
    }
    
}
