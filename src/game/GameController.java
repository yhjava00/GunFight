package game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameController {
	
	private final int MAP_HEIGHT = 50;
	private final int MAP_WIDTH = 50;
	private final int ITEM_SIZE = 10;
	
	public Set<int[]> bulletSet;
	public Set<int[]> removeSet;

	public int[] p;
	
	public String move;
	
	EnemyController enemyController;
	
	Thread gameRun;
	
	public boolean inGame;
	
	GameController() {
		
		enemyController = new EnemyController(this);
		
		inGame = false;
	}
	
	public void gameStart() {

		bulletSet = new HashSet<>();
		removeSet = new HashSet<>();
				
		p = new int[] {MAP_WIDTH/2, MAP_HEIGHT-2, 4};
		
		move = "stop";
		
		enemyController.deployEnemy(10);
		
		inGame = true;
		
		gameRun = new gameRun();
		gameRun.start();
	}
	
	public Set<int[]> getEnemySet() {
		return enemyController.enemySet;
	}
	
	public void createBullet(int[] p) {
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
	
	public void bulletMove() {
		
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
			
			if(checkHit(p)) {
				System.out.println("end");
				inGame = false;
			}
			
			Iterator<int[]> enemyIter = getEnemySet().iterator();
			
			while(enemyIter.hasNext()) {
				int[] enemy = enemyIter.next();
				
				if(checkHit(enemy)) {
					enemyIter.remove();
				}
			}
		}
		
		removeBullet();
	}

	public boolean checkHit(int[] item) {
		Iterator<int[]> iter = bulletSet.iterator();
		
		while(iter.hasNext()) {
			
			int[] bullet = iter.next();
			
			if(item!=bullet&&(item[0]==bullet[0]&&item[1]==bullet[1])) {
				removeSet.add(bullet);
				return true;
			}
		}
		
		return false;
	}
	
	public void removeBullet() {
		for(int[] bullet : removeSet) {
			bulletSet.remove(bullet);
		}
	}
	
	public void itemMove(int[] item, int direction) {
		
		item[2] = direction;
		
		switch (item[2]) {
		case 1:
			item[0]++;
			break;
		case 2:
			item[1]++;
			break;
		case 3:
			item[0]--;
			break;
		case 4:
			item[1]--;
			break;
		}
		
		if(!checkOut(item)&&!bump(item)) 
			return;
		
		switch (item[2]) {
		case 1:
			item[0]--;
			break;
		case 2:
			item[1]--;
			break;
		case 3:
			item[0]++;
			break;
		case 4:
			item[1]++;
			break;
		}
	}
	
	public void playerMove() {
		
		switch (move) {
		case "right":
			itemMove(p, 1);
			break;
		case "down":
			itemMove(p, 2);
			break;
		case "left":
			itemMove(p, 3);
			break;
		case "up":
			itemMove(p, 4);
			break;
		}
		
	}
	
	public boolean bump(int[] item) {
		
		if(item!=p&&(p[0]==item[0]&&p[1]==item[1])) 
			return true;
		
		for(int[] enemy : enemyController.enemySet) {
			if(item!=enemy&&(enemy[0]==item[0]&&enemy[1]==item[1])) 
				return true;
		}
		
		return false;
	}

	public boolean checkOut(int[] item) {
		if((item[0]<0||item[0]>=MAP_WIDTH)||(item[1]<0||item[1]>=MAP_HEIGHT))
			return true;
		return false;
	}
	
	class gameRun extends Thread {
		
		int playerMoveStack = 0;
		int bulletStack = 0;
		int enemyStack = 0;
		
		@Override
		public void run() {
			while(inGame) {
				
				try {
					sleep(1);
				} catch (Exception e) {}
				
				if(playerMoveStack++>100) {
					playerMove();
					playerMoveStack = 0;
				}
				
				if(bulletStack++>10) {
					bulletMove();
					bulletStack = 0;
				}
				
				if(enemyStack++>100) {
					enemyController.enemyAction();
					enemyStack = 0;
				}
			}
		}
	}
	
}




