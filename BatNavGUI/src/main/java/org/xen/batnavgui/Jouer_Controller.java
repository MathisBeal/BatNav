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


/**
 * @author Mathis BÉAL
 * @version 1.0
 */
public class Jouer_Controller implements Initializable {

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

    /**
     * Renvoie la l'indice colonne du GridPane dans lequel il se trouve
     *
     * @param event Source
     * @return int
     */
    Integer getColonne(ActionEvent event) {
        return GridPane.getColumnIndex(((Node) event.getSource())) == null ? 0 : GridPane.getColumnIndex(((Node) event.getSource()));
    }

    /**
     * Renvoie la l'indice ligne du GridPane dans lequel il se trouve
     *
     * @param event Source
     * @return int
     */
    Integer getLigne(ActionEvent event) {
        return GridPane.getRowIndex(((Node) event.getSource())) == null ? 0 : GridPane.getRowIndex(((Node) event.getSource()));
    }

    /**
     * Fait les actions voulues lors d'un clic dans la grille adverse
     * <ul>
     *     <li>
     *         Fait un tir du joueur
     *     </li>
     *     <li>
     *         Fait un tir du robot
     *     </li>
     *     <li>
     *         Vérifie si l'un gagne
     *     </li>
     * </ul>
     *
     * @param event
     */
    public void ClicGrilleAdverse(ActionEvent event) {

        if (partieTerminee)
            return;

        Integer colonne = getColonne(event);
        Integer ligne = getLigne(event);

        System.out.print("x" + colonne + " y" + ligne + '\n');

        // Fait un tir du joueur
        int res = ApplicationPrincipale.batailleUI.EffectuerTir(ApplicationPrincipale.batailleUI.grilleOrdi, ligne, colonne);

        String color = "grey";

        String resultatTirTexte = "";

        switch (res) {
            case 0:
                // Tir dans l'eau
                color = "blue";
                resultatTirTexte = "Dans l'eau...";
                break;
            case 1:
                // Bateau adverse coulé

                // Vérifie si gagné
                if (ApplicationPrincipale.batailleUI.EstPerdant(ApplicationPrincipale.batailleUI.grilleOrdi))
                    FinDePartie(true);
                resultatTirTexte = "Bateau coulé";
                color = "red";
                break;
            case 2:
                // Bateau adverse touché
                resultatTirTexte = "Bateau touché";
                color = "red";
                break;
        }

        AffichageInfosJoueur.setText("Tir en x:" + colonne + " & y:" + ligne + "\n" + resultatTirTexte);

        // Désactive la possibilité de cliquer sur le bouton et change la couleur en fonction du résultat du tir
        Button btn = (Button) (event.getSource());
        btn.setOpacity(0.5);
        btn.setStyle("-fx-background-color:" + color + "; -fx-border-width:0; -fx-border-radius:0; -fx-background-radius:0;");
        btn.setMaxHeight(29);
        btn.toFront();
        btn.setDisable(true);

        // Fait la même chose pour l'ordinateur
        BatailleUI.Coord tirOrdi = ApplicationPrincipale.batailleUI.EffectuerTirOrdi();

        if (partieTerminee)
            return;

        switch (tirOrdi.o) {
            case 0:
                color = "blue";
                resultatTirTexte = "Dans l'eau...";
                break;
            case 1:
                if (ApplicationPrincipale.batailleUI.EstPerdant(ApplicationPrincipale.batailleUI.grilleJoueur))
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

    /**
     * Fonction se lancant automatiquement à l'affiche de l'activité
     * Permet d'afficher les bateaux du joueur et ceux de l'adversaire si la triche est activée
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 5; i++) {
            ImageView im = new ImageView(Objects.requireNonNull(Jouer_Controller.class.getResource("images/Boats " + (i + 1) + ".png")).toString());

            im.setFitHeight(30);
            im.setPreserveRatio(true);
            im.setMouseTransparent(true);
            im.toBack();

            BatailleUI.Coord co = ApplicationPrincipale.batailleUI.coordsBateauxJoueur[i];

            if (co.o == 2)
                im.getTransforms().add(new Rotate(90, 15, 15));


            GrilleJoueur.add(im, co.x, co.y);
        }

        ApplicationPrincipale.batailleUI.initGrilleOrdi();

        if (ApplicationPrincipale.batailleUI.triche)
            AfficherTriche();
    }

    /**
     * Affiche les bateaux de l'adversaire (triche)
     */
    @FXML
    void AfficherTriche() {
        ApplicationPrincipale.batailleUI.triche = true;
        ActiverTriche.setDisable(true);

        for (int i = 0; i < 5; i++) {
            ImageView im = new ImageView(Objects.requireNonNull(Jouer_Controller.class.getResource("images/Boats " + (i + 1) + ".png")).toString());

            im.setFitHeight(30);
            im.setPreserveRatio(true);
            im.setMouseTransparent(true);

            BatailleUI.Coord co = ApplicationPrincipale.batailleUI.coordsBateauxAdversaire[i];

            if (co.o == 2)
                im.getTransforms().add(new Rotate(90, 15, 15));


            GrilleAdverse.add(im, co.x, co.y);
        }
    }

    public void MenuPrincipal(ActionEvent event) throws IOException {
        ApplicationPrincipale.PreparerNouvellePartie();
    }

    public void Quitter(ActionEvent event) {
        exit();
    }
}
