package com.example.papercult;



import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

import bayaba.engine.lib.*;

public class BGViewMain
{
	public GL10 mGL = null; // OpenGL ��ü
	private Context MainContext;
	public GameInfo gInfo; // ���� ȯ�� ������ Ŭ���� : MainActivity�� ����� ���� ���� �޴´�.
	public float TouchX, TouchY;
    
	public Sprite heroSpr = new Sprite();
	public Sprite back = new Sprite();
	public GameObject heroObj = new GameObject();

	public BGViewMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.ghost);
		heroSpr.LoadSprite(mGL, MainContext, R.drawable.hero, "hero.spr");
		heroObj.SetObject(heroSpr, 0, 0, 400, 700, 0, 0);
	}
	
	public void PushButton( boolean flag )
	{
		
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		heroObj.AddFrameLoop(0.5f);
		heroObj.DrawSprite(gInfo);
	}
}
