package paper.cgameActivity;

import com.example.papercult.R;
import bayaba.engine.lib.*;
import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class CGameActivity extends Activity {
	public CPaperView pv;
	public CGView cgView;
	public CGViewMain cgMain;
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
        
        cgMain = new CGViewMain( this, gInfo);
        cgView = new CGView( this, cgMain );
        cgView.setRenderer( new CGSurfaceClass(cgMain) );
        
        FrameLayout r = new FrameLayout(this);
        r.addView(cgView, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        
        Intent intent = getIntent();
        int stageNum = intent.getIntExtra("cstageNum", 3);
        
        pv = new CPaperView(this, gInfo.ScreenXsize, gInfo.ScreenYsize, stageNum, cgMain);
        cgMain.pv = pv;        
        
        r.addView(pv, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        
        setContentView( r );
	}
}
