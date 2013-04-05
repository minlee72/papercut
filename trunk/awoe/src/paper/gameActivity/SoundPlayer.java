package paper.gameActivity;

import android.os.Handler;
import android.os.Message;

public class SoundPlayer {
	int count = 0;
	Timer timer = new Timer();
	
	public void start(){
		
	}
	
	private class Timer extends Handler{
		private boolean isON = false;
		
		public void handleMessage(Message msg){
			count++;
			if(isON == true)
				this.sendEmptyMessageDelayed(0, 3000);
		}
		
		public void setOn(){
			isON = true;
		}
		
		public void setOff(){
			isON = true;
		}
	}
}
