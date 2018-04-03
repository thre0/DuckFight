package duck_fight;

public class Frame {
    int FrameNo;
    int PosX;
    int PosY;
    String sFrameNo;
    String sPosX;
    String sPosY;
    String Frame;
    
    boolean sent;
    boolean received;
    
    int  checksum;
    String schecksum;
    
    public Frame(int id,int x, int y){
        this.FrameNo = id;
        this.PosX=x;
        this.PosY=y;
        
        this.sent= false;
        this.received = false;
    }
    private int CheckSum(){
        int Sum;
        Sum = this.PosX+this.PosY;
        return Sum;
    }
    public String CreateFrame(){
        String Frame = new String("");
        this.sFrameNo=Integer.toString(this.FrameNo);
        while (this.sFrameNo.length() < 5) this.sFrameNo = "0" + this.sFrameNo;
        this.sPosX=Integer.toString(this.PosX);
        while (this.sPosX.length() < 5) this.sPosX = "0" + this.sPosX;
        this.sPosY=Integer.toString(this.PosY);
        while (this.sPosY.length() < 5) this.sPosY = "0" + this.sPosY;
        
        this.checksum = CheckSum();
        this.schecksum=Integer.toString(this.checksum);
        while (this.schecksum.length() < 5) this.schecksum = "0" + this.schecksum;
        
        Frame = this.FrameNo+this.sPosX+this.sPosY+this.checksum;
        this.Frame = Frame;
        
        return Frame;
    }
    public String getFrame(){
        return this.Frame;
    }
    
}
