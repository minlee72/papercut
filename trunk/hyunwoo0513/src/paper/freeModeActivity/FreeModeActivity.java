package paper.freeModeActivity;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;



public class FreeModeActivity extends Activity {
	enum d_state {toVisible, toInvisible, stop};
	public FBGView fbgView;
	public FBGViewMain fbgMain;
	public FFGView ffgView;
	public GameInfo gInfo;
	public ListView stageList;
	StageAdapter adt;
	ScrTimer scrTimer;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toast.makeText(this, "test..", Toast.LENGTH_SHORT).show();
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        gInfo = new GameInfo( 800, 480 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        
        Toast.makeText(this, "test..2", Toast.LENGTH_SHORT).show();
        scrTimer = new ScrTimer();
        FrameLayout r = new FrameLayout(this);
        
        fbgMain = new FBGViewMain( this, gInfo );
        fbgView = new FBGView( this, fbgMain );
        fbgView.setRenderer( new FBGSurfaceClass(fbgMain) );
        
        r.addView(fbgView);
         
        Toast.makeText(this, "test..3", Toast.LENGTH_SHORT).show();
		adt = new StageAdapter(this, StageData.getInstance().list, (int)gInfo.ScreenYsize);
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		stageList = (ListView)inflater.inflate(R.layout.listview, r, false);
		
		stageList.setAdapter(adt);
		stageList.setDivider(null);
		
		stageList.setOnTouchListener(new StageListListener(fbgMain, stageList, (int)gInfo.ScreenYsize));
		fbgMain.lv = stageList;
		
		ffgView = new FFGView(this,(int)(gInfo.ScreenXsize/10)*6, (int)gInfo.ScreenYsize);
		
		fbgMain.afgv = ffgView;
		fbgMain.adt = adt;
		r.addView(stageList, (int)((gInfo.ScreenXsize/10)*6), (int)gInfo.ScreenYsize);
		r.addView(ffgView , (int)((gInfo.ScreenXsize/10)*6), (int)gInfo.ScreenYsize);

        setContentView( r );
       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onResume(){
		super.onResume();
		StageData.getInstance().setStageLock();
		adt.alpha=0;
		ffgView.setAlpha(0);
		adt.notifyDataSetChanged();
		ffgView.invalidate();
		scrTimer.draw_state = d_state.toVisible;
		scrTimer.sendEmptyMessageDelayed(0, 1000);
		fbgMain.startScr();
	}
	
	class ScrTimer extends Handler{
   	 d_state draw_state = d_state.stop;
   	 public void handleMessage(Message msg){
     		 if(draw_state == d_state.toVisible){
     			if(adt.alpha<1){
     				adt.alpha = adt.alpha + 0.03f;
     				ffgView.setAlpha(adt.alpha);
     				adt.notifyDataSetChanged();
     				ffgView.invalidate();
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			}
     			else{
     				adt.alpha = 1;
     				ffgView.setAlpha(1);
     				ffgView.setAlpha(adt.alpha);
     				adt.notifyDataSetChanged();
     				draw_state = d_state.stop;
     			}
     		}
     		 else if(draw_state == d_state.toInvisible){
     			 if(adt.alpha>0){
     				adt.alpha = adt.alpha - 0.03f;
     				ffgView.setAlpha(adt.alpha);
     				adt.notifyDataSetChanged();
     				ffgView.invalidate();
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			 }
     			 else{
     				adt.alpha = 0;
     				ffgView.setAlpha(0);
     				ffgView.setAlpha(adt.alpha);
     				adt.notifyDataSetChanged();
     				draw_state = d_state.stop;
     			 }
     		 }
     		 else{
     			 ;
     		 }
     	}
    }
}
