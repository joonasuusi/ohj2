/**
 * 
 */
package ht.wt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

 /**
 * Pitää yllä varsinaista saatilarekisteria eli osaa lisätä ja poistaa säätilan               
 * Lukee ja kirjoittaa saatila tiedostoon        
 * Osaa etsiä ja lajitella
 * Avustaja: Saatila
 * @author Joonas Uusi-Autti ja Sini Lällä
 * @version 2.3.2020
 *
 */
public class Saatilat implements Iterable<Saatila> {
    private int lkm = 0;
    private String tiedostonNimi = "";
    
    /**
     * Taulukko säätiloista
     */
    private final ArrayList<Saatila> alkiot = new ArrayList<Saatila>();
    
    
    /**
     * Säätilojen alustaminen
     */
    public Saatilat() {
        // toistaiseksi ei tartte tehä mitää
    }
    
    /**
     * Lisää uuden säätilan tietorakenteeseen
     * @param saa lisättävä säätila
     */
    public void lisaa(Saatila saa) {
        alkiot.add(saa);
        lkm++;
    }
    
    /**
     * Palauttaa säätilojen lukumäärän
     * @return säätilojen lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    @Override
    public Iterator<Saatila> iterator() {
        return alkiot.iterator();
    }
    

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {;
        //
    
    }

    /**
     * Haetaan numeroa vastaava säätilan arvo
     * @param arpa arvottu numero
     * @return numeroa vastaava säätila
     */
    public static String haeSaatila(int arpa) {
        return Saatila.haeSaatila(arpa);
    }

    public void tallenna() {
        // TODO Auto-generated method stub
        
    }
}
