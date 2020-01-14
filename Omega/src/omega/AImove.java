//package omega;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AImove {
//    private int player,score,evaluation,value;
//    private boolean done=false;
//    private int round;
//    private List<Coordinates> coordinatesList;
//    private List<Integer> capturedbyList;
//
//    private int[] nextmove,empties;
//    private int position;
//    private List<Integer> trialmoves;
//
//
////
////    public AImove(int player,int round,List <Coordinates> coordinatesList,List<Integer> capturedbyList){
////
////        this.capturedbyList=capturedbyList;
////        this.coordinatesList=coordinatesList;
////        this.player=player;
////        this.round=round;
////    }
//
//    public AINextmove(List<Coordinates> coordinatesList,List<Integer> capturedbyList){
//
//        this.capturedbyList=capturedbyList;
//        this.coordinatesList=coordinatesList;
//        nextmove=new int[2];
//       // trialmoves=new ArrayList<>();
//        int s=NegaMax(List<Integer> capturedbyList,int depth,int alpha,int beta);
//
//
//
//    }
//
//    public int NegaMax(List<Integer> capturedbyList,int depth,int alpha,int beta){
//        int empt_counter=0;
//        int current_p=0;
//
//
//        for(int i = 0; i< capturedbyList.size(); i++){
//
//            if (depth==0){
//                return (Evaluate(capturedbyList));
//            }
//
//
//            if (capturedbyList.get(i)==-1) empt_counter++;
//        }
//
//        empties=new int[empt_counter];
//
//
//        if (empties==null || empties.length<=coordinatesList.size()%4){
//            return (Evaluate(capturedbyList));
//
//        }
//
//        score=-10000000;
//
//        for(int j=1; j<empties.length;) {
//
//            current_p = GenerateMove();
//            value = -NegaMax();
//            if (value > score) {
//                score = value;
//            }
//
//            UndoMove();
//            if (score >= beta) {
//                done=true;
//
//            }
//        }
//            if (done){return score;}
//
//
//
//
//        }
//
//
//    public int GenerateMove(colour){
//
//
//
//        return newmove;
//    }
//
//    public UndoMove(){
//
//
//
//    }
//
//    public int[] getNextmove() {
//        return nextmove;
//    }
//
//
//    public int Evaluate(List<Integer> capturedbyList){
//
//
//        return evaluation;
//    }
//
//}
