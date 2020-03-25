package FXht;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import ht.wt.SailoException;
import ht.wt.WeatherTracker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * @author Joonas Uusi-Autti
 * @version 24.2.2020
 *  Keksitään parempi nimi
 */
public class SaatilaController implements ModalControllerInterface<String> {
    
    @FXML private Button textCancel;
    
    @FXML private TextField saaLisays;
    
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    @FXML private void handleCancel() {
        ModalController.closeStage(textCancel);
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }
    
    // ================= omat koodit ===============
    WeatherTracker weathertracker;

    
    private void tallenna() {
        //Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
        try {
            weathertracker.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallentamisessa tapahtui virhe: " + e.getMessage());
        } 
    }
    
    /**
     * Asetetaan controllerin weathertracker viite
     * @param weathertracker mihin viitataan
     */
    public void setWeatherTracker(WeatherTracker weathertracker) {
        this.weathertracker = weathertracker;
    }

}
