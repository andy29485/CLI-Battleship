import java.util.Scanner;

class GameBoard {
  public static final String ANSI_RESET   = "\u001B[0m";
  public static final String ANSI_RED     = "\u001B[31m";
  public static final String ANSI_GREEN   = "\u001B[32m";
  public static final String ANSI_YELLOW  = "\u001B[33m";
  public static final String ANSI_BLUE    = "\u001B[34m";
  
  private short[] asGrid;
  private Ship[]  aShips;
  
  public GameBoard() {
    asGrid = new short[10];
    aShips = new Ship[5];
    
    aShips[0] = new Ship(11, 11, true, 0);       //Destroyer
    aShips[1] = new Ship(11, 11, true, 1);       //Cruiser
    aShips[2] = new Ship(11, 11, true, 1, true); //Submarine
    aShips[3] = new Ship(11, 11, true, 2);       //Battleship
    aShips[4] = new Ship(11, 11, true, 3);       //Aircraft Carrier
    
    initShips(false);
  }
  
  public Ship[] getShips() {
    return aShips;
  }
  
  public GameBoard(boolean isAI) {
    asGrid = new short[10];
    aShips = new Ship[5];
    
    aShips[0] = new Ship(11, 11, true, 0);       //Destroyer
    aShips[1] = new Ship(11, 11, true, 1);       //Cruiser
    aShips[2] = new Ship(11, 11, true, 1, true); //Submarine
    aShips[3] = new Ship(11, 11, true, 2);       //Battleship
    aShips[4] = new Ship(11, 11, true, 3);       //Aircraft Carrier
    
    initShips(isAI);
  }
  
  public void initShips(boolean isAI) {
    if(isAI) {
      for(int i=0; i<5; i++) {
        int nX = (int)(Math.random()*10);
        int nY = (int)(Math.random()*10);
      
        if(aShips[i].isHorizontal()
         ? (nY > 9 || nX + aShips[i].getSize() > 10)
         : (nX > 9 || nY + aShips[i].getSize() > 10) ) {
          i--;
          continue;
        }

        aShips[i].setX(nX);
        aShips[i].setY(nY);
        
        for(int j=0; j<i; j++) {
          if(aShips[i].intersects(aShips[j])) {
            i--;
            break;
          }
        }
      }
    }
    else {
      Scanner keyboard = Battleship.keyboard;
      
      for(int i=0; i<5; i++) {
        System.out.printf("Position of %s:\n  X(1-10): ", aShips[i]);
        String strX = keyboard.nextLine();
          
        System.out.print("  Y(1-10): ");
        String strY = keyboard.nextLine();
          
        System.out.print("  Set vertical(y/n): ");
        aShips[i].setVert(keyboard.nextLine().matches("(?i)y.*"));
        
        if(strX.matches("\\d+") && strY.matches("\\d+")) {
          int nX = Integer.valueOf(strX)-1;
          int nY = Integer.valueOf(strY)-1;
        
          if(nY < 0 || nX < 0 || (aShips[i].isHorizontal()
            ? (nY > 9 || nX + aShips[i].getSize() > 10)
            : (nX > 9 || nY + aShips[i].getSize() > 10) )) {
              System.out.printf("Your %s has fallen off the Earth!\n" +
                 "The Divine Cosmic Turtle has" +
                 "given you another chance!\n",
               aShips[i]);
            i--;
          }

          aShips[i].setX(nX);
          aShips[i].setY(nY); 
        }
        else {
          System.out.printf("(%s, %s) is not a valid position\n",
            strX,
            strY);
          i--;
        }
        
        for(int j=0; j<i; j++) {
          if(aShips[i].intersects(aShips[j])) {
            aShips[i].setX(11);
            aShips[i].setY(11);
            System.out.printf("Your %s is on top of %s \\o/\n"+
              "Try again\n",
              aShips[i],
              aShips[j]);
            i--;
            break;
          }
        }
        
        String[]board = print(true).split("\n");
        System.out.println("         1 2 3 4 5 6 7 8 9 10");
        for(int j=0; j<21; j++) {
          System.out.printf("  %s%s %s\n",
           (j%2==1 ? (char)(65+j/2) : ' '),
           (j%2==1 ? String.format("(%02d)", j/2+1) : "    "),
           board[j]);
        }
        
        //Debug stuff
        //System.out.printf("\n%s:\n  Pos:  (%d, %d)\n  Size: %d\n\n",
        //  aShips[i],
        //  aShips[i].getX()+1,
        //  aShips[i].getY()+1,
        //  aShips[i].getSize());
      }
    }
  }
  
  public boolean allShipsSunk() {
    for(Ship ship : aShips)
      if(ship.floating() > 0) return false;
    return true;
  }
  
  public String status() {
    String strOut    = "";
    String strColour = "";
    String strStatus = "";
    
    for(Ship ship : aShips) {
      switch(ship.floating()) {
        case 0:
          strStatus = "SUNK";
          strColour = ANSI_RED;
          break;
        case 1:
          strStatus = "HIT";
          strColour = ANSI_YELLOW;
          break;
        case 2:
          strStatus = "FLOATING";
          strColour = ANSI_GREEN;
          break;
      }
      if(Battleship.ansi)
        strOut += String.format("%s%s%s\n",
          strColour,
          strStatus,
          ANSI_RESET);
      else
         strOut += String.format("%s\n", strStatus);
    }
    
    return strOut;
  }
  
  public boolean wasShot(int nPosX, int nPosY) {
    if(nPosX >= 10 || nPosY > 10 || nPosX < 0 || nPosY < 0)
      return false;
    return (asGrid[nPosY] & (short)Math.pow(2, nPosX)) > 0;
  }
  
  public String hit(int nPosX, int nPosY) {
    if(nPosX > 10 || nPosY > 10)
      return "Artillery batteries, misfire";//this should never happen
    
    byte nHit = 0;
    byte i;
    asGrid[nPosY] |= (short)Math.pow(2, nPosX);
    
    for(i=0; i<aShips.length; i++) {
      nHit = aShips[i].hit(nPosX, nPosY);
      if(nHit != 0)
        break;
    }
    if(nHit == 0)
      return "MISS";
    return (nHit == 1 ? "HIT " : "SUNK ") + aShips[i];
  }
  
  //set isPlayer to false to dissable printing of ships(unless hit)
  public String print(boolean isPlayer) {
    String strOut = "+-+-+-+-+-+-+-+-+-+-+\n";
    
    for(int i=0; i<10; i++) {//i - row - y pos
      strOut += "|";
      
      for(int j=0; j<10; j++) {//j - col - x pos
        byte   nTile  = 0;
        char   cTile  = '?';
        String colour = "";
        
        for(Ship ship : aShips) {
          nTile = ship.statusOnTile(j, i);
          if(nTile != 0) {
            cTile  = ship.getName().charAt(0);
            break;
          }
        }
        switch(nTile) {
          case 0:
            if((asGrid[i] & (short)Math.pow(2, j)) > 0) {
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
			strOut += String.format("%s%s%s|", colour,
			                        cTile, ANSI_RESET);
		else
			strOut += String.format("%s|", cTile);
      }
      strOut += "\n+-+-+-+-+-+-+-+-+-+-+\n";
    }
    return strOut;
  }
}
