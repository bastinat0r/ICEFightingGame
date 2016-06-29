import gameInterface.AIInterface;
import structs.CharacterData;
import structs.FrameData;
import structs.GameData;
import structs.Key;
import java.util.Random;

import commandcenter.CommandCenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class TestAI implements AIInterface {
	public Random rnd;
	private static Key[] action_keymap;
	private static Key[] action_keymap_reversed;
	private LinkedList<StateAction> actionQueue;
	private final int StateActionLength = 100;
	private State currentState;
	private int lastHPDiff;
	private int myHP;
	private int enemyHP;
	private boolean player;
	private GameData game;
	private FrameData frame;
	private MatrixValue[][] reward_matrix;
	private CommandCenter cc;

	@Override
	public void close() {
		// TODO Auto-generated method stub
		/* Datei Speichern */
		try {
			ReadSaveData.saveMatrixData(game.getMyName(player), game.getOpponentName(player), reward_matrix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		cc.setFrameData(frame, player);
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
		action_keymap_reversed = new Key[20];
		for(int i = 0; i<action_keymap_reversed.length; i++){
			action_keymap_reversed[i] = new Key();
			if(i%5 == 0 || i%5 == 1) {
				action_keymap_reversed[i].R = true;
			}
			if(i%5 == 1 || i%5 == 2 || i%5 == 3){
				action_keymap_reversed[i].D = true;
			}
			if(i%5 == 3 || i%5 == 4) {
				action_keymap_reversed[i].L = true;
			}
			if(i >=5 && i < 10) {
				action_keymap_reversed[i].A = true;
			}
			if(i >=10 && i < 15) {
				action_keymap_reversed[i].B = true;
			}
			if(i >=15 && i < 20) {
				action_keymap_reversed[i].C = true;
			}
		}
		player  = arg1;
		game = arg0;
		frame = new FrameData();

		actionQueue = new LinkedList<StateAction>();
		lastHPDiff = 0;
		
		
		reward_matrix = ReadSaveData.readMatrixData(game.getMyName(player), game.getOpponentName(player));
		cc = new CommandCenter();
		currentState = new State(0);
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

		float[] rewards = new float[20]; /* rewards of each action */

		for (int i = 0; i < rewards.length; i++) {
			if(reward_matrix == null) {
				System.err.println("Reward Matrix not initialized" );
			}
			if(reward_matrix[s.toInt()] == null) {
				System.err.println("Reward Matrix [" + s.toInt() + "] not initialized" );
			}
			if(reward_matrix[s.toInt()][i].getVisitors() == 0) {
				rewards[i] = 0;
			} else {
				rewards[i] = reward_matrix[s.toInt()][i].getReward() / reward_matrix[s.toInt()][i].getVisitors();
			}
		}


		float exploration_propability = 0.05f;
		if (rnd.nextDouble() > exploration_propability) {
			ArrayList<Integer> max = maxIndex(rewards);
			max.get(rnd.nextInt(max.size())); /* return randomly selected action from actions with best value */
		}

		return rnd.nextInt(20); // return random action
	}

	private int insertStateIntoActionQueue(StateAction s){
		actionQueue.add(s);
		if(actionQueue.size() > StateActionLength) {
			StateAction s_fin = actionQueue.remove();
			MatrixValue v = reward_matrix[s_fin.toInt()][s_fin.action];
			v.setReward(v.getReward() + s_fin.reward);
			v.setVisitors(v.getVisitors() + 1);
			/*TODO add s_fin to knowledge base -> not only mockup function (replaceme)*/
			reward_matrix[s_fin.toInt()][s_fin.action] = v;
			switch (v.getVisitors()) {
			case 1:
				System.out.println(s_fin.toInt() + " : " + s_fin.action + " : " + s_fin.reward);
				break;

			case 10:
				System.out.println("x" + s_fin.toInt() + " : " + s_fin.action + " : " + s_fin.reward);
			case 100:
				System.out.println("y" + s_fin.toInt() + " : " + s_fin.action + " : " + s_fin.reward);
			default:
				break;
			}
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
			for (Iterator<StateAction> iterator = actionQueue.iterator(); iterator.hasNext();) {
				StateAction stateAction = (StateAction) iterator.next();
				stateAction.reward += hpDiff;
				hpDiff *= 1.05f;
			}
		}
		lastHPDiff = myHP - enemyHP;
	}
	@Override
	public Key input() {
		int x = chose_action_from_state(currentState);
		StateAction s = new StateAction(currentState.toInt(), x);
		insertStateIntoActionQueue(s);
		try {
			return cc.getMyX() < cc.getEnemyX() ? action_keymap[x] : action_keymap_reversed[x]; // reverse Keymap if enemy character is to our left
		} catch (NullPointerException e) {
			System.out.println("CC not initialized");
			return action_keymap[x];
		}
	}

	@Override
	public void processing() {
		if(!frame.getEmptyFlag() && frame.getRemainingTime() > 0) {
			CharacterData me = this.cc.getMyCharacter();
			
			this.currentState.recordNewHitbox(me.attack.getAttackType(),cc.getMyY(),cc.getMyX(),me.getAttack().getHitAreaNow().getL(),me.getAttack().getHitAreaNow().getR(),me.getAttack().getHitAreaNow().getB(),me.getAttack().getHitAreaNow().getT());
			this.currentState.stateRefresh(this.cc.getEnemyCharacter().getState().toString(),cc.getMyY(),cc.getMyX(),cc.getDistanceX());

			myHP = frame.getMyCharacter(player).getHp();
			enemyHP = frame.getOpponentCharacter(player).getHp();
			addRewards();
		}
	}

}
