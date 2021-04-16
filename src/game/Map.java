package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.Map.GunFightAdapter;

public class Map extends JPanel implements ActionListener{

	private final int MAP_HEIGHT = 50;
	private final int MAP_WIDTH = 50;
	private final int ITEM_SIZE = 10;
	private final int SPEED = 30;

	private int[] p1, p2;

	private Timer timer;
	
	private final KeyAdapter gunFightAdapter;
	
	public Map() {

		gunFightAdapter = new GunFightAdapter();
		
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(MAP_WIDTH * ITEM_SIZE, MAP_HEIGHT * ITEM_SIZE));

        timer = new Timer(SPEED, this);
        
        startGame();
	}

	private void startGame() {
		addKeyListener(gunFightAdapter);
		
		p1 = new int[] {0, MAP_HEIGHT/2, 1};
		p2 = new int[] {MAP_WIDTH-1, MAP_HEIGHT/2, 3};
		
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.red);
		g.fillRect(p1[0]*ITEM_SIZE, p1[1]*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);

		g.setColor(Color.blue);
		g.fillRect(p2[0]*ITEM_SIZE, p2[1]*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
		
	}

	private void movePlayer(int[] p, int direction) {
		
		p[2] = direction;
		
		switch (p[2]) {
		case 1:
			p[0]++;
			break;
		case 2:
			p[1]++;
			break;
		case 3:
			p[0]--;
			break;
		case 4:
			p[1]--;
			break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	class GunFightAdapter extends KeyAdapter {
		
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			
			switch(key) {
            case KeyEvent.VK_D:
            	movePlayer(p1, 1);
            	break;
            case KeyEvent.VK_S:
            	movePlayer(p1, 2);
            	break;
            case KeyEvent.VK_A:
            	movePlayer(p1, 3);
            	break;
            case KeyEvent.VK_W:
            	movePlayer(p1, 4);
            	break;
            case KeyEvent.VK_SPACE:
            	break;
            case KeyEvent.VK_RIGHT:
            	movePlayer(p2, 1);
            	break;
            case KeyEvent.VK_DOWN:
            	movePlayer(p2, 2);
            	break;
            case KeyEvent.VK_LEFT:
            	movePlayer(p2, 3);
            	break;
            case KeyEvent.VK_UP:
            	movePlayer(p2, 4);
            	break;
            case KeyEvent.VK_NUMPAD0:
            	break;
            }
		}
		    	
    }

}
