import javafx.animation.TranslateTransition;
import javafx.util.Duration;

/**
 * Abstract klasse for alle
 * elementer som kan bevege seg på
 * banen.
 */

public abstract class LevendeElement extends Element {

    protected int hastighet = 10;

    private TranslateTransition tt;

    LevendeElement(ElementNavn element, int rad, int kol) {
        super(element, rad, kol);
        getImageView().setFitHeight(GUI.STR_ELEMENT_LEVENDE);
        getImageView().setFitWidth(GUI.STR_ELEMENT_LEVENDE);
    }

    /**
     * Flytter det levende elementet
     * en avstand høyre eller venstre
     * som er tilsvarende
     * bredden på elementet
     *
     * @param venstre
     * @return
     */
    public boolean flyttX(boolean venstre) {
        int flytt = venstre ? -1 : 1;             //Flytter ikke hvis det er en vegg
        if (Bane.getElement(this.getRad(), (this.getKol() + flytt)).type == ElementNavn.VEGG) {
            return false;
        } else { //Flyttes seg fra punkt a -> b på 16 milisekund
            tt = new TranslateTransition(Duration.millis(16), this);
            tt.setByX(venstre ? -this.getWidth() : this.getWidth());
            tt.play();
            tt.setOnFinished(e -> GUI.timeline.play());
        }

        if (this.type == ElementNavn.PACMAN) { //Hvis element som flytter seg er pacman og det er
            //av type mat vil pacman spise den
            if (Bane.getElement(this.getRad(), (this.getKol() + flytt)).type == ElementNavn.MAT
                    || Bane.getElement(this.getRad(), (this.getKol() + flytt)).type == ElementNavn.SUPERMAT) {
                this.spiser(Bane.getElement(this.getRad(), (this.getKol() + flytt)));
            }    //Hvis pacman går til utgang og ikke mer mat. Så kjøres neste nivå
            else if (Bane.getElement(this.getRad(), (this.getKol() + flytt)).type == ElementNavn.FERDIG && Bane.merMat()) {
                Main.initBane(++Main.level); //Fikk ikke implimentert at pacman telepoteres til andre siden
            }
        }

        return true;
    }
    /**
     * Flytter det levende elementet
     * en avstand opp eller ned
     * som er tilsvarende
     * bredden på elementet
     *
     * @param opp
     * @return
     */
    public boolean flyttY(boolean opp) {
        int flytt = opp ? -1 : 1;
        if (Bane.getElement((this.getRad() + flytt), this.getKol()).type == ElementNavn.VEGG) {
            return false;
        } else {
            tt = new TranslateTransition(Duration.millis(16), this);
            tt.setByY(opp ? -this.getHeight() : this.getHeight());
            tt.play();
            tt.setOnFinished(e -> GUI.timeline.play());
        }
        if (this.type == ElementNavn.PACMAN) {
            if (Bane.getElement((this.getRad() + flytt), this.getKol()).type == ElementNavn.MAT
                    || Bane.getElement((this.getRad() + flytt), this.getKol()).type == ElementNavn.SUPERMAT) {
                this.spiser(Bane.getElement((this.getRad() + flytt), this.getKol()));
            }
        }
        return true;
    }

    /**
     * Vi vil bare at pacman skal spise
     * denne vil dermed da "ikke" bli kjørt
     * @param f
     */
    public void spiser(Element f){};

    /**
     * Henter ut hastigheten til det levende elementet
     * @return
     */
    public int getHastighet() {
        return hastighet;
    }

    /**
     * Setter ny hastighet til det levende elementet
     * @param hastighet
     */
    public void setHastighet(int hastighet) {
        if (hastighet < 600) //ikke raskere vil jeg ha det
            this.hastighet = hastighet;
    }

}
