/*
 * This is the Client class. It reads the server running and tehn creates the connection with the server.
 */
package GUI;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import uk.ac.tees.cis2001.pocketbeasts.BeastCard;
import uk.ac.tees.cis2001.pocketbeasts.Card;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NoHeal;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NormalAttack;
import uk.ac.tees.cis2001.pocketbeasts.Deck;
import uk.ac.tees.cis2001.pocketbeasts.Graveyard;
import uk.ac.tees.cis2001.pocketbeasts.InPlay;
import uk.ac.tees.cis2001.pocketbeasts.MagicCard;
import uk.ac.tees.cis2001.pocketbeasts.Player;

/**
 *
 * @author JATIN ANEJA
 */
public class GameClientMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Begining , taking user name 
        ImageIcon icon = new ImageIcon("src/1.jpg");
        String n = (String) JOptionPane.showInputDialog(null, "Enter Your Name: ", "Pocket-Beast Game", JOptionPane.QUESTION_MESSAGE, icon, null, null);
        if (n == null) {
            System.exit(0);
        }

        // Main Game Start  for the client
        Player player = new Player(n, new Deck(getStarterDeck()));
        PoacketBeastGUI GamePlayer1 = new PoacketBeastGUI(player, false);
        GamePlayer1.setVisible(true);
        final ExecutorService service = Executors.newCachedThreadPool();    // service 
        try {

            GamePlayer1.s = new Socket("127.0.0.1", GameServerMain.PORT);   //setting up port 
            GamePlayer1.outStream = new ObjectOutputStream(GamePlayer1.s.getOutputStream());
            GamePlayer1.inStream = new ObjectInputStream(GamePlayer1.s.getInputStream());
            GamePlayer1.outStream.writeObject(GamePlayer1.player);
            GamePlayer1.outStream.flush();
            GamePlayer1.outStream.reset();
            String msginput = "";
            Player opponent;
            while (true) {
                Object object = GamePlayer1.inStream.readObject();
                Class c = object.getClass();
                if (c == Player.class) {     // reading object passed from socket as Player class
                    System.out.println(object);
                    opponent = (Player) object;
                    System.out.println(opponent.toString());
                    GamePlayer1.OpponentPlayer = opponent;
                    System.out.println(GamePlayer1.OpponentPlayer.toString());
                    GamePlayer1.opponentNameLB.setText(GamePlayer1.OpponentPlayer.getName());
                    GamePlayer1.healthLB.setText(Integer.toString(GamePlayer1.player.getHealth()));
                    GamePlayer1.GameFrame.validate();
                    GamePlayer1.GameFrame.validate();
                    object = null;
                } else if (c == InPlay.class) {     // reading object passed from socket as InPlay class
                    GamePlayer1.player.setInPlay((InPlay) object);
                } else if (c == Graveyard.class) {    // reading object passed from socket as GraveYard class
                    GamePlayer1.player.setGraveYard((Graveyard) object);
                    GamePlayer1.graveyardLB.setText(Integer.toString(GamePlayer1.player.getGraveyard().count()));
                    GamePlayer1.GameFrame.validate();
                } else if (c == Integer.class) {      // reading object passed from socket as Integer class
                    GamePlayer1.player.setHealth((Integer) object);
                    GamePlayer1.healthLB.setText(Integer.toString(GamePlayer1.player.getHealth()));
                    GamePlayer1.GameFrame.validate();
                } else if (c == String.class) {       // reading object passed from socket as String class
                    GamePlayer1.ChatAreaPanel.setLayout(new BorderLayout());
                    msginput = (String) object;
                    JPanel p2 = GamePlayer1.formatLabel(msginput);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2, BorderLayout.LINE_START);
                    GamePlayer1.vertical.add(left);
                    GamePlayer1.vertical.add(Box.createVerticalStrut(15));
                    GamePlayer1.ChatAreaPanel.add(GamePlayer1.vertical, BorderLayout.PAGE_START);
                    GamePlayer1.GameFrame.validate();
                } else if (c == Character.class) {    // reading object passed from socket as Char  class  for Winning Message
                    GamePlayer1.opponentWon = true;
                    service.submit(GamePlayer1);
                } else if (c == Boolean.class) {

                    GamePlayer1.Turn = (Boolean) object;
                    service.submit(GamePlayer1);
                }
            }

        } catch (UnknownHostException e) {
            System.out.println(e.toString());
        } finally {
        }
    }

     //--------------------------------------------------------------
        // Creating DECK for the Game
    //String id, String name, int attack, int manaCost, int health, AttackBehaviour a
    // Randomly gives the Spell cards. Will not crowd the panel only will spell cards in the game
    public static final Card[] STARTER_CARDS = new Card[]{
        new BeastCard("BR", "Barn Rat", 1, 1, 1, new NormalAttack(), new NoHeal()),
        new BeastCard("SP", "Scampering Pup", 2, 2, 1, new NormalAttack(), new NoHeal()),
        new BeastCard("HB", "Hardshell Beetle", 2, 1, 2, new NormalAttack(), new NoHeal()),
        new BeastCard("VHC", "Vicious House Cat", 3, 3, 2, new NormalAttack(), new NoHeal()),
        new BeastCard("GD", "Guard Dog", 3, 2, 3, new NormalAttack(), new NoHeal()),
        new BeastCard("ARH", "All Round Hound", 3, 3, 3, new NormalAttack(), new NoHeal()),
        new BeastCard("MO", "Moor Owl", 4, 4, 2, new NormalAttack(), new NoHeal()),
        new BeastCard("HT", "Highland Tiger", 5, 4, 4, new NormalAttack(), new NoHeal()),
        new BeastCard("KK", "King Kong", 2, 3, 3, new NormalAttack(), new NoHeal()),
        // add new Functionality
        getRandomMagicCard(),  

    };

    public static ArrayList<Card> getStarterDeck() {
        ArrayList<Card> starterDeck = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            for (Card card : STARTER_CARDS) {
                if (card.getClass() == BeastCard.class) {
                    System.out.println(((BeastCard) card).getAttack() + " " + card.getName());
                    starterDeck.add(new BeastCard(((BeastCard) card)));
                } else if (card.getClass() == MagicCard.class) {
                    starterDeck.add(((MagicCard) card));
                }

            }
        }

        return starterDeck;
    }
    
    
    // Randomly gives the Spell cards. Will not crowd the panel only will spell cards in the game

    public static MagicCard getRandomMagicCard() {
        Random r = new Random();
        Card[] STARTER_CARDS = new Card[]{
            new MagicCard("DP+2", "Double Attack", 2), // id , Name , ManaCost
            new MagicCard("TP+3", "Tripple Attack", 3),
            new MagicCard("H+2", "Heal by2", 2),
            new MagicCard("H+4", "Heal by4", 3)
        };

        int m = r.nextInt(STARTER_CARDS.length);
        return (MagicCard) STARTER_CARDS[m];
    }

}
