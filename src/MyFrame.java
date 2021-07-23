import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MyFrame extends JFrame {
 
    public MyFrame(String Cap) {
        super(Cap);
        setSize(new Dimension(500, 500));
        setVisible(true);
 
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyChar()) {
                    case 'w':
                        y -= 1;
                        DrawRect();
                        break;
                    case 's':
                        y += 1;
                        DrawRect();
                        break;
                    case 'a':
                        x -= 1;
                        DrawRect();
                        break;
                    case 'd':
                        x += 1;
                        DrawRect();
                        break;
                }
            }
        });
    }
 
    @Override
    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 100, 100);
    }
 
    public void DrawRect() {
        this.repaint();
        this.getGraphics().drawRect(x, y, 100, 100);
    }
    private int x, y;
}