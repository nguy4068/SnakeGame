import javax.swing.*;
import java.awt.*;
public class Paint1 extends JPanel {
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Snake1.snake.repaint(g);
    }
}
