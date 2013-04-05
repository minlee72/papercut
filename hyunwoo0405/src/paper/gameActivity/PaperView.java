package paper.gameActivity;


import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;

public class PaperView extends View {
	Paper paper;
	Stage sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	public BGView bgView;
	public FGView fgView;
	boolean click = false;
	boolean isManageMode;
	Button resetBtn;
	Button manageBtn;
	
	public PaperView(Context context, float scrWidth, float scrHeight) {
		super(context);
		
		paper = new Paper(scrWidth, scrHeight);
		resetPolygon();
		isManageMode = false;
		resetBtn = new Button(this.getContext());
		resetBtn.setText("Reset");
		resetBtn.setX(10);
		resetBtn.setY(50);
		resetBtn.setWidth(50);
		resetBtn.setHeight(30);
		resetBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resetPolygon();
			}
		});
		
		
		manageBtn = new Button(this.getContext());
		manageBtn.setText("Manager Mode");
		manageBtn.setX(10);
		manageBtn.setY(100);
		manageBtn.setWidth(50);
		manageBtn.setHeight(30);
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
					Toast.makeText(this.getContext(), "here4", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(this.getContext(), "here6", Toast.LENGTH_SHORT).show();
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
				
				this.invalidate();
				return true;
			}
			return false;
		}
		else{
			if (event.getAction() == MotionEvent.ACTION_DOWN){
				touchStart.x = event.getX();
				touchStart.y = event.getY();
				this.invalidate();
				return true;
			}
			else if(event.getAction() == MotionEvent.ACTION_MOVE){
				touchStart.x = event.getX();
				touchStart.y = event.getY();
				this.invalidate();
				return true;
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
		Toast.makeText(this.getContext(), "here3", Toast.LENGTH_SHORT).show();
		if(isManageMode == true){
			Paint pnt = new Paint();
			pnt.setColor(Color.BLUE);
			pnt.setAntiAlias(true);
			pnt.setStrokeWidth(3);
			pnt.setColor(Color.BLACK);
			pnt.setStyle(Paint.Style.STROKE);
			
			canvas.drawPoint(touchStart.x, touchStart.y, pnt);
			Toast.makeText(this.getContext(), "here2", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(this.getContext(), "here", Toast.LENGTH_SHORT).show();
			paper.draw(canvas);
		}
	}	
}


