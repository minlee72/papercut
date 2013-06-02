package paper.cstageCreateActivity;

import paper.data.SurfaceClass;
import paper.gameActivity.BGView;
import paper.gameActivity.BGViewMain;
import paper.gameActivity.PaperView;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import bayaba.engine.lib.GameInfo;

import com.example.papercult.R;

public class CStageCreateActivity extends Activity {
	public CSCPaperView pv;
	public CSCView cscView;
	public CSCViewMain cscMain;
	public GameInfo gInfo;
	
	@Override
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
        
        cscMain = new CSCViewMain( this, gInfo);
        cscView = new CSCView( this, cscMain );
        cscView.setRenderer( new SurfaceClass(cscMain) );
        
        FrameLayout r = new FrameLayout(this);
        r.addView(cscView, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        
        pv = new CSCPaperView(this, gInfo.ScreenXsize, gInfo.ScreenYsize, cscMain);
        cscMain.pv = pv;        
        
        r.addView(pv, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        
        setContentView( r );
	}

}