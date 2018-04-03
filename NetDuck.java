
package duck_fight;

import java.awt.image.BufferedImage;

public class NetDuck extends Duck{
    int bx;
    boolean right;
    public NetDuck(int id, int x, int y, BufferedImage duckImg, boolean flip, int DuckSize, int life, int armor){
        super(id,x,y,duckImg,flip,DuckSize,life,armor);
        bx=x;
        right = flip;
    }
    public void TeleportDuck(int x, int y)
    {
        this.x =x;
        this.y =y;
        if(bx>x&&right){
            this.FlipDuck(); 
            right = false;
        }
        if(bx<x&&!right){
            this.FlipDuck(); 
            right = true;
        }
        bx = x;
    }
    
}
