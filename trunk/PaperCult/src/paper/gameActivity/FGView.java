package paper.gameActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class FGView extends GLSurfaceView
{
	Context mContext;	
	public FGViewMain sImg;
	
	public FGView( Context context, FGViewMain img, PaperView v )
	{
		super( context );
		mContext = context;
		sImg = img;
	}
}