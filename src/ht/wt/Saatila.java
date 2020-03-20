/**
 * 
 */
package ht.wt;

import java.io.PrintStream;

 /**
 * Tietää säätilojen kentät.                   
 * Osaa tarkistaa tietyn kentän oikeellisuuden (syntaksin)                                 
 * Osaa muuttaa 3|Puolipilvinen -merkkijonon   säätilan tiedoiksi.                         
 * Osaa antaa merkkijonona i:n kentän tiedot   
 * Osaa laittaa merkkijonon i:neksi kentäksi
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 2.3.2020
 *
 */
public class Saatila {
    private static Saatila[] saa =  {
            new Saatila (1, "aurinkoinen"), 
            new Saatila (2, "pilvinen"), 
            new Saatila (3, "puolipilvinen"),
            new Saatila (4, "vesisade"), 
            new Saatila (5, "räntäsade"), 
            new Saatila (6, "lumisade")
    };
    
    private String saatila = "";
    private int id;
    
    /**
     * Alustetaan sää
     * @param id säätilan id
     * @param saa säätila
     */
    public Saatila(int id, String saa) {
        this.id = id;
        this.saatila = saa;
    }


    /**
     * Arvotaan satunnainen kokonaisluku välille [ala, ylä]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala, ylä]
     */
    public int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    

    /**
     * @return palauttaa säätilan id:n
     */
    public String getSaatila() {
        return saatila;
    }
    
    /**
     * @return palauttaa säätilan id:n
     */
    public int getId() {
        return id;
    }
    
    
    /**
     * Tulostetaan päivän tiedot
     * @param out mihin virtaa tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Säätila: " + saatila);    
    } 

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //
    }

    /**
     * Haetaan numeroa vastaava säätilan arvo
     * @param arpa arvottu numero
     * @return numeroa vastaava säätila
     */
    public static String haeSaatila(int arpa) {
        for (int i = 0; i < saa.length; i++) {
            if (saa[i].getId() == arpa) 
                return saa[i].getSaatila();
        }
        return "";
    }
}