import acm.graphics.GCanvas;
import acm.graphics.GOval;

import java.awt.*;

public class Ball extends GOval {

    private double deltaX = 1;
    private double deltaY = -1;
    private GCanvas screen;
    public boolean lost = false;

    public Ball(double x, double y, double size, GCanvas screen){
        super(x, y, size, size);
        setFilled(true);
        setFillColor(Color.white);
        this.screen = screen;
    }

    public void handleMove(){
        // move the ball
        move(deltaX, -deltaY);

        // check to see if the ball is too high
        if(getY() <= 0){
            // start moving down
            deltaY *= -1.005;
        }

        // check to see if the ball is too low
        if(getY() >= screen.getHeight() - getHeight()){
            // set the lost flag
            lost = true;
            deltaX = 1;
            deltaY = -1;
        }

        // check to see if the ball hits the left side of the screen
        if(getX() <= 0){
            // start moving right
            deltaX *= -1.005;
        }

        // check to see if the ball hits the right side of the screen
        if(getX() >= screen.getWidth()-getWidth()){
            // start moving left
            deltaX *= -1.005;
        }
    }

    public void bounce(){
        // flip the y direction
        deltaY *= -1.005;
    }

    public void bounceLeft(){
        // flip the y direction
        deltaY *= -1;
        // force the x direction to be negative
        deltaX = -Math.abs(deltaX*1.005);
    }

    public void bounceRight(){
        // flip the y direction
        deltaY *= -1;
        // force the x direction to be positive
        deltaX = Math.abs(deltaX*1.005);
    }
}