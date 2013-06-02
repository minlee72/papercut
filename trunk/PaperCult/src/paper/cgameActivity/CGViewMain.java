package paper.cgameActivity;



import javax.microedition.khronos.opengles.GL10;

import paper.data.GameMain;

import com.example.papercult.R;


import android.content.Context;
import android.widget.Toast;

import bayaba.engine.lib.*;

public class CGViewMain implements GameMain
{
	GL10 mGL = null; // OpenGL 객체
	Context MainContext;
	GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	CPaperView pv;
    
	Sprite back = new Sprite();
	Sprite num[] = new Sprite[8];
	Sprite redraw = new Sprite();
	Sprite scoreBar = new Sprite();
	Sprite scoreNum = new Sprite();
	
	GameObject numObj[] = new GameObject[8];
	GameObject curNumObj;
	GameObject redrawObj = new GameObject();
	GameObject scoreBarObj = new GameObject();
	GameObject scoreNumObj1 = new GameObject();
	GameObject scoreNumObj10 = new GameObject();
	GameObject scoreNumObj100 = new GameObject();
	GameObject scoreNumObjP = new GameObject();
	
	int remain;
	enum numState {stop, dec, inc};
	numState n_state = numState.stop;

	public CGViewMain( Context context, GameInfo info )
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
		
		scoreBar.LoadSprite(mGL, MainContext, R.drawable.s_scorebar, "s_scorebar.spr");
		scoreBarObj.SetObject(scoreBar, 0, 0, 85, 80, 0, 0);
		scoreBarObj.SetZoom(gInfo, 1.6f, 1.7f);
		
		scoreNum.LoadSprite(mGL, MainContext, R.drawable.s_scorenum, "s_scorenum.spr");
		scoreNumObj1.SetObject(scoreNum, 0, 0, 90, 120, 0, 0);
		scoreNumObj10.SetObject(scoreNum, 0, 0, 60, 120, 0, 0);
		scoreNumObj100.SetObject(scoreNum, 0, 0, 35, 120, 0, 0);
		scoreNumObjP.SetObject(scoreNum, 0, 0, 127, 120, 10, 0);
		
		scoreNumObj1.SetZoom(gInfo, 1.5f, 1.8f);
		scoreNumObj10.SetZoom(gInfo, 1.5f, 1.8f);
		scoreNumObj100.SetZoom(gInfo, 1.5f, 1.8f);
		scoreNumObjP.SetZoom(gInfo, 1.25f, 1.8f);
		
		scoreNumObj10.show = false;
		scoreNumObj100.show = false;
	}
	
	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		updateRedraw();
		updateNum();
		curNumObj.DrawSprite(gInfo);
		redrawObj.DrawSprite(gInfo);
		scoreBarObj.DrawSprite(gInfo);
		scoreNumObj1.DrawSprite(gInfo);
		scoreNumObj10.DrawSprite(gInfo);
		scoreNumObj100.DrawSprite(gInfo);
		scoreNumObjP.DrawSprite(gInfo);
	}
	
	public boolean checkRedrawBtn(float inputX, float inputY)
	{
		float x = inputX * gInfo.ScalePx;
		float y = inputY * gInfo.ScalePy;
		return redrawObj.CheckPos((int)x, (int)y);
	}
	
	public boolean checkBackBtn(float inputX, float inputY)
	{
		float x = inputX * gInfo.ScalePx;
		float y = inputY * gInfo.ScalePy;
		return numObj[remain].CheckPos((int)x, (int)y);
	}
	
	public void decRemain(int current)
	{
		if(remain == 0)
			return;
		remain = current;
		curNumObj.motion = 1;
		curNumObj.frame = 0;
		n_state = numState.dec;
	}
	
	public void incRemain(int current)
	{
		if(remain == pv.sObj.limit)
			return;
		remain = current;
		curNumObj = numObj[remain];
		curNumObj.motion = 1;
		curNumObj.frame = (num[remain].Count[curNumObj.motion]) - 1;
		n_state = numState.inc;
	}
	public void setSnum(int score)
	{
		int remain;
		
		int hn = score / 100;
		remain = score % 100;
		
		int dn = remain / 10;
		remain = score % 10;
		
		int on = remain;
		
		if(hn == 0)
			scoreNumObj100.show = false;
		else{
			scoreNumObj100.show = true;
			scoreNumObj100.motion = hn;	
		}
		
		if((dn == 0)&&(hn == 0))
			scoreNumObj10.show = false;
		else{
			scoreNumObj10.show = true;
			scoreNumObj10.motion = dn;	
		}
		scoreNumObj1.motion = on;
	}
	public void setBarImg(int score)
	{
		if (score==100){
			scoreBarObj.motion = 10;
		}
		else if (score<70){
			scoreBarObj.motion = (int)(score/13);
		}
		else{
			int rp = score-70;
			scoreBarObj.motion = ((int)(rp/10))+6;
		}
	}
	public void updateNum()
	{
		if(n_state == numState.stop){
			curNumObj = numObj[remain];
			curNumObj.motion = 0;
			curNumObj.frame = 0;
		}
		else if(n_state == numState.dec){
			if(curNumObj.EndFrame()){
				curNumObj.frame = 0;
				curNumObj.motion = 0;
				curNumObj = numObj[remain];
				n_state = numState.stop;
			}
			else
				curNumObj.AddFrame(0.25f);
		}
		else if(n_state == numState.inc){
			if(curNumObj.frame == 0){
				curNumObj.motion = 0;
				n_state = numState.stop;
			}
			else
				curNumObj.SubFrame(0.25f);
		}
	}
	public void updateRedraw()
	{
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

	public int getPaperColor(){
		int rgb = 0;
		switch (redrawObj.motion){
		case 0:
		case 1:
			rgb = 0x40fcff29;
			redrawObj.motion = 1;
			break;
		case 2:
		case 3:
			rgb = 0x4023cf37;
			redrawObj.motion = 3;
			break;
		case 4:
		case 5:
			rgb = 0x40ff6345;
			redrawObj.motion = 5;
			break;
		case 6:
		case 7:
			rgb = 0x404378f0;
			redrawObj.motion = 7;
			break;
		case 8:
		case 9:
			rgb = 0x40a340ff;
			redrawObj.motion = 9;
			break;
		}
		return rgb;
	}

	@Override
	public void setGl(GL10 gl) {
		mGL = gl;
		
	}

	@Override
	public GameInfo getGInfo() {
		// TODO Auto-generated method stub
		return gInfo;
	}
}
