package paper.cstageCreateActivity;

import java.util.Vector;

import paper.data.CStageData;
import paper.data.Paper;
import paper.data.Polygon;
import paper.data.Stage;
import paper.data.StageData;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.papercult.R;

public class CSCPaperView extends View {
	int rgb;
	SoundTimer timer = new SoundTimer();
	Paper paper;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	CSCViewMain cscMain;
	boolean click = false;
	Context con;
	Stage stg;
	
	private SoundPool SndPool;
	int soundBuf[] = new int[10];
	public CSCPaperView(Context context, float scrWidth, float scrHeight, int stageIndex, CSCViewMain bgvm) {
		super(context);
		rgb = 0x40FFFF00;
		con = context;
		cscMain = bgvm;
		SndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundBuf[0] = SndPool.load(getContext(), R.raw.fold0, 1);
		soundBuf[1] = SndPool.load(getContext(), R.raw.fold1, 1);
		soundBuf[2] = SndPool.load(getContext(), R.raw.fold2, 1);
		
		paper = new Paper(scrWidth, scrHeight);
		paper.reset();
		
		float lineLength = Math.min(scrWidth, scrHeight); 
        lineLength = lineLength * (float)0.8; 
		stg = new Stage(paper.baseRect, lineLength);
	}

	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				if(cscMain.checkRedrawBtn(event.getX(), event.getY())){
					rgb = cscMain.getPaperColor();
					this.resetPolygon();
					stg.limit = 0;
					cscMain.decRemain(stg.limit);
					stg.setInnerPolygon(paper.baseRect);
					paper.initHistory();
					cscMain.motionInit();
					this.invalidate();
					return true;
				}
				else if(cscMain.checkBackBtn(event.getX(), event.getY())){
					if(paper.history.size()<1)
						return true;
					if(stg.limit==0)
						return true;
					stg.limit--;
					cscMain.decRemain(stg.limit);
					int index = paper.history.size() - 1;
					paper.base = paper.history.get(index);
					paper.history.remove(index);
					paper.poly = (Vector<Polygon>)paper.base.clone();
					stg.setInnerPolygon(paper.getStagePoint());
					this.invalidate();
					return true;
				}
				else if(cscMain.checkSaveBtn(event.getX(), event.getY())){
					onInputNameDialog();
					return true;
				}
				if(stg.limit == 7)
					return true;
				click = true;
				touchStart.x = event.getX();
				touchStart.y = event.getY();
				touchEnd.x = touchStart.x;
				touchEnd.y = touchStart.y;
				timer.setOn();
				timer.sendEmptyMessageDelayed(0, 500);
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(click == true){
				touchEnd.x = event.getX();
				touchEnd.y = event.getY();
				paper.foldStart(touchStart, touchEnd);
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(click == true){
				paper.foldEnd();
				timer.setOff();
				stg.setInnerPolygon(paper.getStagePoint());
				stg.limit++;
				cscMain.incRemain(stg.limit);
				click = false;
				this.invalidate();
			}
			return true;
		}
		return false;
	}
	public void resetPolygon(){
		paper.reset();
	}
	
	public void onDraw(Canvas canvas){
		paper.draw(canvas, rgb);
		stg.innerPolyDraw(canvas);
	//	stg.outerPolyDraw(canvas);
	}
	
	public void onInputNameDialog(){
		final LinearLayout linear = (LinearLayout)View.inflate(con, R.layout.nameinputdialog, null);
		
		new AlertDialog.Builder(con)
		.setIcon(R.drawable.c_clear)
		.setView(linear)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText iname = (EditText)linear.findViewById(R.id.inputname);
				stg.setOuterPolygon();
				stg.name = iname.getText().toString();
				stg.score = 0;
				CStageData.getInstance().addStage(stg);
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				;
			}
		})
		.show();
	}
	
	private class SoundTimer extends Handler{
		private boolean isON = false;
		private boolean isFirst = true;
		PointF start = new PointF();
		PointF end = new PointF();
		
		public void handleMessage(Message msg){
			if (isFirst == true){
				isFirst = false;
				start.x = touchStart.x;
				start.y = touchStart.y;
			}
			end.x = touchEnd.x;
			end.y = touchEnd.y;
			
			int x = (int)(start.x - end.x);
			int y = (int)(start.y - end.y);
			int result =  (int) Math.sqrt(x * x + y * y);
		
			if(result < 10){
		
			}
			else if(result < 30){
				SndPool.play(soundBuf[0], 1, 1, 0, 0, 1);
			}
			else if(result < 100){
				SndPool.play(soundBuf[1], 1, 1, 0, 0, 1);
			}
			else {
				SndPool.play(soundBuf[2], 1, 1, 0, 0, 1);
			}
			
			start.x = end.x;
			start.y = end.y;
			
			if(isON == true)
				this.sendEmptyMessageDelayed(0, 1000);
		}
		
		public void setOn(){
			isON = true;
		}
		
		public void setOff(){
			isON = false;
			isFirst = true;
		}
	}
}
