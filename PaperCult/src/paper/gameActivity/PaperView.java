package paper.gameActivity;

import java.util.Vector;

import paper.data.GameOption;
import paper.data.Paper;
import paper.data.Polygon;
import paper.data.Stage;
import paper.data.StageData;
import paper.gameActivity.BGViewMain.nbState;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.papercult.R;

public class PaperView extends View {
	int rgb;
	Paper paper;
	Stage sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	BGViewMain bgMain;
	boolean click = false;
	Context con;
	int curScore;
	int curRemain;
	int stageNum;
	Vibrator vibe;
	Toast clearToast;
	Toast failToast;
	GameActivity aActivity = (GameActivity)GameActivity.AActivity;
	
	private SoundPool SndPool;
	int soundBuf[] = new int[10];
	public PaperView(Context context, float scrWidth, float scrHeight, int stageIndex, BGViewMain bgvm) {
		super(context);
		stageNum = stageIndex;
		rgb = 0x40FFFF00;
		con = context;
		bgMain = bgvm;
		vibe = (Vibrator)con.getSystemService(Context.VIBRATOR_SERVICE);
		SndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundBuf[0] = SndPool.load(getContext(), R.raw.fold0, 1);
		soundBuf[1] = SndPool.load(getContext(), R.raw.fold1, 1);
		soundBuf[2] = SndPool.load(getContext(), R.raw.fold2, 1);
		
		paper = new Paper(scrWidth, scrHeight);
		sObj = StageData.getInstance().getStage(stageNum);
		curRemain = sObj.limit;
		bgMain.remain = sObj.limit;
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
				if(bgMain.checkRedrawBtn(event.getX(), event.getY())){
					vibe.vibrate(GameOption.vibePower);
					rgb = bgMain.getPaperColor();
					this.resetPolygon();
					paper.initHistory();
					curRemain = sObj.limit;
					bgMain.remain = sObj.limit;
					bgMain.motionInit();
					curScore=0;
					bgMain.setSnum(curScore);
					bgMain.setBarImg(curScore);
					bgMain.nb_state = nbState.invisible;
					return true;
				}
				else if(bgMain.checkBackBtn(event.getX(), event.getY())){
					if(paper.history.size()<1)
						return true;
					vibe.vibrate(GameOption.vibePower);
					int index = paper.history.size() - 1;
					paper.base = paper.history.get(index);
					paper.history.remove(index);
					paper.poly = (Vector<Polygon>)paper.base.clone();
					curRemain++;
					bgMain.incRemain(curRemain);
					curScore = sObj.calcScore(paper);
					bgMain.setSnum(curScore);
					bgMain.setBarImg(curScore);
					bgMain.nb_state = nbState.invisible;
					this.invalidate();
					return true;
				}
				else if(bgMain.checkNextBtn(event.getX(), event.getY())){
					bgMain.nb_state = nbState.invisible;
					stageNum++;
					if(stageNum>=(StageData.getInstance().list.size()-2))
						aActivity.onBackPressed();
					sObj = StageData.getInstance().getStage(stageNum);
					curRemain = sObj.limit;
					bgMain.remain = sObj.limit;
					curScore=0;
					bgMain.setSnum(curScore);
					bgMain.setBarImg(curScore);
					resetPolygon();
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
				paper.foldStart(touchStart, touchEnd);
				curScore = sObj.calcScore(paper);
				bgMain.setSnum(curScore);
				bgMain.setBarImg(curScore);
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(click == true){
				paper.foldEnd();
				if(curRemain>0){
					curRemain--;
					bgMain.decRemain(curRemain);
				}
				if(curRemain==0){
					if(curScore>sObj.score)
						sObj.score = curScore;
					if(curScore>69){
						bgMain.nb_state = nbState.visible;
						clearToast.show();
					}
					else{
						failToast.show();
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


