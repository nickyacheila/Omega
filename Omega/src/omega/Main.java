package omega;



import javax.swing.JFrame;
import java.awt.*;


public class Main  {



    public static void main(String[] args)  {

        JFrame f = new JFrame("Omega");
        f.setSize(1100,700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setBackground(new Color(177, 211, 211));
        GameBoard g = new GameBoard();
        f.setResizable(false);
        f.setContentPane(g);
        f.setVisible(true);
    }
}
