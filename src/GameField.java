import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class GameField extends JFrame{
	private int blockSize = 50;
	private static int gameFieldWeight = 8;
	private static int gameFieldHight = 12;
	private Timer timer;
	private boolean shapeIsAlive = false;
	Painter paintPanel;
	IShape iShape;
	OShape oShape;
	JShape jShape;
	LShape lShape;
	SShape sShape;
	TShape tShape;
	ZShape zShape;
	int randomShapeIndex;
	Shapes randomShape;
	int[] randomShapeLocation;
	Color randomShapeColor;
	FallenShapes fallenShapes = new FallenShapes();
	int[] fallenShapesArray;
	static int[] lastLine;
	static int[] firstLine;
	int gameSpeed = 10;
	boolean gameIsRunning = false;
	
	public GameField(String title) {
		super(title);
		addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) { 
                char key = evt.getKeyChar();
            	if ( key == 'a' || key == 'A' || key == '�' || key == '�' ) moveShape("left");
            	else if ( key == 'd' || key == 'D' || key == '�' || key == '�') moveShape("right");
            	else if ( key == 's' || key == 'S' || key == '�' || key == '�') {
            		if (!isGameOver()) 
        				if (shapeIsAlive) { 
        					moveShape(); // thapes fall if it's alive
        					randomShapeLocation = randomShape.getLocation(); // getting actual locations of alive shape
        					fallenShapesArray = fallenShapes.getLocation(); // getting actual locations of fallen shapes
        					if (isFallen()) {  // check if shape fell onto another shape or onto the bottom of gamefield
        						shapeIsAlive = false; // setting shape as alive
        						fallenShapes.add(randomShapeLocation); // adding fallen shape to fallenShapes list
        						repaint();
        						if (fallenShapes.isLineFull()) { // check if there are full lines for destroying 
        							fallenShapes.destroyFullLine(); // dostroy them if exists
        							// begin falling objects
        							ArrayList<Integer> tempArray = new ArrayList<Integer>();
        							if (!fallenShapes.isEmpty())
        							for ( int i=0; i<fallenShapes.getHighestPosition(); i++ ) {	
        							for (int h=0; h<gameFieldHight;h++) {
        								tempArray.clear();
        								for ( int w=0; w<gameFieldWeight; w++ ) {
        									tempArray.add( (h*gameFieldWeight)+w  );
        									}
        								if (fallenShapes.canBlockFall(tempArray, h))
        									fallenShapes.blocksFall();	
        							}
        						} 
        							// end
        							}
        							repaint();
        						}
        				}
            	}
                }
        });
	}
	
	public void startGame() {
		if (!gameIsRunning) {
			createShape(); 
			gameIsRunning = true;
			repaint();
			if (isFallen()) {
				shapeIsAlive = false;
				fallenShapes.add(randomShapeLocation);
			}
			timer.start();
		} else System.out.println("Game is already running");
	}
	
	public void stopGame() { 
		gameIsRunning = false;
		repaint();
		fallenShapes.clearLocations();

		timer.stop();
	}
	
	public void pauseGame() { 
		timer.stop();
	}
	
	public void continueGame() { 
		timer.start();
	}
	
	public void go() {
		lastLine = new int[gameFieldWeight]; // ������ ��������� ����� �������� ����
		for (int i=gameFieldWeight-1;i>=0; i--)	lastLine[i] = (gameFieldWeight*gameFieldHight)-i-1;
		firstLine = new int[gameFieldWeight]; // ������ ������ ����� �������� ����
		for (int i=0;i<gameFieldWeight; i++) firstLine[i] = i;
		// Making FRAME
		JButton stopButton = new JButton("Stop");
		JButton startButton = new JButton("Start");
		JButton restartButton = new JButton("Restart");
		JButton pauseButton = new JButton("Pause");
		JButton playButton = new JButton("Play");
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(stopButton);
		buttonsPanel.add(startButton);
		buttonsPanel.add(restartButton);
		buttonsPanel.add(pauseButton);
		buttonsPanel.add(playButton);
		restartButton.addActionListener(new buttonRestartActionListener());
		startButton.addActionListener(new buttonStartActionListener());
		stopButton.addActionListener(new buttonStopActionListener());
		pauseButton.addActionListener(new buttonPauseActionListener());
		playButton.addActionListener(new buttonPlayActionListener());
		getContentPane().add(BorderLayout.NORTH, buttonsPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((blockSize*gameFieldWeight)+18, (blockSize*gameFieldHight)+40);
		paintPanel = new Painter();
		getContentPane().add(BorderLayout.CENTER, paintPanel);
		timer = new Timer(1000/gameSpeed, new TimerActionListener());
		setLocation(400, 50);
		setVisible(true); 
		
		
		}
	
	public void createShape() { // �������� ��������-����� ��� �������� ����
			randomShapeIndex = getRandomNumber(7);
			//randomShapeIndex = 1; // ��� �����
			switch (randomShapeIndex) {
	           case  (0): {
	        	   iShape = new IShape(gameFieldWeight, gameFieldHight) ;
	        	   randomShape = iShape; 
	        	   break;
	        	   }
	           case (1): {
	        	   oShape = new OShape(gameFieldWeight, gameFieldHight);
	        	   randomShape = oShape; 
	        	   break;
	        	   }
	           case (2): {
	        	   jShape = new JShape(gameFieldWeight, gameFieldHight);
	        	   randomShape = jShape; 
	        	   break;
	        	   }
	           case (3): {
	        	   lShape = new LShape(gameFieldWeight, gameFieldHight);
	        	   randomShape = lShape; 
	        	   break;
	        	   }
	           case (4): {
	        	   sShape = new SShape(gameFieldWeight, gameFieldHight);
	        	   randomShape = sShape; 
	        	   break;
	        	   }
	           case (5): {
	        	   tShape = new TShape(gameFieldWeight, gameFieldHight);
	        	   randomShape = tShape; 
	        	   break;
	        	   }
	           case (6): {
	        	   zShape = new ZShape(gameFieldWeight, gameFieldHight);
	        	   randomShape = zShape; 
	        	   break;
	        	   }
	           default: {
	        	   randomShape = null; 
	        	   break;
	           }
			}
			randomShapeColor = randomShape.getColor();
			shapeIsAlive = true;	
	}
	
	public void moveShape(){ // ��������� ��������� �������-������ - ���� �� �������� ����
		randomShape.fall();
	}
	public boolean isFallen() {
		randomShapeLocation = randomShape.getLocation();
		// ����������� �� ������-������ � �������� ������-��������?
		if (!fallenShapes.isEmpty()) {
			int [] newFallLocation = randomShape.getNewFallLocation();
			for (int a=0; a<4; a++) 
			for (int b=0; b<fallenShapesArray.length ;b++) 
				if (newFallLocation[a] == fallenShapesArray[b] ) {
					return true;
				}
			}
		// ����� �� ������-������ �� ��� �������� ����?
		for (int i=0; i<4; i++) 
		for (int j=0; j<lastLine.length ;j++)
			if (randomShapeLocation[i] == lastLine[j] ) {
				return true;

			}
		return false;
	}
	public void moveShape(String direction){ // ��������� ��������� �������-������ - �����/������ �� �������� ����
		if (!isGameOver()) {
			if (shapeIsAlive){
				if (direction.equals("left")) {
					if (canMoveLeftOfRight(direction)){
					randomShape.moveLeft();
					randomShapeLocation = randomShape.getLocation();
					fallenShapesArray = fallenShapes.getLocation();
					if (isFallen()) {
						shapeIsAlive = false;
						fallenShapes.add(randomShapeLocation);
						repaint();
						if (fallenShapes.isLineFull()) {
							fallenShapes.destroyFullLine();
							repaint();
							}
						}
					}} else {
					if (canMoveLeftOfRight(direction)){
					randomShape.moveRight();
					randomShapeLocation = randomShape.getLocation();
					fallenShapesArray = fallenShapes.getLocation();
					if (isFallen()) {
						shapeIsAlive = false;
						fallenShapes.add(randomShapeLocation);
						repaint();
						if (fallenShapes.isLineFull()) {
							fallenShapes.destroyFullLine();
							repaint();
							}
						}
					}}
					repaint();
				}			
			} else 	timer.stop();	
	
	}
	
	
	public boolean canMoveLeftOfRight(String direction) {
		randomShapeLocation = randomShape.getLocation();
		// ����������� �� ������-������ � �������� ������-�������� �� �����?
		if (!fallenShapes.isEmpty()) {
			int [] newRandomShapeLocation;
			if (direction.equals("left")) { newRandomShapeLocation = randomShape.getNewLeft(); 
			} else	newRandomShapeLocation = randomShape.getNewRight();
			for (int a=0; a<4; a++) 
			for (int b=0; b<fallenShapesArray.length ;b++) 
				if (newRandomShapeLocation[a] == fallenShapesArray[b] ) {
					return false;
				}
			}
		// ����������� �� ������-������ � ������ �������� ����?
		if (direction.equals("left")) {
			for (int i=0; i<4; i++) 
				if ((randomShapeLocation[i]%gameFieldWeight)==0 ) 
					return false;
		} else
			for (int i=0; i<4; i++) 
				if (((randomShapeLocation[i]+1)%gameFieldWeight)==0 ) {
					return false;
		}
		return true;
	}
	
	public boolean isGameOver() {
		boolean result = false;
		mainLoopGameOver:
		if (!fallenShapes.isEmpty()) 
			for (int i=0; i<firstLine.length; i++) 
				for (int j=fallenShapesArray.length-1; j>=0 ;j--) 
					if (firstLine[i] == fallenShapesArray[j] ) {
						result = true;
						break mainLoopGameOver;
					}		
		return result;
	}
	
	public int getRandomNumber(int size) { // ��������� ������������� integer'� � �������� ����������� ���������
		int number = (int)(Math.random()*size);
		return number;}
	
	public Color getRandomColor() { // ��������� ������������� �����
		int red = (int)(Math.random()*255);
		int green = (int)(Math.random()*255);
		int blue = (int)(Math.random()*255);
		return new Color(red, green, blue);}
	
	public class Painter extends JPanel { // ������ ��� ��������� ������� ������ �� �� ���������		
		public void paintComponent(Graphics g) {
			for (int w=0; w<=gameFieldWeight; w++)	// ������ ������������ ����� �����
				for (int h=0; h<=(gameFieldHight)*blockSize; h++){
					g.setColor(Color.gray);
					g.fillRect(w*blockSize, h, 1, 1);	}
			for (int h=0; h<=gameFieldHight; h++) // ������ �������������� ����� �����
				for (int w=0; w<=(gameFieldWeight)*blockSize; w++){
					g.setColor(Color.gray);
					g.fillRect(w, h*blockSize, 1, 1);}
			if (gameIsRunning) {
				paintObjects(g);
			}
			
		}
		public void paintObjects(Graphics g) {
			randomShapeLocation = randomShape.getLocation();
			for (int i=0; i<4; i++) { // ������ �������� ������-������
				int x = (randomShapeLocation[i]%gameFieldWeight)*blockSize;
				int y = (int)(randomShapeLocation[i]/gameFieldWeight)*blockSize;
				g.setColor(randomShapeColor);
				g.fillRect(x, y, blockSize, blockSize);}
			if (!fallenShapes.isEmpty()) paintFallenShapes(g);// ������ ������� �������-������, ���� ��� ����
		}		
		
		public void paintFallenShapes(Graphics g) { // ����� ��� ��������� ������� ������-�����
			fallenShapesArray = fallenShapes.getLocation();
			for ( int i=0; i<fallenShapesArray.length;i++ ) {
				int x = (fallenShapesArray[i]%gameFieldWeight)*blockSize;
				int y = (int)(fallenShapesArray[i]/gameFieldWeight)*blockSize;
				g.setColor(new Color(150, 5, 5));
				g.fillRect(x, y, blockSize, blockSize);}}}
	
	public class TimerActionListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (!isGameOver()) {
				if (shapeIsAlive) { 
					moveShape(); // thapes fall if it's alive
					randomShapeLocation = randomShape.getLocation(); // getting actual locations of alive shape
					fallenShapesArray = fallenShapes.getLocation(); // getting actual locations of fallen shapes
					if (isFallen()) {  // check if shape fell onto another shape or onto the bottom of gamefield
						shapeIsAlive = false; // setting shape as alive
						fallenShapes.add(randomShapeLocation); // adding fallen shape to fallenShapes list
						repaint();
						if (fallenShapes.isLineFull()) { // check if there are full lines for destroying 
							fallenShapes.destroyFullLine(); // dostroy them if exists
							// begin falling objects
							ArrayList<Integer> tempArray = new ArrayList<Integer>();
							if (!fallenShapes.isEmpty())
							for ( int i=0; i<fallenShapes.getHighestPosition(); i++ ) {	
							for (int h=0; h<gameFieldHight;h++) {
								tempArray.clear();
								for ( int w=0; w<gameFieldWeight; w++ ) {
									tempArray.add( (h*gameFieldWeight)+w  );
									}
								if (fallenShapes.canBlockFall(tempArray, h))
									fallenShapes.blocksFall();	
							}
						} 
							// end
							}
							repaint();
						}
				} else {
					createShape(); 
					randomShapeLocation = randomShape.getLocation(); // getting actual locations of alive shape
					fallenShapesArray = fallenShapes.getLocation(); // getting actual locations of fallen shapes
					if (isFallen()) {  // check if shape fell onto another shape or onto the bottom of gamefield
						shapeIsAlive = false; // setting shape as dead
						fallenShapes.add(randomShapeLocation); // cleate new shape if it's dead
						repaint();
						if (fallenShapes.isLineFull()) { // check if there are full lines for destroying 
							fallenShapes.destroyFullLine();  // dostroy them if exists
							// begin falling objects
							ArrayList<Integer> tempArray = new ArrayList<Integer>();
							if (!fallenShapes.isEmpty())
							for ( int i=0; i<fallenShapes.getHighestPosition(); i++ ) {	
							for (int h=0; h<gameFieldHight;h++) {
								tempArray.clear();
								for ( int w=0; w<gameFieldWeight; w++ ) {
									tempArray.add( (h*gameFieldWeight)+w  );
									}
								if (fallenShapes.canBlockFall(tempArray, h))
									fallenShapes.blocksFall();	
							}
						} 
							// end
							repaint();
						}
					}	
				} 
				repaint();
			} else {
				timer.stop();
			}
		}
}
	
	public static int getGameFieldHight() {
		return gameFieldHight;
	}
	public static int getGameFieldWeight() {
		return gameFieldWeight;
	}
	
	public static int[] getLastLine() {
		return lastLine;
	}
	
	public static int[] getFirstLine() {
		return firstLine;
	}
	
	public class buttonRestartActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			stopGame();
			startGame();
		}	
	}
	public class buttonStartActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			startGame();
		}
	}
	public class buttonStopActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			stopGame();
		}
	}
	
	public class buttonPauseActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			pauseGame();
		}
	}
	
	public class buttonPlayActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			continueGame();
		}
	}
}