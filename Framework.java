package duck_fight;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class Framework extends Canvas {

    private Game game;
    private NetGame Ngame;
    public static String netOption1;
    public static String netOption2;
    public static boolean serverActive;
    public static boolean clientActive;
    public static enum GameState{START, LOADING, NLOADING, LOADED, PLAY, NPLAY, OVER, NOVER, SHOW, NSHOW, RESULTS, NRESULTS,RESTART}
    public static enum GameMode{NONE,EASY, NORMAL, HARD, SERVER, CLIENT, DUCKCLIENT}
    public static enum RewardType{NONE,WEAPON, ARMOR, FANCY}
    public static GameState gameState;
    public static GameMode gameMode;
    public static RewardType Reward;
    public static boolean GodMode;
    public static int level;
    public static boolean MouseClicked;
    public static int Ticker;
    public static int AllScore;
    public static int Score;
    public static int TimeScore;
    public static int ExtraTimeScore;
    
    Font font = new Font("monospaced", Font.BOLD, 18);
    Font fontsmall = new Font("monospaced", Font.BOLD, 20);
    Font fontbig = new Font("monospaced", Font.BOLD, 40);
    public Framework ()
    {
        super();
        level =1;
        Score = 0;
        TimeScore = 0;
        ExtraTimeScore = 0 ;
        Ticker = 0;
        gameState = GameState.START;
        Reward=RewardType.NONE;
        GodMode = true;
        this.setCursor(this.niceCursor);
        serverActive = false;
        clientActive = false;
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                GameLoop();
            }
        };
        gameThread.start();
    }
    private void GameLoop(){
        
        while(true){
            //game.Draw(g2d, mousePosition());
            //System.out.println("Repainting");
            switch(gameState){
                case PLAY: {game.UpdateGame(mousePosition());Ticker+=1;}
            break;
                case NPLAY: {Ngame.UpdateGame(mousePosition());Ticker+=1;}
            break;
                case RESULTS: {TimeScore=0;Ticker=1;}
            break;
                case NRESULTS: {TimeScore=0;Ticker=1;}
            break;
            }
            
            repaint();
            if(Ticker>100000&&gameState!=GameState.OVER){
                Ticker =0;
                Score=0;
                TimeScore+=1;}
        }       
        //repaint();
    }
    @Override
    public void Draw(Graphics2D g2d){
        g2d.setFont(font);
        switch(gameState){
            //case START:
            case PLAY: game.DrawGame(g2d, mousePosition());
                break;
            case NPLAY: Ngame.DrawGame(g2d, mousePosition());
                break;
            case LOADING: game.DrawLoading(g2d, mousePosition());
                break;
            case NLOADING: Ngame.DrawLoading(g2d, mousePosition());
                break;
            case START: {
                         //g2d.drawString("Don't touch the border. Game's not ready for it yet...", 10, 21);
                         g2d.setColor(Color.GREEN);
                         g2d.drawString("EASY", 70, 100);
                         g2d.drawRect(50, 50, 200, 100);
                         g2d.setColor(Color.YELLOW);
                         g2d.drawString("NORMAL", 70, 200);
                         g2d.drawRect(50, 150, 200, 100);
                         g2d.setColor(Color.RED);
                         g2d.drawString("HARD", 70, 300);
                         g2d.drawRect(50, 250, 200, 100);
                         
                         g2d.setColor(Color.RED);
                         g2d.drawString("SET SERVER", 420, 100);
                         g2d.drawRect(400, 50, 350, 100);
                         g2d.setColor(Color.RED);
                         g2d.drawString("CONNECT TO EXTERNAL SERVER", 420, 300);
                         g2d.drawRect(400, 250, 350, 100);
                         g2d.setColor(Color.RED);
                         g2d.drawString("CONNECT TO DUCK SERVER", 420, 450);
                         g2d.drawRect(400, 400, 350, 100);
                }
                break;  
            case RESULTS: {
                    this.setCursor(this.niceCursor);
                    game.DrawResults(g2d, mousePosition());
                }
                break;
            case SHOW: {
                    this.setCursor(this.niceCursor);
                    game.DrawResults(g2d, mousePosition());
                }
                break;
            case OVER: {g2d.setColor(Color.RED); g2d.setFont(fontbig);g2d.drawString("GAME OVER", 400, 300);
                        g2d.setColor(Color.RED); g2d.setFont(fontsmall);g2d.drawString("SCORE: "+Score, 400, 400);
                        g2d.setColor(Color.RED); g2d.setFont(fontsmall);g2d.drawString("TIME: "+TimeScore+"."+Ticker, 400, 500);
                }
                break;  
        }
    }

    private void newGame()
    {
        System.out.println("New Game");
        game = new Game();
    }
    private void newNetGame()
    {
        System.out.println("New NET Game");
        Ngame = new NetGame();
    }
    public Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
           //System.out.println(mp.x+"||"+mp.y);
            if(mp != null)
                return mp;//this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            
            return new Point(0, 0);
        }
        //return new Point(10, 10);
    }
    @Override
    public void keyReleasedFramework(KeyEvent e)
    {

    } 
    @Override
    public void mouseClicked(MouseEvent e)
    {
        //System.out.println(gameState+"||"+Reward);
        MouseClicked=true;
        
            switch (gameState){

                case START: {
                    gameMode=GameMode.NONE;
                    if(e.getX()>50&&e.getX()<250&&e.getY()>50&&e.getY()<350){
                        if(e.getY()>50&&e.getY()<150)gameMode=GameMode.EASY;
                        if(e.getY()>150&&e.getY()<250)gameMode=GameMode.NORMAL;
                        if(e.getY()>250&&e.getY()<350)gameMode=GameMode.HARD;
                        System.out.println("Mouse New Game"); 
                        MouseClicked=false; 
                        this.setCursor(this.blankCursor);
                        newGame(); 
                        gameState = Framework.GameState.LOADING;
                    }               
                    
                    if(e.getX()>400&&e.getX()<700){
                         if(e.getY()>50&&e.getY()<150) gameMode=GameMode.SERVER;
                         if(e.getY()>250&&e.getY()<350) gameMode=GameMode.CLIENT;
                         if(e.getY()>400&&e.getY()<500) gameMode=GameMode.DUCKCLIENT;
                    }
                    if(gameMode ==GameMode.SERVER) {
                        netOption2 = (String)JOptionPane.showInputDialog(
                                        this,
                                        "ServerPort",
                                        "Port",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        null,
                                        "6066");
                        serverActive = true;
                        MouseClicked=false; 
                        this.setCursor(this.blankCursor);
                        newNetGame();
                        gameState = Framework.GameState.NLOADING;
                    }
                    if(gameMode ==GameMode.CLIENT) {
                        netOption1 = (String)JOptionPane.showInputDialog(
                                        this,
                                        "Server Address",
                                        "Server",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        null,
                                        "localhost");
                        netOption2 = (String)JOptionPane.showInputDialog(
                                        this,
                                        "ServerPort",
                                        "Port",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        null,
                                        "6066");
                        clientActive = true;
                        MouseClicked=false; 
                        this.setCursor(this.blankCursor);
                        newNetGame();
                        gameState = Framework.GameState.NLOADING;
                    }
                    System.out.println(gameMode);
                    
                }
                break;
                case RESULTS: {
                    if(e.getY()>100&&e.getY()<200&& Reward==RewardType.NONE) {
                        if(e.getX()>200&&e.getX()<300)Reward=RewardType.WEAPON;
                        if(e.getX()>400&&e.getX()<500)Reward=RewardType.ARMOR;
                        if(e.getX()>600&&e.getX()<700)Reward=RewardType.FANCY; 
                    }
                    gameState = Framework.GameState.SHOW;
                    MouseClicked=false; 
                }
                break;
                case SHOW: {
                        level+=1;
                        MouseClicked=false;
                        this.setCursor(this.blankCursor);
                        switch (Reward){
                            case WEAPON: {}
                                break;                            
                            case ARMOR: game.PlayerDuck.armor+=100;
                                break;
                            case FANCY: {}
                                break;  
                            }
                        gameState = Framework.GameState.LOADING;
                        game.LoadContent(level);
                        Framework.gameState = Framework.GameState.PLAY;
                }
                break;
            }
        
        
    }
}
