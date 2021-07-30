import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Disc extends Object{
    private int x0,y0;  //Contains info on where the disk is drawn.
    private int rodNumber;  //1,2 or 3. Determines the rod that holds the disc.
    private int indexer;   // 1,2,3 ... max. Detrmines the size of a disc form its postion.
    public final int GAP=3,W,H=10;  //Spacing gap between discs, width,height.
    private static final int MAX_DISC=7,
                      BASE=400;    //Y-coordinates at the drawing area.
    private Rod rod;
    public static int[] discsMade={0,0,0};  //Monitors the number of discs formed on each rod.
    private static int[][] discMonitor=new int[MAX_DISC][3];  //Two dimensional array that monitors the discs.
    private static int n=0; //Number of discs formed.
    private static boolean firstTime=true;
    private String winInfo;  //Contain the message to be displayed upon winning.
    private static long startTime; //For finding the time taken.
    private static int attempts=0; //Number of moves made.
    
    public Disc()
    {
        /**This constructor creates a disc whose sole purpose is to allow an array
         *of discs to be able to access member methods. It contains no important data.
         */                
        W=0; //final variable W has to be intitialized.
        //A restarting flag is also placed here, since this is called only once.
        if(firstTime)
        {
            firstTime=false;
        }        
    }
    public Disc(int index, int location)
    {
        if(firstTime==false)
        {
            restart();
            firstTime=true;
        }
        setIndex(index);
        setRodNumber(location);
        W=10+2*10*indexer;  //adjacent widths differ by 20 units
        rod=new Rod(rodNumber);
        setX0();
        setY0();
       
        discsMade[rodNumber-1]++;  //rodNumber-1 since rod count start from 1.
        discMonitor[MAX_DISC-n-1][rodNumber-1]=indexer;
        n++;  //One more disc made.
//        seeDiscMonitor();
    }
    public void seeDiscMonitor()
    {
        
        for(int i=0;i<discMonitor.length;i++)
        {
            for(int j=0;j<discMonitor[i].length;j++)
            {
               System.out.print(discMonitor[i][j]+"  ");
            }
            System.out.print("\n");
        }
        System.out.print("\n*******\n");
        for(int k=0;k<discsMade.length;k++)
        {
            System.err.print(discsMade[k]+" , ");
        }
        System.out.print("\n");        
        for(int l=0;l<discsMade.length;l++)
            System.err.print(getTop(l+1)+(l!=2?",":"."));
        System.out.print("\n");        
    }
    public void setIndex(int index)
    {
        if(index<1)
            indexer=1;
        else if(index>MAX_DISC)
            indexer=MAX_DISC;
        else
            indexer=index;            
    }
    public void setRodNumber(int location)
    {
        rodNumber=location%3;  //Toggle along the rods 1,2,3,1 ...
        if(rodNumber==0)
            rodNumber=3;
        if(rod!=null)  //Update location. Ignore for constuctor call.
        {
            setX0();
            setY0();
        }        
    }
    public void setX0()
    {
        rod.setX0(rodNumber);  //Update the rod's x coordinates.
        x0=(rod.x0+Rod.W/2)-W/2;  //center of rod, then shift left
    }
    public void setY0()
    {
        y0=BASE-(discsMade[rodNumber-1]+1)*(GAP+H);
    }
    public int getX0()
    {
        return x0;
    }
    public int getY0()
    {
        return y0;
    }        
    public int getRodNumber()
    {
        return rodNumber;
    }
    public int getTop(int rodNum)  //Returns the indexer for the topmost disc.
    {
        int topDisc=MAX_DISC;
        for(int i=0; i<discMonitor.length;i++)
        {
            if(discMonitor[i][rodNum-1]>0)
            {
                topDisc=discMonitor[i][rodNum-1]; //Indexer.
                break;
             }
        }
        return topDisc;        
    }
    public int getTopIndex(int rodNum)  //Returns the array index for the top disc.
    {
        int topDiscIndex=MAX_DISC-1;
        for(int i=0; i<discMonitor.length;i++)
        {
            if(discMonitor[i][rodNum-1]>0)
            {
                topDiscIndex=i; //Array index.
                break;
             }
        }
        return topDiscIndex;
    }
    
    public void moveDisc(Disc[] discs, int startRod,int finalRod)
    {
        /**
         *This method moves one of the discs in array discs from 
         *startRod to finalRod.
         *startRod and finalRod have to be distinct.
         *The top disc in finalRod should be wider (i.e have a larger indexer value)
         *than the top disc of the startRod. This is determined by the inequality
         *(getTop(finalRod)>getTop(startRod))
         */
        if(startRod==0||finalRod==0)
        {
//             System.err.println("\n INVALID MOVE!");
        }
        else  if(startRod!=finalRod) //Move only to another disc.
        {
//             System.err.println("Attempting to move disc from rod "+startRod+
//                                         " to rod "+finalRod);
         
            if(getTop(finalRod)>=getTop(startRod))  //Large disc on a small one.
            {
                
                int discTemp;
                int sR1,fR1; //Hold the array indices.
                int sR2;  //Hold the startRod's indexer.
                sR1=getTopIndex(startRod);
                sR2=getTop(startRod);
                discTemp=discMonitor[sR1][startRod-1];
                discMonitor[sR1][startRod-1]=0;
                fR1=getTopIndex(finalRod);
                if(discMonitor[fR1][finalRod-1]==0) //Case of an empty rod
                {
                    fR1++; //To cater for [fR1-1] below.
                }
                discMonitor[fR1-1][finalRod-1]=discTemp; //Place above.
                discs[sR2-1].setRodNumber(finalRod);      //Transfer a disc.
                discsMade[finalRod-1]++;
                discsMade[startRod-1]--; 
//                 System.err.println("\nsR1="+sR1+" sR2="+sR2+" fR1="+fR1+"\n");
//                 seeDiscMonitor();   //View  progress  
                if(attempts==0)  //Start timing after first successful move.
                {
                    startTime=System.currentTimeMillis();
                }
                attempts++;
                checkWin();
            }
            else
            {
//                 System.err.println("\nAttemp failed."+getTop(finalRod)+"<"+getTop(startRod));
            }           
        }
    }
    public void checkWin()
    {
        if(discsMade[1]==GamePanel.numberOfDiscs||discsMade[2]==GamePanel.numberOfDiscs)
        {
            winInfo="Congratulations!!! You won.\n";
            winInfo+="Time Taken "+(System.currentTimeMillis()-startTime)/1000+
                            " seconds\n";
            winInfo+="Total trials "+attempts+"\n";
            if(attempts==Math.pow(2,n)-1) //2^n-1 is the least possible no. of moves
            {
                winInfo+="Great Work!";
            }
            JOptionPane.showMessageDialog(null,
            winInfo);
            int a=JOptionPane.showConfirmDialog(null,
                                    "Would you like to play again?",
                                    "Try Again?",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
            if(a==0)  //Yes
            {
                TowerApplet.restart();                
            }
            else if(a==1) //No
            {
                System.exit(0);
            }            
        }
    }
    public synchronized void restart()
    {
        int i,j;
        n=0;
        for(i=0;i<discsMade.length;i++)
        {
            discsMade[i]=0;
        }
        for(i=0; i<discMonitor.length;i++)
        {
            for(j=0;j<discMonitor[i].length;j++)
            {
                discMonitor[i][j]=0;
             }
        }
        attempts=0;
        startTime=System.currentTimeMillis();
        System.err.println("Game restarted.");
    }
        
}//End of class Disc.