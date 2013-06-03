package paper.gameActivity;

import paper.data.SurfaceClass;
import paper.stageSelectActivity.StageSelectActivity;

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

public class GameActivity extends Activity {
	public PaperView pv;
	public BGView bgView;
	public BGViewMain bgMain;
	public GameInfo gInfo;
	public static Activity AActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        AActivity = GameActivity.this;
        
        gInfo = new GameInfo( 800, 480 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        
        bgMain = new BGViewMain( this, gInfo);
        bgView = new BGView( this, bgMain );
        bgView.setRenderer( new SurfaceClass(bgMain) );
        
        FrameLayout r = new FrameLayout(this);
        r.addView(bgView, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        
        Intent intent = getIntent();
        int stageNum = intent.getIntExtra("stageNum", 3);
        
        pv = new PaperView(this, gInfo.ScreenXsize, gInfo.ScreenYsize, stageNum, bgMain);
        bgMain.pv = pv;        
        
        r.addView(pv, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        
        setContentView( r );
	}
}
