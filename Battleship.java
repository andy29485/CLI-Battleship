import java.util.Scanner;

public class Battleship {
  public static Scanner keyboard;
  public static boolean ansi;
  public static boolean ai;
  
  public static void main(String[] args) {
    keyboard = new Scanner(System.in);
    
    GameBoard player1;
    GameBoard player2;
    
    System.out.print("Do ANSI Colours work(Are you using *NIX)[y/n]: ");
    ansi = keyboard.next().matches("(?i)y.*"); keyboard.nextLine();
    
    clear();
    System.out.println("Player 1");
    player1 = new GameBoard();
    
    clear();
    System.out.print("Is player 2 to be an AI[y/n]: ");
    ai = keyboard.next().matches("(?i)y.*"); keyboard.nextLine();
    if(!ai) {
      clear();
      System.out.println("Player 2");
      player2 = new GameBoard();
    }
    else
      player2 = new GameBoard(true);
    
    boolean bOnPlayer1 = true;
    while(!player1.allShipsSunk() && !player2.allShipsSunk()) {
      clear();
      
      if(bOnPlayer1) {
        System.out.println("Press return to continue to Player 1");
        keyboard.nextLine();
        clear();
        System.out.println("                       "+
                           "                Player 1");
        String[]  enemy_board = player2.print(false).split("\n");
        String[] player_board = player1.print(true ).split("\n");
        
        String[]  enemy_status = player2.status().split("\n");
        String[] player_status = player1.status().split("\n");
        
        System.out.println("                    "+
          "|-----player--2-----|   |-----player--1----|");
        System.out.println("                    "+
          " 1 2 3 4 5 6 7 8 9 10    1 2 3 4 5 6 7 8 9 10");
        for(int i=0; i<19; i++) {
          System.out.printf("              %s(%02d) %s   %s\n",
           (i%2==1 ? (char)(65+i/2) : ' '),
           i+1,
            enemy_board[i],
           player_board[i]);
        }
        
        if(ansi) {
          for(int i=0; i<5; i++) {
            System.out.printf("%-27s%-23s | %24s\n",
             player1.getShips()[i].toString(),
              enemy_status[i],
             player_status[i]);
          }
        }
        else {
          for(int i=0; i<5; i++) {
            System.out.printf("%-27s%-14s | %15s\n",
             player1.getShips()[i].toString(),
              enemy_status[i],
             player_status[i]);
          }
        }
        String strX;
        String strY;
        int x; 
        int y;
        do {
          System.out.printf("Location to attack\n  X(1-10): ");
          strX = keyboard.nextLine();
          
          System.out.print("  Y(1-10): ");
          strY = keyboard.nextLine();
          
          if(strX.matches("\\d+") && strX.matches("\\d+")) {
            x = Integer.valueOf(strX);
            y = Integer.valueOf(strY);
          }
          else {
            x = 0;
            y = 0;
          }
        } while (x > 10 || y > 10 || x <= 0 || y <= 0);
        System.out.println(player2.getShot(x-1, y-1));
      }
      else {
        if(!ai) {
          System.out.println("Press return to continue to Player 2");
          keyboard.nextLine();
          clear();
        System.out.println("                       "+
                           "                Player 2");
          String[]  enemy_board = player1.print(false).split("\n");
          String[] player_board = player2.print(true ).split("\n");
          
          String[]  enemy_status = player1.status().split("\n");
          String[] player_status = player2.status().split("\n");
          
          System.out.println("                    "+
          "|-----player--2-----|   |-----player--1----|");
        System.out.println("                    "+
          " 1 2 3 4 5 6 7 8 9 10    1 2 3 4 5 6 7 8 9 10");
        for(int i=0; i<19; i++) {
          System.out.printf("              %s(%02d) %s   %s\n",
           (i%2==1 ? (char)(65+i/2) : ' '),
           i+1,
            enemy_board[i],
           player_board[i]);
        }
        
        if(ansi) {
          for(int i=0; i<5; i++) {
            System.out.printf("%-27s%-23s | %24s\n",
             player1.getShips()[i].toString(),
              enemy_status[i],
             player_status[i]);
          }
        }
        else {
          for(int i=0; i<5; i++) {
            System.out.printf("%-27s%-14s | %15s\n",
             player1.getShips()[i].toString(),
              enemy_status[i],
             player_status[i]);
          }
        }
          
          String strX;
          String strY;
          int x; 
          int y;
          do {
            System.out.printf("Location to attack\n  X(1-10): ");
            strX = keyboard.nextLine();
            
            System.out.print("  Y(1-10): ");
            strY = keyboard.nextLine();
            
            if(strX.matches("\\d+") && strX.matches("\\d+")) {
              x = Integer.valueOf(strX);
              y = Integer.valueOf(strY);
            }
            else {
              x = 0;
              y = 0;
            }
          } while (x > 10 || y > 10 || x <= 0 || y <= 0);
          System.out.println(player1.getShot(x-1, y-1));
        }
        else {
          player1.getShot(
            (int)(Math.random()*10),
            (int)(Math.random()*10));
        }
      }
      bOnPlayer1 = !bOnPlayer1;
      if(bOnPlayer1 && !ai) {
        System.out.println("Press return to continue");
        keyboard.nextLine();
      }
    }
    keyboard.close();
  }
  
  public static void clear() { //Should do the trick, y'know?
    System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
  }
}
