package paper.sfx;

import java.io.IOException;

import paper.data.GameOption;

import com.example.papercult.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

public class Music {
	private static Music instance = null;
	
	MediaPlayer stageMusicPlayer;
	MediaPlayer createMusicPlayer;
	boolean ready = false;
	
	private Music(final Context context){
		stageMusicPlayer = MediaPlayer.create(context, R.raw.stagemusic);
		createMusicPlayer = MediaPlayer.create(context, R.raw.createmusic);
		
		
	}
	public static void init(){
		instance = null;
	}
	public static void create(Context context){
		if(instance == null)
			instance = new Music(context);
	}
	
	public static void stageMusicStart(Context context){
		if(GameOption.getInstance().musicPlay == false)
			return;
		if(instance == null)
			instance = new Music(context);
		instance.stageMusicPlayer.start();
	}

	public static void stageMusicPause(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.stageMusicPlayer.seekTo(0);
		instance.stageMusicPlayer.pause();
	}
	
	public static void createMusicStart(Context context){
		if(GameOption.getInstance().musicPlay == false)
			return;
		if(instance == null)
			instance = new Music(context);
		instance.createMusicPlayer.start();
	}
	public static void createMusicPause(Context context){
		if(instance == null)
			instance = new Music(context);
		instance.createMusicPlayer.seekTo(0);
		instance.createMusicPlayer.pause();
	}
	
	public static void releaseMusic(){
		if(instance != null){
			instance.stageMusicPlayer.release();
			instance.createMusicPlayer.release();
		}
	}
}
