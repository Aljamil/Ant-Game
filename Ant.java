import java.awt.EventQueue;
import javax.swing.JFrame;


public class Ant extends JFrame {

    public Ant() {

        add(new Board());
        
        setResizable(false);
        pack();
        
        setTitle("Ant");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {                
                JFrame ex = new Ant();
                ex.setVisible(true);                
            }
        });
    }
}