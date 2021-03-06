package ht.wt;

import java.io.PrintStream;
import java.util.Comparator;

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
public class Paiva implements Cloneable {
    
    private int tunnusNro;
    private static int seuraavaNro = 1;
    private String pvm = "";
    private String paikka = "";
    private String kello = "";
    private double alinLampo = 0.0;
    private double ylinLampo = 0.0;
    private double sademaara = 0.0; 
    private String huomiot = "";
    private int saatila;
    

    /**
     * Vertailija joka osaa verrata tietyn kentän mukaan
     */
    public static class Vertailija implements Comparator<Paiva> {

        private int k;
        
        /**
         * @param k minkä kentän mukaan vertaillaan
         */
        public Vertailija(int k) {
            this.k = k;
        }
        
        @Override
        public int compare(Paiva pvm1, Paiva pvm2) {
            return pvm1.getAvain(k).compareTo(pvm2.getAvain(k));
        }
        
    }
    
    /**
     * kloonataan päivä
     */
    @Override
    public Paiva clone() throws CloneNotSupportedException {
        Paiva uusi = (Paiva) super.clone();
        return uusi;
    }
    
    /**
     * Hakee päivämäärän
     * @return palauttaa päivämäärän
     */
    public String getPvm() {
        return pvm;
    }
    
    /**
     * Palauttaa kentän k:n mukaisen lajitteluavaimen
     * @param k minkä kentän mukaan lajitellaan
     * @return lajitteluavain
     */
    public String getAvain(int k) {
        switch(k) {
        case 1: return "" + vertailija(pvm);
        case 2: return "" + vertailija(pvm);
        case 3: return "" + vertailija(pvm);
        case 4: return "" + String.format("%02.1f", alinLampo);
        case 5: return "" + String.format("%02.1f", ylinLampo);
        case 6: return "" + String.format("%02.1f", sademaara);
        case 7: return "" + vertailija(pvm);
        case 8: return "" + saatila;
        default: return "Nyt meni jokin vikaan!";
        }
    }


    /**
     * Hakee paikan
     * @return palauttaa paikan
     */
    public String getPaikka() {
        return paikka;
    } 
    
 
    /**
     * Hakee kellonajan
     * @return palauttaa kellonajan
     */
    public String getKello() {
        return kello;
    }
    
    
    /**
     * Hakee alimman lämpötilan
     * @return palauttaa alimman lämpötilan
     */
    public double getAlinLampo() {
        return alinLampo;
    }
    
    /**
     * Hakee ylimmän lämpötilan
     * @return palauttaa ylimmän lämpötilan
     */
    public double getYlinLampo() {
        return ylinLampo;
    }
    
    /**
     * Hakee sademäärän
     * @return palauttaa sademäärän
     */
    public double getSademaara() {
        return sademaara;
    }
    
    /**
     * Hakee päivän huomiot
     * @return palauttaa päivän huomiot
     */
    public String getHuomiot() {
        return huomiot;
    }
    
    /**
     * @return palauttaa säätilan
     */
    public int getSaatila() {
        return saatila;
    }
    
    /**
     * Palauttaa päivän tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return päivä tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.parse("3  |    12.3.2020   |   Orivesi       |  07:18");
     * paiva.toString().startsWith("3|12.3.2020|Orivesi|07:18|") === true;
     * </pre>
     */
    @Override
    public String toString() {
        return "" + 
                getTunnusNro() + "|" +
                pvm + "|" +
                paikka + "|" +
                kello + "|" +
                alinLampo + "|" +
                ylinLampo + "|" +
                sademaara + "|" +
                huomiot + "|" +
                saatila + "|";
    }
    
    
    /**
     * Luetaan tiedoston rivit
     * @param rivi rivi jota luetaan 
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        pvm = Mjonot.erota(sb, '|', pvm);
        paikka = Mjonot.erota(sb, '|', paikka);
        kello = Mjonot.erota(sb, '|', kello);
        alinLampo = Mjonot.erota(sb, '|', alinLampo);
        ylinLampo = Mjonot.erota(sb, '|', ylinLampo);
        sademaara = Mjonot.erota(sb, '|', sademaara);
        huomiot = Mjonot.erota(sb, '|', huomiot);
        saatila = Mjonot.erota(sb, '|', saatila);
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Paiva pvm = new Paiva();
        Paiva pvm1 = new Paiva();
        Paiva pvm2 = new Paiva();
        
        pvm.rekisteroi();
        pvm.taytaPvmTiedoilla();
        pvm.tulosta(System.out);
        
        pvm1.rekisteroi();
        pvm1.taytaPvmTiedoilla();
        pvm1.tulosta(System.out);
        
        pvm2.rekisteroi();
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
       // saatila = haeSaatila(rand(0,6));
        sademaara = 0.2;
        huomiot = "";
    }
    
    
    /**
     * Antaa päivälle seuraaavan rekisterinumeron
     * @return päivän uusi tunnusnumero
     * @example
     * <pre name="test">
     * Paiva paiva1 = new Paiva();
     * paiva1.getTunnusNro() === 0;
     * paiva1.rekisteroi();
     * Paiva paiva2 = new Paiva();
     * paiva2.rekisteroi();
     * int n1 = paiva1.getTunnusNro();
     * int n2 = paiva2.getTunnusNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * 
     * @return palauttaa päivän tunnusnumeron
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * asettaa tunnusnron ja samalla varmistaa että
     * seuraava on numero on aina suurempi kuin tähän mennessä suurin
     * @param nro asetettava tunnusnrro
     */
    public void setTunnusNro(int nro) {
        tunnusNro = nro;
        if (tunnusNro >= seuraavaNro)
            seuraavaNro = tunnusNro+1;
    }

    
    /**
     * Tulostetaan päivän tiedot
     * @param out mihin virtaa tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Päivämäärä:        " + pvm);
        out.println("Paikka:            " + paikka);
        out.println("Kellonaika:        " + kello);
        out.println("Alin lämpötila:    " + String.format("%2.1f", alinLampo) + "°C");
        out.println("Ylin lämpötila:    " + String.format("%2.1f", ylinLampo) + "°C");
        out.println("Sademäärä:         " + String.format("%2.1f", sademaara) + "mm");
        out.println("Huomiot:           " + huomiot); 
    }


    /**
     * Asetetetaan päivämäärä ja tarkistetaan se
     * @param s asetettava päivämäärä
     * @return null, jos onnistui
     */
    public String setPvm(String s) {
        if( !s.matches("((((([0-2]{1})[0-9]{1}))|(3{1}[0-1]{1}))\\.((0{1}[1-9])|1{1}[0-2]{1})\\.([0-9]{4}))")) 
            return "Päivämäärän pitää olla muota pp.kk.vvvv";
        pvm = s;
        return null;
    }


    /**
     * Asetetaan paikka
     * @param s asetettava paikka
     * @return null, jos onnistui
     */
    public String setPaikka(String s) {
        paikka = s;
        return null;
    }


    /**
     * Asetetaan kellonaika
     * @param s asetettava kellonaika
     * @return nuul, jos onnistui
     */
    public String setKello(String s) {
        if( !s.matches("^([0-1][0-9]|[2][0-3])[\\\\.|:]([0-5][0-9])$")) return "Kellonaika väärin!";
        kello = s;
        return null;
    }


   /**
    * Asetetaan alinlämpö
    * @param s asetettava alinlämpö
    * @return null, jos onnistui
    */
   public String setAlinLampo(String s) {
       StringBuilder sb = new StringBuilder(s);
       alinLampo = Mjonot.erota(sb, '§', alinLampo);
       return null;
    }
   
   
   /**
    * Asetetaan ylinlämpö
    * @param s asetettavayalinlämpö
    * @return null, jos onnistui
    */
   public String setYlinLampo(String s) {
       StringBuilder sb = new StringBuilder(s);
        ylinLampo = Mjonot.erota(sb, '§', ylinLampo);
        return null;
    }
   
   
   /**
    * Asetetaan sademäärä
    * @param s asetettava sademäärä
    * @return null, jos onnistui
    */
   public String setSademaara(String s) {
       StringBuilder sb = new StringBuilder(s);
        sademaara = Mjonot.erota(sb, '§', sademaara);
        return null;
    }
   
   /**
    * Asetetaan huomio
    * @param s asetettavat huomiot
    * @return null, jos onnistui
    */
   public String setHuomiot(String s) {
       huomiot = s;
       return null;
    }
   
     /**
     * Asettaa säätilan
     * @param s asetettava säätila
     */
    public void setSaatila(int s) {
       //StringBuilder sb = new StringBuilder(s);
       //saatila = Mjonot.erota(sb, '§', saatila);
       //return null;
        saatila = s;
    }
    

   
    /**
     * Palauttaa pyydetyn kentän indeksin
     * @param hk pyydetty kenttö
     * @return pyydetty kenttä
     * TODO: korjaa nää kommentit
     */
    public String anna(int hk) {
        switch(hk) {
            case 1: return "" + pvm;
            case 2: return "" + paikka;
            case 3: return "" + kello;
            case 4: return "" + alinLampo;
            case 5: return "" + ylinLampo;
            case 6: return "" + sademaara;
            case 7: return "" + huomiot;
            case 8: return "" + saatila;
            default: return "jee";
        }
    }

    /**
     * 
     * @param paiva Päivä
     * @return palauttaa päivämäärän muodossa vvvkkpv
     */
    public static String vertailija(String paiva) {
        StringBuilder sb = new StringBuilder(paiva);
        String pv = Mjonot.erota(sb, '.');
        String kk = Mjonot.erota(sb, '.');
        String vv = Mjonot.erota(sb, '.');
        
        //String vvkkpp = String.format("%04s", "%02s", "%02s",vv + kk + pv);
        String vvkkpp = vv + kk + pv;
        return vvkkpp;
    }

}
