public class FallenShapesLine {
	static int lineLength;
	char [] values;
	
	public FallenShapesLine(int newLength) {
		values = new char[newLength];
		setFalseValues();
	}
	
	public static void setLineLength(int newLength) {
		lineLength = newLength;
	}
	
	public void setValue(int index, char newValue) {
		values[index] = newValue;
	}
	public void setValues(char[] newValues) {
		values = newValues;
	}
	
	public void setFalseValues() {
		for (int i=0; i<lineLength; i++)
			values[i] = '_';
	}
	
	public char[] getValues() {return values;}
	
	public boolean isFull() {
		int trueCount = 0;
		for (int i=0; i<lineLength; i++)
		if (values[i] == 'o') trueCount++;
		if (trueCount == lineLength) return true;
		
		return false;
	}
}
