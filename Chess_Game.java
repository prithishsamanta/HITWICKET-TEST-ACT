import java.util.*;
import java.io.*;
import java.lang.*;

public class Chess_Game {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String[][] board = new String[5][5];
        
        //Taking Input
        LinkedHashMap<String, int[]> map1 = new LinkedHashMap<>();
        System.out.println("Player A Enter Your Positions: ");
        for(int i = 0; i < 5; i++){
            int[] pos = new int[2];
            pos[0] = 0; pos[1] = i;

            String temp = sc.next();
            map1.put("A-" + temp, pos);
        }
        System.out.println();

        LinkedHashMap<String, int[]> map2 = new LinkedHashMap<>();
        System.out.println("Player B Enter Your Positions: ");
        for(int i = 0; i < 5; i++){
            int[] pos = new int[2];
            pos[0] = 4; pos[1] = i;

            String temp = sc.next();
            map2.put("B-" + temp, pos);
        }
        

        //Creating Board
        board = fill_board(board, map1, map2);
        int n1 = 5, n2 = 5;
        int turn = 1;

        //Game Play
        while(n1 != 0 && n2 != 0){
            System.out.println("=========================================================================");
            System.out.println();

            if(turn == 1) System.out.println("Player A, Enter Your Move: ");
            else System.out.println("Player B, Enter Your Move: ");
            
            String move = sc.next();
            String dir = move.substring(3);
            String pawn = move.substring(0, 2);
            System.out.println(); 

            //Checking Whether Pawn is Alive
            if(!check_pawn_alive(turn, pawn, map1, map2)){
                System.out.println("This pawn is dead/ doesn't exits, Please play another pawn... ");
                System.out.println(); 
                continue;
            }

            if(!check_valid_move(dir)){
                System.out.println("Invalid Move, Please play again!!");
                continue;
            }

            if(turn == 1){
                pawn = "A-" + pawn;
                int[] ppos = map1.get(pawn);
                int px = ppos[0];
                int py = ppos[1];
                if(check_board(board, pawn, dir, map1, turn)){
                    int[] pos = map1.get(pawn);
                    int x = pos[0];
                    int y = pos[1];
                    String cur_pos = board[x][y];

                    if(cur_pos != null) cur_pos = cur_pos.substring(0, 1);

                    //CHECK THIS AREA
                    if(cur_pos != null && cur_pos.equals("B")){
                        //If Opponent Pawn Dies
                        String opp_pawn = board[x][y];
                        map2.remove(opp_pawn);
                        System.out.println("Opponent Pawn " + opp_pawn + " Killed.... ");
                        board[x][y] = pawn;
                        board[px][py] = null;
                        n2--;
                    }
                    else{
                        board[x][y] = pawn;
                        board[px][py] = null;
                    }
                }
                else{
                    System.out.println("Invalid Move, Player A, please play again!!");
                    continue;
                }

                turn = 2;
                print_board(board);
                System.out.println();
            }
            else if(turn == 2){
                pawn = "B-" + pawn;
                int[] ppos = map2.get(pawn);
                int px = ppos[0];
                int py = ppos[1];

                if(check_board(board, pawn, dir, map2, turn)){
                    int[] pos = map2.get(pawn);
                    int x = pos[0];
                    int y = pos[1];
                    String cur_pos = board[x][y];
                    
                    if(cur_pos != null) cur_pos = cur_pos.substring(0, 1);
                    System.out.println(cur_pos);
                    
                    if(cur_pos != null && cur_pos.equals("A")){
                        //If Opponent Pawn Dies
                        String opp_pawn = board[x][y];
                        map1.remove(opp_pawn);
                        System.out.println("Opponent Pawn " + opp_pawn + " Killed.... ");
                        board[x][y] = pawn;
                        board[px][py] = null;
                        n1--;
                    }
                    else{
                        board[x][y] = pawn;
                        board[px][py] = null;
                    }
                }
                else{
                    System.out.println("Invalid Move, Player B, please play again!!");
                    continue;
                }

                turn = 1;
                print_board(board);
                System.out.println();
            }
        }

        //Results
        if(n1 == 0) System.out.println("*****PLAYER B WINS*****");
        else System.out.println("*****PLAYER A WINS*****");
        System.out.println();

    }

    //Checking if the Pawn is Alive
    static boolean check_pawn_alive(int turn, String pawn, HashMap<String, int[]> map1, HashMap<String, int[]> map2){
        if(turn == 1){
            if(map1.containsKey("A-" + pawn))
                return true;
        }
        else{
            if(map2.containsKey("B-" + pawn))
                return true;
        }
        return false;
    }
    
    static void print_board(String[][] board){
        //Printing the Status of the Board

        System.out.println("");
        System.out.println("BOARD: ");
        System.out.println("----------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(board[i][j] + "   ");
            }
            System.out.println("");
            System.out.println("");
        }
        System.out.println("");
        System.out.println("----------------------------------------------------------------");
        System.out.println("");
    }

    static boolean check_board(String[][] board, String pawn, String dir, LinkedHashMap<String, int[]> map, int turn){
        int[] pos = map.get(pawn);
        int x = pos[0];
        int y = pos[1];

        //Changing Direction of Front and Back for Player B
        if(turn == 2){
            if(dir.equals("F"))
                dir = "B";
            else if(dir.equals("B"))
                dir = "F";
        }

        // String temp = board[x][y];
        if(dir.equals("B")){
            if(x == 0) return false;
            x--;
        }
        else if(dir.equals("F")){
            if(x == 4) return false;
            x++;
        }
        else if(dir.equals("R")){
            if(y == 4) return false; 
            y++;
        }
        else{
            if(y == 0) return false;
            y--;
        }

        String temp = board[x][y];
        if(temp != null && temp.substring(0, 1).equals(pawn.substring(0, 1))){
            return false;
        }

        pos[0] = x;
        pos[1] = y;
        map.replace(pawn, pos);

        return true;
    }

    static boolean check_valid_move(String dir){
        if(dir.equals("L") || dir.equals("R") || dir.equals("F") || dir.equals("B")) {
            return true;
        }
        return false;
    }

    static String[][] fill_board(String[][] board, LinkedHashMap<String, int[]> map1, LinkedHashMap<String, int[]> map2){
        int i = 0;
        //Entring Player A Pawns
        for(Map.Entry<String, int[]> entry: map1.entrySet()){
            board[0][i] = entry.getKey();
            i++;
        }

        i = 0;
        //Entering Player B Pawns
        for(Map.Entry<String, int[]> entry: map2.entrySet()){
            board[4][i] = entry.getKey();
            i++;
        }
        print_board(board);
        System.out.println();
        return board;
    }
}
