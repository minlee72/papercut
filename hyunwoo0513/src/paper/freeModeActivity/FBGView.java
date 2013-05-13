package paper.freeModeActivity;


import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class FBGView extends GLSurfaceView
{
	Context mContext;	
	public FBGViewMain fImg;
	
	public FBGView( Context context, FBGViewMain img )
	{
		super( context );
		setFocusable( true );
		
		mContext = context;
		fImg = img;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
    {
		final int action = event.getAction();
		
		synchronized ( fImg.mGL )
		{
			fImg.TouchX = event.getX() * fImg.gInfo.ScalePx;
			fImg.TouchY = event.getY() * fImg.gInfo.ScalePy;
			
			switch ( action & MotionEvent.ACTION_MASK )
			{
				case	MotionEvent.ACTION_DOWN	:
						{
							fImg.checkButton();
							return true;
						}
				case	MotionEvent.ACTION_MOVE :
						{
							//fImg.gInfo.ListViewActionMove( fImg.TouchX, fImg.TouchY );
						}
				case	MotionEvent.ACTION_POINTER_DOWN	:
						{
							//fImg.PushButton( true );
						}
						break;
		
				case	MotionEvent.ACTION_UP :
				case	MotionEvent.ACTION_POINTER_UP :
						{

						}
						break;
			}
		}
      	return true;
    }
}
