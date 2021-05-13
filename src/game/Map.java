package game;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
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

	private Button restart = new Button("restart");
	
	private GameController gameController;
	
	private Timer timer;
	
	private final KeyAdapter gunFightAdapter;
	
	private void setLabelAndButton() {
		restart.setBounds(MAP_WIDTH*ITEM_SIZE/2-37, 130, 75, 30);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(restart);
			}
		});
	}
	public Map() {

		gunFightAdapter = new GunFightAdapter();
		
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(MAP_WIDTH * ITEM_SIZE, MAP_HEIGHT * ITEM_SIZE));

        addKeyListener(gunFightAdapter);
        
        setLabelAndButton();
        
        gameController = new GameController();
        
        gameController.gameStart();
        
        timer = new Timer(SPEED, this);
		timer.start();
	}

	private void endGame() {
		timer.stop();
		add(restart);
	}

	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.red);
		g.fillRect(gameController.p1[0]*ITEM_SIZE, gameController.p1[1]*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
		
		g.setColor(Color.blue);
		Set<int[]> enemySet = gameController.getEnemySet();
		for(int[] enemy : enemySet) {
			g.fillRect(enemy[0]*ITEM_SIZE, enemy[1]*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
		}
		
		g.setColor(Color.yellow);
		for(int[] bullet : gameController.bulletSet) {
			g.fillRect(bullet[0]*ITEM_SIZE, bullet[1]*ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
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
            case KeyEvent.VK_RIGHT:
            	gameController.itemMove(gameController.p1, 1);
            	break;
            case KeyEvent.VK_DOWN:
            	gameController.itemMove(gameController.p1, 2);
            	break;
            case KeyEvent.VK_LEFT:
            	gameController.itemMove(gameController.p1, 3);
            	break;
            case KeyEvent.VK_UP:
            	gameController.itemMove(gameController.p1, 4);
            	break;
            case KeyEvent.VK_SPACE:
            	gameController.createBullet(gameController.p1);
            	break;
            }
		} 	
    }
}
