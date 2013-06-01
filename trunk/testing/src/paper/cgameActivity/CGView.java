package paper.cgameActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class CGView extends GLSurfaceView
{
	Context mContext;	
	public CGViewMain sImg;
	
	public CGView( Context context, CGViewMain img )
	{
		super( context );
		setFocusable( true );
		
		mContext = context;
		sImg = img;
	}
}
