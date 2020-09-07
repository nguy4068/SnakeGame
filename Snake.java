import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Snake implements ActionListener, KeyListener{
    public final int WIDTH = 600;
    public final int HEIGHT = 600;
    public static Snake snake;
    public int timeDelay = 70;
    public Paint p;
    public int xStart = 0;
    public int yStart = 0;
    public int xApple;
    public int yApple;
    public int WIDTHSNAKE = 20;
    public int HEIGHTSNAKE = 20;
    public String initialStage;
    public String nextStage;
    public ArrayList<Rectangle> snakeBody = new ArrayList<>();
    public Rectangle apple;
    public Random rand = new Random();
    public int point;

    public Snake(){
        JFrame frame;
        Timer t = new Timer(timeDelay,this);
        p = new Paint();
        initialStage = "RIGHT";
        nextStage = "RIGHT";
        Rectangle a = new Rectangle(0,0,WIDTH,HEIGHT);
        Rectangle b = new Rectangle(20,0,WIDTH,HEIGHT);
        Rectangle c = new Rectangle(40,0,WIDTH,HEIGHT);
        xApple = rand.nextInt(WIDTH-20);
        yApple = rand.nextInt(HEIGHT-20);
        apple = new Rectangle(xApple,yApple,WIDTHSNAKE,HEIGHTSNAKE);
        snakeBody.add(a);
        snakeBody.add(b);
        snakeBody.add(c);
        frame = new JFrame();
        frame.setSize(WIDTH,HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(p);
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        t.start();

    }
    public void painSnake(Graphics g, Rectangle a){
        g.setColor(Color.green);
        g.fillRect(a.x,a.y,WIDTHSNAKE,HEIGHTSNAKE);
    }
    public void repaint(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.red);
        g.fillRect(xApple,yApple,WIDTHSNAKE,HEIGHTSNAKE);
        for (int i = 0; i < snakeBody.size();i++){
            painSnake(g, snakeBody.get(i));
        }
        g.setColor(Color.black);
        String s = ""+ point;
        g.setFont(new Font("Arial",1,20));
        g.drawString(s,300,20);

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int savedX1 = snakeBody.get(0).x;
        int savedY1 = snakeBody.get(0).y;
        int savedX2;
        int savedY2;
        if (nextStage.equals("LEFT")){
            snakeBody.get(0).x = snakeBody.get(0).x - 20;
        } else if (nextStage.equals("RIGHT")){
            snakeBody.get(0).x = snakeBody.get(0).x + 20;
        } else if (nextStage.equals("UP")){
            snakeBody.get(0).y = snakeBody.get(0).y - 20;
        } else if (nextStage.equals("DOWN")){
            snakeBody.get(0).y = snakeBody.get(0).y + 20;
        }
        for(int i = 1; i < snakeBody.size(); i++){
            savedX2 = snakeBody.get(i).x;
            savedY2 = snakeBody.get(i).y;
            snakeBody.get(i).x = savedX1;
            snakeBody.get(i).y = savedY1;
            savedX1 = savedX2;
            savedY1 = savedY2;
        }
        if (snakeBody.get(0).intersects(apple)){
            point++;
            xApple = rand.nextInt(WIDTH);
            yApple = rand.nextInt(HEIGHT);
            apple.x = xApple;
            apple.y = yApple;
            int lastX = snakeBody.get(snakeBody.size()-1).x;
            int lastY = snakeBody.get(snakeBody.size()-1).y;
            int mostLastX = snakeBody.get(snakeBody.size()-2).x;
            int mostLastY = snakeBody.get(snakeBody.size()-2).y;
            if (lastX == mostLastX){
                snakeBody.add(new Rectangle(lastX,lastY+WIDTHSNAKE,WIDTHSNAKE,HEIGHTSNAKE));
            } else if(lastY == mostLastY){
                snakeBody.add(new Rectangle(lastX + WIDTHSNAKE,lastY,WIDTHSNAKE,HEIGHTSNAKE));
            }
        }
        p.repaint();

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
            nextStage = "LEFT";
        } else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
            nextStage = "RIGHT";
        } else if(keyEvent.getKeyCode() == KeyEvent.VK_UP){
            nextStage = "UP";

        }else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
            nextStage = "DOWN";
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    public static void main(String[] args){
        snake = new Snake();
    }
}
