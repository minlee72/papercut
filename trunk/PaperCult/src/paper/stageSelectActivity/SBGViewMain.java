package paper.stageSelectActivity;

import javax.microedition.khronos.opengles.GL10;

import paper.gameActivity.GameActivity;

import com.example.papercult.R;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import bayaba.engine.lib.*;

public class SBGViewMain
{
	float scrSpd = 20;
	public GL10 mGL = null; // OpenGL 객체
	public ListView lv;
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
    
	private Sprite back = new Sprite();
	private Sprite paper = new Sprite();
	private Sprite startBtn = new Sprite();
	private Sprite left = new Sprite();
	private Sprite right = new Sprite();
	
	private GameObject paperObj = new GameObject();
	private GameObject startBtnObj = new GameObject();
	public GameObject leftObj = new GameObject();
	private GameObject rightObj = new GameObject();
	
	public SBGViewMain( Context context, GameInfo info)
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.stageback);
		
		startBtn.LoadSprite(mGL, MainContext, R.drawable.startbtn, "startbtn.spr");
		left.LoadSprite(mGL, MainContext, R.drawable.left, "left.spr");
		right.LoadSprite(mGL, MainContext, R.drawable.right, "right.spr");
		
		paperObj.SetObject(paper, 0, 0, 300, 300, 0, 0);
		startBtnObj.SetObject(startBtn, 0, 0, 700, 100, 0, 0);
		leftObj.SetObject(left, 0, 0, -480, 0, 0, 0);
		rightObj.SetObject(right, 0, 0, 800, 0, 0, 0);
	}
	
	public void scrollBG()
	{
		if(leftObj.x < 0){
			leftObj.x = leftObj.x + (480/scrSpd);
		}
		else{
			leftObj.x = 0;
		}
		
		if(rightObj.x >  480){
			rightObj.x = rightObj.x - (320/scrSpd);
		}
		else{
			rightObj.x = 480;
		}
		
		if((leftObj.x == 0) && (rightObj.x == 480))
			scrSpd = 20;
		else
			scrSpd = scrSpd + 1.5f;
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		scrollBG();
		leftObj.DrawSprite(gInfo);
		rightObj.DrawSprite(gInfo);
		startBtnObj.DrawSprite(gInfo);
	}
	
	public void checkButton()
	{
		if(startBtnObj.CheckPos((int)TouchX, (int)TouchY) == true){
			Intent intent = new Intent(MainContext, GameActivity.class);
			intent.putExtra("stageNum",lv.getFirstVisiblePosition()+2 );
			MainContext.startActivity(intent);
		}
	}
	
	public void startScr(){
		leftObj.x = -480;
		rightObj.x = 800;
	}
}
