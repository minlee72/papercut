package paper.stageSelectActivity;

import javax.microedition.khronos.opengles.GL10;
import com.example.papercult.R;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

import bayaba.engine.lib.*;

public class SBGViewMain
{
	public GL10 mGL = null; // OpenGL 객체
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
		
		paper.LoadSprite(mGL, MainContext, R.drawable.paper, "paper.spr");
		startBtn.LoadSprite(mGL, MainContext, R.drawable.startbtn, "startbtn.spr");
		left.LoadSprite(mGL, MainContext, R.drawable.left, "left.spr");
		right.LoadSprite(mGL, MainContext, R.drawable.right, "right.spr");
		
		paperObj.SetObject(paper, 0, 0, 300, 300, 0, 0);
		startBtnObj.SetObject(startBtn, 0, 0, 700, 100, 0, 0);
		leftObj.SetObject(left, 0, 0, -480, 0, 0, 0);
		rightObj.SetObject(right, 0, 0, 800, 0, 0, 0);
	}
	
	public void updatePaper()
	{
		if(leftObj.x < 0){
			leftObj.x = leftObj.x + (480/60);
		}
		if(rightObj.x >  480){
			rightObj.x = rightObj.x - (320/60);
		}
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		updatePaper();
		leftObj.DrawSprite(gInfo);
		rightObj.DrawSprite(gInfo);
		startBtnObj.DrawSprite(gInfo);
	}
	
	public void checkButton()
	{
		if(startBtnObj.CheckPos((int)TouchX, (int)TouchY) == true){
			leftObj.x = -480;
			rightObj.x = 800;
		}
	}
}
