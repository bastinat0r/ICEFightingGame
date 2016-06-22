
public class Hitbox {

	public int attacktype;
	public int left;
	public int right;
	public int top;
	public int bottom;
	public int damage;//?? würde sinn machen das gleich für später mit abzuspeichern
	// Mann könnte dann überlegen ob man nicht vielleicht auch im State eine greedy Funktion mit einbaut die die Attacke mit dem meisten Schaden zurückliefert
	// Andere Idee wäre unsere 8 Hitboxen mit den herrausgefundenen zu belegen die den meisten Schaden liefern und dann nur darüber auszusuchen--> erpart das durchwandern des Arrays
		public Hitbox(int attackType2, int L, int R, int T, int B) {
		this.attacktype = attackType2;
		this.left = L;
		this.right = R;
		this.top = T;
		this.bottom = B;
	}
}
