package paper.gameActivity;



import javax.microedition.khronos.opengles.GL10;

import com.example.papercult.R;


import android.content.Context;

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
	
	int remain;

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
		back.LoadBitmap(mGL, MainContext, R.drawable.back);
		redraw.LoadSprite(mGL, MainContext, R.drawable.redraw, "redraw.spr");
		redrawObj.SetObject(redraw, 0, 0, 720, 400, 0, 0);
		redrawObj.motion = 2;
	}
	
	public boolean checkBtn(float inputX, float inputY)
	{
		float x = inputX * gInfo.ScalePx;
		float y = inputY * gInfo.ScalePy;
		return redrawObj.CheckPos((int)x, (int)y);
	}
	
	public int getPaperColor(){
		int rgb = 0;
		switch (redrawObj.motion){
		case 0:
			rgb = 0x40fcff29;
			redrawObj.motion = 1;
			break;
		case 2:
			rgb = 0x4023cf37;
			redrawObj.motion = 3;
			break;
		case 4:
			rgb = 0x40ff6345;
			redrawObj.motion = 5;
			break;
		case 6:
			rgb = 0x404378f0;
			redrawObj.motion = 7;
			break;
		case 8:
			rgb = 0x40a340ff;
			redrawObj.motion = 9;
			break;
		}
		return rgb;
	}
	
	public void decRemain()
	{
		numObj[remain].motion = 1;
	}
	
	public void doDec()
	{
		if(numObj[remain].motion == 1){
			if(numObj[remain].frame > 8){
				numObj[remain].motion = 0;
				remain--;
			}
			numObj[remain].AddFrame(0.25f);
		}
		
		if((redrawObj.motion%2)==1){
			if(redrawObj.frame > 5){
				redrawObj.motion = redrawObj.motion + 1;
				if(redrawObj.motion == 10)
					redrawObj.motion = 0;
			}
			redrawObj.AddFrame(0.25f);
		}
	}
	
	public void motionInit()
	{
		for(int i=0; i<8; i++){
			numObj[i].motion = 0;
			numObj[i].frame = 0;
		}
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		doDec();
		numObj[remain].DrawSprite(gInfo);
		redrawObj.DrawSprite(gInfo);
	}
}
