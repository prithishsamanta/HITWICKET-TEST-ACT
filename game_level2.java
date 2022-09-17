import java.util.*;
import java.io.*;
import java.lang.*;

public class game_level2 {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String[][] board = new String[5][5];


        System.out.println("   -------------------------  INSTRUCTIONS  ------------------------- \n\n");
        System.out.println("   This game is similar to a chess game, where there are 2 players.");
        System.out.println("   Each player gets 5 pawns out of which 3 of them are Normal Soldiers (i.e. P1, P2, P3),");
        System.out.println("   and these can move only 1 space at a time, either Front(F), Back(B), Left(L) or Right(R).");
        System.out.println("   The remaining 2 pawns are the heroes (i.e. H1 and H2), and these can move 2 spaces at a time");
        System.out.println("   (i.e if the are moving front, back, right or left), or they can move 1 space diagonally,");
        System.out.println("   (i.e. Front-Left(FL), Front-Right(FR), Back-Left(BL) or Back-Right(BR)). The heroes can Kill any Opponent that come in its way.");
        System.out.println("   To enter move in this format: <PAWN_NAME>:<DIRECTION> (eg:- P1:F, P2:R, H1:FR, H2, BL)");
        System.out.println("\n\n   ------------------------------------------------------------------ \n\n");

        
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

            boolean spcl = false;

            if(pawn.indexOf("H") != -1){
                spcl = true;
            }

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
                if(check_board(board, pawn, dir, map1, turn, spcl)){
                    int[] pos = map1.get(pawn);
                    int x = pos[0];
                    int y = pos[1];
                    String cur_pos = board[x][y];
                    String death2 = " ";
                    String opp_pawn = board[x][y];

                    if(cur_pos != null) cur_pos = cur_pos.substring(0, 1);

                    //If Opponent Pawn dies in the middle by hero
                    if(spcl == true){
                        if(dir.equals("F")){
                            death2 = board[px + 1][py];
                            System.out.println(death2);
                            
                            if(death2 != null){
                                if(death2.charAt(0) == 'B'){
                                    System.out.println("Opponent Pawn " + death2 + " Killed.... ");
                                    map2.remove(death2);
                                    board[px + 1][py] = null;
                                    n2--;
                                }
                            }

                        }
                        else if(dir.equals("L")){
                            death2 = board[px][py - 1];
                            System.out.println(death2);
                            
                            if(death2 != null){
                                if(death2.charAt(0) == 'B'){
                                    System.out.println("Opponent Pawn " + death2 + " Killed.... ");
                                    map2.remove(death2);
                                    board[px][py - 1] = null;
                                    n2--;
                                }
                            }

                        }
                        else if(dir.equals("B")){
                            death2 = board[px - 1][py];
                            System.out.println(death2);
                            
                            if(death2 != null){
                                if(death2.charAt(0) == 'B'){
                                    System.out.println("Opponent Pawn " + death2 + " Killed.... ");
                                    map2.remove(death2);
                                    board[px - 1][py] = null;
                                    n2--;
                                }
                            }

                        }
                        else if(dir.equals("R")){
                            death2 = board[px][py + 1];
                            System.out.println(death2);
                            
                            if(death2 != null){
                                if(death2.charAt(0) == 'B'){
                                    System.out.println("Opponent Pawn " + death2 + " Killed.... ");
                                    map2.remove(death2);
                                    board[px][py + 1] = null;
                                    n2--;
                                }
                            }

                        }
                    }

                    //If Pawn Kills Opponent Pawn
                    if(cur_pos != null && cur_pos.equals("B")){
                        //If Opponent Pawn Dies
                        if(!death2.equals(opp_pawn)){
                            map2.remove(opp_pawn);

                            System.out.println("Opponent Pawn " + opp_pawn + " Killed.... ");
                            n2--;
                        }
                        board[x][y] = pawn;
                        board[px][py] = null;
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

                if(check_board(board, pawn, dir, map2, turn, spcl)){
                    int[] pos = map2.get(pawn);
                    int x = pos[0];
                    int y = pos[1];
                    String cur_pos = board[x][y];
                    String death2 = "";
                    String opp_pawn = board[x][y];
                    
                    if(cur_pos != null) cur_pos = cur_pos.substring(0, 1);
                    System.out.println(cur_pos);

                    //If Opponent Pawn dies in the middle by hero
                    if(spcl == true){
                        if(dir.equals("F")){
                            death2 = board[px - 1][py];
                            // System.out.println(death2);

                            if(death2 != null){
                                if(death2.charAt(0) == 'A'){
                                    System.out.println("Opponent Pawn " + death2 + " Killed.... ");
                                    map1.remove(death2);
                                    board[px - 1][py] = null;
                                    n1--;
                                }
                            }

                        }
                        else if(dir.equals("L")){
                            death2 = board[px][py - 1];
                            // System.out.println(death2);
                            
                            if(death2 != null){
                                if(death2.charAt(0) == 'A'){
                                    System.out.println("Opponent Pawn " + death2 + " Killed.... ");
                                    map1.remove(death2);
                                    board[px][py - 1] = null;
                                    n1--;
                                }
                            }

                        }
                        else if(dir.equals("B")){
                            death2 = board[px + 1][py];
                            // System.out.println(death2);
                            
                            if(death2 != null){
                                if(death2.charAt(0) == 'A'){
                                    System.out.println("Opponent Pawn " + death2 + " Killed.... ");
                                    map1.remove(death2);
                                    board[px + 1][py] = null;
                                    n1--;
                                }
                            }

                        }
                        else if(dir.equals("R")){
                            death2 = board[px][py + 1];
                            // System.out.println(death2);
                            
                            if(death2 != null){
                                if(death2.charAt(0) == 'A'){
                                    System.out.println("Opponent Pawn " + death2 + " Killed.... ");
                                    map1.remove(death2);
                                    board[px][py + 1] = null;
                                    n1--;
                                }
                            }

                        }
                    }

                    //If Pawn Kills Opponent Pawn
                    if(cur_pos != null && cur_pos.equals("A")){
                        //If Opponent Pawn Dies

                        if(!death2.equals(opp_pawn)){
                            map1.remove(opp_pawn);

                            System.out.println("Opponent Pawn " + opp_pawn + " Killed.... ");
                            n1--;
                        }
                        board[x][y] = pawn;
                        board[px][py] = null;
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

    static boolean check_pawn_alive(int turn, String pawn, HashMap<String, int[]> map1, HashMap<String, int[]> map2){
        //Checking if the Pawn is Alive
        
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

    static boolean check_board(String[][] board, String pawn, String dir, LinkedHashMap<String, int[]> map, int turn, boolean spcl){
        //Checking Whether the Move can be Played or Not

        int[] pos = map.get(pawn);
        int x = pos[0];
        int y = pos[1];

        //Changing Direction of Front and Back for Player B
        if(turn == 2){
            if(dir.equals("F"))
                dir = "B";
            else if(dir.equals("B"))
                dir = "F";
            else if(dir.equals("FL"))
                dir = "BL";
            else if(dir.equals("FR"))
                dir = "BR";
            else if(dir.equals("BR"))
                dir = "FR";
            else if(dir.equals("BL"))
                dir = "FL";
        }

        // String temp = board[x][y];
        if(spcl == false){ //For Normal Pawn 
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
        }
        else{
            if(dir.equals("B")){
                if(x == 0) return false;
                else if(x == 1) x--;
                else x -= 2;
            }
            else if(dir.equals("F")){
                if(x == 4) return false;
                else if(x == 3) x++;
                else x += 2;
            }
            else if(dir.equals("R")){
                if(y == 4) return false; 
                else if(y == 3) y++;
                else y += 2;
            }
            else if(dir.equals("L")){
                if(y == 0) return false;
                else if(y == 1) y--;
                else y -= 2;
            }
            else if(dir.equals("FR")){
                if(y == 4 || x == 4) return false;
                y++;
                x++;
            }
            else if(dir.equals("FL")){
                if(y == 0 || x == 4) return false;
                y--;
                x++;
            }
            else if(dir.equals("BR")){
                if(y == 4 || x == 0) return false;
                y++;
                x--;
            }
            else if(dir.equals("BL")){
                if(y == 0 || x == 0) return false;
                y--;
                x--;
            }
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
        if(dir.equals("L") || dir.equals("R") || dir.equals("F") || dir.equals("B") || dir.equals("FL") || dir.equals("FR") || dir.equals("BL") || dir.equals("BR")) {
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
