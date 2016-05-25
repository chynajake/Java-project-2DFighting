package project;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Jonny extends Pane{
    Image jonnyImg = new Image(getClass().getResourceAsStream("Jonny.png"));
    ImageView imageView = new ImageView(jonnyImg);
    Rectangle2D rect;
    int count = 2;
    int columns = 4;
    int offsetX = 0;
    int offsetY = 5;
    int width = 182;
    int height = 255;
    public SpriteAnimation animation;
    public Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;
    private boolean canHit = true;

    public Jonny(){
        imageView.setFitWidth(182);
        imageView.setFitHeight(255);
        rect = new Rectangle2D(offsetX,offsetY,width,height);
        imageView.setViewport(rect);
        animation = new SpriteAnimation(this.imageView,Duration.millis(500),count,columns,offsetX,offsetY,width,height);
        getChildren().addAll(this.imageView);
    }

    public void moveX(int value){
        boolean movingRight = value > 0;
        for(int i = 0; i<Math.abs(value); i++) {
                if(this.getBoundsInParent().intersects(Game.player_1.getBoundsInParent())) { // if it meets another player
                    if (movingRight) {
                        if (this.getTranslateX() + Game.Jonny_Size_X == Game.player_1.getTranslateX()){ // he can't go through 2nd player from left
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == Game.player_1.getTranslateX() + Game.Count_Size_X) { // he can't go through 2nd player from right
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }else if(this.getTranslateX() + Game.Jonny_Size_X == Game.WINDOW_X){// borders of window from right
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
                        if(this.getTranslateY()+ Game.Jonny_Size_Y == Game.WINDOW_Y){ // make it to land on ground
                            this.setTranslateY(this.getTranslateY()-1);
                            canJump = true;
                            return;
                        }
                    }
                    else{
                        if(this.getTranslateY() == Game.player_1.getTranslateY()+ Game.Count_Size_Y){ // hit bottom of other player
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
        System.out.println(this.getScaleX()>0); // getScale > 0 - left             getscale < 0 right
        if(!canHit){
            if(this.getScaleX()<0 && this.getTranslateX()+Game.Jonny_Size_X + 20/*Extra pixels for jonny*/ > 
                    Game.player_1.getTranslateX() + (Game.Count_Size_X / 2)){ // if hit reaches 1/3 of the enemy he will be hitted
                Game.player_1.animation.stop();
                this.setTranslateX(this.getTranslateX()+5); // so his kick reaches count (super kick)
                Game.player_1.setTranslateX(Game.player_1.getTranslateX()+5);
                Game.player_1.animation.play();
                System.out.println("HITTED");
                Game.pb1.setProgress(Game.pb1.getProgress()-0.001);
            }else if(this.getScaleX()>0 && this.getTranslateX() - 20 < 
                    Game.player_1.getTranslateX() + (Game.Count_Size_X / 2)){ // As well 1/3 but as we go from left it will be 2/3
                Game.player_1.animation.stop();
                Game.player_1.setTranslateX(Game.player_1.getTranslateX()-5);
                Game.player_1.animation.play();
                System.out.println("HITTED");
                Game.pb1.setProgress(Game.pb1.getProgress()-0.001);
            }
        }
    }
    
    public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0,-35);
            canJump = false;
        }
    }
    public void Walking(){
        animation.setCount(3);
        animation.setOffsetX(5);
        animation.setOffsetY(295);
        animation.setWidth(170);
        animation.setHeight(250);
        imageView.setFitWidth(170);
        imageView.setFitHeight(250);
        Game.Jonny_Size_X = 170;
        Game.Jonny_Size_Y = 250;
        canHit = false;
    }
    public void Standing(){
        animation.setCount(2);
        animation.setOffsetX(5); // 0
        animation.setOffsetY(5);
        animation.setWidth(170);  //182
        animation.setHeight(250); //250
        imageView.setFitWidth(170); //170
        imageView.setFitHeight(250);
        Game.Jonny_Size_X = 170;
        Game.Jonny_Size_Y = 250;
        canHit = true;
    }
    public void HandPunch(){
        if(canHit){
            animation.setCount(2);
            animation.setOffsetX(30);
            animation.setOffsetY(570);
            animation.setWidth(200);
            animation.setHeight(250);
            animation.setDuration(Duration.millis(100));
            imageView.setFitWidth(200);
            imageView.setFitHeight(250);
            if(this.getScaleX()<0){
                this.setTranslateX(this.getTranslateX() + 1);
            }else if(this.getScaleX()>0){
                this.setTranslateX(this.getTranslateX() - 1);
            }
            Game.Jonny_Size_X = 200;
            Game.Jonny_Size_Y = 250;
            canHit = false;
        }
    }
    public void LegPunch(){
        if(canHit){
            animation.setCount(2);
            animation.setOffsetX(0);
            animation.setOffsetY(830);
            animation.setWidth(265); // 265
            animation.setHeight(270);
            imageView.setFitWidth(265);
            imageView.setFitHeight(250);
            if(this.getScaleX()<0){
                this.setTranslateX(this.getTranslateX() + 1);
            }else if(this.getScaleX()>0){
                this.setTranslateX(this.getTranslateX() - 1);
            }
            Game.Jonny_Size_X = 265;
            Game.Jonny_Size_Y = 250;
            canHit = false;
        }
    }
}
