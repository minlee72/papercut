package paper.gameActivity;



import javax.microedition.khronos.opengles.GL10;

import com.example.papercult.R;
import com.example.papercult.R.drawable;

import android.content.Context;

import bayaba.engine.lib.*;

public class BGViewMain
{
	GL10 mGL = null; // OpenGL 객체
	Context MainContext;
	GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	float TouchX, TouchY;
    
	Sprite back = new Sprite();
	Sprite bomb = new Sprite();
	Sprite redraw = new Sprite();
	
	GameObject bombObj = new GameObject();
	GameObject redrawObj = new GameObject();
	

	public BGViewMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.stageback);
		redraw.LoadSprite(mGL, MainContext, R.drawable.redraw, "redraw.spr");
		bomb.LoadSprite(mGL, MainContext, R.drawable.bomb, "bomb.spr");
		
		bombObj.SetObject(bomb, 0, 0, 80, 400, 0, 0);
		bombObj.Zoom(gInfo, 1.2f, 1.2f);
		
		redrawObj.SetObject(redraw, 0, 0, 710, 380, 0, 0);
		redrawObj.Zoom(gInfo, 1.2f, 1.2f);
	}
	
	public void quake(long time, float x, float y)
	{
		
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		bombObj.AddFrameLoop(0.1f);
		bombObj.DrawSprite(gInfo);
		redrawObj.DrawSprite(gInfo);
	}
}
