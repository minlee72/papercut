package paper.startActivity;

import paper.data.CStageData;
import paper.data.StageData;
import bayaba.engine.lib.GameInfo;
import com.example.papercult.R;
import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;


public class StartActivity extends Activity {

	public STView stView;
	public STViewMain stMain;
	public GameInfo gInfo;
	public ListView stageList;
	float scrWidth;
	float scrHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        gInfo = new GameInfo( 800, 480 );
        scrWidth = super.getWindowManager().getDefaultDisplay().getWidth();
        scrHeight = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.ScreenXsize = scrWidth;
        gInfo.ScreenYsize = scrHeight;
        gInfo.SetScale();
        
        stMain = new STViewMain( this, gInfo );
        stView = new STView( this, stMain );
        stView.setRenderer( new STSurfaceClass(stMain) );       
        
        setContentView( stView );
	}
	public void onResume(){
		super.onResume();
		StageData.createInstance(scrWidth, scrHeight);
		CStageData.createInstance(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load, menu);
		return true;
	}
}
