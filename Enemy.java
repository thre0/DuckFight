
package duck_fight;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Duck{
    public double tmpx;
    public double tmpy;
    //speed
    public float ConstDuckSpeedX;
    public float ConstDuckSpeedY;
    public int VarDuckSpeedX;
    public int VarDuckSpeedY;
    //speed  modifiers
    Random randomX;
    Random randomY;
    
    int randX;
    int randY;
    public Enemy(int id,int x, int y, BufferedImage EnemyImg,boolean flip,int DuckSize,int life, int armor,float constspeedX,float constspeedY,int VarspeedX,int VarspeedY,int shotratio){
        super(id,x,y,EnemyImg,true,DuckSize,life,armor);
        this.tmpx = super.x;
        this.tmpy = super.y;
        //speed
        this.ConstDuckSpeedX = constspeedX;
        this.ConstDuckSpeedY = constspeedY;
        this.VarDuckSpeedX = VarspeedX;
        this.VarDuckSpeedY = VarspeedY;
        //speed  modifiers
        randX = 0;
        randY = 0;   
        randomY = new Random();
        //action
        this.ShotRatio = shotratio;
    }
    public void MoveDuck()
    {
        ConstMove();
        //if(VarDuckSpeedX!=0||VarDuckSpeedY!=0)
        //    VarmMove();
    }
    private void ConstMove(){
        int RoundPosX;
        int RoundPosY;
        this.tmpx = this.tmpx + ConstDuckSpeedX ; 
        this.tmpy = this.tmpy + ConstDuckSpeedY ;
        RoundPosX = (int)Math.floor(this.tmpx);
        RoundPosY = (int)Math.floor(this.tmpy);
        if (RoundPosX!=this.x) this.x = RoundPosX; ;        
        if (RoundPosY!=this.y) this.y = RoundPosY; ;
    }
    private void VarMove(){
        boolean Xsign = randomX.nextBoolean();   
        boolean Ysign = randomY.nextBoolean();  
    }
}
