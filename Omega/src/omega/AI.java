package omega;
//fist problem Last played: 33 55 77 99 ...
//second problem caprturedbylist paei mexri ligoter apo tria pou de tha prepe
//ekei sto telos ta exeiw kanei sakta me tis theseis tvn entolon

import java.util.ArrayList;
import java.util.List;

public class AI {

    private List<Coordinates> coordinatesList;
    private List<Integer> capturedby, empties,lastplayed,tempempties,myListi,myListj;
    private int current_depth,alpha,beta,value,score,player;
    private static int[] nextmove;
    private int prunings=0;
    private int iterations=0;
    private static final int initial_depth=3;
    private static final long TIMEOUT_MSEC=10000;
    private int globalwhitemove,globalblackmove,blackmove,whitemove;

private long starttime;
private boolean timeout;



    public AI(List<Coordinates> coordinatesList,List<Integer> capturedby,int player){
        this.player=player;
        this.capturedby=capturedby;
        this.coordinatesList=coordinatesList;
        empties=new ArrayList<>();
        lastplayed=new ArrayList<>();
        myListi=new ArrayList<>();
        myListj=new ArrayList<>();
        int prunings=0;
        current_depth =initial_depth;
        alpha=-100000;
        beta=100000;



        //int forwho=current_depth%2==0?1: -1;
        //score=-10000;
timeout=false;
int maxdepth=(emptiesList(capturedby).size()-capturedby.size()%4)/2;
       starttime=Long.MAX_VALUE ;
       value = AlphaBeta_NegaMax(coordinatesList, capturedby, current_depth, alpha, beta, player);
       // System.out.println("Value "+ ( value ));
        globalblackmove=blackmove;
        globalwhitemove=whitemove;
        starttime=System.currentTimeMillis();
        for (int d=1;;d++) {
           if(current_depth>=maxdepth){return;}

                globalwhitemove=blackmove;
                globalblackmove=whitemove;
            System.out.println("blackmove "+ globalblackmove +" whitemove "+globalwhitemove);
            current_depth=initial_depth+d;
            value = AlphaBeta_NegaMax(coordinatesList, capturedby, current_depth, alpha, beta, player);
          System.out.println("current depth "+ (current_depth ));

            if (timeout ){ return; }
        }
       // System.out.println(iterations);

    }


    public int AlphaBeta_NegaMax(List<Coordinates> coordinates, List<Integer> capturedbyList, int depth, int alpha, int beta,int player){
       // System.out.println(capturedbyList.toString());

        //stop if timeout was reached
   if (System.currentTimeMillis()-starttime>TIMEOUT_MSEC){
       timeout=true;
       return alpha;
   }

         iterations+=1;


        
       //criterion to end recursion depth=0 or no more move are allowed
        if(depth==0 || emptiesList(capturedbyList).size()<=(coordinatesList.size()%4)){
            return Evaluation(capturedbyList,coordinatesList,player);
        }

        score=-1000000000; //initial score
        for(int i=0;i<emptiesList(capturedbyList).size();i++){//iteration for placing black stone
            if (depth==current_depth){
            myListi.add(emptiesList(capturedbyList).get(i));}
            lastplayed.add(emptiesList(capturedbyList).get(i));
            capturedbyList.set(emptiesList(capturedbyList).get(i),1);


            for(int j=0;j<emptiesList(capturedbyList).size();j++) { //iteration for placing white stone
               lastplayed.add(emptiesList(capturedbyList).get(j));

                myListj.add(emptiesList(capturedbyList).get(j));
                int temp=emptiesList(capturedbyList).get(j);
                capturedbyList.set(emptiesList(capturedbyList).get(j),0);


                  //  whichscore*=-1;

                value=-AlphaBeta_NegaMax(coordinates,capturedbyList,depth-1,-beta,-alpha,player);

                if(value>score){
                    score=value;

                    }

                if(score>alpha) {
                    alpha = score;
                   // System.out.println(capturedbyList);
                    if (depth==current_depth){
                    myListj.add(temp);
                    blackmove= myListi.get(myListi.size()-1);
                    whitemove= myListj.get(myListj.size()-1);

                       // System.out.println(capturedbyList);
//                        System.out.println("Evaluation "+Evaluation(capturedbyList,coordinatesList,player,current_depth));
//                  System.out.println("blackmove "+ blackmove +" whitemove "+whitemove);
//                  System.out.println("Score= "+score+" Alpha= "+alpha+" Beta= "+beta+ " Value= "+value);

                    }


                }
                capturedbyList.set(lastplayed.get(lastplayed.size()-1),-1);
                lastplayed.remove(lastplayed.size()-1);
                if(score>=beta){
                    prunings+=1;
                 //System.out.println("i pruned: "+prunings);
                    break;}
            }

            if (!lastplayed.isEmpty()){
                capturedbyList.set(lastplayed.get(lastplayed.size()-1),-1);
              lastplayed.remove(lastplayed.size()-1);
                }



        }


        return value;
    }




    public static int[] getNextmove(){


        return nextmove;
    }

    public int getWhiteMove(){
        return globalwhitemove;
    }
    public int getBlackMove(){
        return globalblackmove;
    }
    public List<Integer> getMyListi(){

        return myListi;
    }
    public List<Integer> getMyListj(){

        return myListj;
    }

public int getValue(){
        return value;
}


public List<Integer> emptiesList(List<Integer> capturedby){
        empties.clear();
    for(int i = 0; i< capturedby.size(); i++){
        if (capturedby.get(i)==-1) empties.add(i);
    }
        return empties;
}

public int Evaluation(List<Integer> capturedbyList, List<Coordinates> coordinatesList, int player){

   ScoreCalculator score=new ScoreCalculator(capturedbyList,coordinatesList);
        int score1=score.getScore1();
        int score2=score.getScore2();
        int groupdifference=score.compareGroups(player);
        int comp=player==0?1:-1;
        //if (current_depth%2==0){comp*=-1;}
        int eval=comp*(score1-score2)+groupdifference;

     //   System.out.println("Current evaluation "+eval);


    return eval;
}


}
