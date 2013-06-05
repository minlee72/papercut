package paper.cstageCreateActivity;

import java.util.Vector;

import paper.cstageCreateActivity.CSCViewMain.sbState;
import paper.data.CStageData;
import paper.data.GameOption;
import paper.data.Paper;
import paper.data.Polygon;
import paper.data.Stage;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.papercult.R;

public class CSCPaperView extends View {
	int rgb;
	Paper paper;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	CSCViewMain cscMain;
	boolean click = false;
	Context con;
	Stage stg;
	Vibrator vibe;
	boolean paperFold = false;
	
	private SoundPool SndPool;
	int soundBuf[] = new int[10];
	public CSCPaperView(Context context, float scrWidth, float scrHeight, CSCViewMain bgvm) {
		super(context);
		rgb = 0x40FFFF00;
		con = context;
		cscMain = bgvm;
		vibe = (Vibrator)con.getSystemService(Context.VIBRATOR_SERVICE);
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
					vibe.vibrate(GameOption.vibePower);
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
					vibe.vibrate(GameOption.vibePower);
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
					if(stg.limit==0)
						return true;
					vibe.vibrate(GameOption.vibePower);
					cscMain.sb_state = sbState.toClose;
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
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(click == true){
				touchEnd.x = event.getX();
				touchEnd.y = event.getY();
				paperFold = paper.foldStart(touchStart, touchEnd);
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(click == true){
				if(paperFold == true){
					paper.foldEnd();
					stg.setInnerPolygon(paper.getStagePoint());
					stg.setOuterPolygon();
					stg.limit++;
					cscMain.incRemain(stg.limit);
				}
				click = false;
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
		if(click==false){
			stg.innerPolyDraw(canvas);
			stg.outerPolyDraw(canvas);
		}		
	}
	
	public void onInputNameDialog(){
		final LinearLayout linear = (LinearLayout)View.inflate(con, R.layout.nameinputdialog, null);
		
		Typeface ft = Typeface.createFromAsset(con.getAssets(), "font.ttf");
		TextView tx = (TextView)linear.findViewById(R.id.inputnametext);
		tx.setTypeface(ft);
		
		AlertDialog.Builder db = new AlertDialog.Builder(con);
		db.setView(linear)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText iname = (EditText)linear.findViewById(R.id.inputname);
				Stage saveStg = new Stage(stg);
				saveStg.setInnerStgPolygon(paper);
				saveStg.name = iname.getText().toString();
				if(saveStg.name.isEmpty())
					saveStg.name = "이름 없음";
				CStageData.getInstance().addStage(saveStg);
				
				InputMethodManager imm = (InputMethodManager)con.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(iname.getWindowToken(), 0);
				
				FrameLayout frame = (FrameLayout)View.inflate(con, R.layout.savetoast_layout, null);
				Toast toast = new Toast(con);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.setView(frame);
				toast.show();
				cscMain.sb_state = sbState.open;
			}
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText et = (EditText)linear.findViewById(R.id.inputname);
				InputMethodManager imm = (InputMethodManager)con.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
				cscMain.sb_state = sbState.open;
			}
		});
		
		AlertDialog md = db.create();
		md.setCancelable(false);
		md.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		md.setOnShowListener(new OnShowListener(){
			@Override
			public void onShow(DialogInterface dialog) {
				EditText et = (EditText)linear.findViewById(R.id.inputname);
				InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
			}
			
		});
		md.show();
	}
}
