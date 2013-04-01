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
	public GameInfo gInfo; // ���� ȯ�� ������ Ŭ���� : MainActivity�� ����� ���� ���� �޴´�.
	public float TouchX, TouchY;
    
	public Sprite back = new Sprite();

	public BGViewMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.bg);
		
	}
	
	public void PushButton( boolean flag )
	{
		
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		
		
	}
}
