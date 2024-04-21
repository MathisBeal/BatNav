package org.xen.batnavgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class MainMenu_Controller {

    @FXML
    void JouerCheated(ActionEvent event) throws IOException {
        Start(true);
    }

    @FXML
    void JouerNoCheat(ActionEvent event) throws IOException {
        Start(false);
    }

    @FXML
    void Quitter(ActionEvent event) {
        exit();
    }

    void Start(boolean cheating) throws IOException {
        // TODO : gérer la partie du paramètre <cheating>

        MainApplication.batailleUI.triche = cheating;
        MainApplication.ChangeScene(MainApplication.PLACE_BOATS_FXML, "Plaçage des bateaux");
    }

}
