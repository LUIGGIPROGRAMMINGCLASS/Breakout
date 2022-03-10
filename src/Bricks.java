import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import java.awt.Color;
public class Bricks extends GCompound {

    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;
    public GRect r;
    public int lives = 6;

    public Bricks(double x, double y, Color color){
        r = new GRect(WIDTH, HEIGHT);
        setLocation(x,y);
        add(r);
        r.setFillColor(new Color(245, 66, 90));
        r.setFilled(true);
        GImage i = new GImage("./mad.png");
        add(i);
        i.setSize(WIDTH,HEIGHT);
        i.setColor(r.getFillColor());
    }

    public boolean ohnono(){
        lives --;
        if(lives < 1){
            return true;
        }
        return false;
    }
}
