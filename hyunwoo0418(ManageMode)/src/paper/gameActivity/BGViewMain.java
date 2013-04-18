package paper.gameActivity;



import javax.microedition.khronos.opengles.GL10;

import com.example.papercult.R;
import com.example.papercult.R.drawable;

import android.content.Context;

import bayaba.engine.lib.*;

public class BGViewMain
{
	public GL10 mGL = null; // OpenGL ��ü
	private Context MainContext;
	public GameInfo gInfo; // ���� ȯ�� ������ Ŭ���� : MainActivity�� ����� ���� ��� �޴´�.
	public float TouchX, TouchY;
    
	public Sprite back = new Sprite();
	Sprite sl = new Sprite();
	Sprite b = new Sprite();
	GameObject slime = new GameObject(); 
	GameObject bt = new GameObject();
	GameObject sp = new GameObject();

	public BGViewMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.bg);
		sl.LoadSprite(mGL, MainContext, "slime.spr");
		slime.SetObject(sl, 0, 0, 50, 430, 0, 0);
		b.LoadSprite(mGL, MainContext, "aaa.spr");
		bt.SetObject(b, 0, 0, 50, 250, 0, 0);
	}
	
	public void quake(long time, float x, float y)
	{
		gInfo.SetQuake(time, x, y);
	}
	
	public void DoGame()
	{
	
		back.PutImage(gInfo, 0, 0);
		slime.DrawSprite(gInfo);
		bt.DrawSprite(gInfo);
		gInfo.DoQuake();
		
	}
}
