import gameInterface.AIInterface;
import structs.FrameData;
import structs.GameData;
import structs.Key;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class TestAI implements AIInterface {
	public Random rnd;
	private static Key[] action_keymap;
	private LinkedList<StateAction> actionQueue; 
	private final int StateActionLength = 100;
	private State currentState;
	private int lastHPDiff;
	private int myHP;
	private int enemyHP;
	private boolean player;
	private GameData game;
	private FrameData frame;
	private float[] replaceme;
	private int[] replaceme_visits;
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		/* Datei Speichern */
		String s1 = "replaceme = new float[]{";
		for (int i = 0; i < replaceme.length; i++) {
			s1 = s1 + replaceme[i] + "f, ";	
		}
		System.out.println(s1 + "};");
		
		
		s1 = "replaceme_visits = new int[]{";
		for (int i = 0; i < replaceme_visits.length; i++) {
			s1 = s1 + replaceme_visits[i] + ", ";	
		}
		System.out.println(s1 + "};");
		
		s1 = "";
		for (int i = 0; i < 20; i++) {
			s1 = s1 +" m " + i +": ";
			if(replaceme_visits[i] == 0) {
				s1 = s1 + "0, ";
			} else {
				s1 = s1 + replaceme[i] / replaceme_visits[i];
			}
		}
		System.out.println(s1);
	}

	@Override
	public String getCharacter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getInformation(FrameData arg0) {
		// TODO Auto-generated method stub
		frame = arg0;
	}

	@Override
	public int initialize(GameData arg0, boolean arg1) {
		// TODO Auto-generated method stub
		System.out.println("Init");
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
		player  = arg1;
		game = arg0;
		frame = new FrameData();
		replaceme = new float[]{66.967155f, -56.12577f, 75.63395f, -27.786104f, 91.81734f, -55.807957f, 119.173965f, 305.30548f, 38.960567f, 55.048256f, -25.1469f, 208.79381f, 552.97925f, 72.88812f, 9.529953f, 232.4335f, 3.6648722f, 68.93345f, 221.60529f, -1.3583927f};
		replaceme_visits = new int[]{1105, 1108, 1137, 1096, 1127, 1091, 1126, 1154, 1104, 1081, 1107, 1110, 1121, 1097, 1071, 1057, 1128, 1132, 1049, 1139};

		actionQueue = new LinkedList<StateAction>();
		lastHPDiff = 0;
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
	
	private ArrayList<Integer> maxIndex(float[] values) {
		ArrayList<Integer> maxIndex = new ArrayList<Integer>();
		float maxValue = Float.NEGATIVE_INFINITY;
		for (int i = 0; i < values.length; i++) {
			if(values[i] > maxValue){
				maxValue = values[i];
				maxIndex = new ArrayList<Integer>();
			}
			if(values[i] == maxValue){
				maxIndex.add(i);
			}
		}
		return maxIndex;
	}
	
	private int chose_action_from_state(State s) {
		
		float[] testvalues = new float[20]; /* mockup for rewards of each action */
		for (int i = 0; i < testvalues.length; i++) {
			if(replaceme_visits[i] == 0) {
				testvalues[i] = 0;
			} else {
				testvalues[i] = replaceme[i] / replaceme_visits[i];
			}
		}
		
		float exploration_propability = 0.05f;
		if (rnd.nextDouble() > exploration_propability) {
			ArrayList<Integer> max = maxIndex(testvalues);
			max.get(rnd.nextInt(max.size())); /* return randomly selected action from actions with best value */
		}
		return rnd.nextInt(20); // return random action
	}
	
	private State getStateFromGame(){
		return new State();
	}

	private int insertStateIntoActionQueue(StateAction s){
		actionQueue.add(s);
		if(actionQueue.size() > StateActionLength) {
			StateAction s_fin = actionQueue.remove();
			replaceme[s_fin.action] += s_fin.reward;
			replaceme_visits[s_fin.action]++;
			/*TODO add s_fin to knowledge base -> not only mockup function (replaceme)*/
			
			
		}
		
		return actionQueue.size();
	}
	
	private void addRewards() {
		float hpDiff = (float)(myHP - enemyHP - lastHPDiff);
		
		if(hpDiff != 0) {
			if(myHP == 0 && enemyHP == 0) {
				return;
			}
			hpDiff /= 100;
			for (Iterator iterator = actionQueue.iterator(); iterator.hasNext();) {
				StateAction stateAction = (StateAction) iterator.next();
				stateAction.reward += hpDiff;
				hpDiff *= 1.05f;
			}
		}
		lastHPDiff = myHP - enemyHP;
	}
	@Override
	public Key input() {
		currentState = getStateFromGame();
		int x = chose_action_from_state(currentState);
		StateAction s = new StateAction(currentState, x);
		insertStateIntoActionQueue(s);
		return action_keymap[x];
	}

	@Override
	public void processing() {
		if(!frame.getEmptyFlag() && frame.getRemainingTime() > 0) {
			myHP = frame.getMyCharacter(player).getHp();
			enemyHP = frame.getOpponentCharacter(player).getHp();
			addRewards();
		}
	}

}
