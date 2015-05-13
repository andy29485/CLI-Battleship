class Ship {
  private byte anPos;      //First 4 bits are X-pos; Last 4 are Y-pos(0-9)
  private byte abPartsHit; //Each bit(starting from left) prepresents part
                           //  the ship that [has|has not] been hit
  private byte anType;     //Sixth bit will be orientation(0:vert|1:horiz)
                           //  Last 2 are size(2-5 -> 0-3)
                           //  If it is a sub then the 5th bit will be set
                           //  First four  bits are used to determine ship
                           //    colour.
  
  public Ship() {
    anPos      = 0;
    abPartsHit = 0;
    anType     = 2;
  }
  
  public Ship(int nPosX, int nPosY, boolean bVertical, int nSize) {
    anPos      = (byte)(nPosX << 4          | nPosY  );
    anType     = (byte)((bVertical ? 0 : 4) | nSize&3);
    abPartsHit = 0;
  }
  
  public Ship(int nPosX, int nPosY, int nOrientation, int nSize) {
    anPos      = (byte)(nPosX << 4     | nPosY  );
    anType     = (byte)(4*nOrientation | nSize&3);
    abPartsHit = 0;
  }
  
  public Ship(int nPosX, int nPosY, int nOrientation, int nSize, boolean isSub) {
    anPos      = (byte)(nPosX << 4     | nPosY);
    anType     = (byte)(4*nOrientation | nSize&3 | isSub*8);
    abPartsHit = 0;
  }
  
  public int getX() {
    return (anPos >> 4) & 31;
  }
  
  public int getY() {
    return anPos & 31;
  }
  
  public boolean isFloating() {
    int parts = 0;
    for(int i=0; i<5; i++) {
      if(sectionHit(i)) parts++;
    }
    return getSize() > parts;
  }
  
  public boolean sectionHit(int nSection) {//Starts at 0(postion (x,y)) to size-1
    return (abPartsHit & (int)Math.pow(2, nSection)) > 0;
  }
  
  public int getSize() {
    return (anType & 3) + 2;
  }
  
  public String getName() {
    switch (anType&3) {
      case 0:
        return "Destroyer";
      
      case 1:
        return (anType&16) > 0 ? "Submarine" : "Cruiser";
      
      case 2:
        return "Battleship";
      
      case 3:
        return "Carrier";
      
      default:
        return "Unkown";
    }
  }
  
  public String toString() {
    return getName();
  }
}
