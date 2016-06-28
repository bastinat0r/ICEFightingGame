
public class MatrixValue {

	private int visitors; //Number of calculations
	private float reward;  //Calculated Reward
	
	public MatrixValue (float reward, int visitors){
		setReward(reward);
		setVisitors(visitors);
	}

	public int getVisitors() {
		return visitors;
	}

	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}

	public float getReward() {
		return reward;
	}

	public void setReward(float reward) {
		this.reward = reward;
	}
}
