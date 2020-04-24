package FXht;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import fi.jyu.mit.ohj2.Mjonot;
import ht.wt.Paiva;
import ht.wt.Saatila;
import ht.wt.SailoException;
import ht.wt.WeatherTracker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 7.2.2020
 *
 */
public class PaaIkkunaController implements Initializable {

    @FXML private Button textCancel;
    @FXML ListChooser<Paiva> chooserPaivat;
    @FXML private ScrollPane panelPaiva;
    @FXML private ComboBoxChooser<String> hakuKentta;
    @FXML private TextField hakuehto;
    
    @FXML private TextField editPvm;
    @FXML private TextField editPaikka;
    @FXML private TextField editKello;
    @FXML private TextField editAlinLampo;
    @FXML private TextField editYlinLampo;
    @FXML private TextField editSademaara;
    @FXML private TextField editHuomiot;
    @FXML private TextField editSaatila;
    
    
    @FXML private void handleUusi() {
        uusiPaiva();
    } 
    
    @FXML private void handleHakuehto() {
        if (paivaKohdalla != null) 
            hae(paivaKohdalla.getTunnusNro());
    }
    
    @FXML private void handleSaatila() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setHeaderText("Lisätään uusi säätila");
        dialog.setTitle("Lisää säätila");
        dialog.setContentText("Lisää uusi säätila:");
        Optional<String> saa = dialog.showAndWait();
        if (saa.isPresent()) { 
            String s = saa.get();
            uusiSaa(s);
        }
    }
    
    @FXML private void handlePoistaSaa() {
        ModalController.showModal(SaatilaController.class.getResource("poistaSaatila.fxml"),
                "Säätilan poisto", null, weathertracker);
    }
    
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    @FXML private void handleMuokkaa() {
        muokkaa();
    }

    @FXML private void handleTallenna() {
        tallenna();
    }
    
    @FXML private void handleApua() {
        apua();
    }
    
    @FXML private void handleTietoja() {
        ModalController.showModal(TietojaController.class.getResource("tietoja.fxml"),
                "Tietoja", null, "");
    }
    
    @FXML private void handleTulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null);
        tulostaValitut(tulostusCtrl.getTextArea());
    }

    @FXML private void handlePoista() {
        poistaPaiva();
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(textCancel);
    }
    
    /**
     * Onko sama kkuin tallennna metodi???
     */
    @FXML private void handleOK() {
        tallenna();
    }

    // =============== omat koodit ===============
    
    WeatherTracker weathertracker;
    private Paiva paivaKohdalla;
    private TextField[] edits;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true, jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /**
     * Aliohjelma joka ohjaa sivustolle jossa käyttöohjeet käyttöliittymälle
     */
    private void apua() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2020k/ht/jopeuusi");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    
    /**
     * Aliohjelma joka tallentaa syötetyt tiedot
     */
    private void tallenna() {
        try {
            weathertracker.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallentamisessa tapahtui virhe: " + e.getMessage());
        } 
    }
    
    private void poistaPaiva() {
        Paiva paiva = paivaKohdalla;
        if ( paiva == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko päivä: " + paiva.getPvm(), "Kyllä", "Ei") )
            return;
        weathertracker.poista(paiva);
        int index = chooserPaivat.getSelectedIndex();
        hae(0);
        chooserPaivat.setSelectedIndex(index);
    }

    
    
    /**
     * Lisätään uusi päivämäärä tiedoilla näyttöön
     */
    private void uusiPaiva() {
        Paiva uusi = new Paiva();
        uusi = MuokkausController.kysyPaiva(null, uusi, weathertracker);
        if (uusi == null) return;
        uusi.rekisteroi();
        weathertracker.lisaa(uusi);
        hae(uusi.getTunnusNro());
    }
    
    
    /**
     * Lisätään uusi säätila ohjelmalle
     * @param saatila lisättävä säätila
     */
    private void uusiSaa(String saatila) {
        Saatila saa = new Saatila(saatila);
        saa.rekisteroi(); 
        weathertracker.lisaa(saa);
    }


    /**
     * Tyhjennetään lista ja haetaan weathertracker luokalta päivämäärä
     * ja lisätään se seuraavaan indeksiin
     * @param pnro päivämäärän järjestysnumero
     */
    private void hae(int pnro) {
        int nro = pnro;
        if ( nro <= 0 ) {
            Paiva kohdalla = paivaKohdalla;
            if (kohdalla != null) nro = kohdalla.getTunnusNro();
        }
        
        int k = hakuKentta.getSelectionModel().getSelectedIndex()+1;
        String ehto = hakuehto.getText();
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";

        chooserPaivat.clear();
        
        int index = 0;
        Collection<Paiva> paivat;
            paivat = weathertracker.etsi(ehto, k); 
            int i = 0;
            for (Paiva paiva : paivat) {
                if (paiva.getTunnusNro() == nro) index = i;
                chooserPaivat.add(paiva.getPvm(), paiva);
                i++;
            }
        chooserPaivat.setSelectedIndex(index);
    }

    
    /**
     * Alustetaan ja luodaan näyttöön uusi päivä
     */
    private void alusta() {
        hakuKentta.clear();
        hakuKentta.add("Päivämäärä");
        hakuKentta.add("Paikka");
        hakuKentta.add("Kello");
        hakuKentta.add("Alin lämpötila");
        hakuKentta.add("Ylin lämpötila");
        hakuKentta.add("Sademäärä");
        hakuKentta.add("Huomiot");
        hakuKentta.add("Säätila");
        hakuKentta.getSelectionModel().select(0);
        
        panelPaiva.setFitToHeight(true);
        
        chooserPaivat.clear();
        chooserPaivat.addSelectionListener(e -> naytaPaiva());
        
        edits = new TextField[] {editPvm, editPaikka, editKello, editAlinLampo,
                                editYlinLampo, editSademaara, editHuomiot};
        
    }
    
    
    /**
     * Aliohjelma jo olemassa olevan päivän muokkaamiseksi
     */
    private void muokkaa() {
        if ( paivaKohdalla == null) return;
        try {
            Paiva paiva = MuokkausController.kysyPaiva(null, paivaKohdalla.clone(), weathertracker);
            if ( paiva == null) return;
            weathertracker.korvaaTaiLisaa(paiva);
            hae(paiva.getTunnusNro());
        } catch (CloneNotSupportedException e) {
            //
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    
    /**
     * "Tulostaa" näyttöön päivän tiedot
     */
    private void naytaPaiva() {
        paivaKohdalla = chooserPaivat.getSelectedObject();
        if ( paivaKohdalla == null) return;
        
        MuokkausController.naytaPaiva(edits, paivaKohdalla);
        
        naytaSaa(editSaatila, paivaKohdalla);
    }
    
    /**
     * Näytetään pääikkunassa säätila tekstinä
     * @param text säätilan tekstikenttä
     * @param paiva päivä
     */
    private void naytaSaa(TextField text, Paiva paiva) {
        if (paiva == null) return;
        int numero = paiva.getSaatila();
        Saatila s = weathertracker.annaSaaId(numero);
        StringBuilder sb = new StringBuilder(s.toString());
        String jono = Mjonot.erota(sb, '|');
        jono = Mjonot.erota(sb, '|');
        text.setText(jono);
    }
    
    
    /**
     * Luetaan tiedosto
     * @return null jos onnistuu muuten virheteksti
     */
    protected String lueTiedostosta() {
        try {
            weathertracker.lueTiedostosta();
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null)
                Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }

    
    /**
     * Asetetaan controllerin weathertracker viite
     * @param weathertracker mihin viitataan
     */
    public void setWeatherTracker(WeatherTracker weathertracker) {
        this.weathertracker = weathertracker;
        naytaPaiva();
    }


    /**
     * Käyttäjälle avautuu aloitusikkuna, jossa hän voi päättää
     * aloitetaanko ohjelman käyttäminen
     * @return true jos aloitetaan käyttö, false jos ei
     */
    public boolean avaa() {
        ModalController.showModal(PaaIkkunaController.class.getResource("aloitusikkuna.fxml"),
                "WeatherTracker", null, "");
        lueTiedostosta();
        return true;
    }
    
    /**
     * Tulostaa listassa olevat päivät tekstialueeseen
     * @param text alue, johon tulostetaan
     */
    private void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki päivät");
            for (Paiva paiva : chooserPaivat.getObjects()) {
                tulosta(os, paiva);
                os.println();
            }
        } 
    }

    /**
     * Tulostetaan jäsenen tiedot
     * @param os tietovirta johon tulostetaan
     * @param paiva tulostettava päivä
     */
    public void tulosta(PrintStream os, final Paiva paiva) {
        os.println("----------------------------------------------");
        paiva.tulosta(os);
        int saaIdNro = paiva.getSaatila();
        Saatila saa = weathertracker.annaSaaId(saaIdNro);
        saa.tulosta(os);
    }

}