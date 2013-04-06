package paper.stageSelectActivity;

import java.util.Vector;

import paper.gameActivity.Paper;
import paper.gameActivity.Stage;
import paper.gameActivity.stagePolygon;

import com.example.papercult.R;

import bayaba.engine.lib.GameInfo;
import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;


public class StageSelectActivity extends Activity {
	public SBGView sbgView;
	public SBGViewMain sbgMain;
	public GameInfo gInfo;
	
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
        
        sbgMain = new SBGViewMain( this, gInfo);
        sbgView = new SBGView( this, sbgMain );
        sbgView.setRenderer( new SBGSurfaceClass(sbgMain) );
        
        r.addView(sbgView);
        
        Vector<Stage> s = new Vector<Stage>();
		
		stagePolygon poly = new stagePolygon();
		stagePolygon polyl = new stagePolygon();

		Stage st = new Stage("test", 1, poly, polyl);
		st.titleImage = R.drawable.back;
		
		Stage st1 = new Stage("23",1, poly,polyl);
		st1.titleImage = R.drawable.bg;
        
		s.add(st);
		s.add(st1);
		s.add(st);
		s.add(st1);
		s.add(st);
		s.add(st1);
		s.add(st);
		s.add(st1);
		s.add(st);
		s.add(st1);
		s.add(st);
		s.add(st1);
		
		StageAdapter adt = new StageAdapter(this, s, (int)gInfo.ScreenYsize);
		
		ListView stageList = new ListView(this);
		stageList.setAdapter(adt);
		stageList.setDivider(null);
		
		stageList.setOnTouchListener(new StageListListener(stageList));
			
		
		
		r.addView(stageList, (int)((gInfo.ScreenXsize/10)*6), (int)gInfo.ScreenYsize);
        setContentView( r );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
