package com.example.papercult;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class GLView extends GLSurfaceView
{
	Context mContext;	
	public GameMain sImg;
	public PaperView pv;
	
	public GLView( Context context, GameMain img )
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
							if(sImg.heroObj.CheckPos((int)sImg.TouchX, (int)sImg.TouchY))
								pv.resetPolygon();
							else
								pv.reTouchEvent(event);
							//sImg.gInfo.ListViewActionDown( sImg.TouchX, sImg.TouchY );	
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
				case	MotionEvent.ACTION_POINTER_UP :
						{

						}
						break;
			}
		}
      	return true;
    }

}
