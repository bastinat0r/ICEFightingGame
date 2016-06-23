
public class StateAction extends State {
	public int action;
	public float reward;
	public int count;
	public StateAction(State s, int a){
		hitbox1 = s.hitbox1; /*a*/
		hitbox2 = s.hitbox2; /*b*/
		hitbox3 = s.hitbox3; /*ra*/
		hitbox4 = s.hitbox4; /*a*/
		hitbox5 = s.hitbox5; /*b*/
		hitbox6 = s.hitbox6; /*ra*/
		hitbox7 = s.hitbox7; /*a*/
		hitbox8 = s.hitbox8; /*b*/
		
		close_to_enemy = s.close_to_enemy;
		far_from_enemy = s.far_from_enemy;
		wall_front = s.wall_front;
		wall_back = s.wall_back;
		enemy_state = s.enemy_state;
		action = a;
		reward = 0.0f;
		count = 0;
	}
}
