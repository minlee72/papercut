package paper.stageSelectActivity;

import javax.microedition.khronos.opengles.GL10;

import paper.data.StageData;
import paper.gameActivity.GameActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

import com.example.papercult.R;

public class SBGViewMain
{
	float scrSpd = 20;
	public GL10 mGL = null; // OpenGL 객체
	public ListView lv;
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
    public StageAdapter adt;
    public SFGView afgv;
    
	private Sprite back = new Sprite();
	private Sprite paper = new Sprite();
	private Sprite startBtn = new Sprite();
	private Sprite left = new Sprite();
	private Sprite mal = new Sprite();
	private Sprite sl = new Sprite();
	
	private GameObject slime = new GameObject();
	private GameObject paperObj = new GameObject();
	private GameObject startBtnObj = new GameObject();
	public GameObject leftObj = new GameObject();
	private GameObject malObj = new GameObject();
	
	private BluetoothAdapter mBTAdapter;
	
	enum scrState {close, open, stop};
	scrState s_state = scrState.close;
	
	enum malState {toVisible, toInvisible, start, end}
	malState m_state = malState.toVisible;
	
	public SBGViewMain( Context context, GameInfo info)
	{
		MainContext = context;
		gInfo = info;
		mBTAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.back);
		
		startBtn.LoadSprite(mGL, MainContext, R.drawable.redraw, "redraw.spr");
		left.LoadSprite(mGL, MainContext, R.drawable.note, "left.spr");
		mal.LoadSprite(mGL, MainContext, R.drawable.mal, "mal.spr");
		sl.LoadSprite(mGL, MainContext, R.drawable.slime, "slime.spr");
		
		slime.SetObject(sl, 0, 0, 580, 400, 0, 0);
		paperObj.SetObject(paper, 0, 0, 300, 300, 0, 0);
		startBtnObj.SetObject(startBtn, 0, 0, 720, 400, 0, 0);
		leftObj.SetObject(left, 0, 0, -480, -10, 0, 0);
		malObj.SetObject(mal, 0, 0, 430, 240, 0, 0);
		malObj.SetZoom(gInfo, 0f, 0f);
		leftObj.SetZoom(gInfo, 1f, 1.05f);
	}

	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		updateBG();
		updateBtn();
		updateMal();
		slime.DrawSprite(gInfo);
		leftObj.DrawSprite(gInfo);
		malObj.DrawSprite(gInfo);
		startBtnObj.DrawSprite(gInfo);
	}
	
	public void updateBG()
	{
		if(s_state == scrState.close){
			if(leftObj.x < 0)
				leftObj.x = leftObj.x + (480/scrSpd);
			else
				leftObj.x = 0;

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
	
	public void checkButton()
	{
		if(startBtnObj.CheckPos((int)TouchX, (int)TouchY) == true){
			if((StageData.getInstance().getStage(lv.getFirstVisiblePosition()+2).locked == false)
					&& adt.alpha == 1){
				startBtnObj.motion = 1;
				adt.alpha = 0;
				adt.notifyDataSetChanged();
			    afgv.setAlpha(0);
				s_state = scrState.open;
				m_state = malState.end;
			}
		}
		else if(slime.CheckPos((int)TouchX, (int)TouchY)==true){
			doDiscovery();
			Toast.makeText(MainContext, "sucess", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void doDiscovery(){
		if(mBTAdapter.isDiscovering()){
			
		}else{
			mBTAdapter.startDiscovery();
		}
	}
	
	public void startScr()
	{
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
			intent.putExtra("stageNum",lv.getFirstVisiblePosition()+2 );
			MainContext.startActivity(intent);
	}
}
