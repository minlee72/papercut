package paper.gameActivity;


import android.content.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.example.papercult.*;

public class PaperView extends View {
	SoundTimer timer = new SoundTimer();
	Paper paper;
	Stage sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	public BGView bgView;
	public FGView fgView;
	boolean click = false;
	boolean isManageMode = false;
	Button resetBtn;
	Button manageBtn;
	private SoundPool SndPool;
	int soundBuf[] = new int[10];
	
	public PaperView(Context context, float scrWidth, float scrHeight) {
		super(context);
		SndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundBuf[0] = SndPool.load(getContext(), R.raw.fold0, 1);
		soundBuf[1] = SndPool.load(getContext(), R.raw.fold1, 1);
		soundBuf[2] = SndPool.load(getContext(), R.raw.fold2, 1);
		
		paper = new Paper(scrWidth, scrHeight);
		/*
		stagePolygon poly = new stagePolygon();
		poly.add(0, 0);
		poly.add((float)1, (float)0);
		poly.add((float)0, (float)1);
		
		stagePolygon polyl = new stagePolygon();
		polyl.add((float)-0.1, (float)-0.1);
		polyl.add((float)1.2, (float)-0.1);
		polyl.add((float)-0.1, (float)1.2);
		
		sObj = new Stage("test", 1, poly, polyl);
		sObj.setStage(paper);
		*/
		resetPolygon();
		
		resetBtn = new Button(context);
		resetBtn.setText("Reset");
		resetBtn.setX(10);
		resetBtn.setY(30);
		resetBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resetPolygon();
			}
		});
		
		
		manageBtn = new Button(context);
		manageBtn.setText("Manager Mode");
		manageBtn.setX(10);
		manageBtn.setY(100);
		manageBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isManageMode == false){
					isManageMode = true;
					resetBtn.setText("User Mode");
				}
				else{
					isManageMode = false;
					resetBtn.setText("Manager Mode");
				}
			}
		});	
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(isManageMode == false){
			if (event.getAction() == MotionEvent.ACTION_DOWN)
			{
				if(click == false){
					click = true;
					touchStart.x = event.getX();
					touchStart.y = event.getY();
					timer.setOn();
					timer.sendEmptyMessageDelayed(0, 500);
				}
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
				paper.foldEnd();
				timer.setOff();
				/*
				if (sObj.clearCheck(paper, 90, 20) == true){
				//	Toast.makeText(this.getContext(), "Clear", Toast.LENGTH_SHORT).show();
				}
				else{
				//	Toast.makeText(this.getContext(), "no", Toast.LENGTH_SHORT).show();	
					bgView.sImg.quake(1000, 5, 5);
				}
				*/
				click = false;
				
				
				return true;
			}
			return false;
		}
		else{
			if (event.getAction() == MotionEvent.ACTION_DOWN){
				
			}
			else if(event.getAction() == MotionEvent.ACTION_MOVE){
				
			}
			else if(event.getAction() == MotionEvent.ACTION_UP){
				
			}
		}
		return false;
		
	}
	public void resetPolygon(){
		paper.reset();
		this.invalidate();
	}
	
	public void onDraw(Canvas canvas){
//		sObj.innerPolyDraw(canvas);
//		sObj.outerPolyDraw(canvas);
		paper.draw(canvas);
		if(isManageMode == true){
			
		}
	}
	
	/*
	private void sndPlay(){
		 int x = (int)(touchStart.x - touchEnd.x);
		 int y = (int)(touchStart.y - touchEnd.y);
		 int result =  (int) Math.sqrt(x * x + y * y);
		 String str = " " + result;
		 SndPool.play(soundBuf[0], 1, 1, 0, 0, 1);
		 Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
	}
	*/
	
	private class SoundTimer extends Handler{
		private boolean isON = false;
		private boolean isFirst = true;
		PointF start = new PointF();
		PointF end = new PointF();
		Toast toast = null;
		
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
			String str = "(" + (int)start.x + "," + (int)start.y + ")(" + (int)end.x + "," + (int)end.y + ")" + result;
		
			if (toast == null){
				toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
			}
			else{
				toast.setText(str);
			}
			toast.show();
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


