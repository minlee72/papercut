package paper.startActivity;

import javax.microedition.khronos.opengles.GL10;

import paper.cstageSelectActivity.CStageSelectActivity;
import paper.data.GameMain;
import paper.data.GameOption;
import paper.sfx.Sound;
import paper.sfx.Vibe;
import paper.stageSelectActivity.StageSelectActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.ListView;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

import com.example.papercult.R;


public class STViewMain implements GameMain
{
	float ttSpd;
	float gbSpd;
	float cbSpd;
	public GL10 mGL = null; // OpenGL 객체
	public ListView lv;
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	Vibrator vibe;
	
	Sprite back = new Sprite();
	Sprite titletext = new Sprite();
	Sprite titlegsbtn = new Sprite();
	Sprite titlecsbtn = new Sprite();
	Sprite soundbtn = new Sprite();
	Sprite vibebtn = new Sprite();
	Sprite musicbtn = new Sprite();
	
	GameObject titletextObj = new GameObject();
	GameObject titlegsbtnObj = new GameObject();
	GameObject titlecsbtnObj = new GameObject();
	GameObject soundbtnObj = new GameObject();
	GameObject vibebtnObj = new GameObject();
	GameObject musicbtnObj = new GameObject();
	
	enum ttState {start, gstart, stop};
	ttState tt_state = ttState.start;
	
	enum gbState {start, gstart, stop};
	gbState gb_state = gbState.start;
	
	enum cbState {start, gstart};
	cbState cb_state = cbState.gstart;
	
	public STViewMain( Context context, GameInfo info)
	{
		MainContext = context;
		gInfo = info;
		vibe = (Vibrator)MainContext.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.startback);
		
		titletext.LoadSprite(mGL, MainContext, R.drawable.titletext, "titletext.spr");
		titletextObj.SetObject(titletext, 0, 0, 400, -50, 0, 0);
		
		titlegsbtn.LoadSprite(mGL, MainContext, R.drawable.titlegsbtn, "titlegsbtn.spr");
		titlegsbtnObj.SetObject(titlegsbtn, 0, 0, -150, 280, 0, 0);
		
		titlecsbtn.LoadSprite(mGL, MainContext, R.drawable.titlecsbtn, "titlecsbtn.spr");
		titlecsbtnObj.SetObject(titlecsbtn, 0, 0, -150, 380, 0, 0);
		
		soundbtn.LoadSprite(mGL, MainContext, R.drawable.soundonoff, "soundonoff.spr");
		soundbtnObj.SetObject(soundbtn, 0, 0, 600, 440, 0, 0);
		soundbtnObj.SetZoom(gInfo, 1.3f, 1.3f);
		
		vibebtn.LoadSprite(mGL, MainContext, R.drawable.vibeonoff, "vibeonoff.spr");
		vibebtnObj.SetObject(vibebtn, 0, 0, 675, 440, 0, 0);
		vibebtnObj.SetZoom(gInfo, 1.3f, 1.3f);
		
		musicbtn.LoadSprite(mGL, MainContext, R.drawable.musiconoff, "musiconoff.spr");
		musicbtnObj.SetObject(musicbtn, 0, 0, 750, 440, 0, 0);
		musicbtnObj.SetZoom(gInfo, 1.3f, 1.3f);
	}

	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		updateTitleText();
		updateTitleGsBtn();
		updateTitleCsBtn();
		
		titletextObj.DrawSprite(gInfo);
		titlegsbtnObj.DrawSprite(gInfo);
		titlecsbtnObj.DrawSprite(gInfo);
		
		soundbtnObj.DrawSprite(gInfo);
		vibebtnObj.DrawSprite(gInfo);
		musicbtnObj.DrawSprite(gInfo);
	}
	
	public void updateTitleText()
	{
		if((titletextObj.y == -50))
			ttSpd = 10;
		else
			ttSpd = ttSpd + 1.0f;
		
		if(tt_state == ttState.start){
			if(titletextObj.y < 100)
				titletextObj.y = titletextObj.y + (100/ttSpd);
			else
				titletextObj.y = 100;
		}
	}
	public void updateTitleGsBtn()
	{
		if(gb_state == gbState.start){
			if(titlegsbtnObj.x <= -150)
				gbSpd = 20;
			else
				gbSpd = gbSpd + 1.0f;
			
			if(titlegsbtnObj.x < 400)
				titlegsbtnObj.x = titlegsbtnObj.x + (400/gbSpd);
			else
				titlegsbtnObj.x = 400;
			
			if((cb_state == cbState.gstart)&&(titlegsbtnObj.x > 200))
				cb_state = cbState.start;
		}
	}
	public void updateTitleCsBtn(){
		if(cb_state == cbState.start){
			if(titlecsbtnObj.x <= -150)
				cbSpd = 20;
			else
				cbSpd = cbSpd + 1.0f;
			
			if(titlecsbtnObj.x < 400)
				titlecsbtnObj.x = titlecsbtnObj.x + (400/cbSpd);
			else
				titlecsbtnObj.x = 400;
		}
	}
	
	public void actionDown()
	{
		if(titlegsbtnObj.CheckPos((int)TouchX, (int)TouchY)){
			titlegsbtnObj.motion = 1;
			Vibe.play(MainContext);;
		    Sound.playDripSound(MainContext);
			Intent intent = new Intent(MainContext, StageSelectActivity.class);
			MainContext.startActivity(intent);
		}
		else if(titlecsbtnObj.CheckPos((int)TouchX, (int)TouchY)){
			titlecsbtnObj.motion = 1;
			Vibe.play(MainContext);;
			Sound.playDripSound(MainContext);
			Intent intent = new Intent(MainContext, CStageSelectActivity.class);
			MainContext.startActivity(intent);
		}
		else if(soundbtnObj.CheckPos((int)TouchX, (int)TouchY)){
			if(soundbtnObj.motion == 0){
				soundbtnObj.motion = 1;
				GameOption.getInstance().soundVolume = 0;
				Sound.playDripSound(MainContext);
				Vibe.play(MainContext);
			}
			else{
				soundbtnObj.motion = 0;
				GameOption.getInstance().soundVolume = 30;
				Sound.playDripSound(MainContext);
				Vibe.play(MainContext);
			}
		}
		else if(vibebtnObj.CheckPos((int)TouchX, (int)TouchY)){
			if(vibebtnObj.motion == 0){
				vibebtnObj.motion = 1;
				GameOption.getInstance().vibePower = 0;
				Sound.playDripSound(MainContext);
				Vibe.play(MainContext);
			}
			else{
				vibebtnObj.motion = 0;
				GameOption.getInstance().vibePower = 30;
				Sound.playDripSound(MainContext);
				Vibe.play(MainContext);
			}
		}
		else if(musicbtnObj.CheckPos((int)TouchX, (int)TouchY)){
			if(musicbtnObj.motion == 0){
				musicbtnObj.motion = 1;
				GameOption.getInstance().musicPlay = false;
				Sound.playDripSound(MainContext);
				Vibe.play(MainContext);
			}
			else{
				musicbtnObj.motion = 0;
				GameOption.getInstance().musicPlay = true;
				Sound.playDripSound(MainContext);
				Vibe.play(MainContext);
			}
		}
	}
	public void actionUp()
	{
		titlegsbtnObj.motion = 0;
		titlecsbtnObj.motion = 0;
	}
	public void startScr()
	{
		titletextObj.y = -50;
		titlegsbtnObj.x = -150;
		titlecsbtnObj.x = -150;
		cb_state = cbState.gstart;
	}

	@Override
	public void setGl(GL10 gl) {
		// TODO Auto-generated method stub
		mGL = gl;
	}

	@Override
	public GameInfo getGInfo() {
		// TODO Auto-generated method stub
		return gInfo;
	}
}