package paper.sfx;

import paper.data.GameOption;

import com.example.papercult.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
	private static Sound instance = null;
	
	Context con;
	SoundPool btnClick;
	int dripSound;
	int paperSound;
	
	private Sound(Context context){
		con = context;
		btnClick = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		dripSound = btnClick.load(con, R.raw.drip, 1);
		paperSound = btnClick.load(con, R.raw.paper, 1);
	}
	
	public static void playDripSound(Context context){
		if(instance == null)
			instance = new Sound(context);
		float vol = GameOption.getInstance().soundVolume;
		instance.btnClick.play(instance.dripSound, vol, vol, 0, 0, 1);
	}
	
	public static void playPaperSound(Context context){
		if(instance == null)
			instance = new Sound(context);
		float vol = GameOption.getInstance().soundVolume;
		vol = setVol(vol, -0.2f);
		instance.btnClick.play(instance.paperSound, vol, vol, 0, 0, 1);
	}
	
	private static float setVol(float base, float change){
		float result = base;
		result = result + change;
		if(result < 0)
			result = 0;
		return result;
	}
}
