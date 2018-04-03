package duck_fight;

import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
import java.awt.image.*;
import java.awt.Image;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Random;
//import java.lang.Math;

public class Duck {
    int Duck_id;
    public boolean alive;
    int life;
    int armor;
    //position
    public int x;
    public int y;
    //duck image
    private BufferedImage duckImg;

    //action options
    public int WeaponID;
    public int DuckHitX;
    public int DuckHitY;
    public int ShotRatio;
    
    //public Duck(int x, int y, BufferedImage duckImg, boolean flip, int DuckSize, float constspeedX,float constspeedY,int VarspeedX,int VarspeedY,int shotratio)
    public Duck(int id, int x, int y, BufferedImage duckImg, boolean flip, int DuckSize, int life, int armor)
    {
        this.Duck_id = id;
        this.alive = true;
        //position
        this.x = x;
        this.y = y;
        //image options
        this.duckImg = duckImg;   
        if(flip) this.FlipDuck();    
        this.duckImg = ResizeDuck(this.duckImg,DuckSize);
        this.DuckHitX = this.duckImg.getWidth();
        this.DuckHitY = this.duckImg.getHeight();
        final int color = this.duckImg.getRGB(0, 0);
        final Image imageWithTransparency = makeColorTransparent(this.duckImg, new Color(color));
        this.duckImg = MakeDuckTransparent(imageWithTransparency);
        //action
        this.life = life;
        this.armor = armor;

    }
    
    public void FlipDuck(){
        
        int width = this.duckImg.getWidth();
        int height = this.duckImg.getHeight();
        
        BufferedImage tmpduckImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //System.out.println("!"+width+"!"+height);
        for(int y = 0; y < height; y++){
            for(int x = width - 1; x >= 0; x--){
            int p = duckImg.getRGB(x, y);            
            int lx = width - 1 - x;
            //System.out.println("!"+p+"!"+lx+"!"+y);
            tmpduckImg.setRGB(lx, y, p);
            }
        }
        this.duckImg = tmpduckImg;        
    }
    
    private static BufferedImage ResizeDuck(BufferedImage img, int NewSize) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage tmpDuck = new BufferedImage(NewSize, NewSize, img.getType());  
        Graphics2D g = tmpDuck.createGraphics();  
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
        g.drawImage(img, 0, 0, NewSize, NewSize, 0, 0, w, h, null);  
        g.dispose();  
        return tmpDuck;  
    }  
    //Duck Utility
    
    private static BufferedImage MakeDuckTransparent(final Image image)
    {
        final BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }
    private static Image makeColorTransparent(final BufferedImage im, final Color color)
    {
        final ImageFilter filter = new RGBImageFilter()
        {
            // the color we are looking for (white)... Alpha bits are set to opaque
           public int markerRGB = color.getRGB() | 0xFFFFFFFF;

            public final int filterRGB(final int x, final int y, final int rgb)
            {
                if ((rgb | 0xFF000000) == markerRGB)
                {
                    // Mark the alpha bits as zero - transparent
                     return 0x00FFFFFF & rgb;
                }
                else
                {
                    // nothing to do
                     return rgb;
                }
            }
         };
        final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
    
    //Move the duck.
     
    public void TeleportDuck(int x, int y)
    {
        this.x =x;
        this.y =y;
    }
    
    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(duckImg,x, y, null);
    }
    public int GiveWeapon(int id){
        this.WeaponID = id;
        return this.Duck_id;
    }
    public void HitTheDuck(int dmg){
        if(armor == 0) life-=dmg;
        else armor-=dmg;
        if(armor<=0) armor=0;
        if(life<=0) alive=false;
    } 
}
