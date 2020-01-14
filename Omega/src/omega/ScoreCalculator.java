package omega;

import java.util.ArrayList;
import java.util.List;

public class ScoreCalculator {

    private int n_groups, current_parent,max_size,score1,score2;
    private int[] parent,size;
    private int max1,max2,l;
    private List<Integer> myNeighbours, player1_parents,player1_sizes, player2_parents,player2_sizes, groupslist1,groupslist2,wplayerlist,bplayerlist,getMyNeighbours;

    public ScoreCalculator(List<Integer> capturedbyList, List<Coordinates> coordinatesList) {
        int n_hex = capturedbyList.size();
        parent = new int[n_hex];
        size = new int[n_hex];
        score1=1;
        score2=1;

        myNeighbours=new ArrayList<>();
        player1_parents=new ArrayList<>();
        player1_sizes=new ArrayList<>();
        player2_parents=new ArrayList<>();
        player2_sizes=new ArrayList<>();
        groupslist1=new ArrayList<>();
        groupslist2=new ArrayList<>();
        wplayerlist=new ArrayList<>();
        bplayerlist=new ArrayList<>();


        //Initialize parent,size arrays
        for (int i = 0; i < n_hex; i++) {
            parent[i] = i;
            size[i] = 1;
        }

//MAKE UNIONS
        for (int i = 0; i < n_hex; i++) {

            myNeighbours.clear();
            myNeighbours= FindNeighbours(i,coordinatesList);
//CHECK THROUGH MY NEIGHBOURS FOR SAME COLOR AND UNITE
           for(int j=0;j<myNeighbours.size();j++){
               if( (capturedbyList.get(i)!=-1 && capturedbyList.get(i)==capturedbyList.get(myNeighbours.get(j))) &&  (parent[i]!=parent[myNeighbours.get(j)]) ){

                   Unite(i,myNeighbours.get(j));
               }}}

//MAKE TWO SETS OF LISTS WITH EACH PLAYER PARENTS & SIZES PAIRS
               for(int i=0;i<n_hex;i++){
                   if (capturedbyList.get(i)==0){
                      // System.out.println(parent[i]);
                       player1_parents.add(parent[i]);
                       player1_sizes.add(size[i]);
                   }
                   else if(capturedbyList.get(i)==1){

                        player2_parents.add(parent[i]);
                        player2_sizes.add(size[i]);
                   }

               }
//MAKE A LIST OF PLAYER 1 GROUPS BY PARENT SERIAL
               for(int i = 0; i< player1_parents.size(); i++){
                   if((player1_sizes.get(i)>1) && (!groupslist1.contains(player1_parents.get((i))))){
                       groupslist1.add(player1_parents.get(i));
                   }
               }
               n_groups= groupslist1.size();
               //GO THROUGH PARENTS 1 LIST AND FIND MAX SIZE OF EACH DISTINCT PARENT
               for(int  i=0;i<n_groups;i++){

                   current_parent = groupslist1.get(i);
                   max1=player1_sizes.get((player1_parents.indexOf(current_parent)));
                   for (int j=0;j<player1_sizes.size();j++){
                       if ((player1_parents.get(j)== current_parent) && (player1_sizes.get(j)>max1)){
                           max1=player1_sizes.get(j); }}

                    wplayerlist.add(max1);
                   score1*=max1; }

        for(int i = 0; i< player2_parents.size(); i++){
            if((player2_sizes.get(i)>1) && (groupslist2.contains(player2_parents.get((i)))==false)){
                groupslist2.add(player2_parents.get(i));
            }
        }
        n_groups= groupslist2.size();
        for(int  i=0;i<n_groups;i++){

            current_parent = groupslist2.get(i);
            max2=player2_sizes.get((player2_parents.indexOf(current_parent)));
            for (int j=0;j<player2_sizes.size();j++){
                if (player2_parents.get(j)== current_parent && player2_sizes.get(j)>max2){
                    max2=player2_sizes.get(j); }}

            bplayerlist.add(max2);
            score2*=max2; }
    }

  public void Unite(int hex1, int hex2) {

        // make root of smaller rank point to root of larger rank
        if      (size[hex1] < size[hex2]) {
            l=parent[hex1];
            max_size=size[hex1];
            //update the previous parent group
            for (int i=0;i<parent.length;i++){
                if (parent[i]== l) {
                    if (max_size<size[i])max_size=size[i];
                    parent[i] = parent[hex2];
                }

            }
            size[parent[hex2]]+=max_size;

        }
        else  {
            l=parent[hex2];
            max_size=size[hex2];
            for (int i=0;i<parent.length;i++){
                if (parent[i]== l){
                    if (max_size<size[i])max_size=size[i];
                    parent[i]=parent[hex1];}
            }
            size[parent[hex1]]+=max_size;

        }
    }

    public List<Integer> FindNeighbours(int index,List<Coordinates> coordinatesList) {

        //cube coordinates of the current hex
        int x=coordinatesList.get(index).x;
        int y=coordinatesList.get(index).y;
        int z=coordinatesList.get(index).z;
        int u,v,w;

        for (int n = 0; n < coordinatesList.size(); n++) {
            u=coordinatesList.get(n).getX();
            v=coordinatesList.get(n).getY();
            w=coordinatesList.get(n).getZ();

            if(((u==x+1)&&(v==y-1)&&(w==z))||((u==x-1)&&(v==y+1)&&(w==z))||((u==x)&&(v==y-1)&&(w==z+1))||((u==x)&&(v==y+1)&&(w==z-1))||((u==x+1)&&(v==y)&&(w==z-1))||((u==x-1)&&(v==y)&&(w==z+1)))

            myNeighbours.add(n);

            }
        return myNeighbours;
                }

    public int compareGroups(int player)   {
        int goodgroups1=0;
        int badgroups1=0;
        int difference;
        int comp=1;
     for(int i=0;i<wplayerlist.size();i++){
         if ((wplayerlist.get(i)==2)||(wplayerlist.get(i)==3)){
             goodgroups1=goodgroups1+1;
         }
         else if (wplayerlist.get(i)>3){
             badgroups1=badgroups1+1;
         }
     }

        int goodgroups2=0;
        int badgroups2=0;
        for(int i=0;i<bplayerlist.size();i++){
            if ((bplayerlist.get(i)==2)||(bplayerlist.get(i)==3)){
                goodgroups2=goodgroups2+1;
            }
            else { //if(bplayerlist.get(i)>3)
                badgroups2=badgroups2+1;
            }
        }

        if (player==1){comp=-1;}
    difference=4*comp*(goodgroups1-goodgroups2)+2*comp*(badgroups2-badgroups1);
      return difference;
    }


    public int getScore1(){return score1;}
    public int getScore2(){return score2;}

//public int Evaluation(){
//
//        return score1-score2
//                = sizeofgroups23
//}

}
