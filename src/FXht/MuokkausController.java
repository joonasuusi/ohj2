package FXht;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import ht.wt.Paiva;
import ht.wt.SailoException;
import ht.wt.WeatherTracker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlleri päivän muokkaukselle ja lisäykselle
 * @author Joonas Uusi-Autti
 * @version 24.3.2020
 *
 */
public class MuokkausController implements ModalControllerInterface<Paiva>, Initializable {
    
    @FXML private TextField editPvm;
    @FXML private TextField editPaikka;
    @FXML private TextField editKello;
    @FXML private TextField editAlinLampo;
    @FXML private Button textCancel;
    @FXML private ComboBoxChooser<String> saatilaValikko;
    
    @FXML private void handleTallenna() {
        ModalController.closeStage(textCancel);
        //tallenna();
    }
    

    @FXML private void handleCancel() {
        ModalController.closeStage(textCancel);
    }

    //=========================== omia koodeja ==========================
    private Paiva paivaKohdalla;
    private TextField[] edits;
    WeatherTracker weathertracker;
    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    @Override
    public Paiva getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefault(Paiva oletus) {
        paivaKohdalla = oletus;
        naytaPaiva(paivaKohdalla);
        
    }
    
    
    /**
     * Tekee tarvittavat muut alustukset
     */
    private void alusta() {
        edits = new TextField[] {editPvm, editPaikka, editKello, editAlinLampo};

    }
    
    
    /**
     * Näytetään päivän tiedot TextField komponentteihin
     * @param edits taulukko, jossa tekstikenttiä
     * @param paiva näytettävä päivä
     */
    public static void naytaPaiva(TextField[] edits, Paiva paiva) {
        if (paiva == null) return;
        edits[0].setText(paiva.getPvm());
        edits[1].setText(paiva.getPaikka());
        edits[2].setText(paiva.getKello());
        edits[3].setText(String.valueOf(paiva.getAlinLampo()));
    }
    
    
    /**
     * Näytetään päivän tiedot TextField komponentteihin
     * @param paiva näytettävä päivä
     */
    public void naytaPaiva(Paiva paiva) {
        naytaPaiva(edits, paiva);
    }
    
    /**
     * Luodaan päivän kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null=sovellukselle
     * @param oletus mitä dataa näytetään oletuksena
     * @return null, jos painetaan Cancel, muuten täytetty tietue
     */
      public static Paiva kysyPaiva(Stage modalityStage, Paiva oletus) {
          return ModalController.showModal(MuokkausController.class.getResource("muokkausikkuna.fxml"), 
                  "Muokkaa", modalityStage, oletus, null);
      } 
    
    }
