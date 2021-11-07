import javafx.geometry.Insets;
/**
 * Arver fra Element, egen vieworder og padding
 */
public class BaneElement extends Element {

    BaneElement(ElementNavn element, int rad, int kol){
        super(element, rad, kol);
        this.setViewOrder(100); //Setter verdi høyere enn 0 som er standard så vegg kommer bakerst
        this.setPadding(new Insets(1));
    }
}
