package paper.sfx;

import com.example.papercult.R;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	private static Music instance = null;
	
	MediaPlayer musicPlayer;
	
	private Music(Context context){
		musicPlayer = MediaPlayer.create(context, R.raw.bgm);
		musicPlayer.setLooping(true);
	}
	
	public static void start(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.musicPlayer.start();
	}
	
	public static void stop(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.musicPlayer.stop();
	}
	
	public static void pause(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.musicPlayer.pause();
	}
}
