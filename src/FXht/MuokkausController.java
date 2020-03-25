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
import javafx.scene.control.Label;
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
    @FXML private Label labelVirhe;
    @FXML private ComboBoxChooser<String> saatilaValikko;
    
    @FXML private void handleTallenna() {
        if (paivaKohdalla != null && paivaKohdalla.getPvm().trim().equals("")) {
            naytaVirhe("Päivämäärä ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    

    @FXML private void handleCancel() {
        paivaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

    //=========================== omia koodeja ==========================
    private Paiva paivaKohdalla;
    private TextField[] edits;
    WeatherTracker weathertracker;
    
    @Override
    public void handleShown() {
        editPvm.requestFocus();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }

    @Override
    public Paiva getResult() {
        return paivaKohdalla;
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
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosPaivaan(k,(TextField)(e.getSource())));
        }
    }
    
    /**
     * Käsitellään päivään tullut muutos
     * @param k kentän numero
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosPaivaan(int k, TextField edit) {
        if (paivaKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
        case 1 : virhe = paivaKohdalla.setPvm(s); break;
        case 2 : virhe = paivaKohdalla.setPaikka(s); break;
        case 3 : virhe = paivaKohdalla.setKello(s); break;
        //case 4 : virhe = paivaKohdalla.setAlinLampo(s); break;
        default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
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
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
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
