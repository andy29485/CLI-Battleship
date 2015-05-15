import java.util.Scanner;

class GameBoard {
  public static final String ANSI_RESET   = "\u001B[0m";
  public static final String ANSI_BLACK   = "\u001B[30m";
  public static final String ANSI_RED     = "\u001B[31m";
  public static final String ANSI_GREEN   = "\u001B[32m";
  public static final String ANSI_YELLOW  = "\u001B[33m";
  public static final String ANSI_BLUE    = "\u001B[34m";
  public static final String ANSI_PURPLE  = "\u001B[35m";
  public static final String ANSI_CYAN    = "\u001B[36m";
  public static final String ANSI_WHITE   = "\u001B[37m";
  
  private short[] asGrid;
  private Ship[]  aShips;
  
  public GameBoard() {
    asGrid = new short[10];
    aShips = new Ship[5];
    
    Scanner keyboard = Battleship.keyboard;
    
    for(int i=0; i<5; i++) {
      if(i==4)
        aShips[i] = new Ship(0, 0, true, 1, true);
      else
        aShips[i] = new Ship(0, 0, true, i);
      
      setter:
      do {
        System.out.printf("Position of new %s:\n  X(1-10): ", aShips[i]);
        String strX = keyboard.nextLine();
        
        System.out.print("  Y(1-10): ");
        String strY = keyboard.nextLine();
        
        System.out.print("  Set vertical(y/n): ");
        aShips[i].setVert(keyboard.nextLine().matches("(?i)y.*"));
        
        if(strX.matches("\\d+") && strY.matches("\\d+")) {
          int nX = Integer.valueOf(strX)-1;
          int nY = Integer.valueOf(strY)-1;
          
          if(nY < 0 || nX < 0|| (aShips[i].isHorizontal()
            ? (nY > 9 || nX + aShips[i].getSize() > 10)
            : (nX > 9 || nY + aShips[i].getSize() > 10) )) {
              System.out.printf("Your %s has fallen off the Earth, The Divine Cosmic Turtle has given you another chance!\n", aShips[i]);
              continue setter;
          }

          aShips[i].setX(nX);
          aShips[i].setY(nY);
        }
        else {
          System.out.printf("(%s, %s) is not a valid position\n", strX, strY);
          continue setter;
        }
        
        for(int j=0; j<i; j++) {
          if(aShips[i].intersects(aShips[j])) {
            System.out.printf("Your %s is on top of %s \\o/\nTry again\n", aShips[i], aShips[j]);
            continue setter;
          }
        }
        break;
      } while(true);
      
      //Debug stuff
      //System.out.printf("\n%s:\n  Pos:  (%d, %d)\n  Size: %d\n\n",
      //  aShips[i],
      //  aShips[i].getX()+1,
      //  aShips[i].getY()+1,
      //  aShips[i].getSize());
    }
  }
  
  public boolean allShipsSunk() {
    for(Ship ship : aShips)
      if(ship.isFloating()) return false;
    return true;
  }
  
  public String status() {
    String strOut = "";
    
    for(Ship ship : aShips) {
	  if(Battleship.ansi)
      strOut += String.format("%-10s - %s%s\n", ship,
        ship.isFloating() ? ANSI_GREEN + "FLOATING" : ANSI_RED + "SUNK",
        ANSI_RESET);
    else
       strOut += String.format("%-10s - %s\n", ship,
        ship.isFloating() ? "FLOATING" : "SUNK");
    }
    
    return strOut;
  }
  
  public boolean getShot(int nPosX, int nPosY) {
    if(nPosX > 10 || nPosY > 10)
      return false;
    
    boolean wasHit = false;
    
    asGrid[nPosY] |= (short)Math.pow(2, nPosX);
    for(Ship ship : aShips) {
      wasHit = wasHit || ship.hit(nPosX, nPosY);
    }
    
    return wasHit;
  }
  
  //set isPlayer to false to dissable printing of ships(unless hit)
  public String print(boolean isPlayer) {
    String strOut = "+-+-+-+-+-+-+-+-+-+-+\n";
    
    for(int i=0; i<10; i++) {
      strOut += "|";
      
      for(int j=0; j<10; j++) {
        byte   nTile  = 0;
        char   cTile  = '?';
        String colour = "";
        
        for(Ship ship : aShips) {
          nTile |= ship.statusOnTile(j, i);
        }
        switch(nTile) {
          case 0:
            if((asGrid[j] & (short)Math.pow(2, i)) > 0) {
              colour = ANSI_YELLOW;
              cTile  = '^';
            }
            else {
              colour = ANSI_BLUE;
              cTile  = '#';
            }
            break;
          case 1:
            if(isPlayer) {
              colour = ANSI_GREEN;
              cTile  = '@';
            }
            else {
              colour = ANSI_BLUE;
              cTile  = '#';
            }
            break;
          default:
            colour = ANSI_RED;
            cTile  = '*';
            break;
        }
		if(Battleship.ansi)
			strOut += String.format("%s%s%s|", colour, cTile, ANSI_RESET);
		else
			strOut += String.format("%s|", cTile);
      }
      strOut += "\n+-+-+-+-+-+-+-+-+-+-+\n";
    }
    return strOut;
  }
}
