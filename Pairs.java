
class Pairs {
	public int x,y;
	public Pairs(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean equal(Pairs p){
		if(p.x == x && p.y == y)
			return true;
		return false;
	}
}
