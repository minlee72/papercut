package paper.sfx;

import com.example.papercult.R;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	private static Music instance = null;
	
	MediaPlayer titleMusicPlayer;
	MediaPlayer stageMusicPlayer;
	MediaPlayer createMusicPlayer;
	
	private Music(Context context){
		titleMusicPlayer = MediaPlayer.create(context, R.raw.titlemusic);
		titleMusicPlayer.setLooping(true);
		
		stageMusicPlayer = MediaPlayer.create(context, R.raw.stagemusic);
		stageMusicPlayer.setLooping(true);
		
		createMusicPlayer = MediaPlayer.create(context, R.raw.createmusic);
		createMusicPlayer.setLooping(true);
	}
	
	public static void titleMusicStart(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.titleMusicPlayer.start();
	}
	
	public static void titleMusicStop(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.titleMusicPlayer.stop();
	}
	
	public static void titleMusicPause(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.titleMusicPlayer.pause();
	}
	
	public static void stageMusicStart(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.stageMusicPlayer.start();
	}
	
	public static void stageMusicStop(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.stageMusicPlayer.stop();
	}
	
	public static void stageMusicPause(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.stageMusicPlayer.pause();
	}
	
	public static void createMusicStart(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.createMusicPlayer.start();
	}
	
	public static void createMusicStop(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.createMusicPlayer.stop();
	}
	
	public static void createMusicPause(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.createMusicPlayer.pause();
	}
	
	public static void releaseMusic(){
		if(instance != null){
			instance.titleMusicPlayer.release();
			instance.stageMusicPlayer.release();
			instance.createMusicPlayer.release();
		}
	}
}
