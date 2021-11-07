/**
 * Klasse med metode som kjøres
 * flere ganger i sekundet
 */
public class Oppdater {

    private static int sitterFast = 0;
    private static int varighetSuperMode = 0;

    /**
     * Flytter pacman bas-------------ert på tastetrykk
     *
     * @param pacMan pacman objektet blir flyttet i rutenett
     *               hvis det ikke er en vegg
     */
    public static void flyttPacman(PacMan pacMan) {
        if (Lytter.oppTast) {
            if (pacMan.flyttY(true))
                pacMan.setRad(true);
            pacMan.roterMeg(270);
        } else if (Lytter.hoyreTast) {
            if (pacMan.flyttX(false))
                pacMan.setKol(false);
            pacMan.roterMeg(0);
        } else if (Lytter.nedTast) {
            if (pacMan.flyttY(false))
                pacMan.setRad(false);
            pacMan.roterMeg(90);
        } else if (Lytter.venstreTast) {
            if (pacMan.flyttX(true))
                pacMan.setKol(true);
            pacMan.roterMeg(180);
        }
        if (Lytter.space)
            Main.initBane(1);
    }

    /**
     * Sjekker om pacman er i super modus
     * som vil si den ikke kan dø av
     * spøkelsene
     *
     * @param pacMan pacman objektet sjekket her og
     *               settes tilbake til normal om lang nok tid har gått
     */
    public static void pacmanISuperMode(PacMan pacMan) {
        if (pacMan.isSuperMode()) {
            varighetSuperMode += 1;
            if (varighetSuperMode > 50) {
                varighetSuperMode = 0;
                pacMan.setSuperMode(false);
                Bane.setTrygg();
            }
        }
    }

    /**
     * Går gjennom alle spøkelsene på banenen.
     * Flytter og sjekker om de kolliderer med pacman
     *
     * @param pacMan
     * @throws InterruptedException La inn en thread.sleep
     *                              så når bruker mister et liv vil det gå et sekund
     *                              før spillet fortsetter
     */
    public static void oppdater(PacMan pacMan) throws InterruptedException {
        flyttPacman(pacMan);
        pacmanISuperMode(pacMan);
        Ghost temp = null;
        for (int i = 0; i < Main.ghostArrayList.size(); i++) {
            flyttMotPacman(Main.ghostArrayList.get(i), pacMan);
            if (pacMan.kollidere(Main.ghostArrayList.get(i))) {
                if (!pacMan.isSuperMode()) {
                    PacMan.setLiv(PacMan.getLiv() - 1);
                    if (PacMan.getLiv() < 1) { //stoppet timeline hvis pacman er tom
                        GUI.timeline.stop();  //for liv

                    } else {
                        Thread.sleep(500);
                        Main.initBane(Main.level); //Banen lastes inn på nytt ved død
                        GUI.txtLiv.setText("Liv igjen: " + PacMan.getLiv());
                    }
                } else { //tar vare på spøkelse om pacman spiser den
                    temp = Main.ghostArrayList.get(i);
                    pacMan.spiser(Main.ghostArrayList.get(i));
                }
                break;
            }
        }
        //fjerner spøkelse objektet fra arraylisten hvis kollisjon med pacman i løkken
        if (temp != null && !Main.ghostArrayList.isEmpty())
            Main.ghostArrayList.remove(temp);

        sjekkFerdig(); //sjekk om vi har spist all maten og siste level
    }

    static private void sjekkFerdig() {
        if (Bane.merMat() && Main.level == GUI.ANTALL_LEVEL) {
            Main.initBane(GUI.ANTALL_LEVEL + 1);
        }
    }

    /**
     * Flytter spøkelse basert på korrdinatene til pacman
     * Hvis spøkelse setter seg fast vil den gå tilfeldig vei
     *
     * @param fiende tar inn et spøkelse objekt fra arraylisten
     * @param pacMan sjekker hvor spøkelse skal flytte seg basert
     *               på pacman sin posisjon.
     */
    private static void flyttMotPacman(LevendeElement fiende, PacMan pacMan) {

        char valg;
        int random = (int) (Math.random() * 2) + 1;

        if (fiende.getRad() < pacMan.getRad() && fiende.getRad() >= 0) {   //er over pacman
            if (fiende.getKol() < pacMan.getKol()) { //er til venstre
                if (random == 0) {
                    valg = 'N'; //ned
                } else {
                    valg = 'H'; //høyre
                }
            } else {   //er til høyre eller samme kol
                if (random % 2 == 0) {
                    valg = 'N';  //ned
                } else {
                    valg = 'V'; //Venstre
                }
            }
        } else {   //er under pacman
            if (fiende.getKol() < pacMan.getKol()) { //er til venstre
                if (random % 2 == 0) {
                    valg = 'O'; //ned
                } else {
                    valg = 'H'; //høyre
                }
            } else {   //er til høyre eller samme kol
                if (random % 2 == 0) {
                    valg = 'O';  //ned
                } else {
                    valg = 'V'; //Venstre
                }

            }
        }
        //snur oppførsel, "flykter" da fra pacman
        if (pacMan.isSuperMode()) {
            if (valg == 'O')
                valg = 'N';
            else if (valg == 'N')
                valg = 'O';
            else if (valg == 'H')
                valg = 'V';
            else
                valg = 'H';
        }


        //sjekk for vegg og flytt
        switch (valg) {
            case 'O':
                if (fiende.flyttY(true))
                    fiende.setRad(true);
                else
                    sitterFast((Ghost) fiende);
                break;
            case 'H':
                if (fiende.flyttX(false))
                    fiende.setKol(false);
                else
                    sitterFast((Ghost) fiende);
                break;
            case 'N':
                if (fiende.flyttY(false))
                    fiende.setRad(false);
                else
                    sitterFast((Ghost) fiende);
                break;
            case 'V':
                if (fiende.flyttX(true))
                    fiende.setKol(true);
                else
                    sitterFast((Ghost) fiende);
                break;

        }
    }

    /**
     * Tar tilfeldig vei hvis spøkelse treffer vegg
     * mange nok ganger. Dette så den ikke skal
     * sitte seg fast for evig.
     *
     * @param fiende Spøkelse blir sendt inn
     *               hvis det sitter seg fast
     */
    private static void sitterFast(Ghost fiende) {

        boolean forsattFast = true;
        if (sitterFast > 3) { //hvis spøkelse treffer vegg etter tre forsøk
            sitterFast = 0;

            while (forsattFast) {
                int random = (int) (Math.random() * 2) + 1;
                if (random % 2 == 0) {
                    random = (int) (Math.random() * 2) + 1;
                    if (fiende.flyttX(random % 2 == 0)) {
                        fiende.setKol(random % 2 == 0);
                        forsattFast = false;
                    } else if (fiende.flyttX(random % 2 == 0)) {
                        fiende.setKol(random % 2 == 0);
                        forsattFast = false;
                    }

                } else {
                    random = (int) (Math.random() * 2) + 1;
                    if (fiende.flyttY(random % 2 == 0)) {
                        fiende.setRad(random % 2 == 0);
                        forsattFast = false;
                    }
                }
            }
        }
        sitterFast++;
    }
}
