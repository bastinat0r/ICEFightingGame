
public class State {

	public int distance;
	public boolean wall_front;
	public boolean wall_back;
	public String enemy_state; /* stehen, vor, zur�ck, fern, nahkampf */
	
	public State(String enemyState,int MyY,int MyX,int enemyDistance){
		this.stateRefresh(enemyState, MyY, MyX, enemyDistance);
	}
	public void stateRefresh(String enemyState,int MyY,int MyX,int enemyDistance){
		
		distance = Math.min(255, enemyDistance);
		this.enemy_state = enemyState;
	
		
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
		int_representation += distance << 2;



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

		distance = int_representation >> 2;
	}
}
