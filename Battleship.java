import java.util.Scanner;

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
      bOnPlayer1 = !bOnPlayer1;
      System.out.println("Press return to continue");
      keyboard.nextLine();
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
