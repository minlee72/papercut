package paper.stageSelectActivity;

import java.util.Vector;

import paper.data.CStageData;
import paper.data.Stage;
import paper.data.StageData;
import paper.stageSelectActivity.SBGViewMain.malState;
import paper.stageSelectActivity.SBGViewMain.scrState;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class StageListListener implements OnTouchListener {
	ListView lv;
	ScrollMove scm;
	int scrHeight;
	SBGViewMain sbgMain;
	
	public StageListListener(SBGViewMain m, ListView l, int height){
		lv = l;
		scm = new ScrollMove(lv);
		scrHeight = height;
		sbgMain = m;
	}
			
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(sbgMain.s_state != scrState.close)
			return false;
		if(event.getAction() == MotionEvent.ACTION_UP){
			ListView lv = (ListView)v;
			Vector<Stage> list = StageData.getInstance().list;
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
			if((setIndex==0)||(setIndex==1)||(setIndex==(list.size()-2))||(setIndex==(list.size()-1)
					||list.get(setIndex).locked==true))
				sbgMain.m_state = malState.toInvisible;
			else{
				score = list.get(setIndex).score;
				sbgMain.setSnum(score);
				sbgMain.setBarImg(score);
				sbgMain.m_state = malState.toVisible;
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			sbgMain.m_state = malState.toInvisible;
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
