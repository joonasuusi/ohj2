package FXht;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 7.2.2020
 *
 */
public class PaaIkkunaController implements Initializable {

    @FXML private Button textCancel;
    @FXML ListChooser<Paiva> chooserPaivat;
    @FXML ListChooser<Saatila> chooserSaatilat;
    @FXML private ScrollPane panelPaiva;
    @FXML private ScrollPane panelSaa;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        // TODO Auto-generated method stub
        alusta();
    }
    
    
    @FXML private void handleUusi() {
        // ModalController.showModal(PaaIkkunaController.class.getResource("lisaysikkuna.fxml"),
           //     "Lisää uusi", null, "");
        uusiPaiva();
    }
    
    @FXML private void handleSaatila() {
        //ModalController.showModal(PaaIkkunaController.class.getResource("lisaaSaatila.fxml"),
        //        "Muokkaa säätiloja", null, "");
        uusiSaa();
    }
    
    @FXML private void handlePoistaSaa() {
        ModalController.showModal(PaaIkkunaController.class.getResource("poistaSaatila.fxml"),
                "Säätilan poisto", null, "");
    }
    
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    @FXML private void handleMuokkaa() {
        ModalController.showModal(PaaIkkunaController.class.getResource("muokkausikkuna.fxml"),
                "Muokkaa", null, "");
    }

    @FXML private void handleTallenna() {
        tallenna();
    }
    
    @FXML private void handleApua() {
        apua();
    }
    
    @FXML private void handleTietoja() {
        ModalController.showModal(PaaIkkunaController.class.getResource("tietoja.fxml"),
                "Tietoja", null, "");
    }
    
    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }
    
    @FXML private void handlePoista() {
        boolean vastaus = Dialogs.showQuestionDialog("Poistetaanko?",
                "Poistetaanko päivämäärä: 12.3.2020", "Kyllä", "Ei");
        // if (vastaus) poistaPvm(true)
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(textCancel);
    }
    
    @FXML private void handleOK() {
        tallenna();
    }

    // =============== omat koodit ===============
    
    WeatherTracker weathertracker;
    private TextArea areaPaiva = new TextArea();
    private Paiva paivaKohdalla;
    private Saatila saa;
    
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
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
    }
    
    
    /**
     * Lisätään uusi päivämäärä tiedoilla näyttöön
     */
    private void uusiPaiva() {
        Paiva uusi = new Paiva();
        uusi.taytaPvmTiedoilla(); // TODO: korvaa oikeasti dialogilla(se on jo) handleuudessa
        
        try {
            weathertracker.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getTunnusNro());
        
    }
    
    private void uusiSaa() {
        if ( paivaKohdalla == null ) return;  
        saa = new Saatila();   
        saa.paivanSaa();  
        weathertracker.lisaa(saa);
        hae(paivaKohdalla.getTunnusNro());
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
             if (paiva.getPvm().equals(pnro)) index = i;
            chooserPaivat.add(paiva.getPvm(), paiva);
        }
        chooserPaivat.setSelectedIndex(index);
    }

    
    /**
     * Alustetaan ja luodaan näyttöön uusi päivä
     */
    private void alusta() {
        panelPaiva.setContent(areaPaiva);
        areaPaiva.setFont(new Font("Courier New", 12));
        panelPaiva.setFitToHeight(true);
        
        chooserPaivat.clear();
        chooserPaivat.addSelectionListener(e -> naytaPaiva());

    }
    

    private void naytaPaiva() {
        paivaKohdalla = chooserPaivat.getSelectedObject();
        if ( paivaKohdalla == null) return;
        areaPaiva.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaPaiva)) {
            paivaKohdalla.tulosta(os);
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

}
