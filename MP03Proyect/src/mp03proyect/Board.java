/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mp03proyect;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author mirokshi
 */
public class Board extends JPanel implements ActionListener {

    JButton buttonStart = new JButton("Start");
    JTextField inputName = new JTextField(10);
    JLabel labelName = new JLabel("Jugador :");
    static File f = new File("scores.dat");
    static Puntuaciones p = new Puntuaciones();
    static int score;
    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 500;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean start = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;

    public Board() {

        initBoard();
    }

    private void startGame(Graphics g) {
        String msg = "Java Game Snake";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, (B_HEIGHT / 2) - 60);

        inputName.setLocation(250, (B_HEIGHT / 2));
        labelName.setForeground(Color.white);
        labelName.setLocation(170, (B_HEIGHT / 2));
        buttonStart.setLocation(217, (B_HEIGHT / 2) + 60);
        //Lsitenr button
        buttonStart.addActionListener(this);
        
        
        add(buttonStart);
        add(inputName);
        add(labelName);
        
        inputName.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    buttonStart.doClick();
                }
            }
        });
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();

    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/img/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/img/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/img/head.png");
        head = iih.getImage();
    }

    private void initGame() {

        dots = 2;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (start == false) {
            timer.stop();
            startGame(g);
        } else {
            timer.start();
            buttonStart.setVisible(false);
            inputName.setVisible(false);
            labelName.setVisible(false);
            doDrawing(g);
        }

    }

    private void doDrawing(Graphics g) {

        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            paintScoreinGame(g);
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {
//            gameOver(g);
            savedScore();
            readScore(g);
        }
    }

    private void paintScoreinGame(Graphics g) {

        String scr = "Score: " + p.getScorePlayer();
        Font small = new Font("Helveltica", Font.BOLD, 14);

        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(scr, B_WIDTH - 95, B_HEIGHT - 10);
    }


    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            score++;
            dots++;
            p.setScorePlayer(score);
            locateApple();
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonStart) {
            start = true;
            System.out.println("Start game!!!");
            p.setNamePlayer(inputName.getText());
        }

        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    //
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

        }
    }

    private static void savedScore() {

        ObjectOutputStream out = null;
        try {
            if (f.exists()) {
                out = new AppendingObjectOutputStream(new FileOutputStream(f, true));
            } else {
                out = new ObjectOutputStream(new FileOutputStream(f, true));
            }

            out.writeObject(p);
//            System.out.println(p);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error al tratar el archivo");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println(e);
                    System.out.println("Error al cerrar el archivo");
                }
            }
        }

    }

    private void readScore(Graphics g) {
        ObjectInputStream in = null;
        Puntuaciones puntuaciones = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        int i=0;
        while (true) {
            try {
                puntuaciones = (Puntuaciones) in.readObject();
            } catch (ClassNotFoundException | IOException e) {
                System.out.println(e);
                break;

            }
            String msg = puntuaciones.toString();
        Font small = new Font("Helvetica", Font.BOLD, 14);

        g.setColor(Color.pink);
        g.setFont(small);

        g.drawString(msg, B_WIDTH-370, i+ B_HEIGHT-450);
        System.out.println(msg);
        i+=20;
            //g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        }

            
        if (in != null) {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        timer.stop();
    }

    static class AppendingObjectOutputStream extends ObjectOutputStream {

        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }

}
