package paper.gameActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.widget.*;

public class BGView extends GLSurfaceView
{
	Context mContext;	
	public BGViewMain sImg;
	public PaperView pv;
	
	public BGView( Context context, BGViewMain img )
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
							if(sImg.bt.CheckPos((int)sImg.TouchX, (int)sImg.TouchY)){
								pv.resetPolygon();
							}
							else if(sImg.slime.CheckPos((int)sImg.TouchX, (int)sImg.TouchY)){
								if(pv.isManageMode){
									pv.isManageMode = false;
									Toast.makeText(this.getContext(), ""+pv.isManageMode, Toast.LENGTH_SHORT).show();
								}
								else{
									pv.isManageMode = true;
									Toast.makeText(this.getContext(), ""+pv.isManageMode, Toast.LENGTH_SHORT).show();
								}
							}
							else{
								pv.reTouchEvent(event);
							}
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
			return true;
		}
      	
    }
	/*
	public void checkButton(PaperView pv, int x, int y){
		if(sImg.bt.CheckPos(x, y)){
			pv.resetPolygon();
		}
		else if(sImg.slime.CheckPos(x, y)){
			if(pv.isManageMode){
				pv.isManageMode = false;
			}
			else{
				pv.isManageMode = true;
			}
			
		}
		
	}
	*/
}
