package paper.stageSelectActivity;

import javax.microedition.khronos.opengles.GL10;

import paper.data.GameMain;
import paper.data.GameOption;
import paper.data.StageData;
import paper.gameActivity.GameActivity;
import paper.stageSelectActivity.StageSelectActivity.ScrTimer;
import paper.stageSelectActivity.StageSelectActivity.d_state;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.ListView;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

import com.example.papercult.R;

public class SBGViewMain implements GameMain
{
	float scrSpd = 20;
	public GL10 mGL = null; // OpenGL 객체
	public ListView lv;
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	int sIndex;
	boolean first = true;
	Vibrator vibe;
	ScrTimer st;
    
	private Sprite back = new Sprite();
	private Sprite paper = new Sprite();
	private Sprite startBtn = new Sprite();
	private Sprite left = new Sprite();
	private Sprite mal = new Sprite();
	Sprite scoreBar = new Sprite();
	Sprite scoreNum = new Sprite();
	
	private GameObject paperObj = new GameObject();
	private GameObject startBtnObj = new GameObject();
	public GameObject leftObj = new GameObject();
	private GameObject malObj = new GameObject();
	GameObject scoreBarObj = new GameObject();
	GameObject scoreNumObj1 = new GameObject();
	GameObject scoreNumObj10 = new GameObject();
	GameObject scoreNumObj100 = new GameObject();
	GameObject scoreNumObjP = new GameObject();
	
	enum scrState {close, open, stop};
	scrState s_state = scrState.close;
	
	enum malState {toVisible, toInvisible, start, end}
	malState m_state = malState.toInvisible;
	
	public SBGViewMain( Context context, GameInfo info)
	{
		MainContext = context;
		gInfo = info;
		vibe = (Vibrator)MainContext.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.back);
		
		startBtn.LoadSprite(mGL, MainContext, R.drawable.redraw, "redraw.spr");
		left.LoadSprite(mGL, MainContext, R.drawable.note, "left.spr");
		mal.LoadSprite(mGL, MainContext, R.drawable.mal, "mal.spr");
		
		paperObj.SetObject(paper, 0, 0, 300, 300, 0, 0);
		startBtnObj.SetObject(startBtn, 0, 0, 720, 400, 0, 0);
		leftObj.SetObject(left, 0, 0, -480, -10, 0, 0);
		malObj.SetObject(mal, 0, 0, 430, 240, 0, 0);
		malObj.SetZoom(gInfo, 0f, 0f);
		leftObj.SetZoom(gInfo, 1f, 1.05f);
		
		scoreBar.LoadSprite(mGL, MainContext, R.drawable.b_scorebar, "b_scorebar.spr");
		scoreBarObj.SetObject(scoreBar, 0, 0, 635, 125, 10, 0);
		scoreBarObj.SetZoom(gInfo, 1.2f, 1.2f);
		
		scoreNum.LoadSprite(mGL, MainContext, R.drawable.b_scorenum, "b_scorenum.spr");
		scoreNumObj1.SetObject(scoreNum, 0, 0, 0, 0, 0, 0);
		scoreNumObj10.SetObject(scoreNum, 0, 0, 0, 0, 0, 0);
		scoreNumObj100.SetObject(scoreNum, 0, 0, 0, 0, 0, 0);
		scoreNumObjP.SetObject(scoreNum, 0, 0, 0, 0, 10, 0);
		
		scoreNumObj1.SetZoom(gInfo, 1.5f, 1.8f);
		scoreNumObj10.SetZoom(gInfo, 1.5f, 1.8f);
		scoreNumObj100.SetZoom(gInfo, 1.5f, 1.8f);
		scoreNumObjP.SetZoom(gInfo, 1.25f, 1.8f);
		
		scoreNumObj1.show = false;
		scoreNumObj10.show = false;
		scoreNumObj100.show = false;
	}

	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		updateBG();
		updateBtn();
		updateMal();
		updateScore();
		leftObj.DrawSprite(gInfo);
		malObj.DrawSprite(gInfo);
		startBtnObj.DrawSprite(gInfo);
		scoreBarObj.DrawSprite(gInfo);
		scoreNumObj1.DrawSprite(gInfo);
		scoreNumObj10.DrawSprite(gInfo);
		scoreNumObj100.DrawSprite(gInfo);
		scoreNumObjP.DrawSprite(gInfo);
	}
	public void updateScore()
	{
		scoreBarObj.x = malObj.x + (malObj.scalex*210);
		scoreBarObj.y = malObj.y - (malObj.scaley*115);
		scoreBarObj.SetZoom(gInfo, 1.2f*malObj.scalex, 1.2f*malObj.scalex);
		
		scoreNumObj100.x = malObj.x + (malObj.scalex*155);
		scoreNumObj100.y = malObj.y - (malObj.scaley*75);
		scoreNumObj100.SetZoom(gInfo, 1.5f*malObj.scalex, 1.8f*malObj.scaley);
		
		scoreNumObj10.x = malObj.x + (malObj.scalex*185);
		scoreNumObj10.y = malObj.y - (malObj.scaley*75);
		scoreNumObj10.SetZoom(gInfo, 1.5f*malObj.scalex, 1.8f*malObj.scaley);
		
		scoreNumObj1.x = malObj.x + (malObj.scalex*220);
		scoreNumObj1.y = malObj.y - (malObj.scaley*75);
		scoreNumObj1.SetZoom(gInfo, 1.5f*malObj.scalex, 1.8f*malObj.scaley);
		
		scoreNumObjP.x = malObj.x + (malObj.scalex*260);
		scoreNumObjP.y = malObj.y - (malObj.scaley*75);
		scoreNumObjP.SetZoom(gInfo, 1.25f*malObj.scalex, 1.8f*malObj.scaley);
	}
	public void updateBG()
	{
		if(s_state == scrState.close){
			if(leftObj.x < 0)
				leftObj.x = leftObj.x + (480/scrSpd);
			else{
				st.draw_state = d_state.toVisible;
				st.sendEmptyMessage(0);
				leftObj.x = 0;
			}

			if((leftObj.x == 0))
				scrSpd = 20;
			else
				scrSpd = scrSpd + 1.5f;
		}
		else if(s_state == scrState.open){
			if(leftObj.x > -480)
				leftObj.x = leftObj.x - (480/scrSpd);
			else
				leftObj.x = -480;
			
			if((leftObj.x == -480)){
				scrSpd = 20;
				s_state = scrState.stop;
				startGame();
			}
			else
				scrSpd = scrSpd + 1.5f;
		}
	}
	public void updateMal()
	{
		if(m_state == malState.toVisible){
			if(malObj.scalex < 1)
				malObj.Zoom(gInfo, 0.1f, 0.1f);
			else
				malObj.SetZoom(gInfo, 1f, 1f);
		}
		else if(m_state == malState.toInvisible){
			if(malObj.scalex > 0)
				malObj.Zoom(gInfo, -0.1f, -0.1f);
			else
				malObj.SetZoom(gInfo, 0, 0);
		}
		else if(m_state == malState.start){
			if(malObj.scalex < 1)
				malObj.Zoom(gInfo, 0.02f, 0.02f);
			else
				malObj.SetZoom(gInfo, 1f, 1f);
		}
		else if(m_state == malState.end){
			if(malObj.scalex > 0)
				malObj.Zoom(gInfo, -0.02f, -0.02f);
			else
				malObj.SetZoom(gInfo, 0, 0);
		}
	}
	
	public void updateBtn()
	{
		if(startBtnObj.motion == 1){
			if(startBtnObj.frame > 5){
				startBtnObj.motion = 2;
			}
		startBtnObj.AddFrame(0.25f);
		}
	}
	
	public void actionDown()
	{
		if(startBtnObj.CheckPos((int)TouchX, (int)TouchY) == true){
			sIndex = lv.getFirstVisiblePosition()+2;
			int lastIndex = lv.getCount()-1;
			if((sIndex==0)||(sIndex==1)||(sIndex==lastIndex-1)||(sIndex==lastIndex))
				return;
			if((StageData.getInstance().getStage(sIndex).locked == false)
					&& lv.getAlpha() == 1){
				vibe.vibrate(GameOption.vibePower);
				startBtnObj.motion = 1;
				st.draw_state = d_state.stop;
				lv.setAlpha(0);
				s_state = scrState.open;
				m_state = malState.end;
			}
		}
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
		scoreNumObj1.show = true;
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
	public void startScr()
	{
		if(first){
			first = false;
			return;
		}
		startBtnObj.motion = 0;
		s_state = scrState.close;
		leftObj.x = -480;
		
		m_state = malState.start;
		malObj.scalex = 0;
		malObj.scaley = 0;
	}
	
	private void startGame()
	{
		Intent intent = new Intent(MainContext, GameActivity.class);
		intent.putExtra("stageNum",sIndex);
		MainContext.startActivity(intent);
	}

	@Override
	public void setGl(GL10 gl) {
		// TODO Auto-generated method stub
		mGL = gl;
	}

	@Override
	public GameInfo getGInfo() {
		// TODO Auto-generated method stub
		return gInfo;
	}
}
