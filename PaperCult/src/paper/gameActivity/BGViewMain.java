package paper.gameActivity;



import javax.microedition.khronos.opengles.GL10;

import com.example.papercult.R;


import android.content.Context;
import android.widget.Toast;

import bayaba.engine.lib.*;

public class BGViewMain
{
	GL10 mGL = null; // OpenGL 객체
	Context MainContext;
	GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	PaperView pv;
    
	Sprite back = new Sprite();
	Sprite num = new Sprite();
	Sprite redraw = new Sprite();
	
	GameObject numObj = new GameObject();
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
		num.LoadSprite(mGL, MainContext, R.drawable.num, "num.spr");
		
		numObj.SetObject(num, 0, 0, 70, 400, 0, 0);
		redrawObj.SetObject(redraw, 0, 0, 720, 400, 0, 0);
		
		numObj.SetZoom(gInfo, 1.2f, 1.2f);
	}
	
	public boolean checkBtn(float inputX, float inputY)
	{
		float x = inputX * gInfo.ScalePx;
		float y = inputY * gInfo.ScalePy;
		return redrawObj.CheckPos((int)x, (int)y);
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		numObj.motion = pv.sObj.current - 1;
		numObj.DrawSprite(gInfo);
		redrawObj.DrawSprite(gInfo);
	}
}
