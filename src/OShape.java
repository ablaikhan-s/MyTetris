import java.awt.Color;

public class OShape extends Shapes {
	int a1,a2,a3,a4;
	int[] location;
	int maxLength = 2;	
	int gameFieldWeight = super.getGameFieldWeight();
	Color color;
	
	public OShape(int w, int h) {
		super(w,h);
		color = getRandomColor();
		a1= getRandomNumber((gameFieldWeight-maxLength)+1);
		a2=a1+1;
		a3=a1+gameFieldWeight;
		a4=a3+1;
		location = new int[]{a1, a2, a3, a4};
	}
	
	public int[] getLocation() {
		return location;
	}
		
	public void fall() {
		a1=a1+gameFieldWeight;
		a2=a2+gameFieldWeight;
		a3=a3+gameFieldWeight;
		a4=a4+gameFieldWeight;
		location = new int[]{a1, a2, a3, a4};	
	}
	
	public int[] getNewFallLocation() {
		return super.getNewFallLocation(a1, a2, a3, a4);
	}
	
	public void moveLeft() {
		a1--;
		a2--;
		a3--;
		a4--;
		location = new int[]{a1, a2, a3, a4};
}

public void moveRight() {
		a1++;
		a2++;
		a3++;
		a4++;
		location = new int[]{a1, a2, a3, a4};
}
public int[] getNewLeft() {
	int newa1=a1-1;
	int newa2=a2-1;
	int newa3=a3-1;
	int newa4=a4-1;
	return new int[]{newa1, newa2, newa3, newa4};
}

public int[] getNewRight() {
	int newa1=a1+1;
	int newa2=a2+1;
	int newa3=a3+1;
	int newa4=a4+1;
	return new int[]{newa1, newa2, newa3, newa4};
}
	public void rotate() { 
		
	}
	
	public Color getColor() {
		return color;
	}

}
