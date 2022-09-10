import java.util.*;
import java.io.*;
import java.lang.*;

public class chess_game {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String[][] board = new String[5][5];
        
        //Taking Input
        HashMap<String, int[]> map1 = new HashMap<>();
        // String[] p1 = new String[5];
        for(int i = 0; i < 5; i++){
            int[] pos = new int[2];
            pos[0] = 0; pos[1] = i;

            String temp = sc.next();
            // p1[i] = "A-" + temp;
            map1.put(temp, pos);
        }

        HashMap<String, int[]> map2 = new HashMap<>();
        // String[] p2 = new String[5];
        for(int i = 0; i < 5; i++){
            int[] pos = new int[2];
            pos[0] = 4; pos[1] = i;

            String temp = sc.next();
            // p2[i] = "B-" + temp;
            map2.put(temp, pos);
        }

        //Creating Board
        board = fill_board(board, map1, map2);
        int n1 = 5, n2 = 5;
        int turn = 1;

        //Game Play
        while(n1 != 0 && n2 != 0){
            String move = sc.next();
            String dir = move.substring(3);
            String pawn = move.substring(0, 2);

            if(!check_valid_move(dir)){
                System.out.println("Invalid Move, Please play again!!");
                continue;
            }

            if(turn == 1){
                pawn = "A-" + pawn;
                if(check_board(board, pawn, dir, map1)){

                }
                else{
                    System.out.println("Invalid Move, Player A, please play again!!");
                    continue;
                }

                turn = 2;
            }
            else if(turn == 2){
                pawn = "B-" + pawn;
                if(check_board(board, pawn, dir, map2)){

                }
                else{
                    System.out.println("Invalid Move, Player B, please play again!!");
                    continue;
                }

                turn = 1;
            }
        }

        //Results
        if(n1 == 0) System.out.println("PLAYER B WINS");
        else System.out.println("PLAYER A WINS");

    }
    
    static void print_board(String[][] board){
        //Printing the Status of the Board
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println("");
        }
    }

    static boolean check_board(String[][] board, String pawn, String dir, HashMap<String, int[]> map){
        boolean flag = true;
        int[] pos = map.get(pawn);
        int x = pos[0];
        int y = pos[1];

        if(dir.equals("L")){

        }
        else if(dir.equals("R")){

        }
        else if(dir.equals("F")){

        }
        else{

        }

        return flag;
    }

    static boolean check_valid_move(String dir){
        if(dir.equals("L") || dir.equals("R") || dir.equals("F") || dir.equals("B")) 
            return true;
        return false;
    }

    static String[][] fill_board(String[][] board, HashMap<String, int[]> map1, HashMap<String, int[]> map2){
        int i = 0;
        //Entring Player A Pawns
        for(Map.Entry<String, int[]> entry: map1.entrySet()){
            board[0][i] = entry.getKey();
            i++;
        }
        print_board(board);

        i = 0;
        //Entering Player B Pawns
        for(Map.Entry<String, int[]> entry: map2.entrySet()){
            board[4][i] = entry.getKey();
            i++;
        }
        print_board(board);
        return board;
    }
}
