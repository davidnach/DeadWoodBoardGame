//Imports
package controller;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.lang.Runnable;
import java.awt.Color;
import javax.swing.JPanel;

public class DieRoll extends JPanel {
    private static final long serialVersionUID = 100000;
    //Nested die class.
    private class Die {
        private int val;

        public Die() {
            roll();
        }

        public void roll() {
            Random rand = new Random();
            val = rand.nextInt(6) + 1;
        }

        public String getValString() {
            return val + "";
        }

        public int getVal() {
            return val;
        }

    }

    //If the button is hovered.
    private boolean hover = false;

    private boolean rolled = false;
    private boolean active = false;
    private boolean rollingForBonus = false;
    private ArrayList < Integer > rolls;
    private model.Scene currentScene;
    private model.SceneRoom currentSceneRoom;
    private int rollsLeft;


    private Die die1 = new Die();

    private CountDownLatch cdLatch;
    private Color rollButtonColor;
    private model.GameController game;

    public DieRoll(model.GameController game) {
        this.game = game;
        setBounds(1200, 600, 300, 150);
        setOpaque(false);
        setVisible(true);
        rollButtonColor = Color.orange;

        //Create a new MouseAdapter
        MouseAdapter listen = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                /*
                 * Only repaint when necessary to prevent excessive repaints that look really bad.
                 */

                //If hover was previously false.
                if (!hover && active || !hover && rollingForBonus) {
                    //Check to see if the mouse is now inside the button.
                    if (e.getX() >= 50 && e.getX() <= 125 && e.getY() >= 50 && e.getY() <= 95) {
                        //Set hovered to true, set the cursor to the hand, and repaint.
                        hover = true;
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                        repaint();
                    }
                } else {
                    //If the mouse is now outside the button.
                    if (!(e.getX() >= 50 && e.getX() <= 125 && e.getY() >= 50 && e.getY() <= 95)) {
                        //Set hovered to false, set the cursor to the default one, and repaint.
                        hover = false;
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                        repaint();
                    }
                }
            };

            public void mouseClicked(MouseEvent e) {
                if ((hover && active) && !rollingForBonus) {
                    roll();

                } else if (hover && rollingForBonus) {
                    die1.roll();
                    rolls.add(die1.getVal());
                    rollsLeft--;
                    if (rollsLeft == 0) {
                        rollingForBonus = false;
                        active = false;
                        currentScene.payMainActorBonuses(rolls);
                        currentSceneRoom.payExtraBonuses();
                        currentSceneRoom.clearRoleSpots();
                        rollButtonColor = Color.orange;
                        game.incrementTurn();
                        game.completeScene();
                    }
                }
            }
        };

        //Make sure we add the listener.
        addMouseMotionListener(listen);
        addMouseListener(listen);
    }

    public void paint(Graphics g) {
        //Make sure we're using Graphics2D
        Graphics2D g2d = (Graphics2D) g;

        //Anti alias graphics.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Set the font.
        g2d.setFont(new Font("Arial", Font.PLAIN, 28));

        //Paint the background.
        g2d.setColor(new Color(68, 102, 0));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //Paint the button.
        //Determine what color the button should be based on whether it is hovered or not.
        g2d.setColor((hover && active) ? Color.gray : rollButtonColor);
        g2d.fillRect(50, 50, 75, 45);

        //Paint Dice
        g2d.setColor(Color.black);
        g2d.fillRect(150, 50, 45, 45);
        //g2d.drawRect(190, 50, 45, 45);

        //Text will be painted later as Trebuchet MS needs to be loaded.
        g2d.setColor(Color.black);
        g2d.drawString("Roll", 65, 80);

        //Paint die values.
        g2d.setColor(Color.white);
        g2d.drawString(die1.getValString(), 165, 82);
        //g2d.drawString(die2.getVal(), 205, 82);
    }


    public void roll() {
        die1.roll();
        model.Actor actor = game.getCurrentActor();
        model.SceneRoom sceneRoom = (model.SceneRoom) game.findRoom(actor.getCurrentRoom());
        model.Scene scene = sceneRoom.getScene();
        if (scene.successfulRoll(die1.getVal() + actor.getRehearsals())) {
            sceneRoom.completeShot();
            if (actor.isExtra()) {
                actor.updateWallet(1, 1);
            } else {
                actor.updateWallet(0, 2);
            }
        } else {

            if (actor.isExtra()) {
                actor.updateWallet(1, 0);
            }
        }
        if (sceneRoom.shotsComplete()) {
            rollButtonColor = Color.green;
            repaint();
            rollsLeft = scene.getBudget();
            this.currentScene = scene;
            this.currentSceneRoom = sceneRoom;
            rolls = new ArrayList < Integer > ();
            rollingForBonus = true;

        } else {
            active = false;
            rollButtonColor = Color.orange;
            repaint();
            game.incrementTurn();
        }
    }



    public int getDieVal() {
        return die1.getVal();
    }

    public void setRollButtonColor(Color color) {
        this.rollButtonColor = color;
    }

    public boolean hasRolled() {
        return this.rolled;
    }

    public void resetRoll() {
        this.rolled = false;
    }

    public void activateRollButton() {
        this.active = true;
        rollButtonColor = Color.green;
        repaint();
    }

    public boolean rollingForBonus() {
        return this.rollingForBonus;
    }


}
