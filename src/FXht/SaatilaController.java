package FXht;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import ht.wt.Saatila;
import ht.wt.SailoException;
import ht.wt.WeatherTracker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 24.2.2020
 *  Keksitään parempi nimi
 */
public class SaatilaController implements ModalControllerInterface<WeatherTracker> {
    
    @FXML private Label labelVirhe;
    @FXML private TextField saaLisays;
    @FXML private ComboBoxChooser<Saatila> saaLista;
    
    @FXML private void handleTallenna() {
        tallenna();
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleCancel() {
        ModalController.closeStage(labelVirhe);
    }

    @Override
    public WeatherTracker getResult() {
        return weathertracker;
    }

    @Override
    public void handleShown() {
        asetaChooser();
    }

    @Override
    public void setDefault(WeatherTracker oletus) {
        weathertracker = oletus;
        
    }
    
    // ================= omat koodit ===============
    private WeatherTracker weathertracker;

    /**
     * TODO: Lisää kommentit
     */
    private void tallenna() {
        try {
            weathertracker.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallentamisessa tapahtui virhe: " + e.getMessage());
        } 
    }
    
    /**
     * Hakee säiden valintalistan
     */
    public void asetaChooser() {
        saaLista.clear();
        String[] rivit = new String[weathertracker.getSaatilat()];
        for (int i = 0; i < weathertracker.getSaatilat(); i++) {
            Saatila saa = weathertracker.annaSaa(i);
            rivit[i] = saa.getSaatila();
        }
        saaLista.setRivit(rivit);
    }
   
}
