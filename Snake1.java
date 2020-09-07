import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

/**
 * Snake game
 */
public class Snake1 implements ActionListener, KeyListener{
    public final int WIDTH = 600;//Width of the frame
    public final int HEIGHT = 600;//Height of the frame
    public static Snake1 snake;
    public int timeDelay = 80;//time delay for timer
    public Paint1 p;//class in charge of graphic illustration
    public int xStart = 0;
    public int yStart = 0;
    public int xApple;// x-coordinate of the apple
    public int yApple;// y-coordinate of the apple
    public int snakeLength;
    public int WIDTHSNAKE = 20;//width of the body of the snake
    public int HEIGHTSNAKE = 20;
    public String initialStage;
    public String nextStage;
    public ArrayList<int[]> snakeBody = new ArrayList<>();//array list used to store
                                                          //coordinate of grid constructing snake body
    //the game board containing 900 grids (each grid symbolizes a pixel)
    public Rectangle1[][] grid = new Rectangle1[30][30];
    public Random rand = new Random();
    public int point;//the score player earned
    public boolean gameOver = false;//game-state
    public int step;//number of grid the snake will pass through after the delay time
    Timer t;// timer
    Color[] listColor;// list of color for the body of the snake
    public Snake1(){
        listColor = new Color[]{Color.green.brighter().brighter(),Color.green,Color.green.darker()};
        JFrame frame;//frame
        t = new Timer(timeDelay,this);
        p = new Paint1();//initialize the graphic painter
        initialStage = "DOWN";//make the snake go down every time game started
        nextStage = "DOWN";
        int initialY = 0;//x-coordinate of the first grid in the board
        for (int i = 0; i <grid.length;i++){
            int initialX = 0;
            for (int j = 0; j < grid.length;j++){
                grid[i][j] = new Rectangle1(initialX,initialY,WIDTHSNAKE,HEIGHTSNAKE);
                grid[i][j].setColor("black");
                initialX = initialX+WIDTHSNAKE;
            }
            initialY = initialY + HEIGHTSNAKE;
        }
        //add the coordinates of those grid to the snakeBody list
        snakeBody.add(new int[]{3,0});
        snakeBody.add(new int[]{2,0});
        snakeBody.add(new int[]{1,0});
        //randomly create the position for the apple
        xApple = rand.nextInt(25);
        yApple = rand.nextInt(25);
        //default step, after each timeDelay elapsed, the snake will pass through one grid
        step = 1;
        frame = new JFrame();//initialize the frame
        frame.setSize(WIDTH,HEIGHT);
        frame.setVisible(true);//make the content of the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(p);//add the JPanel to the frame
        frame.addKeyListener(this);//enable the frame to listen to the input event

        t.start();

    }

    /**
     * This function is in charge of drawing every grid inside the frame
     * @param rec: the grid
     * @param g: Graphics object used to draw
     */
    public void paintGrid(Rectangle1 rec, Graphics g){
        if (rec.getColor().equals("black")){
            g.setColor(Color.black);
            g.fillRect(rec.x,rec.y,rec.HEIGHT,rec.WIDTH);
        } else if(rec.getColor().equals("green")){
            Color a = listColor[rand.nextInt(listColor.length)];
            g.setColor(a);
            g.fillRect(rec.x,rec.y,rec.HEIGHT,rec.WIDTH);
            g.setColor(Color.black);
        } else if(rec.getColor().equals("red")){
            g.setColor(Color.red.brighter());
            g.fillRect(rec.x,rec.y,rec.HEIGHT,rec.WIDTH);
        }else if(rec.getColor().equals("Orange")){
            //g.drawImage(image, rec.x, rec.y, rec.HEIGHT, rec.WIDTH, null);
            g.setColor(Color.orange);
            g.fillRect(rec.x,rec.y,rec.HEIGHT,rec.WIDTH);
            g.setColor(Color.black);
        }


    }

    /**
     * restart the game
     */
    public void restart(){
        gameOver = false;//reset gameOver to false
        nextStage = "DOWN";
        for (int i = 0; i <grid.length;i++){
            for (int j = 0; j < grid.length;j++){
                grid[i][j].setColor("black");
            }
        }
        snakeBody = new ArrayList<>();
        snakeBody.add(new int[]{3,0});
        snakeBody.add(new int[]{2,0});
        snakeBody.add(new int[]{1,0});
        //randomly create the position for the apple
        xApple = rand.nextInt(25);
        yApple = rand.nextInt(25);
        t.start();
    }

    /**
     * function used to check whether the snake has eaten the apple
     * @param xApple: the x-coordinate of the apple
     * @param yApple: y-coordinate of the apple
     * @param xSnake: x-coordinate of the snake head
     * @param ySnake: y-coordinate of the snake head
     * @return: true if head of the snake touches the apple , false otherwise
     */
    public boolean eatApple(int xApple,int yApple, int xSnake,int ySnake){
        if (xSnake  == xApple && ySnake==yApple){
            return true;
        }
        return false;
    }

    /**
     * function in charge of graphic illustration (evoked from the JPanel )
     * @param g: Graphics painter passed from the JPanel
     */
    public void repaint(Graphics g){
        for (int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid.length;j++){
                paintGrid(grid[i][j],g);
            }
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",1,20));
        String score = "" + point;
        g.drawString(score,300,30);
        if(gameOver){
            g.drawString(new String("GAME OVER!"),150,200);
            g.drawString(new String("HIT SPACE BUTTON TO START AGAIN!"),150,300);
        }
    }

    /**
     * check whether the head collides the tail
     * @param xHead: x-coordinate of the head
     * @param yHead: y-coordinate of the head
     * @return: true if the head hits the tail, false otherwise
     */
    public boolean Collide(int xHead,int yHead){
        for (int i = 1; i < snakeBody.size();i++){
            if (xHead == snakeBody.get(i)[0] && yHead == snakeBody.get(i)[1]){
                return true;
            }
        }
        return false;
    }
    @Override
    /**
     * called by Timer after every delay time elapse
     */
    public void actionPerformed(ActionEvent actionEvent) {
        grid[xApple][yApple].setColor("red");
        int[] head = snakeBody.get(0);//get the coordinate of the snake head
        int[] tail = snakeBody.get(snakeBody.size() - 1);// get the coordinate of the snake tail
        int savedX = head[0];
        int savedY = head[1];
        int newX = savedX;
        int newY = savedY;
        if (!gameOver){//if the game is not over
            if (nextStage.equals("LEFT")) {
                if (savedY - step < 0) {
                    gameOver = true;

                } else {
                    newY = savedY - step;//move the head to the left
                }
            } else if (nextStage.equals("RIGHT")) {
                if (savedY + step >= 29) {
                    gameOver = true;
                } else {
                    newY = savedY + step;//move the head to the right one grid
                }
            } else if (nextStage.equals("UP")) {//move the head up one grid
                if (savedX - step < 0) {
                    gameOver = true;
                } else {
                    newX = savedX - step;
                }
            } else if (nextStage.equals("DOWN")) {//move the head down one grid
                if (savedX + step >= 28) {
                    gameOver = true;
                } else {
                    newX = savedX + step;
                }
            }
        head = new int[]{newX, newY};//re-update the coordinate of the head
        snakeBody.add(0, head);//add the new head to the body
        snakeBody.remove(snakeBody.size() - 1);//remove that current tail
        if(Collide(newX,newY)){//check whether the head collide with the tail
            gameOver = true;
        }
        grid[tail[0]][tail[1]].setColor("black");//set the current tail color back to black
            /**
             * check whether the snake gets the apple
             */
        if (eatApple(xApple, yApple, newX, newY)) {
            point = point + 1;//increment the player's score
            grid[xApple][yApple].setColor("black");//set the apple back to a normal grid
            int[] finalSpot = snakeBody.get(snakeBody.size() - 1);//get the coordinate of the tail
            int[] almostFinal = snakeBody.get(snakeBody.size() - 2);//get the coordinate of the part
                                                                    //closest to the tail
            /**
             * check the current alignment of the snake (vertical or horizontal)
             * increment the length of the snake in accordance to the provided alignment
             */
            if (finalSpot[0] == almostFinal[0]) {
                snakeBody.add(new int[]{finalSpot[0], finalSpot[1] + step});
            } else if (finalSpot[1] == almostFinal[1]) {
                snakeBody.add(new int[]{finalSpot[0] + step, finalSpot[1]});
            }
            //randomly create a new apple
            xApple = rand.nextInt(25);
            yApple = rand.nextInt(25);
        }
        //set the color of whole snake to be green
        for (int i = 0; i < snakeBody.size(); i++) {
            int x = snakeBody.get(i)[0];
            int y = snakeBody.get(i)[1];
            grid[x][y].setColor("green");
        }
        //set the head color to orange;
        grid[newX][newY].setColor("Orange");
    }
        //call the paint function
        p.repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (!gameOver) {//only process the event if the game is not over
            //update the state of the snake
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT && !nextStage.equals("RIGHT")) {
                nextStage = "LEFT";
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT && !nextStage.equals("LEFT")) {
                nextStage = "RIGHT";
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && !nextStage.equals("DOWN")) {
                nextStage = "UP";

            } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN && !nextStage.equals("UP")) {
                nextStage = "DOWN";
            }
        } if (gameOver){//if the game is over
            nextStage = "IDLE";//make the snake stop running
            if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE){//restart the game
                restart();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
    public static void main(String[] args){
        snake = new Snake1();
    }
}