package game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameController {
	
	private final int MAP_HEIGHT = 50;
	private final int MAP_WIDTH = 50;
	private final int ITEM_SIZE = 10;
	
	private final int BULLET_SPEED = 19;
	
	public Set<int[]> bulletSet;
	public Set<int[]> removeSet;

	public int[] p1;
	
	EnemyController enemyController;
	
	Thread bulletMoveThread;
	
	public boolean inGame;
	
	GameController() {
		
		enemyController = new EnemyController(this);
		
		inGame = false;
	}
	
	public void gameStart() {

		bulletSet = new HashSet<>();
		removeSet = new HashSet<>();
				
		p1 = new int[] {MAP_WIDTH/2, MAP_HEIGHT-2, 4};
		
		enemyController.deployEnemy(10);
		
		bulletMoveThread = new bulletMoveThread();
		bulletMoveThread.start();
		
		inGame = true;
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
	
	public void movePlayer(int direction) {
		
		p1[2] = direction;
		
		switch (p1[2]) {
		case 1:
			p1[0]++;
			break;
		case 2:
			p1[1]++;
			break;
		case 3:
			p1[0]--;
			break;
		case 4:
			p1[1]--;
			break;
		}
		
		if(!checkOut(p1)) 
			return;
		
		switch (p1[2]) {
		case 1:
			p1[0]--;
			break;
		case 2:
			p1[1]--;
			break;
		case 3:
			p1[0]++;
			break;
		case 4:
			p1[1]++;
			break;
		}
	}

	public boolean checkOut(int[] item) {
		if((item[0]<0||item[0]>=MAP_WIDTH)||(item[1]<0||item[1]>=MAP_HEIGHT))
			return true;
		return false;
	}
	
	class bulletMoveThread extends Thread {
		@Override
		public void run() {
			while(inGame) {
				try {
					sleep(BULLET_SPEED);
				} catch (Exception e) {}
				bulletMove();
			}
		}
	}
}




