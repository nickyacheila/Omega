package omega;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

 class GameBoard extends JPanel {

     private final int WIDTH = 1100;
     private final int HEIGHT = 700;
     private final int W2 = WIDTH / 2;
     private final int H2 = HEIGHT / 2;
     private HashMap<Integer, Hexagon> board;
     private Hexagon hex;
     private int move_counter=0;
     private List<Coordinates> coordinatesList;
     private List<Integer> capturedbyList;
     private AI ai;
     private int side_size = 3;
     //radius of a single hexagon
     private int radius=30;
     private int player = 0;
     private int turn = 0;
     private int color = 0;
     private int gameType = 0;

     private int maxmove;

     public GameBoard()  {

        //Init
         setBackground(Color.white);
         setFocusable(true);
         setPreferredSize(new Dimension(WIDTH, HEIGHT));
         coordinatesList = new ArrayList<>();
         capturedbyList=new ArrayList<>();
         board=new HashMap<>();



         // show menu and set players
        Menu menu= new Menu();
        side_size=menu.getBsize();
        gameType=menu.getGametype();

         int j=0;

//         whitePlayer = new Player(1, 0);
//         blackPlayer = new Player(0, 1);


         board = Init_HexGrid(side_size, radius); //build game board
         maxmove=board.size()-board.size()%4;  //find maximum number of moves allowed
         repaint();
         requestFocus();
         addMouseListener(new Mouse());

         if (gameType==1 && menu.getWhitePlayer()==1){//check if AI plays first
             player=1;
             AIMove();
             move_counter+=2;
             swapColors();
             swapPlayer();
             repaint();
         }
         else if(gameType ==2) // if AI vs AI then play the whole game
         { for(int i=0;i<maxmove;i+=2){
             move_counter=i;
             repaint();
             AIMove();
             swapColors();

         }
             repaint();
             EndofGame();
         }


        }




     @Override
     public void paintComponent(Graphics gg) { //board gets painted or repainted
         super.paintComponent(gg);
         Graphics2D g = (Graphics2D) gg;
         for (Hexagon hex : board.values()) {
             hex.draw(gg);
         }
     }

     private HashMap<Integer, Hexagon> Init_HexGrid ( int size, int r){


             int hex_side_distance = (int) (r * (Math.sqrt(3))) + 2; //distance between two hexagon centers
             int length = size;
             int c = 1;
             //calculate position for first hexagon
             int start_x = (size % 2 == 1) ? W2 - (size / 2) * hex_side_distance : W2 - (size / 2) * hex_side_distance - hex_side_distance / 2;
             int x_pos = start_x;
             int h_pos = H2 - hex_side_distance * (size - 1);
             int x_coord =0;
             int y_coord=size-1;
             int z_coord=-size+1;
             int n_hex=0;

             for (int i = 1; i <= 2 * size - 1; i++) {
                 for (int j = 1; j <= length; j++) {
                      hex=new Hexagon(x_pos, h_pos, r);

                        hex.setSerialNumber(n_hex);
                        coordinatesList.add(new Coordinates(x_coord,y_coord,z_coord)); //this will be used to calculate score
                        capturedbyList.add(-1); //this will be used in AI player
                        board.put(n_hex,hex );
                         n_hex += 1;
                         x_coord+= 1;
                         y_coord-= 1;

                         x_pos += hex_side_distance; //move to the next hexagon center
                 }

                 if (i >= 1 && i < size) {
                     x_pos = start_x - i * (hex_side_distance / 2);

                     length += 1;
                     x_coord-=length;
                     y_coord=size-1;


                 } else {
                     x_pos = start_x - (size - 1) * hex_side_distance / 2 + c * (hex_side_distance / 2);
                     length -= 1;
                     x_coord=-(size-1);
                     y_coord+=length;


                     c += 1;
                 }
                 h_pos += r + (r / 2);
                 z_coord=z_coord+1;

             }
             return board;

         }

     private void swapTurn(){
         turn = turn == 0 ? 1 : 0;
     }

     private void swapPlayer() {
         player = player == 0 ? 1 : 0;
     }

     private void swapColors() {
         color = color == 0 ? 1 : 0;
     }

     private class Mouse  extends MouseAdapter {

         /**
          * Handles a left click event and makes a play if necessary.
          *
          * @param e A MouseEvent to process.
          */
         public void mousePressed(MouseEvent e) {

             if (gameType==2 || (gameType==1 && player==1) ) {
                 return;
             }

             for (Hexagon hex : board.values()) {
                 if (hex.contains(e.getX(), e.getY()) && hex.hasBeenSelectedBy == -1 ) {
                     nextMove(hex.getSerialNumber());

                     return;
             }


         }}}

     private void nextMove(int serialNumber) {

         if (move_counter>=maxmove){  //check if we reached max number of moves allowed
             EndofGame();
             return;}

                if (player==0) {
                    Hexagon hexagon = board.get(serialNumber);
                    hexagon.setSelected(color);
                    capturedbyList.set(hexagon.getSerialNumber(), color);
                    move_counter=move_counter+1;
                    repaint();
                    //swapColors();
                    //swapTurn();
                        }

                if (gameType == 1 && turn==1 ) {
                             swapPlayer();
                            // swapColors();
                             swapTurn();
                         }
                if (gameType==1 && player==1 && turn==0 && move_counter<maxmove) {

             AIMove();
             swapPlayer();
             repaint();
             move_counter=move_counter+2;
             swapTurn();

         }


         if (gameType==0 && turn==1){swapColors();}

         swapTurn();
         swapColors();
         //System.out.println(move_counter);

         if (move_counter>=maxmove){  //check if we reached max number of moves allowed
             EndofGame();
             return;}
     }

     // Just for testing
     public void RandomMove(){
         int a = 0;
         //random moves for now
         do {
             Random rand = new Random();
             a = rand.nextInt(capturedbyList.size() - 1);
         } while (capturedbyList.get(a) != -1);

         hex = board.get(a);
         hex.setSelected(1);
         capturedbyList.set(hex.getSerialNumber(), 1);
         a = 0;
         //random moves for now
         do {
             Random rand = new Random();
             a = rand.nextInt(capturedbyList.size() - 1);
         } while (capturedbyList.get(a) != -1);
         hex = board.get(a);
         hex.setSelected(0);
         capturedbyList.set(hex.getSerialNumber(), 0);


     }


     public void AIMove(){
        //first move to save time put this players color towards the edges and the opponents' towards the center
       //  if (move_counter<(maxmove/3)){
             if (move_counter<maxmove/3){
             int a;
             boolean coordloc;
             do {

                 Random rand = new Random();
                 a = rand.nextInt(capturedbyList.size() - 1);
                 hex = board.get(a);
                 int x=Math.abs(coordinatesList.get(hex.getSerialNumber()).getX());
                 int y=Math.abs(coordinatesList.get(hex.getSerialNumber()).getY());
                 int z=Math.abs(coordinatesList.get(hex.getSerialNumber()).getZ());
                 coordloc=(x==side_size-1 || y==side_size-1 || z==side_size-1);

             } while (!coordloc || capturedbyList.get(hex.getSerialNumber())!=-1);


             hex.setSelected(color);
             capturedbyList.set(hex.getSerialNumber(), color);

             swapColors();
              //  boolean coordloc2=(Math.abs(coordinatesList.get(hex.getSerialNumber()).getX())==1 || Math.abs(coordinatesList.get(hex.getSerialNumber()).getY())==1 || Math.abs(coordinatesList.get(hex.getSerialNumber()).getZ())==1) && (Math.abs(coordinatesList.get(hex.getSerialNumber()).getX())==2 || Math.abs(coordinatesList.get(hex.getSerialNumber()).getY())==2 || Math.abs(coordinatesList.get(hex.getSerialNumber()).getZ())==2);

                 a=0;
                 boolean cond2,cond1;

                   do {
                       Random rand = new Random();
                     a = rand.nextInt(capturedbyList.size() - 1);
                     hex = board.get(a);
                     int u=Math.abs(coordinatesList.get(hex.getSerialNumber()).getX());
                     int v= Math.abs(coordinatesList.get(hex.getSerialNumber()).getY());
                     int w=Math.abs(coordinatesList.get(hex.getSerialNumber()).getZ());
                       //coordloc=((x==1 || y==1 || z==1) && (x==0 ||y==0 || z==0))&&(x!=side_size-1 || y!=side_size-1 || z!=side_size-1);
                       int centerish=(int)Math.floor((side_size)/2);
                       cond1=(u<=centerish && v<=centerish && w<=centerish) ;

                   } while ((!cond1)  || capturedbyList.get(hex.getSerialNumber())!=-1);


                 hex.setSelected(color);
                 capturedbyList.set(hex.getSerialNumber(), color);


             swapColors();
     }

         else {

             List<Integer> copy = new ArrayList<>(capturedbyList);
             ai = new AI(coordinatesList, copy, color);
             int[] aimove = ai.getNextmove();
             int blackmove = ai.getBlackMove();
             int whitemove = ai.getWhiteMove();

             Hexagon hexagonb = board.get(blackmove);
             hexagonb.setSelected(1);
             System.out.println(hexagonb.getSerialNumber()+ " is black now");
             capturedbyList.set(hexagonb.getSerialNumber(), 1);

             Hexagon hexagonw = board.get(whitemove);
             System.out.println(hexagonw.getSerialNumber()+ " is white now");
             hexagonw.setSelected(0);
             capturedbyList.set(hexagonw.getSerialNumber(), 0);


             repaint();

             //System.out.println(ai.getValue());
         }
     }

  public void EndofGame(){
      ScoreCalculator sc = new ScoreCalculator(capturedbyList, coordinatesList);
      int score1 = sc.getScore1();
      int score2 = sc.getScore2();
      String swinner;
      int winner;
      if(score1>score2){
          winner=0;
          swinner="White player is the winner!\n";
      }
      else if (score2>score1){
          winner=1;
          swinner="Black player is the winner!\n";
      }
      else{
          winner=2;
          swinner="It's a draw!\n";
      }
      JOptionPane.showMessageDialog(null,swinner+"White Player : "+score1+"\nBlack Player  : "+score2);


  }



 }