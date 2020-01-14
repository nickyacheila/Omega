package omega;

import javax.swing.*;

public class Menu {
    private int whitePlayer;
    private int colour;
    private int gametype,bsize;


   // Creates players according to user input

    public Menu(){
        String[] nopts={"3", "5", "7"};
        bsize= JOptionPane.showOptionDialog(null, "What should be the size of the board? Choose side: ", "Choose Board Size", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, nopts, nopts[1]);
if (bsize==0)
    bsize=3;
else if (bsize==1||bsize==7)
    bsize=bsize==1?5:7;
else
    bsize=5;

        String[] opts={"User vs User", "User vs PC", "PC vs PC"};
        gametype = JOptionPane.showOptionDialog(null, "Who will play at this game? ", "Choose Players", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);

        if (gametype ==1){
            String[] coloropts = {"White", "Black"};
            colour = JOptionPane.showOptionDialog(null, "Which colour would you like to play as?", "Choose Colour", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, coloropts, coloropts[0]);


            //if user clicked close then by default white is manual black is AI
            whitePlayer =colour==1?1:0;


        }



        JOptionPane.showMessageDialog(null,"White Player plays first!");


    }

    public int getWhitePlayer() {
        return whitePlayer;
    }


    public int getGametype(){
        return gametype;
    }

    public int getBsize(){return bsize;}
}




