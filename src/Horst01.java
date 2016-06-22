import gameInterface.AIInterface;
import structs.CharacterData;
import structs.FrameData;
import structs.GameData;
import structs.Key;
import commandcenter.CommandCenter;
import fighting.Attack;

public class Horst01 implements AIInterface {
	
	Key inputKey;
	boolean playerNumber;
	FrameData frameData;
	CommandCenter cc;
	State state;
	CharacterData me;
	Attack a;
	

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getCharacter() {
		this.me = this.cc.getMyCharacter();// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getInformation(FrameData arg0) {
		this.frameData = frameData;
		cc.setFrameData(this.frameData, playerNumber);
		
		
		

	}

	@Override
	public int initialize(GameData arg0, boolean arg1) {
		this.playerNumber = playerNumber;
		this.inputKey = new Key();
		cc = new CommandCenter();
		frameData = new FrameData();
		this.state = new State(this.cc.getEnemyCharacter().getState().toString(),cc.getMyY(),cc.getMyX(),cc.getDistanceX());
		return 0;
	}

	@Override
	public Key input() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processing() {
		this.state.recordNewHitbox(me.attack.getAttackType(),cc.getMyY(),cc.getMyX(),me.getAttack().getHitAreaNow().getL(),me.getAttack().getHitAreaNow().getR(),me.getAttack().getHitAreaNow().getB(),me.getAttack().getHitAreaNow().getT());
		this.state.stateRefresh(this.cc.getEnemyCharacter().getState().toString(),cc.getMyY(),cc.getMyX(),cc.getDistanceX());
		
	}

}
