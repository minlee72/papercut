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
	boolean click = false;
	boolean isManageMode = false;
	float scrWidth;
	float scrHeight;
	
	private SoundPool SndPool;
	int soundBuf[] = new int[10];
	
	public PaperView(Context context, float scrWidth, float scrHeight) {
		super(context);
		SndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundBuf[0] = SndPool.load(getContext(), R.raw.fold0, 1);
		soundBuf[1] = SndPool.load(getContext(), R.raw.fold1, 1);
		soundBuf[2] = SndPool.load(getContext(), R.raw.fold2, 1);
		
		paper = new Paper(scrWidth, scrHeight);
		
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
		resetPolygon();
		
		this.scrWidth = scrWidth;
		this.scrHeight = scrHeight;
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				bgView.onTouchEvent(event);
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			
			if(isManageMode == true){
				touchStart.x = event.getX();
				touchStart.y = event.getY();
			}
			else{
				if(click == true){
					touchEnd.x = event.getX();
					touchEnd.y = event.getY();
					paper.foldStart(touchStart, touchEnd);
				}
			}
			this.invalidate();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(isManageMode == false){
				paper.foldEnd();	
			}
			else{
				float tempX = 0;
				float tempY = 0;
				
//				Toast.makeText(this.getContext(), "X :"+((touchStart.x-paper.getLeftTop().x)/paper.getWidth())+
//						"\nY :"+((touchStart.y-paper.getLeftTop().y)/paper.getHeight()),
//						Toast.LENGTH_SHORT).show();
				
				Polygon polygon = new Polygon();
				polygon.add(new PointF(touchStart.x-10, touchStart.y-10));
				polygon.add(new PointF(touchStart.x+10, touchStart.y-10));
				polygon.add(new PointF(touchStart.x+10, touchStart.y+10));
				polygon.add(new PointF(touchStart.x-10, touchStart.y+10));
				
				for(int i=0; i<paper.base.size(); i++){
					for(int j=0; j<paper.base.get(i).pointVector.size(); j++){
						if(polygon.contains(paper.base.get(i).pointVector.get(j).x, paper.base.get(i).pointVector.get(j).y)){
							tempX = paper.base.get(i).pointVector.get(j).x;
							tempY = paper.base.get(i).pointVector.get(j).y;
							Toast.makeText(this.getContext(), "X :"+((tempX-paper.getLeftTop().x)/paper.getWidth())+
									"\nY :"+((tempY-paper.getLeftTop().y)/paper.getHeight()),
									Toast.LENGTH_SHORT).show();
							break;
						}
					}
				}				
			}
			click = false;
			return true;
		}
		return false;
	}
	
	public void resetPolygon(){
		paper.reset();
		this.invalidate();
	}
	
	public void reTouchEvent(MotionEvent event){
		if(click == false){
			click = true;
			touchStart.x = event.getX();
			touchStart.y = event.getY();
			if(isManageMode== true){
				this.invalidate();
			}
		}
	}
	
	public void onDraw(Canvas canvas){
		paper.draw(canvas);
		
		if(isManageMode == true){
			Paint pnt = new Paint();
			pnt.setAntiAlias(true);
			pnt.setStrokeWidth(20);
			pnt.setColor(Color.BLUE);
			canvas.drawPoint(touchStart.x, touchStart.y, pnt);
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


