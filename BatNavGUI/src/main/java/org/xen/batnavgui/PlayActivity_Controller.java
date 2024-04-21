package org.xen.batnavgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;


public class PlayActivity_Controller implements Initializable {

    @FXML
    private Text AffichageInfosJoueur;

    @FXML
    private Text AffichageInfosOrdi;

    @FXML
    private Text AffichageVictoire;

    @FXML
    private Button ActiverTriche;

    @FXML
    private GridPane GrilleAdverse;

    @FXML
    private GridPane GrilleJoueur;

    private boolean partieTerminee = false;

    Integer getColonne(ActionEvent event) {
        return GridPane.getColumnIndex(((Node) event.getSource())) == null ? 0 : GridPane.getColumnIndex(((Node) event.getSource()));
    }

    Integer getLigne(ActionEvent event) {
        return GridPane.getRowIndex(((Node) event.getSource())) == null ? 0 : GridPane.getRowIndex(((Node) event.getSource()));
    }


    public void ClicGrilleAdverse(ActionEvent event) {

        if (partieTerminee)
            return;

        Integer colonne = getColonne(event);
        Integer ligne = getLigne(event);

        System.out.print("x" + colonne + " y" + ligne + '\n');

        int res = MainApplication.batailleUI.EffectuerTir(MainApplication.batailleUI.grilleOrdi, ligne, colonne);

        String color = "grey";

        String resultatTirTexte = "";

        switch (res) {
            case 0:
                color = "blue";
                resultatTirTexte = "Dans l'eau...";
                break;
            case 1:
                if (MainApplication.batailleUI.EstPerdant(MainApplication.batailleUI.grilleOrdi))
                    FinDePartie(true);
                System.out.print("COULÉ");
                resultatTirTexte = "Bateau coulé";
                color = "red";
                break;
            case 2:
                resultatTirTexte = "Bateau touché";
                color = "red";
                break;
        }

        AffichageInfosJoueur.setText("Tir en x:" + colonne + " & y:" + ligne + "\n" + resultatTirTexte);

        Button btn = (Button) (event.getSource());
        btn.setOpacity(0.5);
        btn.setStyle("-fx-background-color:" + color + "; -fx-border-width:0; -fx-border-radius:0; -fx-background-radius:0;");
        btn.setMaxHeight(29);
        btn.toFront();
        btn.setDisable(true);

        BatailleUI.Coord tirOrdi = MainApplication.batailleUI.EffectuerTirOrdi();

        if (partieTerminee)
            return;

        switch (tirOrdi.o) {
            case 0:
                color = "blue";
                resultatTirTexte = "Dans l'eau...";
                break;
            case 1:
                if (MainApplication.batailleUI.EstPerdant(MainApplication.batailleUI.grilleJoueur))
                    FinDePartie(false);
                System.out.print("COULÉ");
                resultatTirTexte = "Bateau coulé";
                color = "red";
                break;
            case 2:
                resultatTirTexte = "Bateau touché";
                color = "red";
                break;
        }

        btn = new Button();
        btn.setOpacity(0.5);
        btn.setStyle("-fx-background-color:" + color + "; -fx-border-width:0; -fx-border-radius:0; -fx-background-radius:0;");
        btn.setMaxHeight(29);
        btn.setPrefWidth(29);
        btn.toFront();
        btn.setDisable(true);
        GrilleJoueur.add(btn, tirOrdi.x, tirOrdi.y);

        AffichageInfosOrdi.setText("Tir en x:" + tirOrdi.x + " & y:" + tirOrdi.y + "\n" + resultatTirTexte);

    }

    private void FinDePartie(boolean gagne) {
        partieTerminee = true;
        if (gagne)
            AffichageVictoire.setText("Partie gagnée");
        else
            AffichageVictoire.setText("Partie perdue");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 5; i++) {
            ImageView im = new ImageView(Objects.requireNonNull(PlayActivity_Controller.class.getResource("images/Boats " + (i + 1) + ".png")).toString());

            im.setFitHeight(30);
            im.setPreserveRatio(true);
            im.setMouseTransparent(true);
            im.toBack();

            BatailleUI.Coord co = MainApplication.batailleUI.coordsBateauxJoueur[i];

            if (co.o == 2)
                im.getTransforms().add(new Rotate(90, 15, 15));


            GrilleJoueur.add(im, co.x, co.y);
        }

        MainApplication.batailleUI.initGrilleOrdi();

        if (MainApplication.batailleUI.triche)
            AfficherTriche();
    }

    @FXML
    void AfficherTriche() {
        ActiverTriche.setDisable(true);

        for (int i = 0; i < 5; i++) {
            ImageView im = new ImageView(Objects.requireNonNull(PlayActivity_Controller.class.getResource("images/Boats " + (i + 1) + ".png")).toString());

            im.setFitHeight(30);
            im.setPreserveRatio(true);
            im.setMouseTransparent(true);

            BatailleUI.Coord co = MainApplication.batailleUI.coordsBateauxAdversaire[i];

            if (co.o == 2)
                im.getTransforms().add(new Rotate(90, 15, 15));


            GrilleAdverse.add(im, co.x, co.y);
        }
    }

    public void MenuPrincipal(ActionEvent event) throws IOException {
        MainApplication.PreparerNouvellePartie();
    }

    public void Quitter(ActionEvent event) {
        exit();
    }
}
