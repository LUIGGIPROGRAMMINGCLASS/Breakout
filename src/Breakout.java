import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Breakout extends GraphicsProgram {

    private Ball ball;
    private Paddle paddle;
    private Bricks lastbrickhit;
    private int numBricksInRow;
    private ArrayList<ArrayList<Bricks>> r = new ArrayList<>();

    @Override
    public void init(){

        numBricksInRow = (int) (getWidth() / (Bricks.WIDTH + 5.0));

        for (int row = 0; row < 6; row++) {
            r.add(new ArrayList<>());
            for (int col = 0; col < numBricksInRow; col++) {
                double brickX = 10 + col * (Bricks.WIDTH + 5);
                double brickY = 2 * Bricks.HEIGHT + row * (Bricks.HEIGHT + 5);
                Bricks brick = new Bricks(brickX, brickY, Color.RED);
                r.get(row).add(brick);
                add(brick);
            }
        }

        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 450, 200 ,10);
        add(paddle);
    }

    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me){
        // make sure that the paddle doesn't go offscreen
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth() / 2)){
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }

    private void gameLoop(){
        while(true){
            // move the ball
            ball.handleMove();

            // handle collisions
            handleCollisions();

            // handle losing the ball
            if(ball.lost){
                handleLoss();
            }

            pause(5);
        }
    }

    private void handleCollisions(){
        // obj can store what we hit
        GObject obj = null;

        // check to see if the ball is about to hit something

        if(obj == null){
            // check the top right corner
            obj = this.getElementAt(ball.getX()+ball.getWidth(), ball.getY());
        }

        if(obj == null){
            // check the top left corner
            obj = this.getElementAt(ball.getX(), ball.getY());
        }

        //check the bottom right corner for collision
        if (obj == null) {
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }
        //check the bottom left corner for collision
        if (obj == null) {
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

        // see if we hit something
        if(obj != null){

            // lets see what we hit!
            if(obj instanceof Paddle){
                paddle.setSize(paddle.getWidth()*0.995,paddle.getHeight());
                if(ball.getX() < (paddle.getX() + (paddle.getWidth() * .2))){
                    // did I hit the left side of the paddle?
                    ball.bounceLeft();
                } else if(ball.getX() > (paddle.getX() + (paddle.getWidth() * .8))) {
                    // did I hit the right side of the paddle?
                    ball.bounceRight();
                } else {
                    // did I hit the middle of the paddle?
                    ball.bounce();
                }

            }


            if(obj instanceof Bricks){
                // bounce the ball
                if (obj != lastbrickhit) {
                    ball.bounce();
                    int x = 0;
                    lastbrickhit = (Bricks) obj;
                    for (int i = 0; i < r.size(); i++) {
                        for (int j = 0; j < r.get(0).size(); j++) {
                            if (r.get(i).get(j) == obj) {
                                x = j;
                            }
                        }
                    }
                    for (int i = 0; i < 100; i++) {
                        for (ArrayList<Bricks> s : r) {
                            Bricks b = s.get(x);
                            b.setLocation(b.getX(), b.getY() + b.getHeight() / 100);
                            if (b.getY() + b.getHeight() > paddle.getY() - b.getHeight()) {
                                b.setLocation(b.getX(), 0);
                            }
                            GObject p = getElementAt(b.getX()+b.getWidth()/2,b.getY()+b.getHeight());
                            if (p instanceof Ball){
                                p.setLocation(p.getX(),p.getY()+b.getHeight()/100);
                            }
                        }

                        pause(5);
                    }
                    // destroy the brick
                    if (((Bricks) obj).ohnono()) {
                        remove(obj);
                    }
                }
            }

        }

        // if by the end of the method obj is still null, we hit nothing
    }

    private void handleLoss(){
        ball.lost = false;
        reset();
    }

    private void reset(){
       Dialog.showMessage("you thought this would be a normal game of breakout");
       Dialog.showMessage("well... your playing a game by luiggi");
       Dialog.showMessage("btw the bricks have 5 lives");
       Dialog.showMessage("well scince you already lost...");
        exit();
    }

    public static void main(String[] args) {
        new Breakout().start();
    }

}
