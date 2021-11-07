import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Abstract klasse som arver fra stackpane
 * har Imageview, Posisjon og ElementNavn
 * som arves videre til alle andre sub elementer
 * i spillet
 */
public abstract class Element extends StackPane {



    private ImageView imageView;
    private final Posisjon posisjon;
    protected ElementNavn type;

    Element(ElementNavn element, int rad, int kol) {
        this.type = element;
        imageView = hentElementBilde(element);
        posisjon = new Posisjon(rad,kol);
        this.getChildren().add(imageView);
    }

    /**
     * Retunere hvilken rad objektet er på
     * @return rad
     */
    public int getRad(){
        return posisjon.rad;
    }

    /**
     * Retunere hvilken kollone objektet er på
     * @return kolonne
     */
    public int getKol(){
        return posisjon.kolonne;
    }

    /**
     * Flytter objektet en plass
     * enten opp eller ned basert på verdien
     * til variabel opp
     * @param opp
     */
    public void setRad(boolean opp){
        this.posisjon.rad += opp?-1:1;
    }
    /**
     * Flytter objektet en plass
     * enten høyre eller venstre basert på verdien
     * til variabel venstre
     * @param venstre
     */
    public void setKol(boolean venstre){
        this.posisjon.kolonne += venstre?-1:1;
    }

    /**
     * Setter nytt imageview basert
     * på navnet den får inn fra
     * ElementNavn enumet
     * @param elementNavn
     */
    public void setImageView(ElementNavn elementNavn){
        this.type = elementNavn;
        imageView.setImage(hentBilde(elementNavn));
    }

    /**
     * Sjekker om et hvis element er av subtypen
     * av ghost
     * @return
     */
    public boolean isGhost(){
        return (this.type==ElementNavn.CLYDE ||
                this.type==ElementNavn.INKY ||
                this.type==ElementNavn.BLINKY ||
                this.type==ElementNavn.PINKY ||
                this.type==ElementNavn.REDD);
    }

    /**
     * Henter imageviewet
     * @return
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Setter nytt imageview
     * @param imageView
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * Henter ut imageviet via bilde som
     * blir lagd via hentBilde metoden
     * @param element
     * @return
     */
    static public ImageView hentElementBilde(ElementNavn element) {
        ImageView showFigur = new ImageView(hentBilde(element));
        showFigur.setFitHeight(GUI.STR_ELEMENT);
        showFigur.setFitWidth(GUI.STR_ELEMENT);
        return showFigur;
    }

    /**
     * Henter bilde fra harddiskens
     * setter dette inn i en image objekt
     * hvis bilde funnet
     * @param element
     * @return
     */
    static private Image hentBilde(ElementNavn element){
        String figur = element.toString();
        Image figurImage = null;
        try { //Flest png filer, så sjekker først om det er av .png filtype
            figurImage = new Image(Element.class.getResourceAsStream("Ressurser\\" + figur + ".png"));
        } catch (Exception e1) {
            try {
                figurImage = new Image(Element.class.getResourceAsStream("Ressurser\\" + figur + ".gif"));
            } catch (Exception e2) {
                System.out.println("Filtype ukjent" + e2.getMessage());
            }
        }
        return figurImage;
    }
}
