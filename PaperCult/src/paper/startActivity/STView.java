package paper.startActivity;

import paper.stageSelectActivity.SBGViewMain;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class STView extends GLSurfaceView
{
	Context mContext;	
	public STViewMain sImg;
	
	public STView( Context context, STViewMain img )
	{
		super( context );
		setFocusable( true );
		
		mContext = context;
		sImg = img;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
    {
		final int action = event.getAction();
		
		synchronized ( sImg.mGL )
		{
			sImg.TouchX = event.getX() * sImg.gInfo.ScalePx;
			sImg.TouchY = event.getY() * sImg.gInfo.ScalePy;
			
			switch ( action & MotionEvent.ACTION_MASK )
			{
				case	MotionEvent.ACTION_DOWN	:
						{
							sImg.actionDown();
							return true;
						}

				case	MotionEvent.ACTION_UP :
						{
							sImg.actionUp();
							return true;
						}

			}
		}
      	return true;
    }
}