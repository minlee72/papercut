package paper.cstageCreateActivity;

import paper.gameActivity.BGViewMain;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class CSCView extends GLSurfaceView
{
	Context mContext;	
	public CSCViewMain sImg;
	
	public CSCView( Context context, CSCViewMain img )
	{
		super( context );
		setFocusable( true );
		
		mContext = context;
		sImg = img;
	}
}

