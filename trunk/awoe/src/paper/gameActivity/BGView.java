package paper.gameActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class BGView extends GLSurfaceView
{
	Context mContext;	
	public BGViewMain sImg;
	
	public BGView( Context context, BGViewMain img )
	{
		super( context );
		setFocusable( true );
		
		mContext = context;
		sImg = img;
	}

	public checkButton(int x, int y){
		sImg.bt.CheckPos(x, y);
	}

}
