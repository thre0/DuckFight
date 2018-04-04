package duck_fight;

public class Frame {
    int FrameNo;
    int PosX;
    int PosY;
    int Addition;
    
    String Type;
    String Status;
    
    String sFrameNo;
    String sPosX;
    String sPosY;
    String sAddition;
    String Frame;
    
    boolean sent;
    boolean received;
    
    int  checksum;
    String schecksum;
    
    public Frame(int id,int x, int y, int add, String type, String status){
        this.FrameNo = id;
        this.PosX = x;
        this.PosY = y;
        this.Addition = add;
        this.Type = type;
        this.Status = status;
        
        this.sent= false;
        this.received = false;
    }
    private int CheckSum(){
        int Sum;
        Sum = this.FrameNo+this.PosX+this.PosY+this.Addition;
        Sum = Sum%883;
        return Sum;
    }
    public String CreateFrame(){
        String Frame = new String("");
        
        this.sFrameNo=Integer.toString(this.FrameNo);
        while (this.sFrameNo.length() < 5) this.sFrameNo = "0" + this.sFrameNo;
        while (this.sFrameNo.length() > 5) this.sFrameNo = this.sFrameNo.substring(1);
        
        this.sPosX=Integer.toString(this.PosX);
        while (this.sPosX.length() < 5) this.sPosX = "0" + this.sPosX;
        while (this.sPosX.length() > 5) this.sPosX = this.sPosX.substring(1);
        
        this.sPosY=Integer.toString(this.PosY);
        while (this.sPosY.length() < 5) this.sPosY = "0" + this.sPosY;
        while (this.sPosY.length() > 5) this.sPosY = this.sPosY.substring(1);
        
        this.sAddition=Integer.toString(this.Addition);
        while (this.sAddition.length() < 2) this.sAddition = "0" + this.sAddition;
        while (this.sAddition.length() > 2) this.sAddition = this.sAddition.substring(1);
        
        this.checksum = CheckSum();
        this.schecksum=Integer.toString(this.checksum);
        while (this.schecksum.length() < 5) this.schecksum = "0" + this.schecksum;
        
        Frame = this.sFrameNo+this.sPosX+this.sPosY+this.sAddition+this.Type+this.Status+this.checksum;
        this.Frame = Frame;
        
        return Frame;
    }
    public String getFrame(){
        return this.Frame;
    }
    public boolean ReadFrame(String Frame){
        String Rid;
        String Rx;
        String Ry;
        String Radd;
        String Rtype;
        String Rstatus;
        String RSum;
        int ISum;
        
        Rid = Frame.substring(0,5);
        Rx = Frame.substring(5,10);
        Ry = Frame.substring(10,15);
        Radd = Frame.substring(15,20);
        Rtype = Frame.substring(20,22);
        Rstatus = Frame.substring(22,24);
        RSum = Frame.substring(24);
        
        this.FrameNo=Integer.parseInt(Rid);
        this.PosX=Integer.parseInt(Rx);
        this.PosY=Integer.parseInt(Ry);
        this.Frame=Frame;
        ISum=Integer.parseInt(RSum);
        
        if(ISum == this.CheckSum())
            return true;
        else
            return false;
    }
    
}
