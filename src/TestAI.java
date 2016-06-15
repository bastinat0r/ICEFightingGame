import gameInterface.AIInterface;
import structs.FrameData;
import structs.GameData;
import structs.Key;
import java.util.Random;


public class TestAI implements AIInterface {
	public Random rnd;
	private static Key[] action_keymap;
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		/* Datei Speichern */

	}

	@Override
	public String getCharacter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getInformation(FrameData arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int initialize(GameData arg0, boolean arg1) {
		// TODO Auto-generated method stub
		rnd = new Random();
		action_keymap = new Key[20];
		for(int i = 0; i<action_keymap.length; i++){
			action_keymap[i] = new Key();
			if(i%5 == 0 || i%5 == 1) {
				action_keymap[i].L = true;
			}
			if(i%5 == 1 || i%5 == 2 || i%5 == 3){
				action_keymap[i].D = true;
			}
			if(i%5 == 3 || i%5 == 4) {
				action_keymap[i].R = true;
			}
			if(i >=5 && i < 10) {
				action_keymap[i].A = true;
			}
			if(i >=10 && i < 15) {
				action_keymap[i].B = true;
			}
			if(i >=15 && i < 20) {
				action_keymap[i].C = true;
			}
		}
		/*
		action_keymap[0].L = true;
		action_keymap[1].L = true;
		action_keymap[1].D = true;
		action_keymap[2].D = true;
		action_keymap[3].R = true;
		action_keymap[3].D = true;
		action_keymap[4].R = true;
		*/
		/*
		 * TODO load file from disk
		 */
		return 0;
	}
	private int chose_action_from_state(State s) {
		float[] testvalues = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5.5f};
		float value_offset = 10f;
		return rnd.nextInt(20);
	}
	
	private State getStateFromGame(){
		return new State();
	}

	@Override
	public Key input() {
		int x = chose_action_from_state(getStateFromGame());


		return action_keymap[x];
	}

	@Override
	public void processing() {


	}

}
