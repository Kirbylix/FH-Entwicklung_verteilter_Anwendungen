package main;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class MeineDaten implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Version
   private Timestamp lastUpdate;

   @Column(nullable = false, length = 200)
   private String meinText;

   // Getter/Setter:
   public Long      getId()         { return id;         }
   public Timestamp getLastUpdate() { return lastUpdate; }
   public String    getMeinText()   { return meinText;   }
   public void setId(         Long      id         ) { this.id = id;                 }
   public void setLastUpdate( Timestamp lastUpdate ) { this.lastUpdate = lastUpdate; }
   public void setMeinText(   String    meinText   ) { this.meinText = meinText;     }

   @Override public String toString() {
      return "MeineDaten: id=" + id + ", lastUpdate='" + lastUpdate + "', meinText='" + meinText + "'";
   }
}