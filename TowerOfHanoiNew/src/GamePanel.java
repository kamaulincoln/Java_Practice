import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel{
        public static int numberOfDiscs=3;  //Number of discs.
        public Rod[] rods=new Rod[3];
        public Disc[] discs;
        public Disc accessorDisc;  //An object that allows the array of discs to access to member methods.
        public int region=0;  //Divide the playing area into three regions: 1,2 and 3.
        public int[] seeBackGround={0,0,0}; //Highlights background as mouse moves.
        public int pick=0,drop=0;  //Pick a disc and drop  it at another region.
        public boolean pickable=true;   //True if a disc  can be picked. Toggles during mouse clicks.
        
        GamePanel(int d)
        {
            super();
            setNumberOfDiscs(d);
             discs=new Disc[numberOfDiscs];
            for(int i=0;i<rods.length;i++)
                rods[i]=new Rod(i+1);
            for(int j=discs.length;j>0;j--)
                discs[j-1]=new Disc(j,1);
            accessorDisc=new Disc();
            
            addMouseMotionListener(
                new MouseMotionAdapter(){
                    public void mouseMoved(MouseEvent e)
                    {
                       // System.err.print("\rMouse at ("+e.getX()+" , "+e.getY()+")");
                        int x=e.getX(),y=e.getY();
                        for(int i=0;i<3;i++)
                            seeBackGround[i]=0;
                        //Split the area into 3 regions.
                        if(x>=0&&x<=2*Rod.XMID&&y>=150&&y<=400)
                        {
                            region=1;
                            seeBackGround[0]=1;
                        }
                        else if(x>=2*Rod.XMID&&x<=4*Rod.XMID&&y>=150&&y<=400)
                        {
                            region=2;
                            seeBackGround[1]=1;
                        }
                        else if(x>=4*Rod.XMID&&x<=6*Rod.XMID&&y>=150&&y<=400)
                        {
                            region=3;
                            seeBackGround[2]=1;
                        }
                        else
                        {
                            region=0;
                        }
                        
                        repaint();    
//                         System.err.print("\rMouse in region "+region);
                    }
            }
            );
            addMouseListener(
                new MouseAdapter(){
                    public void mouseClicked(MouseEvent e)
                    {
                        int x=e.getX(),y=e.getY();
//                         System.err.println("\nMouse clicked in region "+region);
                        if(region!=0)  //Ignore empty rods.
                        {
                            if(pickable)
                            {
                                if(Disc.discsMade[region-1]!=0)
                                {
                                    pick=region;
                                    TowerApplet.setStatus("Picked the disc at rod "+pick);
//                                     System.err.println("\n**Picked at region "+pick+"\n");
                                    rods[pick-1].setColor(Color.red);
                                    repaint();
                                    pickable=false;  //To move to else block on next click.
                                }
                            }
                            else
                            {
                                drop=region;
                                TowerApplet.setStatus("Placed the disc at rod "+drop);
//                                 System.err.println("\n**Dropped at region "+drop+"\n");
                                rods[pick-1].setColor(Color.blue);
                                repaint();
                                pickable=true;  ////To move to if block on next click.
                                if(pick!=drop&&(pick!=0&&drop!=0))
                                 {
                                        accessorDisc.moveDisc(discs,pick,drop);
                                        repaint();
                                }
                            }
                        }
                        
                    }
            }
            );
        }
        public void setNumberOfDiscs(int n)
        {
            if(n<3)
            {
                numberOfDiscs=3;
            }
            else if(n>7)
            {
//                  System.err.println("\nInvalid choice.\n");
                 numberOfDiscs=7;
            }
            else
            {
                numberOfDiscs=n;
            }
        }
        public void paintComponent(Graphics g)
        {
            //Draw backGrounds for the areas based on mouse postion.
             
             for(int k=0;k<3;k++)
             {
                 if(seeBackGround[k]==1)
                 {
                     g.setColor(Color.yellow);
                     g.fillRect(k*160,140,160,260);
                 }
                 else
                 {
                    g.setColor(Color.lightGray);
                    g.fillRect(k*160,140,160,260);
                 }
             }
            //Draw the three rods.
            for(int i=0;i<rods.length;i++)
            {
                g.setColor(rods[i].getColor());
                g.fillRect(rods[i].getX0(),rods[i].getY0(),Rod.W,Rod.H);
            }
            
            //Draw the Base.
            g.setColor(Color.orange);
            g.fillRect(0,400,480,50);
            
            //Draw the discs.
            g.setColor(Color.green);
            for(int j=0;j<discs.length;j++)
            {
                g.fillRoundRect(discs[j].getX0(),discs[j].getY0(),discs[j].W,discs[j].H,
                                           discs[j].H,discs[j].H);  //Arcs equal to height.
            }            
        }
    }