package paper.cstageSelectActivity;

import java.util.Vector;

import paper.cstageSelectActivity.CSBViewMain.malState;
import paper.cstageSelectActivity.CSBViewMain.scrState;
import paper.data.CStageData;
import paper.data.Stage;
import paper.data.StageData;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class CStageListListener implements OnTouchListener {
	ListView lv;
	ScrollMove scm;
	int scrHeight;
	CSBViewMain csbMain;
	
	public CStageListListener(CSBViewMain m, ListView l, int height){
		lv = l;
		scm = new ScrollMove(lv);
		scrHeight = height;
		csbMain = m;
	}
			
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(csbMain.s_state != scrState.close)
			return false;
		if(event.getAction() == MotionEvent.ACTION_UP){
			ListView lv = (ListView)v;
			Vector<Stage> list = CStageData.getInstance().list;
			int index = lv.getFirstVisiblePosition();
			int endIndex = lv.getLastVisiblePosition();
			int setIndex;
			int score;
			if(endIndex == lv.getCount()-1){
				setIndex = endIndex-2;
				scm.set(setIndex, (scrHeight/8)*3);
				scm.run();
			}
			else{
				setIndex = index+2;
				scm.set(setIndex, (scrHeight/8)*3);
				scm.run();
			}
			if((setIndex==0)||(setIndex==1)||(setIndex==(list.size()-2))||(setIndex==(list.size()-1)))
				csbMain.m_state = malState.toInvisible;
			else{
				score = list.get(setIndex).score;
				csbMain.setSnum(score);
				csbMain.setBarImg(score);
				csbMain.m_state = malState.toVisible;
			}
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			csbMain.m_state = malState.toInvisible;
		}
		return false;
	}

	class ScrollMove implements Runnable{
		int index;
		int offset;
		ListView lv;
		
		ScrollMove(ListView l){
			this.lv = l;
		}
		public void set(int index, int offset){
			this.index = index;
			this.offset = offset;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			lv.smoothScrollToPositionFromTop(index, offset);
		}
	}
}
