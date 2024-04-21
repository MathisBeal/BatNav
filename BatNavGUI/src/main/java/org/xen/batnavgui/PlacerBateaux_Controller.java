package org.xen.batnavgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;

import java.io.IOException;

/**
 * @author Mathis BÉAL
 * @version 1.0
 */
public class PlacerBateaux_Controller {

    public Button BoutonContinuer;
    private Integer BateauSelectione = 1;

    @FXML
    private ImageView ApercuBateau;

    @FXML
    private GridPane Grille;

    // Stocke les ImageView des bateaux du joueur
    private ImageView[] BateauxImg = new ImageView[5];

    /**
     * Initialise toutes les ImageView(s) des bateaux
     */
    public PlacerBateaux_Controller() {
        super();

        ImageView img;

        for (int i = 0; i < 5; i++) {
            img = new ImageView("org/xen/batnavgui/Images/Boats " + (i + 1) + ".png");
            img.setMouseTransparent(true);
            BateauxImg[i] = img;

            ApplicationPrincipale.batailleUI.bateauxPlaces[i] = false;
        }
    }

    // Orientation des bateaux (Horizontal par défaut)
    int orientation = 1;

    @FXML
    void SetHorizontal(ActionEvent event) {
        ApercuBateau.getTransforms().clear();

        orientation = 1;
    }

    @FXML
    void SetVertical(ActionEvent event) {
        ApercuBateau.getTransforms().clear();
        ApercuBateau.getTransforms().add(new Rotate(90, 25, 25));

        orientation = 2;
    }

    /**
     * Sélectionne le bateau suivant le bouton cliqué
     *
     * @param actionEvent
     */
    public void SelectionnerBateau(ActionEvent actionEvent) {
        BateauSelectione = Integer.parseInt(((Button) actionEvent.getSource()).getText());
        System.out.println(BateauSelectione - 1);

        ApercuBateau.setImage(new Image("org/xen/batnavgui/Images/Boats " + BateauSelectione + ".png"));
    }

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
     * Fait les actions voulues lors d'un clic dans la grille de placement
     * Comprend le placage du bateau en back-end et l'affichage du bateau en front-end
     *
     * @param actionEvent
     */
    public void ClicGrille(ActionEvent actionEvent) {


        Integer x = getColonne(actionEvent);
        Integer y = getLigne(actionEvent);

        boolean res = ApplicationPrincipale.batailleUI.DemanderPlacement(BateauSelectione, x, y, orientation);
        if (res) {
            if (!ApplicationPrincipale.batailleUI.bateauxPlaces[BateauSelectione - 1]) {
                if (ApercuBateau.getTransforms().isEmpty()) {
                    BateauxImg[BateauSelectione - 1].getTransforms().clear();
                } else {
                    BateauxImg[BateauSelectione - 1].getTransforms().clear();
                    BateauxImg[BateauSelectione - 1].getTransforms().add(ApercuBateau.getTransforms().getLast());
                }
                Grille.add(BateauxImg[BateauSelectione - 1], x, y);
                ApplicationPrincipale.batailleUI.bateauxPlaces[BateauSelectione - 1] = true;
            } else {
                if (ApercuBateau.getTransforms().isEmpty()) {
                    BateauxImg[BateauSelectione - 1].getTransforms().clear();
                } else {
                    BateauxImg[BateauSelectione - 1].getTransforms().clear();
                    BateauxImg[BateauSelectione - 1].getTransforms().add(ApercuBateau.getTransforms().getLast());
                }
                GridPane.setConstraints(BateauxImg[BateauSelectione - 1], x, y);
            }
            ApplicationPrincipale.batailleUI.coordsBateauxJoueur[BateauSelectione - 1].x = x;
            ApplicationPrincipale.batailleUI.coordsBateauxJoueur[BateauSelectione - 1].y = y;
            ApplicationPrincipale.batailleUI.coordsBateauxJoueur[BateauSelectione - 1].o = orientation;

            BoutonContinuer.setDisable(!BatailleUI.TousVrais(ApplicationPrincipale.batailleUI.bateauxPlaces));
        }
    }

    @FXML
    void LancerPartie(ActionEvent event) throws IOException {
        ApplicationPrincipale.ChangerScene(ApplicationPrincipale.JOUER_FXML, "Bataille navale", 800, 700);
    }
}
