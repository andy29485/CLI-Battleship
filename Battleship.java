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
        System.out.println("Player 1");
        System.out.println(player2.print(false));
        System.out.println(player1.print(true));
        System.out.println(player1.status());
        
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
          System.out.println("Player 2");
          System.out.println(player1.print(false));
          System.out.println(player2.print(true));
          System.out.println(player2.status());
          
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
