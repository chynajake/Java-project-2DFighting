package project;

import javafx.animation.AnimationTimer;
import  javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;


public class Game extends Application {
    private HashMap<KeyCode,Boolean> keys = new HashMap<>(); // pressed or not check
    
    //Image backgroundImg = new Image(getClass().getResourceAsStream("background.png"));
    public static int Count_Size_X = 151;
    public static int Count_Size_Y = 290;
    
    public static int Jonny_Size_X = 182;
    public static int Jonny_Size_Y = 255;
    
    public static final int WINDOW_X = 1200;
    public static final int WINDOW_Y = 600;
    
    public VBox vbox1;// for text name of characters
    public VBox vbox2;
    public static ProgressBar pb1;
    public static ProgressBar pb2;

    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    public static Count player_1; // граф 
    public static Jonny player_2; // джонни браво с отрезанной прической

    private void initContent(){
        /*ImageView backgroundIV = new ImageView(backgroundImg);
        backgroundIV.setFitHeight(14*BLOCK_SIZE);
        backgroundIV.setFitWidth(212*BLOCK_SIZE);*/ // Background sizing
        //ProgressBars
        pb1 = new ProgressBar(1);
        pb2 = new ProgressBar(1);
        pb1.setTranslateX(20);
        pb1.setTranslateY(20);
        pb2.setTranslateX(1100);
        pb2.setTranslateY(20);
        // PBars ended
        
        player_1 = new Count();
        player_2 = new Jonny();
        
        player_1.animation.play();
        player_2.animation.play();
        
        player_1.setTranslateX(0);
        player_1.setTranslateY(0);
        player_2.setTranslateX(1000);
        player_2.setTranslateY(0);
        gameRoot.getChildren().addAll(player_1, player_2, pb1, pb2);
        //appRoot.getChildren().addAll(backgroundIV,gameRoot); // background in appRoot
        appRoot.getChildren().add(gameRoot);
    }

    private void update1(){ // for count 
        if(isPressed(KeyCode.W) && player_1.getTranslateY()>=5){
            System.out.println("W");
            player_1.jumpPlayer();
        }
        if(isPressed(KeyCode.A) && player_1.getTranslateX()>=5){
            System.out.println("A");
            player_1.setScaleX(-1);
            player_1.Walking();
            player_1.animation.play();
            player_1.moveX(-20);
        }
        if(isPressed(KeyCode.D)){
            System.out.println("D");
            player_1.setScaleX(1);
            player_1.Walking();
            player_1.animation.play();
            player_1.moveX(20);
        }
        if(isPressed(KeyCode.H)){
            System.out.println("H");
            player_1.HandPunch();
            player_1.hit();
            player_1.animation.play();
        }
        if(isPressed(KeyCode.J)){
            System.out.println("J");
            player_1.LegPunch();
            player_1.hit();
            player_1.animation.play();
        }
        if(player_1.playerVelocity.getY()<10){                          
           player_1.playerVelocity = player_1.playerVelocity.add(0,1);
        }
        player_1.moveY((int)player_1.playerVelocity.getY());
    }
    
    private void update2(){ // for jonny
        if(isPressed(KeyCode.UP)){
            System.out.println("UP");
            player_2.jumpPlayer();
        }
        if(isPressed(KeyCode.LEFT)){
            System.out.println("A");
            player_2.setScaleX(1);
            player_2.Walking();
            player_2.animation.play();
            player_2.moveX(-20);
        }
        if(isPressed(KeyCode.RIGHT)){
            System.out.println("RIGHT");
            player_2.setScaleX(-1);
            player_2.Walking();
            player_2.animation.play();
            player_2.moveX(20);
        }
        if(isPressed(KeyCode.NUMPAD4)){
            System.out.println("NUMBPAD4");
            player_2.HandPunch();
            player_2.hit();
            player_2.animation.play();
        }
        if(isPressed(KeyCode.NUMPAD5)){
            System.out.println("NUMPAD5");
            player_2.LegPunch();
            player_2.hit();
            player_2.animation.play();
        }
        if(player_2.playerVelocity.getY()<10){                          
           player_2.playerVelocity = player_2.playerVelocity.add(0,1);
        }
        player_2.moveY((int)player_2.playerVelocity.getY());
    }
    
    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,false);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        initContent();
        Scene scene = new Scene(appRoot,1200,600);
        scene.setOnKeyPressed(event-> {
            keys.put(event.getCode(), true);
        });
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
            player_1.Standing();
            player_1.animation.play();
            player_2.Standing();
        });
        primaryStage.setTitle("2D Fighting");
        primaryStage.setScene(scene);
        primaryStage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update1();
                update2();
            }
        };
        timer.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}