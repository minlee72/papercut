package paper.stageSelectActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SBGView extends GLSurfaceView
{
	Context mContext;	
	public SBGViewMain sImg;
	
	public SBGView( Context context, SBGViewMain img )
	{
		super( context );
		setFocusable( true );
		
		mContext = context;
		sImg = img;
	}
}
