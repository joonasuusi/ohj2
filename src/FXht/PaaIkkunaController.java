package FXht;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 7.2.2020
 *
 */
public class PaaIkkunaController implements ModalControllerInterface<String> {
    @FXML
    private Button textCancel;
    
    @FXML private void handleUusi() {
        ModalController.showModal(PaaIkkunaController.class.getResource("lisaysikkuna.fxml"),
                "Lisää uusi", null, "");
    }
    
    @FXML private void handleSaatila() {
        ModalController.showModal(SaatilaController.class.getResource("lisaaSaatila.fxml"),
                "Muokkaa säätiloja", null, "");
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
    
    @FXML private void handlePoistaSaa() {
        ModalController.showModal(PaaIkkunaController.class.getResource("poistaSaatila.fxml"),
                "Säätilan poisto", null, "");
    }
    
    @FXML private void handleCancel() {
        ModalController.closeStage(textCancel);
    }
    
    @FXML private void handleOK() {
        tallenna();
    }

    @Override
    public String getResult() {
        // 
        return null;
    }


    @Override
    public void handleShown() {
        // 
        
    }


    @Override
    public void setDefault(String oletus) {
        //
        
    }
    
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
    
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
    }
    

}
