import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Klasse for grafisk paneler og elementer
 */
public class GUI {

    static final int STR_VINDU_HOYDE = 650;
    static final int STR_VINDU_BREDDE = 540;
    static final int STR_VINDU_SPILLPANEL = 500;
    static final int ANTALL_RAD_KOL = 16;
    static final int STR_ELEMENT = Math.round(STR_VINDU_SPILLPANEL / ANTALL_RAD_KOL);
    static final int ELEMENT_PADDING = 5;
    static final int STR_ELEMENT_LEVENDE = STR_ELEMENT -ELEMENT_PADDING;
    static final int ANTALL_LEVEL = 3;


    static BorderPane panel;
    static Pane spillPanel;
    static Timeline timeline;
    static final Text txtLiv = new Text();
    static final Text txtscore = new Text();
    static final Text txtLevel = new Text();

    /**
     * Her lages alt som trengst for at scenen kan bli
     * satt i javafx
     * @param primaryStage får inn stagen
     * @return returner scene til Main classen
     */
    static public Scene setStage(Stage primaryStage){
        panel = new BorderPane();
        spillPanel = new Pane();
        setStilPanel();
        primaryStage.setTitle("PacManOblig");
        Scene scene = new Scene(panel, STR_VINDU_BREDDE, STR_VINDU_HOYDE);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{ //Fix for at animation timeline skal avslutte
            timeline.stop();                //sikkelig ved spill slutt
            Platform.exit();
            System.exit(0);
        });
        return scene;

    }
    static private  void setStilPanel(){
        panel.setStyle("-fx-background-color: black");

    }

    /**
     * lager topp panelet
     * hvor informasjon om score og level blir
     * vist på skjermen
     * @return retunere dette panelet til borderpanet
     */
   static public Pane topPanel() {
       HBox score = new HBox();
       score.setPrefWidth(STR_VINDU_BREDDE);
       txtscore.setTextAlignment(TextAlignment.LEFT);
       txtLevel.setTextAlignment(TextAlignment.RIGHT);
       score.setPadding(new Insets(5, 5, 5, 5));
       txtscore.setFont(Font.font("IMPACT", GUI.STR_ELEMENT_LEVENDE));
       txtscore.setFill(Color.WHITE);
       txtLevel.setFont(Font.font("IMPACT", GUI.STR_ELEMENT_LEVENDE));
       txtLevel.setFill(Color.WHITE);
       txtscore.setText("Score: " + PacMan.getScore());
       txtLevel.setText("    Level: "+ Main.level);
       score.setAlignment(Pos.CENTER);
       score.getChildren().addAll(txtscore,txtLevel);
       return score;
    }

    /**
     * Lager bunn panelet
     * hvor infromasjon om liv blir
     * vist på skjermen
     * @return retunere dette panelet til borderpanet
     */
    static public Pane bunnPanel() {
        HBox antallLiv = new HBox();
        antallLiv.setPadding(new Insets(5, 5, 5, 5));
        txtLiv.setFont(Font.font("IMPACT", GUI.STR_ELEMENT_LEVENDE));
        txtLiv.setFill(Color.ORANGE);
        txtLiv.setText("Liv igjen: " + PacMan.getLiv());
        antallLiv.getChildren().add(txtLiv);
        return antallLiv;
    }
}
