package ht.wt;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

import static kanta.PaivaLuonti.*;

 /**
 * Tietää päivän kentät(pvm, paikka, kellonaika, lämpötila,säätila jne.                          
 * Osaa tarkistaa tietyn kentän oikeellisuuden (syntaksin).                                    
 * Osaa muuttaa 08.01.2020|Jyväskylä|06:20|..| -merkkijonon päivän tiedoiksi.                  
 * Osaa antaa merkkijonona i:n kentän tideot       
 * Osaa laittaa merkkijonon i:neksi kentäksi  
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 27.2.2020
 *
 */
public class Paiva {
    
    private String pvm = "";
    private String paikka = "";
    private String kello = "";
    private double alinLampo = 0.0;
    private double ylinLampo = 0.0;
    private String saatila;
    private double sademaara = 0.0; 
    private String huomiot = "";
    

    /**
     * Hakee päivämäärän
     * @return palauttaa päivämäärän
     */
    public String getPvm() {
        return pvm;
    }
    
    /**
     * Palauttaa päivän tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return päivä tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.parse("    12.3.2020   |   Orivesi       |  07:18");
     * paiva.toString().startsWith("12.3.2020|Orivesi|07:18|") === true;
     * </pre>
     */
    @Override
    public String toString() {
        return "" + 
                pvm + "|" +
                paikka + "|" +
                kello + "|" +
                alinLampo + "|" +
                ylinLampo + "|" +
                saatila + "|" +
                sademaara + "|" +
                huomiot + "|";
    }
    
    /**
     * @param rivi rivi jota luetaan TODO: tarvitaanko setTunnsNro?
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        pvm = Mjonot.erota(sb, '|', pvm);
        paikka = Mjonot.erota(sb, '|', paikka);
        kello = Mjonot.erota(sb, '|', kello);
        alinLampo = Mjonot.erota(sb, '|', alinLampo);
        ylinLampo = Mjonot.erota(sb, '|', ylinLampo);
        saatila = Mjonot.erota(sb, '|', saatila);
        sademaara = Mjonot.erota(sb, '|', sademaara);
        huomiot = Mjonot.erota(sb, '|', huomiot); 
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Paiva pvm = new Paiva();
        Paiva pvm1 = new Paiva();
        Paiva pvm2 = new Paiva();
        
        pvm.taytaPvmTiedoilla();
        pvm.tulosta(System.out);
        
        pvm1.taytaPvmTiedoilla();
        pvm1.tulosta(System.out);
        
        pvm2.taytaPvmTiedoilla();
        pvm2.tulosta(System.out);
        
    }
    
    
    
    /**
     * Arvotaan satunnainen kokonaisluku välille [ala, ylä]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala, ylä]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot päivämäärälle
     * TODO: poista kun kun kaikki toimii
     */
    public void taytaPvmTiedoilla() {
        pvm = arvoPaiva();
        paikka = "Jyväskylä " +rand(1, 310);
        kello = "10:29";
        alinLampo = -2.2;
        ylinLampo = 3.3;
        saatila = haeSaatila(rand(0,6));
        sademaara = 0.2;
        huomiot = "";
    }

    /**
     * Haetaan numeroa vastaava säätilan arvo
     * @param arpa arvottu numero
     * @return numeroa vastaava säätila
     */
    private String haeSaatila(int arpa) {
        return Paivat.haeSaatila(arpa);
    }

    /**
     * Tulostetaan päivän tiedot
     * @param out mihin virtaa tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(pvm +  ", " + paikka + ", " + kello);
        out.println("Päivän alin lämpötila " + String.format("%2.1f", alinLampo) + 
                "°C" + ", ylin lämpötila " + String.format("%2.1f", ylinLampo) + 
                "°C" + " ja sademäärä " + String.format("%2.1f", sademaara) + "mm");
        out.println("Huomiot: " + huomiot);
        out.println("Säätila: " + saatila);
    } 
}
