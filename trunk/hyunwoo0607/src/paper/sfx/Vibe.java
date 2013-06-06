package paper.sfx;

import paper.data.GameOption;
import android.content.Context;
import android.os.Vibrator;

public class Vibe {
	private static Vibe instnace = null;
	
	Vibrator vibe;
	
	private Vibe(Context con){
		vibe = (Vibrator)con.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	public static void play(Context con){
		if (instnace == null)
			instnace = new Vibe(con);
		instnace.vibe.vibrate(GameOption.getInstance().vibePower);
	}
	
}
