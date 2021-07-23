import java.awt.Container;
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
		for (int i=0;i<GameField.getGameFieldHight();i++) {// end of loop by HIGHT
			int count =0;
			for (int j=0; j<GameField.getGameFieldWeight();j++) { //loop by WEIGHT
				if ( location.contains((i*GameField.getGameFieldWeight())+j)) { // if block of gameField is in location
					count++;
					if (count == GameField.getGameFieldWeight()) {
						fullLines.add(i);
						System.out.println("Full lines index is: " + i);
						result = true;
						}	
				}	// if block of game field is in location
			}	 // end of loop by WEIGHT
		} // end of loop by HIGHT
		return result;
	} // end of isLineFull
	
	
	public void makeFallingBlocksList(){
		fallingBlocks.clear();
		int maxIndex = Collections.max(fullLines);
		int gameFieldWeight = GameField.getGameFieldWeight();
		for (int i=0; i<location.size(); i++) { 
			System.out.println( "index: " + (int)Math.floor(location.get(i) / gameFieldWeight ) + " Item: " + location.get(i) + " MaxIndex: " + maxIndex);
			if(  (int)Math.floor(location.get(i) / gameFieldWeight ) < maxIndex) { // if location element index in gameField is less than last destroyed line
				fallingBlocks.add(location.get(i));
		}
		}
	}
	public void blocksFall() {
		int gameFieldWeight = GameField.getGameFieldWeight();
		fallingBlocks.sort(null);
		for (int j=fallingBlocks.size()-1; j>=0 ;j--) {
			location.set(location.indexOf(fallingBlocks.get(j)), fallingBlocks.get(j) + gameFieldWeight);
			makeFallingBlocksList();
			System.out.println( "New index: " + (int)Math.floor(location.get(j) / gameFieldWeight ) + " Item: " + location.get(j));
			}
		
	}
	public void destroyFullLine() {
		for (int i=0; i<fullLines.size();i++)
		for (int j=0; j<GameField.getGameFieldWeight();j++) {
			location.remove((Integer)((fullLines.get(i)*GameField.getGameFieldWeight())+j));	
		}
		makeFallingBlocksList();// SUCCESS
		//for (int c=0; c<fullLines.size(); c++)
			blocksFall();
			blocksFall();
	}	
	

	
	public boolean contains(ArrayList<Integer> array) { // // Not used in this game!
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
	public boolean canBlockFall() { // Not used in this game!
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
	
	public boolean canBlockFall(ArrayList<Integer> array, int hight) { // Not used in this game!
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