
public class State {
	public boolean hitbox1; /*a*/
	public boolean hitbox2; /*b*/
	public boolean hitbox3; /*ra*/
	public boolean hitbox4; /*la*/
	public boolean hitbox5; /*da*/
	public boolean hitbox6; /*rb*/
	public boolean hitbox7; /*lb*/
	public boolean hitbox8; /*db*/
	public Hitbox[] hitboxes;
	public boolean close_to_enemy;
	public boolean far_from_enemy;
	public boolean wall_front;
	public boolean wall_back;
	public String enemy_state; /* stehen, vor, zurück, fern, nahkampf */
	
	public State(String enemyState,int MyY,int MyX,int enemyDistance){
		for (int i = 0;i<this.hitboxes.length;i++ ){
			if (enemyDistance >= hitboxes[i].left || enemyDistance <= hitboxes[i].right){
				this.close_to_enemy = true;
				this.far_from_enemy = false;
			}
			else{
				this.close_to_enemy = false;
				this.far_from_enemy = true;
			}
			this.enemy_state = enemyState;
		}
	}
	public void stateRefresh(String enemyState,int MyY,int MyX,int enemyDistance){
		for (int i = 0;i<this.hitboxes.length;i++ ){
			if (enemyDistance >= hitboxes[i].left || enemyDistance <= hitboxes[i].right){
				this.close_to_enemy = true;
				this.far_from_enemy = false;
			}
			else{
				this.close_to_enemy = false;
				this.far_from_enemy = true;
			}	
			this.enemy_state = enemyState;
		}
	}
	
	public void recordNewHitbox(int attackType,int MyY,int MyX,int hitboxL, int hitboxR, int hitboxB, int hitboxT){
		if(this.hitboxes[attackType] != null){
			Hitbox h = new Hitbox(attackType,MyX + hitboxL,MyX + hitboxR,MyY + hitboxT,MyY + hitboxB);//Hier muss man mal gucken ob das mit der addition überhaubt nötig ist
			this.hitboxes[h.attacktype] = h;
		}
		
	}
	
	public Hitbox[] getHitboxes(){
		return this.hitboxes;
	}
	public boolean isClose_to_enemy() {
		return close_to_enemy;
	}

	public boolean isFar_from_enemy() {
		return far_from_enemy;
	}

	public boolean isWall_front() {
		return wall_front;
	}

	public boolean isWall_back() {
		return wall_back;
	}

	public String getEnemy_state() {
		return enemy_state;
	}

	
}
