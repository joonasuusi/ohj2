package FXht;

import fi.jyu.mit.fxgui.Dialogs;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;


/**
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 17.1.2020
 *
 */
public class AloitusIkkunaController implements ModalControllerInterface<String> {
    @FXML private void handleAloita() {
        ModalController.showModal(AloitusIkkunaController.class.getResource("paaikkuna.fxml"),
                "Tarkastele", null, "");
    }

    @FXML private void handleLopeta() {
        Platform.exit();
    }
    
    @FXML private void handleTietoja() {
        ModalController.showModal(AloitusIkkunaController.class.getResource("tietoja.fxml"),
                "Tietoja", null, "");
    }
    
    @FXML private void handleApua() {
        apua();
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
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
    
}