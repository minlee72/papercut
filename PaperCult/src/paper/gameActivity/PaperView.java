package paper.gameActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PaperView extends View {
	Paper paper;
	Stage sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	public BGView bgView;
	public FGView fgView;
	boolean click = false;
	
	public PaperView(Context context, float scrWidth, float scrHeight) {
		super(context);
		
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
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				click = true;
				touchStart.x = event.getX();
				touchStart.y = event.getY();
				fgView.sImg.test = true;
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
			fgView.sImg.test = false;
			if (sObj.clearCheck(paper, 90, 20) == true){
				Toast.makeText(this.getContext(), "Clear", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(this.getContext(), "no", Toast.LENGTH_SHORT).show();	
				bgView.sImg.quake(1000, 5, 5);
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
	
	public void onDraw(Canvas canvas){
		sObj.innerPolyDraw(canvas);
		sObj.outerPolyDraw(canvas);
		paper.draw(canvas);
	}
}


