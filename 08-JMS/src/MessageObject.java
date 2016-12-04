import java.io.Serializable;

/**
 * Created by Kai on 28.11.2016.
 */
public class MessageObject implements Serializable {

    private String name;
    private String vorname;
    private int matrikelNr;
    private char geschlecht;
    private boolean eingeschrieben;

    public MessageObject(){}

    public MessageObject(String name, String vorname, int matrikelNr, char geschlecht, boolean eingeschrieben) {
        this.name = name;
        this.vorname = vorname;
        this.matrikelNr = matrikelNr;
        this.geschlecht = geschlecht;
        this.eingeschrieben = eingeschrieben;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public int getMatrikelNr() {
        return matrikelNr;
    }

    public void setMatrikelNr(int matrikelNr) {
        this.matrikelNr = matrikelNr;
    }

    public char getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(char geschlecht) {
        this.geschlecht = geschlecht;
    }

    public boolean isEingeschrieben() {
        return eingeschrieben;
    }

    public void setEingeschrieben(boolean eingeschrieben) {
        this.eingeschrieben = eingeschrieben;
    }

    @Override
    public String toString() {
        return "MessageObject{" +
                "name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                ", matrikelNr=" + matrikelNr +
                ", geschlecht=" + geschlecht +
                ", eingeschrieben=" + eingeschrieben +
                '}';
    }
}
