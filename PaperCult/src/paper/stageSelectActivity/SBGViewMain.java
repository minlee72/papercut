package paper.stageSelectActivity;

import javax.microedition.khronos.opengles.GL10;
import com.example.papercult.R;
import android.content.Context;

import bayaba.engine.lib.*;

public class SBGViewMain
{
	public GL10 mGL = null; // OpenGL 객체
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
    
	public Sprite back = new Sprite();
	Sprite sl = new Sprite();
	GameObject slime = new GameObject(); 
	
	public SBGViewMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.stageback);
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		gInfo.DoQuake();
		
	}
}
