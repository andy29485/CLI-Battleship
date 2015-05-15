class Ship {
  private byte anPos;      //First 4 bits are X-pos; Last 4 are Y-pos(0-9)
  private byte abPartsHit; //Each bit(starting from left) prepresents part
                           //  the ship that [has|has not] been hit
  private byte anType;     //Sixth bit will be orientation(0:horiz|1:vert)
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
  
  public Ship(int nPosX, int nPosY, int nOrientation, int nSize) {//orientation: odd: vert, even: horiz
    anPos      = (byte)(nPosX << 4         | nPosY  );
    anType     = (byte)(4*(nOrientation&1) | nSize&3);
    abPartsHit = 0;
  }
  
  public Ship(int nPosX, int nPosY, int nOrientation, int nSize, boolean isSub) {
    anPos      = (byte)(nPosX << 4         | nPosY);
    anType     = (byte)(4*(nOrientation&1) | nSize&3 | (isSub ? 8 : 0));
    abPartsHit = 0;
  }
  
  public Ship(int nPosX, int nPosY, boolean bVertical, int nSize, boolean isSub) {
    anPos      = (byte)(nPosX << 4          | nPosY  );
    anType     = (byte)((bVertical ? 0 : 4) | nSize&3 | (isSub ? 8 : 0));
    abPartsHit = 0;
  }
  
  public int getX() {
    return (anPos >> 4) & 15;
  }
  
  public int getY() {
    return anPos & 15;
  }
  
  public void setX(int nPosX) {
    anPos = (byte)(nPosX  << 4 | getY());
  }
  
  public void setY(int nPosY) {
    anPos = (byte)(getX() << 4 | nPosY);
  }
  
  public void setHoriz(boolean horiz) {
    if(horiz)
      anType &= ~(byte)4;
    else
      anType |= 4;
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
  
  public boolean isHorizontal() {
    return (anType&4) > 0;
  }
  
  public boolean hit(int nPosX, int nPosY) {
    if(isHorizontal()) {
      if(nPosY != getY() || nPosX < getX() || nPosX >= getX() + getSize())
        return false;
      abPartsHit |= (int)Math.pow(2, (nPosX-getX()+1));
    }
    else {
      if(nPosX != getX() || nPosY < getY() || nPosY >= getY() + getSize())
        return false;
      abPartsHit |= (int)Math.pow(2, (nPosY-getY()+1));
    }
    return true;
  }
  
  /*Return values
   * 0: Not on tile
   * 1: On tile, not hit
   * 2: Hit
   */
  public int statusOnTile(int nPosX, int nPosY) {
    if(isHorizontal()) {
      if(nPosY != getY() || nPosX < getX() || nPosX >= (getX() + getSize()))
        return 0;
      return sectionHit(nPosX-getX()+1) ? 2 : 1;
    }
    else {
      if(nPosX != getX() || nPosY < getY() || nPosY >= (getY() + getSize()))
        return 0;
      return sectionHit(nPosY-getY()+1) ? 2 : 1;
    }
  }
  
  public String getName() {
    switch (anType&3) {
      case 0:
        return "Destroyer";
      
      case 1:
        return (anType&8) > 0 ? "Submarine" : "Cruiser";
      
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
