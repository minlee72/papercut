package paper.gameActivity;



import javax.microedition.khronos.opengles.GL10;

import com.example.papercult.R;
import com.example.papercult.R.drawable;

import android.content.Context;

import bayaba.engine.lib.*;

public class FGViewMain
{
	public GL10 mGL = null; // OpenGL ��ü
	private Context MainContext;
	public GameInfo gInfo; // ���� ȯ�� ������ Ŭ���� : MainActivity�� ����� ���� ���� �޴´�.
    
	boolean test = false;
	
	public Sprite heroSpr = new Sprite();
	public GameObject heroObj = new GameObject();
	
	public FGViewMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		heroSpr.LoadSprite(mGL, MainContext, R.drawable.aaa, "aaa.spr");
		heroObj.SetObject(heroSpr, 0, 0, 300, 300, 0, 0);
	}
	
	
	public void DoGame()
	{
		if (test == true){
			heroObj.AddFrameLoop(0.5f);
			heroObj.DrawSprite(gInfo);
		}
	}
}