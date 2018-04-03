
package duck_fight;

import static duck_fight.Framework.Reward;
import static duck_fight.Framework.Score;
import static duck_fight.Framework.Ticker;
import static duck_fight.Framework.TimeScore;
import java.awt.event.MouseEvent;
import java.io.*;
//import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.applet.Applet;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Font;
import java.util.Random;

public class Game {
    
    private BufferedImage duckImg;
    public Duck PlayerDuck;
    private int DuckCounter;
    private ArrayList<Bullet> BulletDucks;
    private ArrayList<Enemy> TargetDucks;
    private ArrayList<Bullet> SparksDucks;
    private ArrayList<Weapon> WeaponList;
    private int[][] DuckWeapon;
    Font font = new Font("monospaced", Font.BOLD, 18);
    
    public Game()
    {
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent(1);
                Framework.gameState = Framework.GameState.PLAY;
            }
        };
        System.out.println("New Game thread start");
        threadForInitGame.start();
    }
    
    private void Initialize(){
        try
        {  
            duckImg = ImageIO.read(this.getClass().getResourceAsStream("/duck_fight/newpackage/duck1.jpg")); 
            WeaponList = new ArrayList<Weapon>();
            BulletDucks = new ArrayList<Bullet>();
            TargetDucks = new ArrayList<Enemy>();   
            SparksDucks = new ArrayList<Bullet>();
            DuckCounter = 1;
            DuckWeapon = new int[2][10000];
            
            WeaponList.add(new Weapon(1,duckImg,true)) ;  
            PlayerDuck = new Duck(1,239,143,duckImg,true,60,2,2);
            DuckWeapon[0][0] = PlayerDuck.Duck_id;
            DuckWeapon[1][0] = WeaponList.get(0).WeaponID;
            WeaponList.get(0).Wielder = PlayerDuck.GiveWeapon(1);
        }
        catch (Exception ex) {
            System.out.println("DUPA!");
            JOptionPane.showMessageDialog(null, "DUPA!DUPA", "InfoBox: " + "titleBar", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("@"+ex.getLocalizedMessage());
        }  
    }
    public void LoadContent(int level){
            //Framework.gameMode = Framework.GameMode.EASY;
            Random random = new Random();
            Random Shotrandom = new Random();
            int randY;
            int[] ShotPattern = new int[20];
            int ModeDucks = 5;
            int ShotMods = 5000;
            Framework.Score = 0;
            System.out.println(Framework.level);
            switch (Framework.gameMode){
                case EASY: ModeDucks=4+level*1; ShotMods=5000 - level*200;
                break;
                case NORMAL: ModeDucks=9+level*1;  ShotMods=3000 - level*200;
                break;
                case HARD: ModeDucks=14+level*1;  ShotMods=1000 - level*200;
                break;
            }
            for(int j=0; j<20;j++){
                ShotPattern[j] = Shotrandom.nextInt(10000)+ShotMods;
                //System.out.println(ShotPattern[j]);
            }
            for(int i=0; i<ModeDucks; i++){
                randY = 50+random.nextInt(500);    
                TargetDucks.add(new Enemy(DuckCounter+1,700,randY,duckImg,false,30,1,0,0,0,0,3,ShotPattern[i]));
                DuckCounter+=1;
            }
    }
    public void CheckHit(){
        for(int i = 0; i < BulletDucks.size(); i++)
            for(int j = 0; j < TargetDucks.size(); j++){
                if(BulletDucks.get(i).x+BulletDucks.get(i).DuckHitX>TargetDucks.get(j).x &&
                   BulletDucks.get(i).x<TargetDucks.get(j).x+TargetDucks.get(j).DuckHitX &&
                   BulletDucks.get(i).y+BulletDucks.get(i).DuckHitY>TargetDucks.get(j).y &&
                   BulletDucks.get(i).y<TargetDucks.get(j).y+TargetDucks.get(j).DuckHitY){
                    BulletDucks.get(i).HitTheDuck(1);
                    TargetDucks.get(j).HitTheDuck(BulletDucks.get(i).power);
                    Framework.Score+=1;
                    Framework.AllScore+=1;
                }
            }
        for(int i = 0; i < SparksDucks.size(); i++)
            if(SparksDucks.get(i).x+SparksDucks.get(i).DuckHitX>PlayerDuck.x &&
               SparksDucks.get(i).x<PlayerDuck.x+PlayerDuck.DuckHitX &&
               SparksDucks.get(i).y+SparksDucks.get(i).DuckHitY>PlayerDuck.y &&
               SparksDucks.get(i).y<PlayerDuck.y+PlayerDuck.DuckHitY){
                    if(!Framework.GodMode)PlayerDuck.HitTheDuck(SparksDucks.get(i).power);
                    SparksDucks.get(i).HitTheDuck(1);
            }
            
    }
    public void UpdateGame(Point mousePosition){
        int XPos=mousePosition.x;;
        int YPos=mousePosition.y;;
              
        //if(mousePosition.x<=0) XPos =5 ; else XPos =mousePosition.x;
        //if(mousePosition.y<=0) YPos =5 ; else YPos =mousePosition.y;
        if(XPos!=0&&YPos!=0)
            PlayerDuck.TeleportDuck(XPos, YPos);
        //System.out.println(mousePosition.x + "||" + mousePosition.y);
        for(int i = 0; i < TargetDucks.size(); i++){
            //TargetDucks.get(i).MoveDuck();
            if(Framework.Ticker%TargetDucks.get(i).ShotRatio==0){
                SparksDucks.add(new Bullet(DuckCounter+1,TargetDucks.get(i).x,TargetDucks.get(i).y,duckImg,false,10,1,-0.2f,0,0,0,0));
                DuckCounter+=1;
                //System.out.println("BANG");
            } 
                
        }
        for(int i = 0; i < BulletDucks.size(); i++){
            BulletDucks.get(i).MoveDuck();
            if(BulletDucks.get(i).x > 1000|| BulletDucks.get(i).x < 0) {
                BulletDucks.get(i).alive = false; //remove(i);
            }
        }
        for(int i = 0; i < SparksDucks.size(); i++){
            SparksDucks.get(i).MoveDuck();
            if(SparksDucks.get(i).x < 0 || SparksDucks.get(i).x > 1000){
                SparksDucks.get(i).alive = false; //remove(i);
            }
        }
        CheckHit();
        for(int i = 0; i < BulletDucks.size(); i++){
            if(!BulletDucks.get(i).alive)BulletDucks.remove(i);
        }    
        for(int i = 0; i < TargetDucks.size(); i++){
            if(!TargetDucks.get(i).alive)TargetDucks.remove(i);
        }    
        for(int i = 0; i < SparksDucks.size(); i++){
            if(!SparksDucks.get(i).alive)SparksDucks.remove(i);
        }    
        //if(Canvas.mouseButtonState(MouseEvent.BUTTON1)){
        if(Framework.MouseClicked){
            if(PlayerDuck.alive){
                //BulletDucks.add(new Bullet(DuckCounter+1,PlayerDuck.x,PlayerDuck.y,duckImg,true,20,1,0.5f,0,0,0,0));
                WeaponList.get(0).StickWielder(PlayerDuck);
                BulletDucks.add(WeaponList.get(0).Bang(DuckCounter+1));
                DuckCounter+=1;
            }
        }
        
        //if(Framework.Ticker==99)PlayerDuck.alive = false;
        if(!PlayerDuck.alive){
            Framework.gameState = Framework.GameState.OVER;
            
        }
        
        Framework.MouseClicked = false; 
        CheckLevelEnd();
    }
    public void CheckLevelEnd(){
        if(TargetDucks.size()<=0){
            Framework.gameState = Framework.GameState.RESULTS;  
            Framework.Reward=Framework.RewardType.NONE;
        }
    }
    public void DrawResults(Graphics2D g2d, Point mousePosition){
        //Framework.gameState = Framework.GameState.RESULTS;
        //Font font = new Font("monospaced", Font.BOLD, 18);
        Font fontsmall = new Font("monospaced", Font.BOLD, 15);
        //Font fontbig = new Font("monospaced", Font.BOLD, 30);
        
        g2d.setColor(Color.WHITE); g2d.setFont(fontsmall);g2d.drawString("You finished level "+Framework.level+", Choose your reward", 280, 50);
        g2d.setColor(Color.WHITE); g2d.drawString("WEAPON", 220, 150); g2d.drawRect(200, 100, 100, 100);
        g2d.setColor(Color.YELLOW); g2d.drawString("ARMOR", 420, 150); g2d.drawRect(400, 100, 100, 100);
        g2d.setColor(Color.MAGENTA); g2d.drawString("FANCY", 620, 150); g2d.drawRect(600, 100, 100, 100);
        
        g2d.setColor(Color.RED); g2d.setFont(fontsmall);g2d.drawString("SCORE: "+Framework.AllScore, 400, 400);
        g2d.setColor(Color.RED); g2d.setFont(fontsmall);g2d.drawString("LEVEL SCORE: "+Framework.Score, 400, 450);
        g2d.setColor(Color.RED); g2d.setFont(fontsmall);g2d.drawString("TIME: "+Framework.TimeScore+"."+Framework.Ticker, 400, 500);
        if(Framework.gameState==Framework.GameState.SHOW){
            switch (Framework.Reward){
                case WEAPON: {
                    g2d.setColor(Color.WHITE); g2d.setFont(fontsmall);g2d.drawString("You receive super double spread gun. Use it well", 280, 250);
                    }
                break;
                case ARMOR: {
                    g2d.setColor(Color.WHITE); g2d.setFont(fontsmall);g2d.drawString("You receive 100 Shield. Don't die so fast.", 280, 250);
                    }
                break;
                case FANCY: {
                    g2d.setColor(Color.WHITE); g2d.setFont(fontsmall);g2d.drawString("You found ultimate lagendary red hat of eternity!! WoW!", 280, 250);
                    }
                break;
                case NONE: {
                    g2d.setColor(Color.WHITE); g2d.setFont(fontsmall);g2d.drawString("You missed the button, LOOSER!!", 280, 250);
                    }
                break;
            }
        }
    }
    
    public void DrawLoading(Graphics2D g2d, Point mousePosition){
        g2d.setFont(font);
        g2d.drawString("LOADING", 10, 21);
    }
    public void DrawGame(Graphics2D g2d, Point mousePosition){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 1000, 600);  
        if(PlayerDuck.alive)
            PlayerDuck.Draw(g2d);
        for(int i = 0; i < BulletDucks.size(); i++)
        {
            if(BulletDucks.get(i).alive)
                BulletDucks.get(i).Draw(g2d);
        }
        for(int i = 0; i < TargetDucks.size(); i++)
        {
            if(TargetDucks.get(i).alive)
                TargetDucks.get(i).Draw(g2d);
        }
        for(int i = 0; i < SparksDucks.size(); i++)
        {
            if(SparksDucks.get(i).alive)
                SparksDucks.get(i).Draw(g2d);
        }
        //g2d.drawImage(grassImg, 0, Framework.frameHeight - grassImg.getHeight(), Framework.frameWidth, grassImg.getHeight(), null);
        
        //g2d.drawImage(sightImg, mousePosition.x - sightImgMiddleWidth, mousePosition.y - sightImgMiddleHeight, null);
        
        //g2d.setFont(font);
        //g2d.setColor(Color.darkGray);
        
        //for(URL url: urls){g2d.drawString("RUNAWAY: " + url.getFile(), 10, 21);}
        g2d.setColor(Color.BLUE);
        g2d.setFont(font);
        //g2d.drawString(""+BulletDucks.size(), 10, 21);
        //g2d.drawString(""+Framework.Ticker, 10, 21);
        g2d.setColor(Color.RED);
        g2d.drawString("Life "+PlayerDuck.life+"  Armor: "+PlayerDuck.armor, 15, 21);
        g2d.drawString("LEVEL "+Framework.level, 350, 21);
        g2d.setColor(Color.CYAN);
        g2d.drawString("Ducks Remaining: "+TargetDucks.size(), 600, 21);
        g2d.setColor(Color.BLUE);
        g2d.drawString(""+Framework.TimeScore+"."+Framework.Ticker, 10, 521);
        g2d.drawString(""+Framework.Score, 10, 471);
        g2d.drawString(":" + Framework.gameState, 10, 501);
        //g2d.drawString("SCORE: " + score, 440, 21);
    }
}
