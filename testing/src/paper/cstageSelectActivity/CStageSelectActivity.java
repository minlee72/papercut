package paper.cstageSelectActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;


import paper.data.CStageData;
import paper.data.Stage;
import paper.data.StageData;
import paper.data.StagePolygon;

import com.example.papercult.R;
import bayaba.engine.lib.GameInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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



public class CStageSelectActivity extends Activity {
	enum d_state {toVisible, toInvisible, stop};
	public CSBView csbView;
	public CSBViewMain csbMain;
	public GameInfo gInfo;
	public ListView stageList;
	CStageAdapter adt;
	ScrTimer scrTimer;
	float alp;
	public static Activity AActivity;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setVolumeControlStream( AudioManager.STREAM_MUSIC );
        
        AActivity = CStageSelectActivity.this;
        
        gInfo = new GameInfo( 800, 480 );
        gInfo.ScreenXsize = super.getWindowManager().getDefaultDisplay().getWidth();
        gInfo.ScreenYsize = super.getWindowManager().getDefaultDisplay().getHeight();
        gInfo.SetScale();
        
        scrTimer = new ScrTimer();
        FrameLayout r = new FrameLayout(this);
        
        csbMain = new CSBViewMain( this, gInfo );
        csbView = new CSBView( this, csbMain );
        csbView.setRenderer( new CSBSurfaceClass(csbMain) );
        
        r.addView(csbView);
        
        CStageData.createInstance(this);
		adt = new CStageAdapter(this, CStageData.getInstance().list, (int)gInfo.ScreenYsize);
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		stageList = (ListView)inflater.inflate(R.layout.listview, r, false);
		
		csbMain.adt = adt;
		
		stageList.setAdapter(adt);
		stageList.setDivider(null);
		alp = 0;
		stageList.setAlpha(alp);
		
		stageList.setOnTouchListener(new CStageListListener(csbMain, stageList, (int)gInfo.ScreenYsize));
		csbMain.lv = stageList;
		
		FrameLayout.LayoutParams listviewParams = new FrameLayout.LayoutParams((int)((gInfo.ScreenXsize/10)*5.3), (int)gInfo.ScreenYsize);
		listviewParams.leftMargin = (int)((gInfo.ScreenXsize/10)*0.5);
		stageList.setLayoutParams(listviewParams);
		r.addView(stageList);
        setContentView( r );
       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onResume(){
		super.onResume();
		if(csbMain.scrAnime){
			adt.notifyDataSetChanged();
			alp = 0;
			stageList.setAlpha(alp);
			scrTimer.draw_state = d_state.toVisible;
			scrTimer.sendEmptyMessageDelayed(0, 1000);
			csbMain.startScr();
			int index = stageList.getFirstVisiblePosition() + 2;
			int score = CStageData.getInstance().getStage(index).score;
			csbMain.setBarImg(score);
			csbMain.setSnum(score);
		}
		else
			csbMain.scrAnime = true;
	}
	
	public void onDestroy(){
		super.onDestroy();
		
		Vector<Stage> stl = CStageData.getInstance().list;

		int size = stl.size()-4;
		Stage writeStg;
		
		if(size < 1)
			return;
		
		try {
			FileOutputStream fos;
			fos = this.openFileOutput("DataList", Context.MODE_PRIVATE);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeInt(size);
			for(int i=0; i<size; i++){
				int index = i + 2;
				writeStg = stl.get(index);
				oos.writeObject(writeStg);
			}
		    oos.flush();
		    oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class ScrTimer extends Handler{
   	 d_state draw_state = d_state.stop;
   	 public void handleMessage(Message msg){
     		 if(draw_state == d_state.toVisible){
     			if(alp<1){
     				alp = alp + 0.03f;
     				stageList.setAlpha(alp);
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			}
     			else{
     				alp = 1;
     				stageList.setAlpha(alp);
     				draw_state = d_state.stop;
     			}
     		}
     		 else if(draw_state == d_state.toInvisible){
     			 if(alp>0){
     				alp = alp - 0.03f;
     				stageList.setAlpha(alp);
     				this.sendEmptyMessageDelayed(0, 1000/30);
     			 }
     			 else{
     				alp = 0;
     				stageList.setAlpha(alp);
     				draw_state = d_state.stop;
     			 }
     		 }
     		 else{
     			 ;
     		 }
     	}
    }
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		csbMain.onActivityResult(requestCode, resultCode, data);
    }
}