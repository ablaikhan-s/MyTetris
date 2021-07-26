import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;

public class FallenShapes {
	ArrayList<Integer> location = new ArrayList<>(); // location of every fallenShape blocks 
	ArrayList<Integer> fullLines = new ArrayList<>(); // indexes of lines must be destroyed
	ArrayList<Integer> fallingBlocks = new ArrayList<>(); // location of every fallenShape blocks must fall after destroying full lines
	int maxIndex;
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
		maxIndex = Collections.max(fullLines);
		int gameFieldWeight = GameField.getGameFieldWeight();
		for (int i=0; i<location.size(); i++) { 
			if(  (int)Math.floor(location.get(i) / gameFieldWeight ) < maxIndex) { // if location element index in gameField is less than last destroyed line
				if (!fallingBlocks.contains(location.get(i)))
				fallingBlocks.add(location.get(i));
				}
			}
	}
	public void blocksFall() {
		int gameFieldWeight = GameField.getGameFieldWeight();
		fallingBlocks.sort(null);
		for (int j=0; j<fallingBlocks.size() ;j++) {
			location.set(location.indexOf(fallingBlocks.get(j)), fallingBlocks.get(j) + gameFieldWeight);
			fallingBlocks.set(j, fallingBlocks.get(j) + gameFieldWeight);
			}
		}
	public void destroyFullLine() {
		int gameFieldWeight = GameField.getGameFieldWeight();
		for (int i=0; i<fullLines.size();i++)
		for (int j=0; j<gameFieldWeight;j++) {
			location.remove((Integer)((fullLines.get(i)*gameFieldWeight+j)));	
		}
		makeFallingBlocksList();
			if (!fallingBlocks.isEmpty()){
			ArrayList<Integer> fallingBlocksIndexes=new ArrayList<Integer>(); 
			for (int q=0; q<fullLines.size();q++) {
				for (int w=0; w<fallingBlocks.size();w++) {
					if (!fallingBlocksIndexes.contains((int)Math.floor(fallingBlocks.get(w) / gameFieldWeight ))) {
						fallingBlocksIndexes.add((int)Math.floor(fallingBlocks.get(w) / gameFieldWeight ));
					}
				}
				int fallingBlocksIndex = fallingBlocksIndexes.get(fallingBlocksIndexes.size()-1);
				if ( fallingBlocksIndex < fullLines.get(q)) blocksFall();
				}
			}
	}	

	public int getHighestPosition() {
		int gameFieldWeight = GameField.getGameFieldWeight();
		return (int)(Collections.max(location)+gameFieldWeight)/gameFieldWeight;
		

	}
}