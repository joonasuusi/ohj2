package FXht;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import ht.wt.Paiva;
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
public class paivaController implements ModalControllerInterface<Paiva>, Initializable {
    @FXML private TextField editPvm;
    @FXML private TextField editPaikka;
    @FXML private TextField editKello;
    @FXML private TextField editAlinLampo;
    @FXML private Button textCancel;
    
    @FXML private void handleTallenna() {
        ModalController.closeStage(textCancel);
        //tallenna();
    }
    
    @FXML private void handleCancel() {
        ModalController.closeStage(textCancel);
    }

    //=========================== omia koodeja ==========================
    private Paiva paivaKohdalla;
    
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
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
     * Näytetään päivän tiedot TextField komponentteihin
     * @param paiva näytettävä päivä
     */
    public void naytaPaiva(Paiva paiva) {
        if (paiva == null) return;
        editPvm.setText(paiva.getPvm());
        editPaikka.setText(paiva.getPaikka());
        editKello.setText(paiva.getKello());
        editAlinLampo.setText(String.valueOf(paiva.getAlinLampo()));
    }
    
/**
 * Luodaan päivän kysymisdialogi ja palautetaan sama tietue muutettuna tai null
 * TODO: korjattava toimimaan
 * @param modalityStage mille ollaan modaalisia, null=sovellukselle
 * @param oletus mitä dataa näytetään oletuksena
 * @return null, jos painetaan Cancel, muuten täytetty tietue
 */
  public static Paiva kysyPaiva(Stage modalityStage, Paiva oletus) {
      return ModalController.showModal(PaaIkkunaController.class.getResource("muokkausikkuna.fxml"), "Muokkaa", modalityStage, oletus, null);
  } //onko PaaIkkunaController vrt vesalla paivaController tässä

}
