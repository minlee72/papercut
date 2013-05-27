package paper.startActivity;

import javax.microedition.khronos.opengles.GL10;

import paper.cstageSelectActivity.CStageSelectActivity;
import paper.stageSelectActivity.StageSelectActivity;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

import com.example.papercult.R;


public class STViewMain
{
	float scrSpd = 20;
	public GL10 mGL = null; // OpenGL 객체
	public ListView lv;
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	
	Sprite back = new Sprite();
	Sprite titletext = new Sprite();
	Sprite titlegsbtn = new Sprite();
	Sprite titlecsbtn = new Sprite();
	
	GameObject titletextObj = new GameObject();
	GameObject titlegsbtnObj = new GameObject();
	GameObject titlecsbtnObj = new GameObject();
	
	public STViewMain( Context context, GameInfo info)
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.startback);
		
		titletext.LoadSprite(mGL, MainContext, R.drawable.titletext, "titletext.spr");
		titletextObj.SetObject(titletext, 0, 0, 0, 0, 0, 0);
		
		titlegsbtn.LoadSprite(mGL, MainContext, R.drawable.titlegsbtn, "titlegsbtn.spr");
		titlegsbtnObj.SetObject(titlegsbtn, 0, 0, 0, 0, 0, 0);
		
		titlecsbtn.LoadSprite(mGL, MainContext, R.drawable.titlecsbtn, "titlecsbtn.spr");
		titlecsbtnObj.SetObject(titlecsbtn, 0, 0, 0, 0, 0, 0);
	}

	public void DoGame()
	{
		updateTitleText();
		updateTitleBtn();
		
		back.PutImage(gInfo, 0, 0);
		titletextObj.DrawSprite(gInfo);
		titlegsbtnObj.DrawSprite(gInfo);
		titlecsbtnObj.DrawSprite(gInfo);
	}
	
	public void updateTitleText()
	{
		titletextObj.x = 400;
		titletextObj.y = 100;
	}
	public void updateTitleBtn()
	{
		titlegsbtnObj.x = 400;
		titlegsbtnObj.y = 250;
		
		titlecsbtnObj.x = 400;
		titlecsbtnObj.y = 350;
	}
	
	public void actionDown()
	{
		if(titlegsbtnObj.CheckPos((int)TouchX, (int)TouchY)){
			titlegsbtnObj.motion = 1;
			Intent intent = new Intent(MainContext, StageSelectActivity.class);
			MainContext.startActivity(intent);
		}
		else if(titlecsbtnObj.CheckPos((int)TouchX, (int)TouchY)){
			titlecsbtnObj.motion = 1;
			Intent intent = new Intent(MainContext, CStageSelectActivity.class);
			MainContext.startActivity(intent);
		}
	}
	public void actionUp()
	{
		titlegsbtnObj.motion = 0;
		titlecsbtnObj.motion = 0;
	}
}