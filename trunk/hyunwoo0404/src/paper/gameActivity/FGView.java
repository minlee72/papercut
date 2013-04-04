package paper.gameActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class FGView extends GLSurfaceView
{
	Context mContext;	
	public FGViewMain sImg;
	private PaperView view;
	
	public FGView( Context context, FGViewMain img, PaperView v )
	{
		super( context );
		//setFocusable( true );
		view = v;
		mContext = context;
		sImg = img;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
    {
		synchronized ( sImg.mGL )
		{
			view.onTouchEvent(event);
		}
      	return true;
    }
}