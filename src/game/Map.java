package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.Map.GunFightAdapter;

public class Map extends JPanel implements ActionListener{

	private final int MAP_HEIGHT = 50;
	private final int MAP_WIDTH = 50;
	private final int ITEM_SIZE = 10;
	private final int SPEED = 30;

	private Set<int[]> bulletSet;
	private Set<int[]> removeSet;
	
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
		
		bulletSet = new HashSet<>();
		removeSet = new HashSet<>();
		
		p1 = new int[] {0, MAP_HEIGHT/2, 1};
		p2 = new int[] {MAP_WIDTH-1, MAP_HEIGHT/2, 3};
		
		timer.start();
	}

	private void createBullet(int[] p) {
		switch (p[2]) {
		case 1:
			bulletSet.add(new int[] {p[0]+1, p[1], p[2]});
			break;
		case 2:
			bulletSet.add(new int[] {p[0], p[1]+1, p[2]});
			break;
		case 3:
			bulletSet.add(new int[] {p[0]-1, p[1], p[2]});
			break;
		case 4:
			bulletSet.add(new int[] {p[0], p[1]-1, p[2]});
			break;
		}
	}

	private void bulletMove() {
		
		Iterator<int[]> iter = bulletSet.iterator();
		
		while(iter.hasNext()) {
			
			int[] bullet = iter.next();
			
			switch (bullet[2]) {
			case 1:
				bullet[0]++;
				break;
			case 2:
				bullet[1]++;
				break;
			case 3:
				bullet[0]--;
				break;
			case 4:
				bullet[1]--;
				break;
			}
			if(checkOut(bullet)||checkHit(bullet))
				removeSet.add(bullet);
		}
		
		removeBullet();
	}
	
	private boolean checkHit(int[] bullet) {
		Iterator<int[]> iter = bulletSet.iterator();
		
		while(iter.hasNext()) {
			
			int[] bullet2 = iter.next();
			
			if(bullet!=bullet2&&(bullet[0]==bullet2[0]&&bullet[1]==bullet2[1])) {
				removeSet.add(bullet2);
				return true;
			}
		}
		
		return false;
	}
	
	private void removeBullet() {
		for(int[] bullet : removeSet) {
			bulletSet.remove(bullet);
		}
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
		
		if(checkOut(p)||(p1[0]==p2[0]&&p1[1]==p2[1])) {
			switch (p[2]) {
			case 1:
				p[0]--;
				break;
			case 2:
				p[1]--;
				break;
			case 3:
				p[0]++;
				break;
			case 4:
				p[1]++;
				break;
			}
		}
	}

	private boolean checkOut(int[] item) {
		if((item[0]<0||item[0]>=MAP_WIDTH)||(item[1]<0||item[1]>=MAP_HEIGHT))
			return true;
		return false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.red);
		g.fillRect(p1[0]*ITEM_SIZE, p1[1]*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);

		g.setColor(Color.blue);
		g.fillRect(p2[0]*ITEM_SIZE, p2[1]*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);

		g.setColor(Color.yellow);
		for(int[] bullet : bulletSet) {
			g.fillRect(bullet[0]*ITEM_SIZE, bullet[1]*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		bulletMove();
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
            	createBullet(p1);
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
            	createBullet(p2);
            	break;
            }
		} 	
    }
}
