import java.awt.event.*;
import javax.swing.*;

public class Application extends JFrame implements ActionListener{

     static JFrame f;
     static ReversAI r;
     static JDialog d;
     static JTextField t;

     public static void main(String[] args)
     {

        f = new JFrame("Initialization");

        Application s = new Application();
        
        JPanel p = new JPanel();
        
        JLabel l=new JLabel("First type the depth of minimax algorithm and then pick your disk color.");
        p.add(l);
 
        JButton b = new JButton("Black");
        JButton w = new JButton("White");
 
        b.addActionListener(s);
        w.addActionListener(s);

        p.add(b);
        p.add(w);
 
        f.add(p);

        t = new JTextField(16);

        t.setSize(50,25);

        p.add(t);
 
        // set the size of frame
        f.setSize(450, 100);
 
        f.setVisible(true);
     }

     public void actionPerformed(ActionEvent e)
     {
         String s = e.getActionCommand();
         if (s.equals("Black")) {
            if(!t.getText().equals("")){ 
               r = new ReversAI(true,Integer.parseInt(t.getText()));
            }else{
               r = new ReversAI(true,-1);
            }
            f.setVisible(false);
         }
         else {
            if(!t.getText().equals("")){
               r = new ReversAI(false,Integer.parseInt(t.getText()));
            }else{
               r = new ReversAI(false,-1);
            }
            
            f.setVisible(false);
         }
     }

}