package com.example.papercult;

import bayaba.engine.lib.*;
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
	public BGViewMain sImg;
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
        
        sImg = new BGViewMain( this, gInfo );
        bgView = new BGView( this, sImg );
        bgView.setRenderer( new SurfaceClass(sImg) );
        
        FrameLayout r = new FrameLayout(this);
        pv = new PaperView(this);
        
        bgView.pv = pv;
        pv.bgView = bgView;
       
        
        r.addView(pv, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        r.addView(bgView, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        
        
        setContentView( r );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
