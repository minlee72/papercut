package paper.gameActivity;

import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import bayaba.engine.lib.*;

import com.example.papercult.*;

public class GameActivity extends Activity {
	public PaperView pv;
	public BGView bgView;
	public BGViewMain bgMain;
	public FGView fgView;
	public FGViewMain fgMain;
	public GameInfo gInfo;
//	public Button resetBtn;
//	public Button manageBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        /*
        gInfo = new GameInfo( 800, 480 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        */
//        pv = new PaperView(this, gInfo.ScreenXsize, gInfo.ScreenYsize);
        pv = new PaperView(this, super.getWindowManager().getDefaultDisplay().getWidth(), 
        		super.getWindowManager().getDefaultDisplay().getHeight());
        /*
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
        pv.bgView = bgView;
        pv.fgView = fgView;
       
        r.addView(bgView, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        r.addView(pv, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        r.addView(fgView, (int)gInfo.ScreenXsize, (int)gInfo.ScreenYsize);
        */
        
        FrameLayout r = new FrameLayout(this);
        r.addView(pv, super.getWindowManager().getDefaultDisplay().getWidth(), 
        		super.getWindowManager().getDefaultDisplay().getHeight());
        
        /*
        resetBtn = new Button(this);
        resetBtn.setText("Reset");
        
        manageBtn = new Button(this);
        manageBtn.setText("Manage Mode");
        
        addListenerOnButton();
        */
        
//        r.addView(pv.resetBtn);
//        r.addView(pv.manageBtn);
        
        setContentView( r );
	}
/*	
	public void addListenerOnButton(){
		resetBtn.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) { 
				pv.resetPolygon();
			}
		});
		manageBtn.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) { 
				if(pv.isManageMode == false){
					pv.isManageMode = true;
					manageBtn.setText("User Mode");
				}
				else{
					pv.isManageMode = false;
					manageBtn.setText("Manage Mode");
				}
			}
		});
	}*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
