package paper.sfx;

import paper.data.GameOption;

import com.example.papercult.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {
	private static Sound instance = null;
	
	Context con;
	SoundPool sound;
	int dripSound;
	int paperSound;
	int clearSound;
	int failSound;
	int delSound;
	int toastSound;
	
	private Sound(Context context){
		con = context;
		sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		dripSound = sound.load(con, R.raw.drip, 1);
		paperSound = sound.load(con, R.raw.paper, 1);
		clearSound = sound.load(con, R.raw.clearsound, 1);
		failSound = sound.load(con, R.raw.failsound, 1);
		delSound = sound.load(con, R.raw.dell, 1);
		toastSound = sound.load(con, R.raw.report, 1);
	}
	
	public static void playDripSound(Context context){
		if(instance == null)
			instance = new Sound(context);
		float vol = GameOption.getInstance().soundVolume;
		instance.sound.play(instance.dripSound, vol, vol, 0, 0, 1);
	}
	
	public static void playPaperSound(Context context){
		if(instance == null)
			instance = new Sound(context);
		float vol = GameOption.getInstance().soundVolume;
		vol = setVol(vol, -0.2f);
		instance.sound.play(instance.paperSound, vol, vol, 0, 0, 1);
	}
	
	public static void playClearSound(Context context){
		if(instance == null)
			instance = new Sound(context);
		float vol = GameOption.getInstance().soundVolume;
		vol = setVol(vol, -0.2f);
		instance.sound.play(instance.clearSound, vol, vol, 0, 0, 1);
	}
	
	public static void playFailSound(Context context){
		if(instance == null)
			instance = new Sound(context);
		float vol = GameOption.getInstance().soundVolume;
		vol = setVol(vol, -0.2f);
		instance.sound.play(instance.failSound, vol, vol, 0, 0, 1);
	}
	
	public static void playDellSound(Context context){
		if(instance == null)
			instance = new Sound(context);
		float vol = GameOption.getInstance().soundVolume;
		instance.sound.play(instance.delSound, vol, vol, 0, 0, 1);
	}
	
	public static void playToastSound(Context context){
		if(instance == null)
			instance = new Sound(context);
		float vol = GameOption.getInstance().soundVolume;
		instance.sound.play(instance.toastSound, vol, vol, 0, 0, 1);
	}
	
	private static float setVol(float base, float change){
		float result = base;
		result = result + change;
		if(result < 0)
			result = 0;
		return result;
	}
}
