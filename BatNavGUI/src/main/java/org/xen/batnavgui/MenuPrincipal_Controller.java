package org.xen.batnavgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import static javafx.application.Platform.exit;

/**
 * @author Mathis BÉAL
 * @version 1.0
 */
public class MenuPrincipal_Controller {

    @FXML
    void JouerAvecTriche(ActionEvent event) throws IOException {
        Start(true);
    }

    @FXML
    void JouerSansTriche(ActionEvent event) throws IOException {
        Start(false);
    }

    @FXML
    void Quitter(ActionEvent event) {
        exit();
    }

    void Start(boolean triche) throws IOException {
        ApplicationPrincipale.batailleUI.triche = triche;
        ApplicationPrincipale.ChangerScene(ApplicationPrincipale.PLACER_BATEAUX_FXML, "Plaçage des bateaux");
    }

}
