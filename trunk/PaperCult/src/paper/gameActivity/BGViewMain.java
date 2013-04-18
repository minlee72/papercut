package paper.gameActivity;



import javax.microedition.khronos.opengles.GL10;

import com.example.papercult.R;


import android.content.Context;
import android.widget.Toast;

import bayaba.engine.lib.*;

public class BGViewMain
{
	GL10 mGL = null; // OpenGL 객체
	Context MainContext;
	GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	PaperView pv;
    
	Sprite back = new Sprite();
	Sprite num[] = new Sprite[8];
	Sprite redraw = new Sprite();
	
	GameObject numObj[] = new GameObject[8];
	GameObject redrawObj = new GameObject();
	

	public BGViewMain( Context context, GameInfo info )
	{
		MainContext = context;
		gInfo = info;
		
		for(int i=0; i<8; i++){
			num[i] = new Sprite();
			numObj[i] = new GameObject();
		}
		
	}

	public void LoadGameData()
	{
		num[0].LoadSprite(mGL, MainContext, R.drawable.pnum0, "pnum0.spr");
		num[1].LoadSprite(mGL, MainContext, R.drawable.pnum1, "pnum1.spr");
		num[2].LoadSprite(mGL, MainContext, R.drawable.pnum2, "pnum2.spr");
		num[3].LoadSprite(mGL, MainContext, R.drawable.pnum3, "pnum3.spr");
		num[4].LoadSprite(mGL, MainContext, R.drawable.pnum4, "pnum4.spr");
		num[5].LoadSprite(mGL, MainContext, R.drawable.pnum5, "pnum5.spr");
		num[6].LoadSprite(mGL, MainContext, R.drawable.pnum6, "pnum6.spr");
		num[7].LoadSprite(mGL, MainContext, R.drawable.pnum7, "pnum7.spr");
		for(int i=0; i<8; i++){
			numObj[i].SetObject(num[i], 0, 0, 10, 300, 0, 0);
		}

		
		
		back.LoadBitmap(mGL, MainContext, R.drawable.stageback);
		redraw.LoadSprite(mGL, MainContext, R.drawable.redraw, "redraw.spr");
		redrawObj.SetObject(redraw, 0, 0, 720, 400, 0, 0);
	}
	
	public boolean checkBtn(float inputX, float inputY)
	{
		float x = inputX * gInfo.ScalePx;
		float y = inputY * gInfo.ScalePy;
		return redrawObj.CheckPos((int)x, (int)y);
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		numObj[pv.sObj.current].motion = 0;
		numObj[pv.sObj.current].AddFrameLoop(0.2f);
		numObj[pv.sObj.current].DrawSprite(gInfo);
		redrawObj.DrawSprite(gInfo);
	}
}
