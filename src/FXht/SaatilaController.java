package FXht;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Joonas Uusi-Autti
 * @version 24.2.2020
 *  Keksitään parempi nimi
 */
public class SaatilaController implements ModalControllerInterface<String> {
    
    @FXML
    private Button textCancel;
    
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
    
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
    }

}
