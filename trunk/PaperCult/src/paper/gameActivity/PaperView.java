package paper.gameActivity;

import java.util.Vector;

import paper.data.Paper;
import paper.data.Polygon;
import paper.data.Stage;
import paper.data.StageData;
import com.example.papercult.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PaperView extends View {
	int rgb;
	SoundTimer timer = new SoundTimer();
	Paper paper;
	Stage sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	BGViewMain bgMain;
	boolean click = false;
	Context con;
	Toast toast = null;
	int curScore;
	
	private SoundPool SndPool;
	int soundBuf[] = new int[10];
	public PaperView(Context context, float scrWidth, float scrHeight, int stageIndex, BGViewMain bgvm) {
		super(context);
		rgb = 0x40FFFF00;
		con = context;
		bgMain = bgvm;
		SndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundBuf[0] = SndPool.load(getContext(), R.raw.fold0, 1);
		soundBuf[1] = SndPool.load(getContext(), R.raw.fold1, 1);
		soundBuf[2] = SndPool.load(getContext(), R.raw.fold2, 1);
		
		paper = new Paper(scrWidth, scrHeight);
		sObj = StageData.getInstance().getStage(stageIndex);
		sObj.current = sObj.limit;
		bgMain.remain = sObj.limit;
		paper.reset();
	}

	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				if(bgMain.checkRedrawBtn(event.getX(), event.getY())){
					rgb = bgMain.getPaperColor();
					this.resetPolygon();
					paper.initHistory();
					sObj.current = sObj.limit;
					bgMain.remain = sObj.limit;
					bgMain.motionInit();
					return true;
				}
				else if(bgMain.checkBackBtn(event.getX(), event.getY())){
					if(paper.history.size()<1)
						return true;
					
					int index = paper.history.size() - 1;
					paper.base = paper.history.get(index);
					paper.history.remove(index);
					paper.poly = (Vector<Polygon>)paper.base.clone();
					sObj.current++;
					bgMain.incRemain(sObj.current);
					this.invalidate();
					return true;
				}
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
				curScore = sObj.inMoveClearCheck(paper);
				
				if(toast == null)
					toast = Toast.makeText(con, ""+curScore, Toast.LENGTH_SHORT);
				else
					toast.setText(""+curScore);
				toast.show();
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(click == true){
				paper.foldEnd();
				
				if(sObj.current>0){
					sObj.current--;
					bgMain.decRemain(sObj.current);
				}
				
				timer.setOff();
			
				if (sObj.clearCheck(paper) > 80){
					sObj.score = sObj.clearCheck(paper);
				}
				else{
				
				}
				
				click = false;
			}
			return true;
		}
		return false;
	}
	public void resetPolygon(){
		paper.reset();
		this.invalidate();
	}
	
	public void onDraw(Canvas canvas){
		sObj.innerPolyDraw(canvas);
		//sObj.outerPolyDraw(canvas);
		paper.draw(canvas, rgb);
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


