
public class Hitbox {

	public int attacktype;
	public int left;
	public int right;
	public int top;
	public int bottom;
	public int damage;//?? w�rde sinn machen das gleich f�r sp�ter mit abzuspeichern
	// Mann k�nnte dann �berlegen ob man nicht vielleicht auch im State eine greedy Funktion mit einbaut die die Attacke mit dem meisten Schaden zur�ckliefert
	// Andere Idee w�re unsere 8 Hitboxen mit den herrausgefundenen zu belegen die den meisten Schaden liefern und dann nur dar�ber auszusuchen--> erpart das durchwandern des Arrays
		public Hitbox(int attackType2, int L, int R, int T, int B) {
		this.attacktype = attackType2;
		this.left = L;
		this.right = R;
		this.top = T;
		this.bottom = B;
	}
}
