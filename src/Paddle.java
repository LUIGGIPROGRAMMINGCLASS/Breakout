
import acm.graphics.GRect;

import java.awt.*;

public class Paddle extends GRect {

    public Paddle(double x, double y, double width, double height){
        super(x,y,width,height);
        setFilled(true);
        setFillColor(Color.lightGray);
    }

}