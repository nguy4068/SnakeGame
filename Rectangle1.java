import java.awt.*;

public class Rectangle1 extends Rectangle {
    public String color;
    public int x;
    public int y;
    public int WIDTH;
    public int HEIGHT;
    public Rectangle1(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.WIDTH = width;
        this.HEIGHT = height;
    }
    public void setColor(String color){
        this.color = color;
    }
    public String getColor(){
        return this.color;
    }
}
