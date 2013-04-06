package paper.stageSelectActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.Toast;

public class StageListListener implements OnTouchListener {
	ListView lv;
	ScrollMove scm;
	
	public StageListListener(ListView l){
		lv = l;
		scm = new ScrollMove(lv);
	}
			
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			ListView lv = (ListView)v;
			int index = lv.getFirstVisiblePosition();
			scm.set(index+1, 200);
			scm.run();
			return true;
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
