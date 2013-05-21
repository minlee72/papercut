package paper.cstageSelectActivity;

import java.util.Vector;


import paper.data.CStageData;
import paper.data.StageData;
import paper.gameActivity.Stage;
import paper.gameActivity.StagePolygon;
import com.example.papercult.R;
import bayaba.engine.lib.GameInfo;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;



public class CStageSelectActivity extends Activity {
	enum d_state {toVisible, toInvisible, stop};
	public CSBView csbView;
	public CSBViewMain csbMain;
	public GameInfo gInfo;
	public ListView stageList;
	CStageAdapter adt;
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
        
        csbMain = new CSBViewMain( this, gInfo );
        csbView = new CSBView( this, csbMain );
        csbView.setRenderer( new CSBSurfaceClass(csbMain) );
        
        r.addView(csbView);
        
        CStageData.createInstance();
		adt = new CStageAdapter(this, CStageData.getInstance().list, (int)gInfo.ScreenYsize);
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		stageList = (ListView)inflater.inflate(R.layout.listview, r, false);
		
		stageList.setAdapter(adt);
		stageList.setDivider(null);
		
		stageList.setOnTouchListener(new CStageListListener(csbMain, stageList, (int)gInfo.ScreenYsize));
		csbMain.lv = stageList;
		
		csbMain.adt = adt;
		FrameLayout.LayoutParams listviewParams = new FrameLayout.LayoutParams((int)((gInfo.ScreenXsize/10)*5.3), (int)gInfo.ScreenYsize);
		listviewParams.leftMargin = (int)((gInfo.ScreenXsize/10)*0.5);
		stageList.setLayoutParams(listviewParams);
		r.addView(stageList);
        setContentView( r );
       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onResume(){
		super.onResume();
		//StageData.getInstance().setStageLock();
		stageList.setAlpha(alp);
		adt.alpha=0;
		adt.notifyDataSetChanged();
		scrTimer.draw_state = d_state.toVisible;
		scrTimer.sendEmptyMessageDelayed(0, 1000);
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
     		 else if(draw_state == d_state.toInvisible){
     			 if(alp>0){
     				alp = alp - 0.03f;
     				stageList.setAlpha(alp);
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			 }
     			 else{
     				alp = 0;
     				stageList.setAlpha(alp);
     				draw_state = d_state.stop;
     			 }
     		 }
     		 else{
     			 ;
     		 }
     	}
    }
}