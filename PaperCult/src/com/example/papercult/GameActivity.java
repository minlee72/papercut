package com.example.papercult;

import bayaba.engine.lib.*;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class GameActivity extends Activity {
	public PaperView pv;
	public BGView bgView;
	public BGViewMain bgMain;
	public FGView fgView;
	public FGViewMain fgMain;
	public GameInfo gInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        gInfo = new GameInfo( 800, 480 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        
        pv = new PaperView(this);
        
        bgMain = new BGViewMain( this, gInfo);
        bgView = new BGView( this, bgMain );
        bgView.setRenderer( new BGSurfaceClass(bgMain) );
        
        fgMain = new FGViewMain(this, gInfo);
        fgView = new FGView(this, fgMain, pv);
        
        fgView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        fgView.setRenderer( new FGSurfaceClass(fgMain) );
        fgView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        fgView.setZOrderOnTop(true);
        
        FrameLayout r = new FrameLayout(this);
        bgView.pv = pv;
        pv.bgView = bgView;
        pv.fgView = fgView;
       
        r.addView(bgView, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        r.addView(pv, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        r.addView(fgView, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
       
        
        setContentView( r );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
