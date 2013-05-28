package paper.cstageSelectActivity;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import paper.cgameActivity.CGameActivity;
import paper.cstageCreateActivity.CStageCreateActivity;
import paper.data.CStageData;
import paper.data.Stage;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

import com.example.papercult.R;

public class CSBViewMain
{
	float scrSpd = 20;
	public GL10 mGL = null; // OpenGL 객체
	public ListView lv;
	private Context MainContext;
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	public CStageAdapter adt;
	int sIndex;
	boolean first = true;
    
	private Sprite back = new Sprite();
	private Sprite paper = new Sprite();
	private Sprite startBtn = new Sprite();
	private Sprite left = new Sprite();
	private Sprite mal = new Sprite();
	Sprite scoreBar = new Sprite();
	Sprite scoreNum = new Sprite();
	Sprite trashCan = new Sprite();
	Sprite sendStage = new Sprite();
	Sprite recStage = new Sprite();
	
	private GameObject paperObj = new GameObject();
	private GameObject startBtnObj = new GameObject();
	public GameObject leftObj = new GameObject();
	private GameObject malObj = new GameObject();
	private GameObject createBtnObj = new GameObject();
	GameObject scoreBarObj = new GameObject();
	GameObject scoreNumObj1 = new GameObject();
	GameObject scoreNumObj10 = new GameObject();
	GameObject scoreNumObj100 = new GameObject();
	GameObject scoreNumObjP = new GameObject();
	GameObject trashCanObj = new GameObject();
	GameObject sendStageObj = new GameObject();
	GameObject recStageObj = new GameObject();
	
	enum scrState {close, open, stop};
	scrState s_state = scrState.close;
	
	enum malState {toVisible, toInvisible, start, end}
	malState m_state = malState.toInvisible;
	
	
	
	public CSBViewMain( Context context, GameInfo info)
	{
		MainContext = context;
		gInfo = info;
	}

	public void LoadGameData()
	{
		back.LoadBitmap(mGL, MainContext, R.drawable.back);
		
		startBtn.LoadSprite(mGL, MainContext, R.drawable.redraw, "redraw.spr");
		left.LoadSprite(mGL, MainContext, R.drawable.note, "left.spr");
		mal.LoadSprite(mGL, MainContext, R.drawable.b_mal, "b_mal.spr");
		
		paperObj.SetObject(paper, 0, 0, 300, 300, 0, 0);
		startBtnObj.SetObject(startBtn, 0, 0, 720, 400, 0, 0);
		leftObj.SetObject(left, 0, 0, -480, -10, 0, 0);
		malObj.SetObject(mal, 0, 0, 430, 240, 0, 0);
		malObj.SetZoom(gInfo, 0f, 0f);
		leftObj.SetZoom(gInfo, 1f, 1.05f);
		createBtnObj.SetObject(startBtn, 0, 0, 600, 400, 0, 0);
		
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
		
		trashCan.LoadSprite(mGL, MainContext, R.drawable.trashcan2, "trashcan2.spr");
		trashCanObj.SetObject(trashCan, 0, 0, 0, 0, 0, 0);
		
		sendStage.LoadSprite(mGL, MainContext, R.drawable.sendstage, "sendstage.spr");
		sendStageObj.SetObject(sendStage, 0, 0, 0, 0, 0, 0);
		
		recStage.LoadSprite(mGL, MainContext, R.drawable.receivestage, "receivestage.spr");
		recStageObj.SetObject(recStage, 0, 0, 0, 0, 0, 0);
	}

	public void DoGame()
	{
		back.PutImage(gInfo, 0, 0);
		updateBG();
		updateBtn();
		updateMal();
		updateScore();
		updateMalBtn();
		leftObj.DrawSprite(gInfo);
		malObj.DrawSprite(gInfo);
		startBtnObj.DrawSprite(gInfo);
		createBtnObj.DrawSprite(gInfo);
		malObj.DrawSprite(gInfo);
		startBtnObj.DrawSprite(gInfo);
		scoreBarObj.DrawSprite(gInfo);
		scoreNumObj1.DrawSprite(gInfo);
		scoreNumObj10.DrawSprite(gInfo);
		scoreNumObj100.DrawSprite(gInfo);
		scoreNumObjP.DrawSprite(gInfo);
		trashCanObj.DrawSprite(gInfo);
		sendStageObj.DrawSprite(gInfo);
		recStageObj.DrawSprite(gInfo);
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
	public void updateMalBtn()
	{
		trashCanObj.x = malObj.x + (malObj.scalex*310);
		trashCanObj.y = malObj.y + (malObj.scaley*45);
		trashCanObj.SetZoom(gInfo, 1.0f*malObj.scalex, 0.8f*malObj.scalex);
		
		sendStageObj.x = malObj.x + (malObj.scalex*210);
		sendStageObj.y = malObj.y + (malObj.scaley*45);
		sendStageObj.SetZoom(gInfo, 1.0f*malObj.scalex, 1.0f*malObj.scalex);
		
		recStageObj.x = malObj.x + (malObj.scalex*110);
		recStageObj.y = malObj.y + (malObj.scaley*45);
		recStageObj.SetZoom(gInfo, 1.0f*malObj.scalex, 1.0f*malObj.scalex);
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
	
	public void actionDown()
	{
		if(startBtnObj.CheckPos((int)TouchX, (int)TouchY) == true){
			if(lv.getAlpha() == 1){
				sIndex = lv.getFirstVisiblePosition()+2;
				int lastIndex = lv.getCount()-1;
				if((sIndex==0)||(sIndex==1)||(sIndex==lastIndex-1)||(sIndex==lastIndex))
					return;
				startBtnObj.motion = 1;
				lv.setAlpha(0);
				s_state = scrState.open;
				m_state = malState.end;
				
			}
		}
		else if(createBtnObj.CheckPos((int)TouchX, (int)TouchY) == true){
			startCreateGame();
		}
		else if(trashCanObj.CheckPos((int)TouchX, (int)TouchY) == true){
			trashCanObj.motion = 1;
			int index = lv.getFirstVisiblePosition()+2;
			int lastIndex = CStageData.getInstance().list.size()-1;
			if((index==0)||(index==1)||(index==lastIndex-1)||(index==lastIndex))
				return;
			delCStage();
			m_state = malState.toInvisible;
		}
		else if(sendStageObj.CheckPos((int)TouchX, (int)TouchY) == true){
			sendStageObj.motion = 1;
		}
		else if(recStageObj.CheckPos((int)TouchX, (int)TouchY) == true){
			recStageObj.motion = 1;
		}
	}
	public void actionUp()
	{
		trashCanObj.motion = 0;
		sendStageObj.motion = 0;
		recStageObj.motion = 0;
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
	private void startCreateGame()
	{
		Intent intent = new Intent(MainContext, CStageCreateActivity.class);
		MainContext.startActivity(intent);
	}
	private void startGame()
	{
		int index = lv.getFirstVisiblePosition()+2;
		int lastIndex = lv.getCount()-1;
		if((index==0)||(index==1)||(index==lastIndex-1)||(index==lastIndex))
			return;
		Intent intent = new Intent(MainContext, CGameActivity.class);
		intent.putExtra("cstageNum",lv.getFirstVisiblePosition()+2 );
		MainContext.startActivity(intent);
		
	}
	public void delCStage()
	{
		final LinearLayout linear = (LinearLayout)View.inflate(MainContext, R.layout.delcstage, null);
		
		Vector<Stage> sv = CStageData.getInstance().list; 
		int index = lv.getFirstVisiblePosition()+2;
		
		Typeface ft = Typeface.createFromAsset(MainContext.getAssets(), "font.ttf");
		TextView tx = (TextView)linear.findViewById(R.id.delstagetitle);
		tx.setTypeface(ft);
		String stName = sv.get(index).name;
		String title = "'"+stName+"'\n모양을 지울까요?";
		tx.setText(title);
		
		AlertDialog.Builder db = new AlertDialog.Builder(MainContext);
		db.setView(linear)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Vector<Stage> sv = CStageData.getInstance().list; 
				int index = lv.getFirstVisiblePosition()+2;
				sv.remove(index);
				FrameLayout frame = (FrameLayout)View.inflate(MainContext, R.layout.deltoast_layout, null);
				Toast toast = new Toast(MainContext);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(frame);
				toast.show();
				adt.notifyDataSetChanged();
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				m_state = malState.toVisible;
			}
		});
		
		AlertDialog md = db.create();
		md.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		md.setCanceledOnTouchOutside(false);
		md.show();
	}
}
