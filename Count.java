package project;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Count extends Pane{
    Image countImg = new Image(getClass().getResourceAsStream("Count.png"));
    ImageView imageView = new ImageView(countImg);
    Rectangle2D rect;
    int count = 3;
    int columns = 4;
    int offsetX = 0;
    int offsetY = 5;
    int width = 151;
    int height = 290;
    public SpriteAnimation animation;
    public Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;
    private boolean canHit = true;

    public Count(){
        imageView.setFitWidth(151);
        imageView.setFitHeight(290);
        rect = new Rectangle2D(offsetX,offsetY,width,height);
        imageView.setViewport(rect);
        animation = new SpriteAnimation(this.imageView,Duration.millis(1500),count,columns,offsetX,offsetY,width,height);
        getChildren().addAll(this.imageView);
    }

    public void moveX(int value){
        boolean movingRight = value > 0;
        for(int i = 0; i<Math.abs(value); i++) {
                if(this.getBoundsInParent().intersects(Game.player_2.getBoundsInParent())) { // if it meets another player
                    if (movingRight) {
                        if (this.getTranslateX() + Game.Count_Size_X == Game.player_2.getTranslateX()){ // he can't go through 2nd player from left
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == Game.player_2.getTranslateX() + Game.Jonny_Size_X) { // he can't go through 2nd player from right
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }else if(this.getTranslateX() + Game.Count_Size_X == Game.WINDOW_X){// borders of window from right
                    this.setTranslateX(this.getTranslateX() - 1); 
                    return;
                }else if(this.getTranslateX() == 0){ // borders of window from left
                    this.setTranslateX(this.getTranslateX() + 1);
                    return;
                }
            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
        }
    }
    public void moveY(int value){
        boolean movingDown = value >0;
        for(int i = 0; i < Math.abs(value); i++){
                    if(movingDown){
                        if(this.getTranslateY()+ Game.Count_Size_Y == Game.WINDOW_Y){ // make it to land on ground
                            this.setTranslateY(this.getTranslateY()-1);
                            canJump = true;
                            return;
                        }
                    }
                    else{
                        if(this.getTranslateY() == Game.player_2.getTranslateY()+ Game.Jonny_Size_Y){ // hit bottom of other player
                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;
                        }
                    }
            this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
        }
    }
    
    public void hit(){
        System.out.println("ITENTERS");
        System.out.println(canHit);
        System.out.println(this.getScaleX()>0); // getScale > 0 - right              getscale < 0 left
        if(!canHit){
            if(this.getScaleX()>0 && this.getTranslateX()+Game.Count_Size_X > 
                    Game.player_2.getTranslateX() + (Game.Jonny_Size_X / 2)){ // if hit reaches 1/2 of the enemy he will be hitted
                Game.player_2.animation.stop();
                Game.player_2.setTranslateX(Game.player_2.getTranslateX()+5);
                Game.player_2.animation.play();
                System.out.println("HITTED");
                Game.pb2.setProgress(Game.pb2.getProgress()-0.001);
            }else if(this.getScaleX()<0 && this.getTranslateX() > 
                    Game.player_2.getTranslateX() + (Game.Jonny_Size_X / 2)){ // As well 1/2 but as we go from left
                Game.player_2.animation.stop();
                Game.player_2.setTranslateX(Game.player_2.getTranslateX()-5);
                Game.player_2.animation.play();
                System.out.println("HITTED");
                Game.pb2.setProgress(Game.pb2.getProgress()-0.001);
            }
        }
    }
    
    public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0,-35);
            canJump = false;
            canHit = false;
        }
    }
    public void Walking(){
        System.out.println("ISWALKING");
        animation.setCount(2);
        animation.setOffsetX(5);
        animation.setOffsetY(302);
        animation.setWidth(151);
        animation.setHeight(290);
        animation.setDuration(Duration.millis(200));
        imageView.setFitWidth(151);
        imageView.setFitHeight(290);
        Game.Count_Size_X = 151;
        Game.Count_Size_Y = 290;
        canHit = false;
    }
    public void Standing(){
        animation.setCount(3);
        animation.setOffsetX(0);
        animation.setOffsetY(5);
        animation.setWidth(151); // 151
        animation.setHeight(290);
        animation.setDuration(Duration.millis(1500));
        imageView.setFitWidth(151); //151
        imageView.setFitHeight(290);
        Game.Count_Size_X = 151;
        Game.Count_Size_Y = 290;
        canHit = true;
    }
    public void HandPunch(){
        if(canHit){
            animation.setCount(2);
            animation.setOffsetX(5);
            animation.setOffsetY(604);
            animation.setWidth(261);
            animation.setHeight(290);
            animation.setDuration(Duration.millis(1000));
            imageView.setFitWidth(261);
            imageView.setFitHeight(290);
            Game.Count_Size_X = 261;
            Game.Count_Size_X = 290;
            canHit = false;
        }
    }
    public void LegPunch(){
        if(canHit){
            animation.setCount(3);
            animation.setOffsetX(5);
            animation.setOffsetY(906);
            animation.setWidth(263);
            animation.setHeight(290);
            animation.setDuration(Duration.millis(500));
            imageView.setFitWidth(263);
            imageView.setFitHeight(290);
            Game.Count_Size_X = 263;
            Game.Count_Size_X = 290;
            canHit = false;
        }
    }
}