package paper.stageSelectActivity;

import java.util.Vector;


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
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;



public class StageSelectActivity extends Activity {
	public SBGView sbgView;
	public SBGViewMain sbgMain;
	public SFGView sfgView;
	public GameInfo gInfo;
	public ListView stageList;
	
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
        
        FrameLayout r = new FrameLayout(this);
        
        sbgMain = new SBGViewMain( this, gInfo );
        sbgView = new SBGView( this, sbgMain );
        sbgView.setRenderer( new SBGSurfaceClass(sbgMain) );
        
        r.addView(sbgView);
         
		StageAdapter adt = new StageAdapter(this, StageData.getInstance().list, (int)gInfo.ScreenYsize);
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		stageList = (ListView)inflater.inflate(R.layout.listview, r, false);
		
		stageList.setAdapter(adt);
		stageList.setDivider(null);
		
		stageList.setOnTouchListener(new StageListListener(stageList, (int)gInfo.ScreenYsize));
		sbgMain.lv = stageList;
		
		sfgView = new SFGView(this,(int)(gInfo.ScreenXsize/10)*6, (int)gInfo.ScreenYsize);
		
		r.addView(stageList, (int)((gInfo.ScreenXsize/10)*6), (int)gInfo.ScreenYsize);
		r.addView(sfgView , (int)((gInfo.ScreenXsize/10)*6), (int)gInfo.ScreenYsize);

        setContentView( r );
        mHandler.sendEmptyMessage(0);
       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void onResume(){
		super.onResume();
		sbgMain.startScr();
	}
	 Handler mHandler = new Handler(){
     	public void handleMessage(Message msg){
     		stageList.setX(sbgMain.leftObj.x / gInfo.ScalePx);
     		sfgView.setX(sbgMain.leftObj.x / gInfo.ScalePx);
     		mHandler.sendEmptyMessageDelayed(0, 1000/60);
     	}
     };
	
}
