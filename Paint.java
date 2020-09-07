import javax.swing.*;
import java.awt.*;

public class Paint extends JPanel {
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Snake.snake.repaint(g);
    }
}
