import java.util.Arrays;

/**
 * Bane er hvor alle elementer blir plassert
 * i et rutenett. Hver bane henter inn en ny
 * data fra BaneDesign klassen
 */

public class Bane {

    private static final Element[][] baneGrid = new Element[GUI.ANTALL_RAD_KOL][GUI.ANTALL_RAD_KOL];
    private PacMan pacMan;
    private boolean clyde, blinky, inky;
    private static final ElementNavn[] tempGhost = new ElementNavn[4];

    /**
     * Konstruktør som lager ny bane
     *
     * @param level Henter inn hvilket
     *              nivå som skal lages bane av
     */

    Bane(int level) {
        settSpokelseTom();
        BaneDesign.lagMaze(level);
        level -= 1; //Visuellt bedre å skrive første nivå som 1, men array begynner på 0 så endrer det her
        for (int i = 0; i < GUI.ANTALL_RAD_KOL; i++) {
            for (int j = 0; j < GUI.ANTALL_RAD_KOL; j++) {
                setBaneGrid(level, i, j);
            }
        }
    }

    /**
     * Går gjennom alle verdiene i tabellen
     * opprettet nytt element basert på
     * hvilken verdi dette må være
     *
     * @param level Hvilket level det gjelder
     * @param rad   Hvilken rad det sjekkes mot
     * @param kol   Hvilken kollone det sjekkes mot
     */
    private void setBaneGrid(int level, int rad, int kol) {
        if (BaneDesign.erVegg(level, rad, kol)) {
            baneGrid[rad][kol] = new BaneElement(ElementNavn.VEGG, rad, kol);

        } else if (BaneDesign.erMat(level, rad, kol)) {
            baneGrid[rad][kol] = new BaneElement(ElementNavn.MAT, rad, kol);
        } else if (BaneDesign.erTom(level, rad, kol)) {
            baneGrid[rad][kol] = new BaneElement(ElementNavn.GULV, rad, kol);
        } else if (BaneDesign.erSpokelse(level, rad, kol)) {
            if (!clyde) { //Sjekker om at det bare forekommer en av hver type spøkelse
                clyde = true;
                baneGrid[rad][kol] = new Clyde(ElementNavn.CLYDE, rad, kol);
            } else if (!blinky) {
                blinky = true;
                baneGrid[rad][kol] = new Blinky(ElementNavn.BLINKY, rad, kol);
            } else if (!inky) {
                inky = true;
                baneGrid[rad][kol] = new Inky(ElementNavn.INKY, rad, kol);
            } else {
                baneGrid[rad][kol] = new Pinky(ElementNavn.PINKY, rad, kol);
            }
        } else if (BaneDesign.erPacMan(level, rad, kol)) {
            pacMan = new PacMan(rad, kol);
            baneGrid[rad][kol] = pacMan;
        } else if (BaneDesign.erFerdigPos(level, rad, kol)) {
            baneGrid[rad][kol] = new BaneElement(ElementNavn.FERDIG, rad, kol);
        } else if (BaneDesign.erSuperMat(level, rad, kol)) {
            baneGrid[rad][kol] = new BaneElement(ElementNavn.SUPERMAT, rad, kol);
        }
    }

    public PacMan getPacMan() {
        return this.pacMan;
    }

    /**
     * Bytter bilde på spokelse som blir spist
     * med et gulv bilde
     *
     * @param spokelse får inn spist element
     */
    static void setGulv(Element spokelse) {
        baneGrid[spokelse.getRad()][spokelse.getKol()] = new BaneElement(ElementNavn.GULV, spokelse.getRad(), spokelse.getKol());
    }

    /**
     * Hvis pacman går i supermodus
     * settes alle spøkelser til redd
     * og endrer da personlighet
     */
    static void setRedd() {
        int ghostCount = 0;
        for (int i = 0; i < GUI.ANTALL_RAD_KOL; i++) {
            for (int j = 0; j < GUI.ANTALL_RAD_KOL; j++) {
                if (erSpokelse(i, j)) {
                    tempGhost[ghostCount] = baneGrid[i][j].type; //Tar vare på hvilket
                    setRedd(i, j);                              //type spøkelse som har blit redd
                    ghostCount++;                               //ettersom ikke alltid 4 spøkelser
                }
            }
        }
    }

    static private void setRedd(int rad, int kol) {
        baneGrid[rad][kol].setImageView(ElementNavn.REDD);
    }

    /**
     * Setter tilbake alle spøkelser sin opprinnelige
     * identitet. Dette skjer når pacman mister
     * sypermoduet sitt
     */
    static void setTrygg() {
        int ghostCount = 0;
        for (int i = 0; i < GUI.ANTALL_RAD_KOL; i++) {
            for (int j = 0; j < GUI.ANTALL_RAD_KOL; j++) {
                if (erSpokelse(i, j)) {
                    if (tempGhost[ghostCount] != null) {
                        if (tempGhost[ghostCount] == ElementNavn.CLYDE) {
                            baneGrid[i][j].setImageView(ElementNavn.CLYDE);
                        } else if (tempGhost[ghostCount] == ElementNavn.BLINKY) {
                            baneGrid[i][j].setImageView(ElementNavn.BLINKY);
                        } else if (tempGhost[ghostCount] == ElementNavn.INKY) {
                            baneGrid[i][j].setImageView(ElementNavn.INKY);
                        } else if (tempGhost[ghostCount] == ElementNavn.PINKY)
                            baneGrid[i][j].setImageView(ElementNavn.PINKY);
                    }
                    ghostCount++;
                }
            }
        }
        Arrays.fill(tempGhost, null); //setter null på alle elementene
    }

    static private boolean erSpokelse(int rad, int kol) { //sjekker om type en av spøkelsene
        return (baneGrid[rad][kol].type == ElementNavn.CLYDE || //fikk ikke til instanceof her
                baneGrid[rad][kol].type == ElementNavn.BLINKY ||
                baneGrid[rad][kol].type == ElementNavn.INKY ||
                baneGrid[rad][kol].type == ElementNavn.PINKY ||
                baneGrid[rad][kol].type == ElementNavn.REDD);
    }

    /**
     * Sjekker brettet for om det er mer mat
     *
     * @return Pacman får gå til nytt nivå
     * hvis det ikke er mer mat igjen
     */
    static boolean merMat() {
        for (int i = 0; i < GUI.ANTALL_RAD_KOL; i++) {
            for (int j = 0; j < GUI.ANTALL_RAD_KOL; j++) {
                if (baneGrid[i][j].type == ElementNavn.MAT) {
                    return false;
                }
            }
        }
        return true;
    }

    private void settSpokelseTom() {
        clyde = false;
        blinky = false;
        inky = false;
    }


    static public Element[][] getBaneGrid() {
        return baneGrid;
    }

    static public Element getElement(int i, int j) {
        return baneGrid[i][j];
    }

}
