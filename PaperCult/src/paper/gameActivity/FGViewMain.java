package paper.gameActivity;



import javax.microedition.khronos.opengles.GL10;

import com.example.papercult.R;
import com.example.papercult.R.drawable;

import android.content.Context;

import bayaba.engine.lib.*;

public class FGViewMain
{
	public GL10 mGL = null; // OpenGL 객체
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	
	Sprite sp = new Sprite();
	GameObject ob = new GameObject();
	
	public FGViewMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		sp.LoadSprite(mGL, MainContext, R.drawable.redraw, "redraw.spr");
		ob.SetObject(sp, 0, 0, 300, 300, 0, 0);
	}
	
	
	public void DoGame()
	{
		ob.AddFrameLoop(0.5f);
		ob.DrawSprite(gInfo);
	}
}