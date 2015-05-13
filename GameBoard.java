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
  
  public GameBoard() {
    asGrid = new short[10];
  }
  
  public String toString() {
    System.out.println("+-+-+-+-+-+-+-+-+-+-+");
    for(int i=0; i<10; i++) {
      System.out.print("|");
      for(int j=0; j<10; j++) {
        
      }
      System.out.println("+-+-+-+-+-+-+-+-+-+-+");
    }
  }
}