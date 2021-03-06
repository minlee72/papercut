package paper.stageSelectActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

import paper.data.CStageData;
import paper.data.Stage;
import paper.data.StageData;
import paper.data.StagePolygon;
import paper.data.SurfaceClass;
import paper.gameActivity.GameActivity;
import paper.sfx.Music;

import com.example.papercult.R;

import bayaba.engine.lib.GameInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;



public class StageSelectActivity extends Activity {
	enum d_state {toVisible, toInvisible, stop}
	public SBGView sbgView;
	public SBGViewMain sbgMain;
	public GameInfo gInfo;
	public ListView stageList;
	public StageListListener stll;
	StageAdapter adt;
	ScrTimer scrTimer;
	float alp;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        gInfo = new GameInfo( 800, 480 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        
        scrTimer = new ScrTimer();
        FrameLayout r = new FrameLayout(this);
        
        sbgMain = new SBGViewMain( this, gInfo );
        sbgView = new SBGView( this, sbgMain );
        sbgView.setRenderer( new SurfaceClass(sbgMain) );
        
        sbgMain.st = scrTimer;
        
        r.addView(sbgView);
         
        StageData sd = StageData.getInstance();
		adt = new StageAdapter(this, StageData.getInstance().list, (int)gInfo.ScreenYsize);
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		stageList = (ListView)inflater.inflate(R.layout.listview, r, false);
		
		stageList.setAdapter(adt);
		stageList.setDivider(null);
		
		stll = new StageListListener(sbgMain, stageList, (int)gInfo.ScreenYsize);
		stageList.setOnTouchListener(stll);
		sbgMain.lv = stageList;
		
		FrameLayout.LayoutParams listviewParams = new FrameLayout.LayoutParams((int)((gInfo.ScreenXsize/10)*5.3), (int)gInfo.ScreenYsize);
		listviewParams.leftMargin = (int)((gInfo.ScreenXsize/10)*0.5);
		stageList.setLayoutParams(listviewParams);
		
		r.addView(stageList);
		
        setContentView( r );
	}
	public void onResume(){
		super.onResume();
		Music.stageMusicStart(this);
		StageData.getInstance().setStageLock();
		alp = 0;
		stageList.setAlpha(alp);
		adt.notifyDataSetChanged();
		sbgMain.startScr();
		int index = stageList.getFirstVisiblePosition() + 2;
		int score = StageData.getInstance().getStage(index).score;
		sbgMain.setBarImg(score);
		sbgMain.setSnum(score);
	}
	public void onPause(){
		super.onPause();
		Music.stageMusicPause(this);
	}
	public void onBackPressed(){
		super.onBackPressed();
		Music.stageMusicPause(this);
		Music.init();
	}
	public void onDestroy(){
		super.onDestroy();
		
		Vector<Stage> stl = StageData.getInstance().list;

		int size = stl.size()-4;
		
		int writeScore;
		
		if(size < 1)
			return;
		
		try {
			FileOutputStream fos;
			fos = this.openFileOutput("ScoreList", Context.MODE_PRIVATE);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeInt(size);
			for(int i=0; i<size; i++){
				int index = i + 2;
				writeScore = stl.get(index).score;
				oos.writeInt(writeScore);
			}
		    oos.flush();
		    oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class ScrTimer extends Handler{
   	 d_state draw_state = d_state.stop;
   	 public void handleMessage(Message msg){
     		 if(draw_state == d_state.toVisible){
     			if(alp<1){
     				alp = alp + 0.03f;
     				stageList.setAlpha(alp);
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			}
     			else{
     				alp = 1;
     				stageList.setAlpha(alp);
     				draw_state = d_state.stop;
     			}
     		}
     	}
    }
}
