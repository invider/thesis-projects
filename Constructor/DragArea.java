
class DragArea {
	Math processor;
	int	R;
	int x, y;

	DragArea(int px, int py, int pR){
		x = px;
		y = py;
		R = pR;
	}

	void moveArea(int x, int y) {
		this.x = x;
		this.y = y;
	}

	boolean isInArea(int px, int py) {
		int pR;
		px = px - x;
		py = py - y;
		px *= px;
		py *= py;
		pR = (int)processor.sqrt(px + py);		
		if (pR > R) return false;
		return true;
	}
}