
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
	public String enemy_state; /* stehen, vor, zur�ck, fern, nahkampf */
	
	public State(String enemyState,int MyY,int MyX,int enemyDistance){
		this.stateRefresh(enemyState, MyY, MyX, enemyDistance);
	}
	public void stateRefresh(String enemyState,int MyY,int MyX,int enemyDistance){
		
		int ed = Math.min(255, enemyDistance);
		boolean t = true;
		System.out.println("ed = " + enemyDistance);
		System.out.println("hl = " + hitboxes[1].left);
		System.out.println("hr = " + hitboxes[1].right);
		
	}
	
	public void recordNewHitbox(int attackType,int MyY,int MyX,int hitboxL, int hitboxR, int hitboxB, int hitboxT){
		System.out.println(attackType);
		System.out.println(MyX + "\t" + MyY);
		System.out.println(hitboxL + "\t" + hitboxT);
		System.out.println((hitboxL - MyX) + "\t" + (hitboxT - MyY));
		Hitbox h = new Hitbox(attackType, hitboxL - MyX, hitboxR - MyX, hitboxT - MyY, hitboxB - MyY);//Hier muss man mal gucken ob das mit der addition �berhaubt n�tig ist -> NEIN!
		this.hitboxes[h.attacktype] = h;
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

	public int toInt(){
		int int_representation = 0;
		switch (this.enemy_state) {
		case "STAND":
			int_representation = 0;
			break;

		case "CROUCH":
			int_representation = 1;
			
			break;
		
		case "DOWN":
			int_representation = 2;
			break;
		case "AIR":
			int_representation = 3;
			break;
		default:
			int_representation = 0;
			System.out.println("Unrecognized enemy state" + this.enemy_state);
			break;
		}
		int_representation += hitbox1 ? (0x1 << 2) : 0;
		int_representation += hitbox2 ? (0x1 << 3) : 0;
		int_representation += hitbox3 ? (0x1 << 4) : 0;
		int_representation += hitbox4 ? (0x1 << 5) : 0;
		int_representation += hitbox5 ? (0x1 << 6) : 0;
		int_representation += hitbox6 ? (0x1 << 7) : 0;
		int_representation += hitbox7 ? (0x1 << 8) : 0;
		int_representation += hitbox8 ? (0x1 << 9) : 0;
		int_representation += close_to_enemy ? (0x1 << 10) : 0;
		int_representation += far_from_enemy ? (0x1 << 11) : 0;
		int_representation += wall_back ? (0x1 << 12) : 0;
		int_representation += wall_front ? (0x1 << 13) : 0;



		return int_representation;
	}
	
	public State(int int_representation) {
		switch (int_representation & 0x3) {
		case 0:
			enemy_state = "STAND";
			break;

		case 1:
			enemy_state = "CROUCH";
			break;
		
		case 2:
			enemy_state = "DOWN";
			break;
		
		case 3:
			enemy_state = "AIR";
			break;
		
		default:
			enemy_state = "fail";
			System.out.println("Unrecognized enemy state" + this.enemy_state);
			break;
		}
		hitboxes = new Hitbox[8];
		for (int i = 0; i < hitboxes.length; i++) {
			hitboxes[i] = new Hitbox(0,0,0,0,0);
		}
	}
}
