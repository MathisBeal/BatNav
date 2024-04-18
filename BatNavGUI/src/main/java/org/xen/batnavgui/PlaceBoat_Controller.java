package org.xen.batnavgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;

public class PlaceBoat_Controller {

    public Button BoutonContinuer;
    private Integer BateauSelectione = 1;

    @FXML
    private ImageView ApercuBateau;

    @FXML
    private GridPane Grille;


    private ImageView[] BateauxImg = new ImageView[5];
    private Boolean[] BateauxPlaces = new Boolean[5];
    private Integer[][] BateauCoord = new Integer[5][2];

    public PlaceBoat_Controller() {
        super();

        ImageView img;

        for (int i = 0; i < 5; i++) {
            img = new ImageView("org/xen/batnavgui/Images/Boats "+(i+1)+".png");
            img.setMouseTransparent(true);
            BateauxImg[i] = img;

            BateauxPlaces[i] = false;
        }
    }

    @FXML
    void SetHorizontal(ActionEvent event) {
        ApercuBateau.getTransforms().clear();
        ApercuBateau.getTransforms().add(new Rotate(90, 25, 25));


    }

    @FXML
    void SetVertical(ActionEvent event) {
        ApercuBateau.getTransforms().clear();
    }



    public void SelectionnerBateau(ActionEvent actionEvent) {
        BateauSelectione = Integer.parseInt(((Button) actionEvent.getSource()).getText());
        System.out.println(BateauSelectione-1);

        ApercuBateau.setImage(new Image("org/xen/batnavgui/Images/Boats "+BateauSelectione+".png"));
    }

    Integer getColonne(ActionEvent event){
        return GridPane.getColumnIndex(((Node) event.getSource()))==null ? 0 : GridPane.getColumnIndex(((Node) event.getSource()));
    }
    Integer getLigne(ActionEvent event){
        return GridPane.getRowIndex(((Node) event.getSource()))==null ? 0 : GridPane.getRowIndex(((Node) event.getSource()));
    }

    boolean TousPlaces() {
        for (int i = 0; i < 5; i++) {
            if (!BateauxPlaces[i])
                return false;
        }
        return true;
    }

    public void ClicGrille(ActionEvent actionEvent) {
        Integer x = getColonne(actionEvent);
        Integer y = getLigne(actionEvent);

        if (!BateauxPlaces[BateauSelectione-1]) {
            if (ApercuBateau.getTransforms().isEmpty()){
                BateauxImg[BateauSelectione-1].getTransforms().clear();
            }
            else {
                BateauxImg[BateauSelectione-1].getTransforms().clear();
                BateauxImg[BateauSelectione-1].getTransforms().add(ApercuBateau.getTransforms().getLast());
            }
            Grille.add(BateauxImg[BateauSelectione-1], x, y);
            BateauxPlaces[BateauSelectione-1] = true;
        }
        else {
            if (ApercuBateau.getTransforms().isEmpty()){
                BateauxImg[BateauSelectione-1].getTransforms().clear();
            }
            else {
                BateauxImg[BateauSelectione-1].getTransforms().clear();
                BateauxImg[BateauSelectione-1].getTransforms().add(ApercuBateau.getTransforms().getLast());
            }
            GridPane.setConstraints(BateauxImg[BateauSelectione-1], x, y);
        }
        BoutonContinuer.setDisable(!TousPlaces());
        BateauCoord[BateauSelectione] = new Integer[]{x, y};
    }
}
