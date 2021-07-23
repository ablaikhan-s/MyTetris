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
	int gameSpeed = 1;
	boolean gameIsRunning = false;
	
	public GameField(String title) {
		super(title);

		lastLine = new int[gameFieldWeight]; // Массив последней линии игрового поля
		for (int i=gameFieldWeight-1;i>=0; i--)	lastLine[i] = (gameFieldWeight*gameFieldHight)-i-1;
		firstLine = new int[gameFieldWeight]; // Массив первой линии игрового поля
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
		
		// keyEvents
		InputMap inputMap = startButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    ActionMap actionMap = startButton.getActionMap();
	    
	    inputMap.put(KeyStroke.getKeyStroke("LEFT"), "goLeft");
	    actionMap.put("goLeft", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	moveShape("left");	       
	        }
	      });
	    
	    inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "goRight");
	    actionMap.put("goRight", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	moveShape("right");
	        }
	      });
	   
	    inputMap.put(KeyStroke.getKeyStroke("DOWN"), "goDown");
	    actionMap.put("goDown", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	for (int i=0; i<gameFieldHight;i++) {
	        		if (!isFallen()) moveShape(); 
	        		if (!shapeIsAlive) break;
	        	}
	        	
    			repaint();
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
		timer.stop();
		fallenShapes.clearLocations();
		repaint();
		gameIsRunning = false;
		}
	
	public void pauseGame() { 
		timer.stop();
	}
	
	public void continueGame() { 
		timer.start();
	}
	
	public void createShape() { // Создание объектов-фигур для игрового поля
			randomShapeIndex = getRandomNumber(7);
			//randomShapeIndex = 1; // для теста
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
	
	public void moveShape(){ // Изменение координат объекта-фигуры - вниз по игровому полю
		randomShape.fall();
	}
	public boolean isFallen() {
		randomShapeLocation = randomShape.getLocation();
		// Столкнулась ли объект-фигура с упавшими объект-фигурами?
		
		if (!fallenShapes.isEmpty()) {
			int [] newFallLocation = randomShape.getNewFallLocation();
			for (int a=0; a<4; a++) 
			for (int b=0; b<fallenShapesArray.length ;b++) 
				if (newFallLocation[a] == fallenShapesArray[b] ) {
					shapeIsAlive = false;
					return true;
				}
			}
		// Упала ли объект-фигура на дно игрового поля?
		for (int i=0; i<4; i++) 
		for (int j=0; j<lastLine.length ;j++)
			if (randomShapeLocation[i] == lastLine[j] ) {
				shapeIsAlive = false;
				return true;

			}
		return false;
	}
	public void moveShape(String direction){ // Изменение координат объекта-фигуры - влево/вправо по игровому полю
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
		// Столкнулась ли объект-фигура с упавшими объект-фигурами по бокам?
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
		// Столкнулась ли объект-фигура с краями игрового поля?
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
		if (!fallenShapes.isEmpty()) 
			for (int i=0; i<firstLine.length; i++) 
				for (int j=fallenShapesArray.length-1; j>=0 ;j--) 
					if (firstLine[i] == fallenShapesArray[j] ) {
						return true;
					}		
		return false;
	}
	
	public int getRandomNumber(int size) { // Получение произвольного integer'а в пределах переданного аргумента
		int number = (int)(Math.random()*size);
		return number;}
	
	public Color getRandomColor() { // Получение произвольного цвета
		int red = (int)(Math.random()*255);
		int green = (int)(Math.random()*255);
		int blue = (int)(Math.random()*255);
		return new Color(red, green, blue);}
	
	public class Painter extends JPanel { // Панель для рисования обектов исходя из их координат		
		public void paintComponent(Graphics g) {
			for (int w=0; w<=gameFieldWeight; w++)	// Рисуем вертикальные линии сетки
				for (int h=0; h<=(gameFieldHight)*blockSize; h++){
					g.setColor(Color.gray);
					g.fillRect(w*blockSize, h, 1, 1);	}
			for (int h=0; h<=gameFieldHight; h++) // Рисуем горизонтальные линии сетки
				for (int w=0; w<=(gameFieldWeight)*blockSize; w++){
					g.setColor(Color.gray);
					g.fillRect(w, h*blockSize, 1, 1);}
			if (gameIsRunning) {
				paintObjects(g);
			}
			
		}
		public void paintObjects(Graphics g) {
			randomShapeLocation = randomShape.getLocation();
			for (int i=0; i<4; i++) { // Рисуем активную объект-фигуру
				int x = (randomShapeLocation[i]%gameFieldWeight)*blockSize;
				int y = (int)(randomShapeLocation[i]/gameFieldWeight)*blockSize;
				g.setColor(randomShapeColor);
				g.fillRect(x, y, blockSize, blockSize);}
			if (!fallenShapes.isEmpty()) paintFallenShapes(g);// Рисуем упавшие объекты-фигуры, если они есть
		}		
		
		public void paintFallenShapes(Graphics g) { // Метод для рисования упавших объект-фигур
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
