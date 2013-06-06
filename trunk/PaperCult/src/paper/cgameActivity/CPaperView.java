package paper.cgameActivity;

import java.util.Vector;

import paper.cgameActivity.CGViewMain.ebState;
import paper.data.CStageData;
import paper.data.GameOption;
import paper.data.Paper;
import paper.data.Polygon;
import paper.data.Stage;
import paper.data.StageData;
import paper.sfx.Sound;
import paper.sfx.Vibe;

import com.example.papercult.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.FloatMath;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CPaperView extends View {
	int rgb;
	Paper paper;
	Stage sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	CGViewMain cgMain;
	boolean click = false;
	Context con;
	int curScore;
	int curRemain;
	Toast clearToast;
	Toast failToast;
	boolean paperFold = false;
	CGameActivity aActivity = (CGameActivity)CGameActivity.AActivity;
	
	public CPaperView(Context context, float scrWidth, float scrHeight, int stageIndex, CGViewMain bgvm) {
		super(context);
		rgb = 0x40FFFF00;
		con = context;
		cgMain = bgvm;
		
		paper = new Paper(scrWidth, scrHeight);
		sObj = CStageData.getInstance().getStage(stageIndex);
		sObj.setOuterPolygon();
		sObj.setInspPolyPoints(paper);
		curRemain = sObj.limit;
		cgMain.remain = sObj.limit;
		paper.reset();
		curScore=0;
		
		FrameLayout ctframe = (FrameLayout)View.inflate(con, R.layout.cleartoast_layout, null);
		clearToast = new Toast(con);
		clearToast.setDuration(Toast.LENGTH_LONG);
		clearToast.setGravity(Gravity.CENTER, 0, 0);
		clearToast.setView(ctframe);
		
		FrameLayout ftframe = (FrameLayout)View.inflate(con, R.layout.failtoast_layout, null);
		failToast = new Toast(con);
		failToast.setDuration(Toast.LENGTH_LONG);
		failToast.setGravity(Gravity.CENTER, 0, 0);
		failToast.setView(ftframe);
	}

	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				clearToast.cancel();
				failToast.cancel();
				if(cgMain.checkRedrawBtn(event.getX(), event.getY())){
					Vibe.play(con);
					Sound.playPaperSound(con);
					rgb = cgMain.getPaperColor();
					this.resetPolygon();
					paper.initHistory();
					curRemain = sObj.limit;
					cgMain.remain = sObj.limit;
					cgMain.motionInit();
					curScore=0;
					cgMain.setSnum(curScore);
					cgMain.setBarImg(curScore);
					cgMain.eb_state = ebState.invisible;
					return true;
				}
				else if(cgMain.checkBackBtn(event.getX(), event.getY())){
					if(paper.history.size()<1)
						return true;
					Vibe.play(con);
					int index = paper.history.size() - 1;
					paper.base = paper.history.get(index);
					paper.history.remove(index);
					paper.poly = (Vector<Polygon>)paper.base.clone();
					curRemain++;
					cgMain.incRemain(curRemain);
					curScore = sObj.calcScore(paper);
					cgMain.setSnum(curScore);
					cgMain.setBarImg(curScore);
					cgMain.eb_state = ebState.invisible;
					this.invalidate();
					return true;
				}
				else if(cgMain.checkExitBtn(event.getX(), event.getY())){
					cgMain.eb_state = ebState.invisible;
					aActivity.onBackPressed();
					return true;
				}
				if(curRemain<=0){
					return true;
				}
				click = true;
				touchStart.x = event.getX();
				touchStart.y = event.getY();
				touchEnd.x = touchStart.x;
				touchEnd.y = touchStart.y;
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(click == true){
				touchEnd.x = event.getX();
				touchEnd.y = event.getY();
				paperFold = paper.foldStart(touchStart, touchEnd);
				curScore = sObj.calcScore(paper);
				cgMain.setSnum(curScore);
				cgMain.setBarImg(curScore);
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if((click == true)){
				if(paperFold == true){
					paper.foldEnd();
					if(curRemain>0){
						curRemain--;
						cgMain.decRemain(curRemain);
					}
					if(curRemain==0){
						if(curScore>sObj.score)
							sObj.score = curScore;
						if(curScore>69){
							clearToast.show();
							cgMain.eb_state = ebState.visible;
							Sound.playClearSound(con);
						}
						else{
							failToast.show();
							Sound.playFailSound(con);
						}
					}
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
	
}


