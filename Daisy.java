import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGKeyListener;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Daisy extends Actor implements GGKeyListener{


    int  oldKey , newKey;
    Feld[][] felder;
    Feld nextFeld;
    Location[][] portals;
    Labyrinth labyrinth = new Labyrinth();
    int punkte;
    Control control;
    boolean isGame;
    boolean noFeld;

    private static final int nbSprites = 1;
    private int idSprite = 0;

    Daisy(Control control) {
        super(false, "sprites/trollface_0.gif", nbSprites);
        felder = labyrinth.getFelder();
        portals = labyrinth.getPortals();
        isGame = true;
        this.control = control;


    }

    @Override
    public void act()
    {
        ++idSprite;
        if (idSprite == nbSprites) {
            idSprite = 0;
        }
    }

    //KeyListener

    public boolean keyReleased (KeyEvent e){
        return true;
    }




    @Override
    public boolean keyPressed(KeyEvent event) {
        noFeld = false;
        int nextX = -1;
        int nextY = -1;
        int feldart = -1;

            switch (event.getKeyCode()) {
                case KeyEvent.VK_P:
                case KeyEvent.VK_SPACE:
                    if(isGame) {
                        control.doPause();
                        isGame = false;
                    }else{
                        control.doRun();
                        isGame = true;
                    }
                    noFeld = true;
                    break;

                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    newKey=event.getKeyCode();
                    nextX = getLocation().getNeighbourLocation(Feld.WEST).getX();
                    nextY = getLocation().getNeighbourLocation(Feld.WEST).getY();
                    feldart = felder[nextX][nextY].getFeldart();
                    nextFeld(nextX, nextY, feldart);
                    setDirection(Location.WEST);
                    break;

                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    nextX = getLocation().getNeighbourLocation(Feld.NORTH).getX();
                    nextY = getLocation().getNeighbourLocation(Feld.NORTH).getY();
                    feldart = felder[nextX][nextY].getFeldart();
                    nextFeld(nextX, nextY, feldart);
                    setDirection(Location.NORTH);
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    nextX = getLocation().getNeighbourLocation(Feld.EAST).getX();
                    nextY = getLocation().getNeighbourLocation(Feld.EAST).getY();
                    feldart = felder[nextX][nextY].getFeldart();
                    nextFeld(nextX, nextY, feldart);
                    setDirection(Location.EAST);
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    nextX = getLocation().getNeighbourLocation(Feld.SOUTH).getX();
                    nextY = getLocation().getNeighbourLocation(Feld.SOUTH).getY();
                    feldart = felder[nextX][nextY].getFeldart();
                    nextFeld(nextX, nextY, feldart);
                    setDirection(Location.SOUTH);
                    break;
                default:
                    System.out.println("JA GEGE");

            }


        if(!noFeld) {
            if (canMove(nextFeld)) {
                setLocation(nextFeld);
                eat(nextFeld);
            }

            portal(nextFeld);
        }

        return true;
    }



    public void  nextFeld(int x, int y, int feldart){
        nextFeld = null;
        nextFeld = new Feld(x ,y , feldart);
    }


    boolean canMove(Feld nextFeld) {
        if (nextFeld.getFeldart() == FeldArt.WALL) {
            return false;
        } else {
            return true;
        }
    }


    void portal(Feld nextFeld) {
        switch (nextFeld.feldart){
            case FeldArt.PORTAL1_1:
                teleport(portals[0][1]);
                break;
            case FeldArt.PORTAL1_2:
                teleport(portals[0][0]);
                break;
            case FeldArt.PORTAL2_1:
                teleport(portals[1][1]);
                break;
            case FeldArt.PORTAL2_2:
                teleport(portals[1][0]);
                break;
            case FeldArt.PORTAL3_1:
                teleport(portals[2][1]);
                    break;
                case FeldArt.PORTAL3_2:
                teleport(portals[2][0]);
                break;


        }
        }

    void teleport(Location location) {
        setLocation(location);
    }



    void eat(Feld nextFeld){
        if(nextFeld.getFeldart() == FeldArt.EAT){
            felder[nextFeld.getX()][nextFeld.getY()].setfeldArt(FeldArt.TERRAIN);
            getBackground().fillCell(nextFeld, Color.gray);
            punkte++;
            control.setTitle("Daisy´s Adventure - Points: " + punkte);
        }
    }

    int getPunkte(){
        return punkte;
    }





}

