import java.util.ArrayList;
import java.util.Collections;

public class FallenShapes {
	ArrayList<Integer> location = new ArrayList<>();
	ArrayList<Integer> fullLines = new ArrayList<>();
	ArrayList<Integer> fallingBlocks = new ArrayList<>();
	ArrayList<Integer> tempList = new ArrayList<Integer>();
	int[] arrayLocation = null;
	
	public boolean isEmpty() {
		return location.isEmpty();
	}
	
	public int[] getLocation(){ // for GameField
		arrayLocation = new int[location.size()];
		int index = 0;
		for (int i=0; i<location.size();i++) 
		{	
			arrayLocation[index] = location.get(i);
			index++;
		}
		return arrayLocation;
	}
	
	public void clearLocations() {
		location.clear();
	}
	
	public void add(int[] fallenShapeLocation) {
		for (int a=0; a<fallenShapeLocation.length; a++)
			location.add(fallenShapeLocation[a]);
	}
	
	public boolean isLineFull() {
		boolean result = false;
		fullLines.clear();
		for (int i=0;i<GameField.getGameFieldHight();i++) {
			int count =0;
			for (int j=0; j<GameField.getGameFieldWeight();j++) {
				if ( location.contains((i*GameField.getGameFieldWeight())+j))
					count++;
					if (count == GameField.getGameFieldWeight()) {
						fullLines.add(i);
						result = true;
						}
					}
			}
		return result;
	}
	
	public void destroyFullLine() {
		for (int i=0; i<fullLines.size();i++)
		for (int j=0; j<GameField.getGameFieldWeight();j++) {
			location.remove((Integer)((fullLines.get(i)*GameField.getGameFieldWeight())+j));	
		}
	}

		public void blocksFall() {
			int gameFieldWeight = GameField.getGameFieldWeight();
			for (int j=0; j<fallingBlocks.size() ;j++)
				location.set(location.indexOf(fallingBlocks.get(j)), fallingBlocks.get(j) + gameFieldWeight);
		}
	
	public boolean contains(ArrayList<Integer> array) {
		boolean result = false;
		tempList.clear();
		
		for (int i=0; i<array.size(); i++) {
			if ( location.contains(array.get(i)) ) {
				result = true;
				tempList.add((Integer) array.get(i));
			}
				
		}
		
		return result;
	}
	public boolean canBlockFall() {
		boolean result = false;
		if (!location.isEmpty()) {
			
			int[] lastLine = GameField.getLastLine();
			int gameFieldWeight = GameField.getGameFieldWeight();
			int gameFieldHight = GameField.getGameFieldHight();
			
			// может ли блок упасть вниз(не на пол)?
			for (int i=0; i<location.size(); i++) 
				if (  ((location.get(i)-1+gameFieldWeight)/gameFieldWeight)<gameFieldHight-1) 
				if ( !location.contains(location.get(i)+gameFieldWeight) ) {
					result = true;
					return result;
			}
			
			// может ли блок упасть на пол?
			for (int j=0; j<location.size(); j++) 
			if (  ((location.get(j)-1+gameFieldWeight)/gameFieldWeight)==gameFieldHight-1) 
				for (int k=0; k<lastLine.length; k++)
				if ( lastLine[k] == location.get(j)+gameFieldWeight ) {
					result = true;
					return result;
			}
		}
		return result;
	}	
	
	public int getHighestPosition() {
		int gameFieldWeight = GameField.getGameFieldWeight();
		return (int)(Collections.max(location)+gameFieldWeight)/gameFieldWeight;
		

	}
	
	public boolean canBlockFall(ArrayList<Integer> array, int hight) {
		boolean result = false;
		fallingBlocks.clear();
		if (!location.isEmpty())
		if (contains(array)) {
			
			int[] lastLine = GameField.getLastLine();
			int gameFieldWeight = GameField.getGameFieldWeight();
			int gameFieldHight = GameField.getGameFieldHight();
			
			// может ли блок упасть вниз(не на пол)?
			for (int i=0; i<tempList.size(); i++) 
				if (  hight<gameFieldHight-1) 
				if ( !location.contains(tempList.get(i)+gameFieldWeight) ) {
					fallingBlocks.add(tempList.get(i));
					result = true;
			}
			
			// может ли блок упасть на пол?
			if (  hight==gameFieldHight-1)
			for (int j=0; j<lastLine.length ;j++)
			for (int i=0; i<tempList.size(); i++) 
				if ( lastLine[j] == tempList.get(i)+gameFieldWeight ) {
					fallingBlocks.add(tempList.get(i));
					result = true;
			}
		}
		//System.out.println(fallingBlocks);
		return result;
	}
	
}