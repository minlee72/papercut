package paper.cstageSelectActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;

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
}
