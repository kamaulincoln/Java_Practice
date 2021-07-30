import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Rod extends Object{
        public static final int W=6,H=300;  //Width and height of the rod.
        public static final int XMID=80,BASE=450;
        public int x0,y0;   //Rod's corners-used for drawing.
        public int number; //1,2 or 3. For the three different rods.
        public Color color;        
        public Rod(int num)
        {
            setNumber(num);     //Ensure that the number is between 1 & 3         
            setX0(num);
            y0=BASE-H;
            setColor(Color.blue);
        }
        public void setNumber(int n)
        {
            number=n<1?1:n;
            number=n>3?3:n;
        }
        public int getNumber()
        {
            return number;
        }
        public void setX0(int n)   //set the rods x co-ordinate according to the rod number.
        {
            x0=XMID+(2*XMID*(n-1))-W/2;
        }
        public int getX0()
        {
            return x0;
        }
        public int getY0()
        {
            return y0;
        }
        public void setColor(Color c)
        {
            color=new Color(c.getRed(),c.getGreen(),c.getBlue());
        }
        public Color getColor()
        {
            return color;
        }
        
    }//End of class Rod.