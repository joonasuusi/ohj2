/**
 * 
 */
package ht.wt;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ht.wt.Kanta.alustaKanta;

 /**
 * Pitää yllä varsinaista saatilarekisteria eli osaa lisätä ja poistaa säätilan               
 * Lukee ja kirjoittaa saatila tiedostoon        
 * Osaa etsiä ja lajitella
 * Avustaja: Saatila
 * @author Joonas Uusi-Autti ja Sini Lällä
 * @version 2.3.2020
 *
 */
public class Saatilat {
    private static Saatila apuSaatila = new Saatila();
    private Kanta kanta;
    
    
    /**
     * Tarkistetaan että tietokannassa säätilojen tarvitsema taulu
     * @param nimi tietokannan nimi
     * @throws SailoException jos ei mahu
     */
    public Saatilat(String nimi) throws SailoException {
        kanta = alustaKanta(nimi);
        try (Connection con = kanta.annaKantayhteys()) {
            DatabaseMetaData meta = con.getMetaData();
            try (ResultSet taulu = meta.getTables(null, null, "Saatilat", null)) {
                if (!taulu.next()) {
                    try (PreparedStatement sql = con.prepareStatement(apuSaatila.annaLuontilauseke())) {
                        sql.execute();
                    }
                }
            }
        } catch(SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa: " +  e.getMessage());
        }
        
    }
    
   
    /**
     * Lisää uuden säätilan tietorakenteeseen
     * @param saa lisättävä säätila
     * @throws SailoException jos ei mahu
     */
    public void lisaa(Saatila saa) throws SailoException {
        try ( Connection con = kanta.annaKantayhteys(); PreparedStatement sql = saa.annaLisayslauseke(con) ) {
            sql.executeUpdate();
            try ( ResultSet rs = sql.getGeneratedKeys() ) {
                saa.tarkistaId(rs);
             }   
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
    }
    

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {;
    try {
        Saatilat saatilat = new Saatilat("kokeilu");
        Saatila saa = new Saatila();
        saa.paivanSaa(2);
        Saatila saa1 = new Saatila();
        saa1.paivanSaa(1);
        Saatila saa2 = new Saatila();
        saa2.paivanSaa(2);
        Saatila saa3 = new Saatila();
        saa3.paivanSaa(2);

        saatilat.lisaa(saa);
        saatilat.lisaa(saa1);
        saatilat.lisaa(saa2);
        saatilat.lisaa(saa3);
        saatilat.lisaa(saa2);
        
        System.out.println("============= Säätilat testi =================");

        List<Saatila> saatilat2;
        
        saatilat2 = saatilat.annaSaatilat(2);
        

        for (Saatila saat : saatilat2) {
            System.out.print(saat.getId() + " ");
            saat.tulosta(System.out);
        }
        
        new File("kokeilu.db").delete();
    } catch (SailoException ex) {
        System.out.println(ex.getMessage());
    }
    }


    public List<Saatila> annaSaatilat(int i) throws SailoException {
        List<Saatila> loydetyt = new ArrayList<Saatila>();
        
        try ( Connection con = kanta.annaKantayhteys();
              PreparedStatement sql = con.prepareStatement("SELECT * FROM Saatilat WHERE saaId = ?")
                ) {
            sql.setInt(1, i);
            try ( ResultSet tulokset = sql.executeQuery() )  {
                while ( tulokset.next() ) {
                    Saatila saa = new Saatila();
                    saa.parse(tulokset);
                    loydetyt.add(saa);
                }
            }
            
        } catch (SQLException e) {
            throw new SailoException("Ongelmia tietokannan kanssa:" + e.getMessage());
        }
        return loydetyt;
    }   
    
}