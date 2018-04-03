
package duck_fight;

import java.awt.image.BufferedImage;

public class Weapon {
    
    public int WeaponID;
    public int Wielder;
    public int WielderType;//1-player, 2-Enemy, 3-Bullet
    public int x;
    public int y;
    private BufferedImage bullImg;
    boolean right;
    
    public Weapon(int id,BufferedImage bimg,boolean right){
        this.WeaponID = id;
        this.bullImg =bimg;
        this.right = right;
    }
    public Bullet Bang(int bid){
        Bullet bull;
        if(this.right)
        { bull = new Bullet(bid,x,y,bullImg,true,20,1,0.5f,0,0,0,0);}
        else
        {bull = new Bullet(bid,x,y,bullImg,true,20,1,-0.5f,0,0,0,0);}
        return bull;
    }
    public void StickWielder(NetDuck Wielder){
        this.x = Wielder.x;
        this.y = Wielder.y;
        this.right = Wielder.right;
    }
    public void StickWielder(Duck Wielder){
        this.x = Wielder.x;
        this.y = Wielder.y;
    }
    
}
