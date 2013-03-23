package com.example.papercult;



import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import bayaba.engine.lib.*;

public class GameMain
{
	public GL10 mGL = null; // OpenGL 객체
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	public int CurLayer = 0;
	public ButtonObject Button[] = new ButtonObject [100];
    
	public Sprite heroSpr = new Sprite();
	public GameObject heroObj = new GameObject();

	public GameMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
		
		for ( int i = 0; i < Button.length; i++ ) Button[i] = new ButtonObject();
	}

	public void LoadGameData()
	{
		heroSpr.LoadSprite(mGL, MainContext, R.drawable.hero, "hero.spr");
		heroObj.SetObject(heroSpr, 0, 0, 400, 700, 0, 0);
	}
	
	public void PushButton( boolean flag )
	{
		
	}
	
	public void DoGame()
	{
		heroObj.AddFrameLoop(0.5f);
		heroObj.DrawSprite(gInfo);
	}
}
