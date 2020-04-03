package FXht;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
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
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }
    
    @FXML private void handlePoista() {
        boolean vastaus = Dialogs.showQuestionDialog("Poistetaanko?",
                "Poistetaanko päivämäärä " + chooserPaivat.getSelectedText().toString(), "Kyllä", "Ei");
        if(vastaus == true) Dialogs.showMessageDialog("Ei osata vielä poistaa");
        // if (vastaus) poistaPvm(true)
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
    
    
    /**
     * Lisätään uusi päivämäärä tiedoilla näyttöön
     */
    private void uusiPaiva() {
        Paiva uusi = new Paiva();
        uusi = MuokkausController.kysyPaiva(null, uusi);
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
        chooserPaivat.clear();
        
        int index = 0;
        for (int i = 0; i < weathertracker.getPaivat(); i++) {
            Paiva paiva = weathertracker.annaPaiva(i);
            if (paiva.getTunnusNro() == pnro) index = i;
            chooserPaivat.add(paiva.getPvm(), paiva);
        }
        chooserPaivat.setSelectedIndex(index);
    }

    
    /**
     * Alustetaan ja luodaan näyttöön uusi päivä
     */
    private void alusta() {
        panelPaiva.setFitToHeight(true);
        
        chooserPaivat.clear();
        chooserPaivat.addSelectionListener(e -> naytaPaiva());
        
        edits = new TextField[] {editPvm, editPaikka, editKello, editAlinLampo,
                                editYlinLampo, editSademaara, editHuomiot, editSaatila};

    }
    
    
    /**
     * Aliohjelma jo olemassa olevan päivän muokkaamiseksi
     */
    private void muokkaa() {
        if ( paivaKohdalla == null) return;
        try {
            Paiva paiva = MuokkausController.kysyPaiva(null, paivaKohdalla.clone());
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
        Saatila s = weathertracker.annaSaa(numero-1);
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
}
