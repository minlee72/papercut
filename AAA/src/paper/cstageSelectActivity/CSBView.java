package paper.cstageSelectActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class CSBView extends GLSurfaceView
{
	Context mContext;	
	public CSBViewMain sImg;
	
	public CSBView( Context context, CSBViewMain img )
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
				case	MotionEvent.ACTION_MOVE :
						{
							//sImg.gInfo.ListViewActionMove( sImg.TouchX, sImg.TouchY );
						}
				case	MotionEvent.ACTION_POINTER_DOWN	:
						{
							//sImg.PushButton( true );
						}
						break;
		
				case	MotionEvent.ACTION_UP :
						{
							sImg.actionUp();
						}
						break;
				case	MotionEvent.ACTION_POINTER_UP :
						{

						}
						break;
			}
		}
      	return true;
    }
}