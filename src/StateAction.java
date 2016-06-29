
public class StateAction extends State {
	public int action;
	public float reward;
	public int count;
	
	
	public StateAction(int int_representation, int a) {
		super(int_representation);
		action = a;
		reward = 0;
		count = 0;
	}
	
	public StateAction(int int_representation, int a, float reward, int count) {
		super(int_representation);
		action = a;
		this.reward = reward;
		this.count = count;
	}
}
