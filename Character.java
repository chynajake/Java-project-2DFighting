package project;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Character extends Pane{
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

    public Character(){
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
                /*if(this.getBoundsInParent().intersects(Game.player_duo.getBoundsInParent())) { // if it meets another player
                    if (movingRight) {
                        if (this.getTranslateX() + Game.Count_Size_X == Game.player_duo.getTranslateX()){ // he can't go through 2nd player from left
                            this.setTranslateX(this.getTranslateX() - 1);
                            return;
                        }
                    } else {
                        if (this.getTranslateX() == Game.player_duo.getTranslateX() + Game.Jonny_Size_X) { // he can't go through 2nd player from right
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }else*/ if(this.getTranslateX() + Game.Count_Size_X == Game.WINDOW_X){// borders of window from right
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
                    /*else{
                        if(this.getTranslateY() == Game.player_duo.getTranslateY()+ Game.Jonny_Size_Y){ // hit bottom of other player
                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;
                        }
                    }*/
            this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
        }
    }
    public void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0,-50);
            canJump = false;
        }
    }
    public void Walking(){
        animation.setCount(2);
        animation.setOffsetX(5);
        animation.setOffsetY(302);
        animation.setWidth(151);
        animation.setHeight(290);
    }
}