import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TowerApplet extends JApplet{
    public Container container;
    public JLabel label1,label2;
    public String text=" ",text2="Game started.";
    public GamePanel panel1;
    public JPanel buttonPanel,textPanel;
    public JButton button1,button2;
    public JMenuBar bar;
    public JMenu fileMenu,helpMenu;
    public JMenuItem newGame, options,save,quit, help,about;
    private int d;// Number of Discs
    private static String status; //Contains the game's status
    public void init()
    {
        bar=new JMenuBar();
       
        fileMenu=new JMenu("File");
        
        newGame=new JMenuItem("New Game");
        newGame.setMnemonic('N');
        newGame.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                restart();
            }
        }
        );

        fileMenu.add(newGame);
        
        options=new JMenuItem("Options...");
        options.setMnemonic('O');
        options.addActionListener(
            new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(TowerApplet.this,
                "Purchase the full version to get access to even more levels.",
                "Options",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        );
        fileMenu.add(options);

        //To be included in later version.
//         save=new JMenuItem("Save");
//         save.setMnemonic('S');
//         fileMenu.add(save);
        
        quit=new JMenuItem("Quit");
        quit.setMnemonic('Q');
        quit.addActionListener(
            new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                quit();
            }
        }
        );
        fileMenu.add(quit);
        fileMenu.setMnemonic('F');
        bar.add(fileMenu);
        
        helpMenu=new JMenu("Help");
        
        help=new JMenuItem("Application Help");
        help.setMnemonic('H');
        help.addActionListener(
            new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(TowerApplet.this,
                "Move all the discs to either the second"+
                "or the third rod. \n" +
                "Only one disc can be moved at a time. \n" +
                "A large disc cannot be placed on a smaller one.\n\n" +
                "To move a disc, click on the region were it is located \n" +
                "then click the region you want to move it to.",
                "Help",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        );
        helpMenu.add(help);
        
        about=new JMenuItem("About");
        about.setMnemonic('A');
        about.addActionListener(
            new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(TowerApplet.this,
                "Created by Linc 2009. \n\nInspired by a very old mobile phone game\n" +
                " by the same name and the book \n" +
                "by Deitel and Deitel:Java, How To Program.",
                "Help",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        );
        helpMenu.add(about);
        helpMenu.setMnemonic('H');
        bar.add(helpMenu);
        
        setJMenuBar(bar);
       
        container=getContentPane();
        container.setLayout(new BorderLayout());
        
        label1=new JLabel(text);
        label2=new JLabel(text2);
                
        textPanel=new JPanel();
        textPanel.setLayout(new GridLayout(1,2,3,3));
        textPanel.add(label1);
        textPanel.add(label2);
        container.add(textPanel,BorderLayout.NORTH);
        
        buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2,3,3));
        
        button1=new JButton("Restart");
        button1.addActionListener(
            new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               // JOptionPane.showMessageDialog(TowerApplet.this,
                //                                            "Purchase the full version to do so.");
                System.err.println("Restarting game.");
                 restart();
            }
        }
        );
        buttonPanel.add(button1);
        
        button2=new JButton("Exit");
        button2.addActionListener(
            new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                quit();
            }
        }
        );
        buttonPanel.add(button2);
        container.add(buttonPanel,BorderLayout.SOUTH);
        
        //A lousy place to ask, but it must come before the GamePanel constructor.
        String discNumber=JOptionPane.showInputDialog(TowerApplet.this,
                                                            "Enter the number of tiles (3-7).",3);
        try
        {
            d=Integer.parseInt(discNumber);
        }
        catch(NumberFormatException e)
        {
//             System.err.println("Invalid number format.");
            d=3;
        }
        finally
        {
           // System.err.println("##The chosen value is "+d+"##");
        }        
        
        panel1=new GamePanel(d);
//         panel1.addMouseMotionListener(
//             new MouseMotionAdapter(){
//             public void mouseMoved(MouseEvent e)
//             {
//                 int x,y;
//                 x=e.getX();
//                 y=e.getY();
//                 text="Coordinates are ("+x+" , "+y+")";
//                 label1.setText(text);
//                 //label2.setText(getStatus());
//                 
//             }
//         }
//         );
        panel1.addMouseListener(
            new MouseAdapter(){
            public void mouseClicked(MouseEvent e)
            {            
                label1.setText("LEVEL "+(d-2));
                label2.setText(getStatus());
            }                
        }
        );
        container.add(panel1,BorderLayout.CENTER);
        container.setBackground(Color.blue);
        
// //         setSize(500,550);
// //         setVisible(true);
    }//End of Constructor.
    
    public synchronized void quit()
    {
        JOptionPane.showMessageDialog(TowerApplet.this,
                                                            "Thank you for playing.","Close",
                                                            JOptionPane.INFORMATION_MESSAGE);
                System.err.println("\nProgramme was closed.");
                System.exit(0);
    }
    public static synchronized void restart()
    {
        String[] s={"Restart"};
        main(s);
    }

    public static synchronized void setStatus(String s)
    {
        status=s;
    }
    public static synchronized String getStatus()
    {
        return status;
    }
    
    public static void main(String args[])
    {
        
        TowerApplet appletObject=new TowerApplet();
        appletObject.init();
        appletObject.start();
        
        JFrame application=new JFrame("Tower of Hanoi");
        application.getContentPane().add(appletObject);
        application.setSize(489,546); //500-11,550-4
        application.setVisible(true);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}