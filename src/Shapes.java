import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Shapes {
	private static int gameFieldWeight;
	private static int gameFieldHight; 
	private static int[] firstVerticalLine;
	private static int[] lastVerticalLine;
	Color color = Color.yellow;
	
	public int[] getLocation() {
		return null;
	}
	
	public void fall() {}
	public int[] getNewFallLocation() { return null;}
	public int[] getNewLeft() { return null;}
	public int[] getNewRight() { return null;}
	public int[] getNewFallLocation(int a1, int a2,int a3, int a4) {
		int newa1=a1+gameFieldWeight;
		int newa2=a2+gameFieldWeight;
		int newa3=a3+gameFieldWeight;
		int newa4=a4+gameFieldWeight;
		return new int[]{newa1, newa2, newa3, newa4};
		}
	public void moveLeft() {}
	public void moveRight() {}
	public void rotate() {}
	
	public Shapes(int w, int h) {
		gameFieldWeight = w;
		gameFieldHight = h;
		firstVerticalLine = new int[gameFieldHight];
		lastVerticalLine = new int[gameFieldHight];
		int index = 0;
		for (int i=0; i<(gameFieldWeight*gameFieldHight);i+=gameFieldWeight) {
			
			firstVerticalLine[index] = i;
			index++;
		}
		index = 0;
		for (int i=0; i<(gameFieldWeight*gameFieldHight);i+=gameFieldWeight) {
			lastVerticalLine[index] = i+gameFieldWeight-1;
			index++;
		}
	}
	
	public Shapes() {}
	
	public int getGameFieldWeight() {
		return gameFieldWeight;
	}
	
	public int getGameFieldHight() {
		return gameFieldHight;
	}
	
	public int[] getFirstVerticalLine() {
		return firstVerticalLine;
	}
	
	public int[] getLastVerticalLine() {
		return lastVerticalLine;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void printGameField() {
		int[][] gameField = new int[gameFieldHight][gameFieldWeight];
		int index = 0;
		for (int i=0;i<gameFieldHight;i++) {
			for (int y=0;y<gameFieldWeight;y++) {
				gameField[i][y] = index ;
				System.out.print(gameField[i][y] + " ");
				index++;
			}
		}	
	}

	
	public int getRandomNumber(int size) { // Получение произвольного integer'а в пределах переданного аргумента
		int number = (int)(Math.random()*size);
		return number;}
	
	public static Color getRandomColor() {
		Integer red = (int)(Math.random()*50+120);
		Integer green = (int)(Math.random()*50+120);
		Integer blue = (int)(Math.random()*50+120);
		return new Color(red, green, blue);
	}
		
	public static Color getRandomColor(int rangeFrom, int rangeTo) {
		Integer red = (int)(Math.random()*(rangeTo-rangeFrom)+rangeFrom);
		Integer green = (int)(Math.random()*(rangeTo-rangeFrom)+rangeFrom);
		Integer blue = (int)(Math.random()*(rangeTo-rangeFrom)+rangeFrom);
		return new Color(red, green, blue);
	}
}
