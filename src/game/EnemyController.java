package game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class EnemyController {
	
	GameController gameController;
	
	Set<int[]> enemySet;
	
	public EnemyController(GameController gameController) {
		this.gameController = gameController;
		enemySet = new HashSet<int[]>();
	}
	
	public void deployEnemy(int n) {
		
		Random r = new Random();
		
		out : while(n>0) {
			int[] enemy = {r.nextInt(50), r.nextInt(25), r.nextInt(4)+1};
			
			for(int[] other : enemySet) {
				if(enemy[0]==other[0]&&enemy[1]==other[1]) 
					continue out;
			}
			
			enemySet.add(enemy);
			
			n--;
			
		}
	}
	
	public void enemyAction() {
		
		Random r = new Random();
		
		for(int[] enemy : enemySet) {
			if(r.nextInt(100)==0) {
				gameController.itemMove(enemy, r.nextInt(4)+1);
			}
		}
		
	}

}
