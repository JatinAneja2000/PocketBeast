/*
 * This is the Advance version of the PocketBeast console based game With Graphical user Interface and Inbuilt Java Chat Application.
 * This Application have to subjects Server and Client where the Conservation is only between Server and Client ,  
 * Author : Jatin Aneja - W9359412
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import uk.ac.tees.cis2001.pocketbeasts.BeastCard;
import uk.ac.tees.cis2001.pocketbeasts.Card;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.DoubleAttack;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealByFour;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.HealByTwo;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NoHeal;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.NormalAttack;
import uk.ac.tees.cis2001.pocketbeasts.Card_Behaviours.TripleAttack;
import uk.ac.tees.cis2001.pocketbeasts.MagicCard;
import uk.ac.tees.cis2001.pocketbeasts.Player;

/**
 *
 * @author JATIN ANEJA
 */
public class PoacketBeastGUI extends javax.swing.JFrame implements Runnable, ActionListener {

    boolean Turn;
    ServerSocket socket;
    Socket s;
    ObjectInputStream inStream;
    ObjectOutputStream outStream;
    Player player, OpponentPlayer;
    int maxHandCardptr;
    int maxInPlayCardptr;
    int maxOppoInPlayCardptr;
    Boolean attacking, choosing, playing, wantToAttack, won;
    ArrayList<String> prompts = new ArrayList<>();
    ArrayList<Card> toRemove = new ArrayList<>();
    Moves moves;
    String[] validResponses;
    JPanel ChatPanel = new JPanel();
    JPanel ChatAreaPanel = new JPanel();
    JTextField ChatTxtField = new JTextField();
    JButton SendBtn = new JButton();
    java.awt.ScrollPane scrollPane = new java.awt.ScrollPane();
    ExecutorService exe = Executors.newCachedThreadPool();
    Box vertical = Box.createVerticalBox();
    Card tempCard;
    String winningMessage = "";
    Boolean opponentWon = false;
    char opponentWonChar = 'O';

    /**
     * Creates new form PoacketBeastGUI
     *
     * @param player
     * @param firstTurn
     */
    public PoacketBeastGUI(Player player, Boolean firstTurn) {
        initComponents();
        this.player = player;
        player.newGame();
        myName.setText(player.getName());
        myName.validate();
        GameFrame.validate();
        Turn = firstTurn;

        manaLB.setText(Integer.toString(this.player.getManaAvailable()));
        healthLB.setText(Integer.toString(this.player.getHealth()));
        deckLB.setText(Integer.toString(this.player.getDeck().count()));
        graveyardLB.setText(Integer.toString(this.player.getGraveyard().count()));
        for (Card card : player.getHand().getCards()) {
            this.addCardsToHandPanel(card, HandPnl, maxInPlayCardptr);
            maxInPlayCardptr++;
        }
        //Chat panel code   CHAT GUI
        ChatPanel.setBackground(new Color(7, 94, 84));
        ChatPanel.setLayout(null);
        ChatPanel.setBounds(3, 115, 420, 735);
        ChatPanel.setVisible(true);

        ChatAreaPanel.setBackground(Color.decode("#eeeeee"));
        ChatAreaPanel.setBounds(ChatPanel.getX(), -60, ChatPanel.getWidth(), ChatPanel.getHeight());
        ChatAreaPanel.setOpaque(true);
        scrollPane.setBounds(ChatPanel.getX(), -60, ChatPanel.getWidth(), ChatPanel.getHeight());
        scrollPane.add(ChatAreaPanel);
        scrollPane.setVisible(true);

        ChatTxtField = new JTextField();
        ChatTxtField.setBounds(5, (ChatPanel.getHeight() - 60), 420, 30);
        ChatTxtField.setFont(new Font("SAN_SERIF", Font.BOLD, 20));

        SendBtn = new JButton("Send");
        SendBtn.setBounds(ChatTxtField.getX(), (ChatTxtField.getY() + ChatTxtField.getHeight()), ChatTxtField.getWidth(), ChatTxtField.getHeight());
        SendBtn.setFont(new Font("Kristen ITC", Font.BOLD, 22));
        SendBtn.addActionListener(this);

        ChatPanel.add(SendBtn);
        ChatPanel.add(ChatTxtField);
        ChatPanel.add(scrollPane);
        selectBtn.addActionListener(this);
        // adding to main GUI

        jPanel1.add(ChatPanel);
        GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameFrame.validate();
        GameFrame.repaint();

    }

    //--------------------------------------------------------------
        // Creating DECK for the Game
    //String id, String name, int attack, int manaCost, int health, AttackBehaviour a
    // Randomly gives the Spell cards. Will not crowd the panel only will spell cards in the game
    public final Card[] STARTER_CARDS = new Card[]{
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
        new MagicCard("DP+2", "Double Attack", 2),
        new MagicCard("TP+3", "Tripple Attack", 3),
        new MagicCard("H+2", "Heal by2", 2),
        new MagicCard("H+4", "Heal by4", 3)
    };

    public ArrayList<Card> getStarterDeck() {
        ArrayList<Card> starterDeck = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            for (Card card : STARTER_CARDS) {
                if (card.getClass() == BeastCard.class) {
                    //  System.out.println(((BeastCard) card).getAttack() + " " + card.getName());
                    starterDeck.add(new BeastCard(((BeastCard) card)));
                } else if (card.getClass() == MagicCard.class) {
                    starterDeck.add(((MagicCard) card));
                }

            }
        }

        return starterDeck;
    }

    
    // GUI Game Run
    @Override
    public void run() {
        if (opponentWon) {
            ImageIcon x = new ImageIcon("src/2.png");
            JOptionPane.showMessageDialog(this, "You Lost", "Loser", JOptionPane.INFORMATION_MESSAGE, x);
            System.exit(0);
        }
        OppoName.setText(OpponentPlayer.getName());
        OppoDeck.setText(Integer.toString(OpponentPlayer.getDeck().count()));
        OppoGrave.setText(Integer.toString(OpponentPlayer.getGraveyard().count()));
        if (Turn == true) {
            maxOppoInPlayCardptr = 0;
            OpponentHandPnl.removeAll();
            for (Card card : OpponentPlayer.getInPlay().getCards()) {
                addCardsToHandPanel(card, OpponentHandPnl, maxOppoInPlayCardptr);
                maxOppoInPlayCardptr++;
            }
            OpponentHandPnl.validate();
            player.addMana();
            manaLB.setText(Integer.toString(player.getManaAvailable()));
            manaLB.validate();
            GameFrame.validate();
            GameFrame.repaint();
            if (player.getDeck().count() > 0) {
                player.drawCard();
            }
            if (player.getDeck().count() < 0) {
                winningMessage = player.getName() + "  Wins!!";
                JOptionPane.showMessageDialog(this, winningMessage);
                return;
            }

            HandPnl.removeAll();
            maxHandCardptr = 0;
            for (Card card : player.getHand().getCards()) {
                this.addCardsToHandPanel(card, HandPnl, maxHandCardptr);
                maxHandCardptr++;
            }
            deckLB.setText(Integer.toString(player.getDeck().count()));
            deckLB.validate();
            GameFrame.validate();
            GameFrame.repaint();

            //--------------------------------------------------------------
            // Executor service for getting respornce fro user wheather to attack or not
            AttackThrd attackThrd = new AttackThrd();
            Future<?> attackFuture = exe.submit(attackThrd);

            try {
                attackFuture.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            ArrayList drawCardsFromInPlay = new ArrayList<>();
            for (Card card : player.getInPlay().getCards()) {
                if (card.getClass() == BeastCard.class) {
                    if (((BeastCard) card).getHealth() <= 0) {
                        drawCardsFromInPlay.add(card);
                        player.getGraveyard().add(card);
                    }
                }
            }
            player.getInPlay().removeAll(drawCardsFromInPlay);

            ArrayList opponentDrawCardsFromInPlay = new ArrayList<>();
            for (Card card : OpponentPlayer.getInPlay().getCards()) {
                if (card.getClass() == BeastCard.class) {
                    if (((BeastCard) card).getHealth() <= 0) {
                        opponentDrawCardsFromInPlay.add(card);
                        OpponentPlayer.getGraveyard().add(card);
                    }
                }

            }
            OpponentPlayer.getInPlay().removeAll(opponentDrawCardsFromInPlay);
            InPlayPnl.removeAll();
            maxInPlayCardptr = 0;
            for (Card card : player.getInPlay().getCards()) {
                addCardsToHandPanel(card, InPlayPnl, maxInPlayCardptr);
                maxInPlayCardptr++;
            }
            OpponentHandPnl.removeAll();
            maxOppoInPlayCardptr = 0;
            for (Card card : OpponentPlayer.getInPlay().getCards()) {
                addCardsToHandPanel(card, OpponentHandPnl, maxOppoInPlayCardptr);
                maxOppoInPlayCardptr++;
            }
            InPlayPnl.validate();
            OpponentHandPnl.validate();
            GameFrame.validate();
            GameFrame.repaint();

            
            //--------------------------------------------------------------
            // Executor service for getting respornce fro user wheather to Inplay Card or not
            PlayThrd playCardThread = new PlayThrd();
            Future<?> play = exe.submit(playCardThread);

            try {
                play.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Turn = false;

            try {
                outStream.writeObject(player);
                outStream.flush();
                outStream.reset();
            } catch (IOException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                outStream.writeObject(OpponentPlayer.getInPlay());
                outStream.flush();
                outStream.reset();
            } catch (IOException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                outStream.writeObject(OpponentPlayer.getGraveyard());
                outStream.flush();
                outStream.reset();
            } catch (IOException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                outStream.writeObject(OpponentPlayer.getHealth());
                outStream.flush();
                outStream.reset();
            } catch (IOException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                outStream.writeObject(true);
                outStream.flush();
                outStream.reset();
            } catch (IOException ex) {
                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            QuesText.setText("Waiting for Opponent's turn! ");
            GameFrame.validate();
            GameFrame.repaint();

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GameFrame = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        opponentNameLB = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        GameBoard = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        OppoName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        OppoDeck = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        OppoGrave = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        myName = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        manaLB = new javax.swing.JLabel();
        healthLB = new javax.swing.JLabel();
        deckLB = new javax.swing.JLabel();
        graveyardLB = new javax.swing.JLabel();
        OpponentHandPnl = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        Rules = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        AnsField = new javax.swing.JTextField();
        selectBtn = new javax.swing.JButton();
        QuesTextPane = new java.awt.ScrollPane();
        QuesText = new javax.swing.JLabel();
        ExitGame = new javax.swing.JButton();
        InPlayPnl = new javax.swing.JPanel();
        HandPnl = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        GameFrame.setBackground(new java.awt.Color(0, 0, 0));
        GameFrame.setVisible(true);

        jPanel1.setBackground(new java.awt.Color(179, 212, 253));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 6));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("HELLO :");

        opponentNameLB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        opponentNameLB.setText("...........................................");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Chat Application");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1524, 1524, 1524))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opponentNameLB, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(opponentNameLB, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GameBoard.setBackground(new java.awt.Color(0, 51, 102));
        GameBoard.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        GameBoard.setForeground(new java.awt.Color(51, 204, 255));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        OppoName.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        OppoName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OppoName.setText("..............");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Deck: ");

        OppoDeck.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        OppoDeck.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("GraveYard:");

        OppoGrave.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        OppoGrave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OppoGrave.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(OppoDeck, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGap(37, 37, 37)
                            .addComponent(OppoName, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(OppoGrave, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OppoName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(OppoDeck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OppoGrave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        myName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        myName.setText("...............");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Mana :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Health :");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Deck :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setText("GraveYard :");

        manaLB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        manaLB.setText(".....");

        healthLB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        healthLB.setText(".....");

        deckLB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        deckLB.setText("......");

        graveyardLB.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        graveyardLB.setText(".....");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(manaLB)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deckLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(healthLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(graveyardLB, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(myName, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(manaLB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(healthLB, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deckLB, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(graveyardLB, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        OpponentHandPnl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        javax.swing.GroupLayout OpponentHandPnlLayout = new javax.swing.GroupLayout(OpponentHandPnl);
        OpponentHandPnl.setLayout(OpponentHandPnlLayout);
        OpponentHandPnlLayout.setHorizontalGroup(
            OpponentHandPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        OpponentHandPnlLayout.setVerticalGroup(
            OpponentHandPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 155, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 177, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 164, Short.MAX_VALUE)
        );

        Rules.setBackground(new java.awt.Color(153, 255, 255));
        Rules.setFont(new java.awt.Font("Kristen ITC", 1, 24)); // NOI18N
        Rules.setText("GAME RULES");
        Rules.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true)));
        Rules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RulesActionPerformed(evt);
            }
        });

        AnsField.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        AnsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnsFieldActionPerformed(evt);
            }
        });

        selectBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        selectBtn.setText("$OKAY$");

        QuesText.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        QuesText.setText("Game Will Begin Shortly!!");
        QuesTextPane.add(QuesText);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(AnsField, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addGap(24, 24, 24))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(QuesTextPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(QuesTextPane, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AnsField, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        ExitGame.setBackground(new java.awt.Color(51, 153, 255));
        ExitGame.setFont(new java.awt.Font("Kristen ITC", 1, 24)); // NOI18N
        ExitGame.setText("EXIT GAME");
        ExitGame.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true)));
        ExitGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitGameActionPerformed(evt);
            }
        });

        InPlayPnl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        javax.swing.GroupLayout InPlayPnlLayout = new javax.swing.GroupLayout(InPlayPnl);
        InPlayPnl.setLayout(InPlayPnlLayout);
        InPlayPnlLayout.setHorizontalGroup(
            InPlayPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        InPlayPnlLayout.setVerticalGroup(
            InPlayPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 155, Short.MAX_VALUE)
        );

        HandPnl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        javax.swing.GroupLayout HandPnlLayout = new javax.swing.GroupLayout(HandPnl);
        HandPnl.setLayout(HandPnlLayout);
        HandPnlLayout.setHorizontalGroup(
            HandPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        HandPnlLayout.setVerticalGroup(
            HandPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 155, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("OPPONENT InPlay Cards");

        jLabel11.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("YOUR InPlay Cards");

        jLabel12.setFont(new java.awt.Font("Kristen ITC", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("YOUR Hand Cards ");

        jLabel6.setFont(new java.awt.Font("Nirmala UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 204));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("POCKETBEAST GAME");

        javax.swing.GroupLayout GameBoardLayout = new javax.swing.GroupLayout(GameBoard);
        GameBoard.setLayout(GameBoardLayout);
        GameBoardLayout.setHorizontalGroup(
            GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(GameBoardLayout.createSequentialGroup()
                        .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GameBoardLayout.createSequentialGroup()
                                .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ExitGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Rules, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(GameBoardLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(GameBoardLayout.createSequentialGroup()
                        .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GameBoardLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(InPlayPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(HandPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(OpponentHandPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(GameBoardLayout.createSequentialGroup()
                                .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        GameBoardLayout.setVerticalGroup(
            GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameBoardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GameBoardLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(OpponentHandPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GameBoardLayout.createSequentialGroup()
                        .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GameBoardLayout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GameBoardLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(InPlayPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(GameBoardLayout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(GameBoardLayout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(HandPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(GameBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(GameBoardLayout.createSequentialGroup()
                        .addComponent(Rules, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ExitGame, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout GameFrameLayout = new javax.swing.GroupLayout(GameFrame.getContentPane());
        GameFrame.getContentPane().setLayout(GameFrameLayout);
        GameFrameLayout.setHorizontalGroup(
            GameFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameFrameLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GameBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        GameFrameLayout.setVerticalGroup(
            GameFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GameFrameLayout.createSequentialGroup()
                .addGroup(GameFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GameBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(GameFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(GameFrame))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void RulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RulesActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, message(), "Game Rules", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_RulesActionPerformed

    private void ExitGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitGameActionPerformed
        // to Exit Game
        System.exit(0);


    }//GEN-LAST:event_ExitGameActionPerformed

    private void AnsFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnsFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnsFieldActionPerformed

    // Game Rules 
    public String message() {

        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        sb.append("<html><font face='Calibri' size='9' color='red'>-+-+-+-+-+-+-+-+-+-+-+-+\n");
        sb.append("<html><font face='Calibri' size='10' color='black'>Welcome to PocketBeasts!\n");
        sb.append("<html><font face='Calibri' size='9' color='red'>-+-+-+-+-+-+-+-+-+-+-+-+\n");
        sb.append("<html><font face='Calibri' size='6'>This basic GUI application tests our underlying software design patterns.\n");
        sb.append("<html><font face='Calibri' size='6'>Here's a key for each card:\n");
        sb.append("\n<html><font face='Calibri' size='6'> Beast Card\n");
        sb.append("                                           +---------------+ \n");
        sb.append("M  = Mana Cost                |                 M| \n");
        sb.append("ID = Card identifier:         |     ID           | \n");
        sb.append("A  = Attack:                       |                    | \n");
        sb.append("H  = Health:                        |A               H| \n");
        sb.append("                                            +---------------+ \n");
        sb.append("\n<html><font face='Calibri' size='6'> Spell Card\n");
        sb.append("                                                 +-------+------+ \n");
        sb.append("M  = Mana Cost                      |                M| \n");
        sb.append("Name = Card identifier:        |   Name     | \n");
        sb.append("E  = Effect:                               |     E            | \n");
        sb.append("                                                 +---------------+ \n");
        sb.append("<html><font face='Calibri' size='6'>New players each start with 15 Health, 10 Cards in the Deck to start and 1 Mana in Each turn to spend on playing cards.\n");
        sb.append("<html><font face='Calibri' size='6'>At the start of the game each player draws 4 cards from their deck to hand( Mix of Spell and Beast Cards.\n");
        sb.append("\n");
        sb.append("<html><font face='Calibri' size='6'>Players each take turns. Each turn consists four phases:\n");
        sb.append("<html><font face='Calibri' size='6'>1. Add mana (mana increases by one each turn and replenishes in full : Max 9).\n");
        sb.append("<html><font face='Calibri' size='6'>2. Draw a card.\n");
        sb.append("<html><font face='Calibri' size='6'>3. Cycle through your cards in play (if any), choosing whether to attack.\n");
        sb.append("<html><font face='Calibri' size='5' color='blue'>\t       a. Attacking the other player directly with your card inflicts damage to their health.\n");
        sb.append("<html><font face='Calibri' size='5' color='blue'>\t          equal to the attack power of the card.\n");
        sb.append("<html><font face='Calibri' size='5' color='blue'>\t       b. Attacking another players beast will damage both cards (equal to their attack values).\n");
        sb.append("<html><font face='Calibri' size='5' color='blue'>\t       c. Any beast with <= 0 health is removed from the play field and placed into the graveyard.\n");
        sb.append("<html><font face='Calibri' size='5' color='blue'>\t       d. Player can Use Spell cards to Increase Attacking and Health power of the player Cards .\n");
        sb.append("<html><font face='Calibri' size='5' color='blue'>\t       e. Spell Card choose random card from Inplay to creats its Effect with can then be useed by player for attacking .\n");
        sb.append("<html><font face='Calibri' size='6'>4. Play cards from hand.\n");
        sb.append("");
        return sb.toString();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AnsField;
    private javax.swing.JButton ExitGame;
    private javax.swing.JPanel GameBoard;
    public static javax.swing.JInternalFrame GameFrame;
    public static javax.swing.JPanel HandPnl;
    private javax.swing.JPanel InPlayPnl;
    public static javax.swing.JLabel OppoDeck;
    public static javax.swing.JLabel OppoGrave;
    public static javax.swing.JLabel OppoName;
    private javax.swing.JPanel OpponentHandPnl;
    public static javax.swing.JLabel QuesText;
    private java.awt.ScrollPane QuesTextPane;
    private javax.swing.JButton Rules;
    private javax.swing.JLabel deckLB;
    public static javax.swing.JLabel graveyardLB;
    public static javax.swing.JLabel healthLB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel manaLB;
    private javax.swing.JLabel myName;
    public static javax.swing.JLabel opponentNameLB;
    private javax.swing.JButton selectBtn;
    // End of variables declaration//GEN-END:variables

    // Actions for the Message and Game prompt Okay button
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == SendBtn) {
            try {
                String out = ChatTxtField.getText();

                JPanel p2 = formatLabel(out);

                ChatAreaPanel.setLayout(new BorderLayout());

                JPanel right = new JPanel(new BorderLayout());
                right.add(p2, BorderLayout.LINE_END);
                vertical.add(right);
                vertical.add(Box.createVerticalStrut(15));

                ChatAreaPanel.add(vertical, BorderLayout.PAGE_START);

                outStream.writeObject(out);
                outStream.flush();
                System.out.println("Sent");
                ChatTxtField.setText("");

            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (ae.getSource() == selectBtn) {
            if (null != moves) {
                switch (moves) {
                    case Playing:
                        String choice = AnsField.getText();
                        if (choice.equals("Yes") || choice.equals("YES") || choice.equals("yes") || choice.equals("y") || choice.equals("Y")) {
                            if (tempCard.getClass() == MagicCard.class) {
                                if (player.getInPlay().count() > 0) {
                                    Collections.shuffle(player.getInPlay().cards);
                                    for (Card card : player.getInPlay().getCards()) {
                                        String cardId = tempCard.getId();
                                        switch (cardId) {
                                            case "DPx2":
                                                ((BeastCard) card).setAttackBehaviour(new DoubleAttack());
                                                break;
                                            case "TPx3":
                                                ((BeastCard) card).setAttackBehaviour(new TripleAttack());
                                                break;
                                            case "H+2":
                                                ((BeastCard) card).setHealBehaviour(new HealByTwo());
                                                break;
                                            case "H+4":
                                                ((BeastCard) card).setHealBehaviour(new HealByFour());
                                                break;
                                            default:
                                                break;
                                        }
                                        break;

                                    }
                                    toRemove.add(tempCard);
                                    QuesText.setText("");
                                    AnsField.setText("");
                                    QuesTextPane.validate();
                                    player.useMana(tempCard.getManaCost());
                                    manaLB.setText(Integer.toString(player.getManaAvailable()));
                                    manaLB.validate();
                                    InPlayPnl.removeAll();
                                    maxInPlayCardptr = 0;
                                    for (Card InPlayCard : player.getInPlay().getCards()) {
                                        addCardsToHandPanel(InPlayCard, InPlayPnl, maxInPlayCardptr);
                                        maxInPlayCardptr++;
                                    }
                                    InPlayPnl.validate();
                                    GameFrame.validate();
                                    GameFrame.repaint();
                                    playing = false;
                                    tempCard = null;

                                } else {
                                    QuesText.setText("Sorry, can't play this Card right now!!");
                                    QuesTextPane.validate();
                                    GameFrame.validate();
                                    GameFrame.repaint();
                                    playing = false;
                                    tempCard = null;
                                }
                            } else {
                                player.getInPlay().add(tempCard);
                                toRemove.add(tempCard);
                                QuesText.setText("");
                                AnsField.setText("");
                                QuesTextPane.validate();
                                // System.out.println(tempCard.getManaCost());
                                player.useMana(tempCard.getManaCost());
                                manaLB.setText(Integer.toString(player.getManaAvailable()));
                                manaLB.validate();
                                GameFrame.validate();
                                GameFrame.repaint();
                                playing = false;
                                tempCard = null;
                            }

                        } else if (choice.equals("NO") || choice.equals("no") || choice.equals("n") || choice.equals("N") || choice.equals("No")) {
                            QuesText.setText("");
                            AnsField.setText("");
                            QuesTextPane.validate();
                            GameFrame.validate();
                            GameFrame.repaint();
                            playing = false;
                            tempCard = null;
                        }
                        break;
                    case Attacking:
                        if (AnsField.getText() != null) {
                            String response = AnsField.getText();

                            if (response.equals("Yes") || response.equals("yes") || response.equals("y") || response.equals("Y")) {
                                wantToAttack = true;
                                attacking = false;
                            } else if (response.equals("No") || response.equals("N") || response.equals("NO") || response.equals("n")) {
                                wantToAttack = false;
                                attacking = false;
                                QuesText.setText("");
                                AnsField.setText("");
                                QuesTextPane.validate();
                                GameFrame.validate();
                                GameFrame.repaint();
                            }
                        }
                        break;
                    case Choosing:
                        String target = AnsField.getText();
                        ImageIcon i = new ImageIcon("src/3.jpg");
                        if (target.equals("1")) { // Player
                            if (OpponentPlayer.damage(((BeastCard) tempCard).getAttack())) {
                                // if true returned players health <= 0
                                // winner
                                won = true;
                                try {
                                    outStream.writeObject(opponentWonChar);
                                    outStream.flush();
                                    outStream.reset();
                                } catch (IOException ex) {
                                    Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                winningMessage = player.getName() + "  Wins!!";
                                JOptionPane.showMessageDialog(this, winningMessage, "Winner", JOptionPane.INFORMATION_MESSAGE, i);
                                System.exit(0);

                            }
                            QuesText.setText(OpponentPlayer.getName() + " is now at " + OpponentPlayer.getHealth());
                            QuesText.validate();
                            GameFrame.validate();
                            GameFrame.repaint();
                            try {
                                exe.awaitTermination(1, TimeUnit.SECONDS);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else { // Beast, index is `target-2`
                            Card targetCard = OpponentPlayer.getInPlay().getCard(Integer.parseInt(target) - 2);
                            ((BeastCard) targetCard).damage(((BeastCard) tempCard).getAttack());
                            ((BeastCard) tempCard).damage(((BeastCard) targetCard).getAttack());
                        }
                        choosing = false;
                        AnsField.setText("");
                        GameFrame.validate();
                        GameFrame.repaint();
                        tempCard = null;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Setting up the format for the Chat Application - GUI Design
     *
     * @param out
     * @return
     */
    public JPanel formatLabel(String out) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">" + out + "</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        l1.setBackground(new Color(83, 182, 245));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(10, 10, 10, 40));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        l2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    // Method to add cards to the Hands of the user
    private void addCardsToHandPanel(Card card, JPanel HandPnl, int maxHandCardptr) {
        if (card.getClass() == BeastCard.class) {
            Card_GUI CardGUI = new Card_GUI((BeastCard) card);
            int x = (maxHandCardptr * 115) + 5;
            CardGUI.setCoordinates(x, 10);
            HandPnl.add(CardGUI.getCardPanel());
            HandPnl.validate();
            GameFrame.validate();
            GameFrame.repaint();
        } else if (card.getClass() == MagicCard.class) {
            SpellCards_GUI CardGUI = new SpellCards_GUI((MagicCard) card);
            int x = (maxHandCardptr * 115) + 5;
            CardGUI.setCoordinates(x, 10);
            HandPnl.add(CardGUI.getCardPanel());
            HandPnl.validate();
            GameFrame.validate();
            GameFrame.repaint();
        }
    }

        // sets Gui for attcking mode
    class AttackThrd implements Runnable {

        @Override
        public void run() {
            for (Iterator it = player.getInPlay().getCards().iterator(); it.hasNext();) {
                Card card = (Card) it.next();
                attacking = true;
                choosing = true;
                if (card.getManaCost() <= player.getManaAvailable()) {

                    //System.out.println(card);
                    validResponses = new String[]{"Yes", "yes", "y", "No", "no", "n"};
                    String prompt = "<html>" + player.getName() + " attack with \n" + card.getName() + "? (Yes/No): " + "</html>";
                    QuesText.setText(prompt);
                    QuesTextPane.validate();
                    GameFrame.validate();
                    GameFrame.repaint();
                    moves = Moves.Attacking;
                    while (attacking) {

                        try {
                            exe.awaitTermination(1, TimeUnit.SECONDS);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    if (wantToAttack) {
                        QuesText.setText("");
                        AnsField.setText("");
                        selectBtn.setEnabled(true);
                        QuesTextPane.validate();
                        AnsField.validate();
                        GameFrame.validate();
                        GameFrame.repaint();

                        ChooseAttackThrd chooseAttackThrd = new ChooseAttackThrd(card);
                        Future<?> chooseAttack = exe.submit(chooseAttackThrd);
                        try {
                            chooseAttack.get();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

        }
    }

        // sets Gui for Choosing which cards or either player to attack on 
    class ChooseAttackThrd implements Runnable {

        ChooseAttackThrd(Card card) {
            tempCard = card;
        }

        @Override
        public void run() {

            if (tempCard.getClass() == BeastCard.class) {
                int choice = 2;
                StringBuilder sb = new StringBuilder();
                sb.append("Who would you like to attack?");
                sb.append("<br>");
                sb.append("1. ").append(OpponentPlayer.getName()).append("<br>");
                for (Card otherCard : OpponentPlayer.getInPlay().getCards()) {
                    sb.append(choice).append(". ").append(otherCard).append("<br>");
                    choice++;
                }
                QuesText.validate();
                GameFrame.validate();
                GameFrame.repaint();
                for (int i = 1; i < choice; i++) {
                    prompts.add(String.valueOf(i));
                }
                sb.append("<br>");
                sb.append("Choose a number: ");

                String prompt = "<html>" + sb.toString() + "</html>";
                QuesText.setText(prompt);
                QuesText.validate();
                GameFrame.validate();
                GameFrame.repaint();
                QuesTextPane.validate();
                GameFrame.validate();
                GameFrame.repaint();
                moves = Moves.Choosing;
                while (choosing) {
                    try {
                        exe.awaitTermination(1, TimeUnit.SECONDS);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
    }
    // sets Gui for Inplay

    class PlayThrd implements Runnable {

        Boolean clicked = false;

        @Override
        public void run() {

            Card playedcard = null;
            for (Card card : player.getHand().getCards()) {
                playing = true;
                if (card.getManaCost() <= player.getManaAvailable()) {
                    tempCard = card;
                    moves = Moves.Playing;
                    String prompt = player.getName() + " play " + tempCard.getName() + "? (Yes/No) ";
                    QuesText.setText(prompt);
                    QuesText.validate();
                    GameFrame.validate();
                    GameFrame.repaint();
                    while (playing) {
                        try {
                            exe.awaitTermination(1, TimeUnit.SECONDS);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PoacketBeastGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    playedcard = card;
                }

            }

            for (Iterator it = toRemove.iterator(); it.hasNext();) {
                Card toAdd = (Card) it.next();
                if (toAdd.getClass() == BeastCard.class) {
                    addCardsToHandPanel(toAdd, InPlayPnl, maxInPlayCardptr);
                    maxInPlayCardptr++;
                }

            }

            player.getHand().removeAll(toRemove);
            toRemove.removeAll(toRemove);
            HandPnl.removeAll();
            maxHandCardptr = 0;
            for (Card tempCard : player.getHand().getCards()) {
                addCardsToHandPanel(tempCard, HandPnl, maxHandCardptr);
                maxHandCardptr++;
            }
            HandPnl.validate();
            InPlayPnl.validate();
            GameFrame.validate();
            GameFrame.repaint();

        }

    }

}
