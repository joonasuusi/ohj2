/**
 * 
 */
package ht.wt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
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
    private final Collection<Saatila> alkiot = new ArrayList<Saatila>();
    
    
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
    public static void main(String[] args) {
        Saatilat saatilat = new Saatilat();
        Saatila aur = new Saatila();
        Saatila pilvi = new Saatila();
        Saatila sade = new Saatila();
        aur.paivanSaa();
        pilvi.paivanSaa();
        sade.paivanSaa();
        
        saatilat.lisaa(aur);
        saatilat.lisaa(pilvi);
        saatilat.lisaa(sade);
    
    }



}
