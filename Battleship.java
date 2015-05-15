import java.util.Scanner;
import java.awt.Color;

public class Battleship {
  public static Scanner keyboard;
  public static boolean ansi;
  
  public static void main(String[] args) {
    keyboard = new Scanner(System.in);
    
    System.out.print("Do ANSI Colours work(Are you using *NIX)[y/n]: ");
    ansi = keyboard.next().matches("(?i)y.*"); keyboard.nextLine();
    
    clear();
    System.out.println("Player 1");
    GameBoard player1 = new GameBoard();
    
    clear();
    System.out.println("Player 2");
    GameBoard player2 = new GameBoard();
    
    boolean bOnPlayer1 = true;
    
    while(!player1.allShipsSunk() && !player2.allShipsSunk()) {
      clear();
      
      if(bOnPlayer1) {
        System.out.println("Player 1");
        System.out.println(player2.print(false));
        System.out.println(player1.print(true));
        System.out.println(player1.status());
        
        int x; 
        int y;
        do {
          System.out.printf("Location to attack\n  X(1-10): ");
          x = keyboard.nextInt(); keyboard.nextLine();
          
          System.out.print("  Y(1-10): ");
          y = keyboard.nextInt(); keyboard.nextLine();
        } while (x > 10 || y > 10 || x <= 0 || y <= 0);
        System.out.println(player2.getShot(y-1, x-1) ? "HIT" : "MISS");
      }
      else {
        System.out.println("Player 2");
        System.out.println(player1.print(false));
        System.out.println(player2.print(true));
        System.out.println(player2.status());
        
        int x; 
        int y;
        do {
          System.out.printf("Location to attack\n  X(1-10): ");
          x = keyboard.nextInt(); keyboard.nextLine();
          
          System.out.print("  Y(1-10): ");
          y = keyboard.nextInt(); keyboard.nextLine();
        } while (x >= 10 || y >= 10);
        System.out.println(player1.getShot(y-1, x-1) ? "HIT" : "MISS");
      }
      bOnPlayer1 = !bOnPlayer1;
      System.out.println("Press return to continue to next player");
      keyboard.nextLine();
    }
    keyboard.close();
  }
  
  public static void clear() { //Should do the trick, y'know?
    System.out.printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    System.out.printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    System.out.printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
  }
}
