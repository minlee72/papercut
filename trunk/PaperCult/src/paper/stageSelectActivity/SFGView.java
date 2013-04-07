package paper.stageSelectActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class SFGView extends View {
	int scrWidth;
	int scrHeight;
	
	public SFGView(Context context, int width, int height) {
		super(context);
		scrWidth = width;
		scrHeight = height;
	}

	public void onDraw(Canvas canvas){
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0x40000000);
		canvas.drawRect(0, 0, scrWidth, (scrHeight/8)*3, paint);
		canvas.drawRect(0,(scrHeight/8)*5, scrWidth, scrHeight, paint);
	}

}
