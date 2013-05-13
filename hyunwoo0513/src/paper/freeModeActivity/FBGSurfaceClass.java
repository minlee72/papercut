package paper.freeModeActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FBGSurfaceClass implements android.opengl.GLSurfaceView.Renderer
{
	public FBGViewMain fImg;

	public FBGSurfaceClass( FBGViewMain dImg )
	{
		fImg = dImg;
	}
	
	@Override
	public void onSurfaceCreated( GL10 gl, EGLConfig config )
	{
		fImg.gInfo.BackB = 1f;
		fImg.gInfo.BackG = 1f;
		fImg.gInfo.BackR = 1f;
		gl.glClearColor( fImg.gInfo.BackR, fImg.gInfo.BackG, fImg.gInfo.BackB, 0.0f );
		gl.glClearDepthf( 1.0f );
		
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glHint( GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST );
		
		fImg.mGL = gl;
		fImg.LoadGameData();
	}
	
	@Override
	public void onSurfaceChanged( GL10 gl, int width, int height )
	{
		if ( fImg.gInfo.ScreenX < fImg.gInfo.ScreenY )
		{
			fImg.gInfo.ScreenXsize = height < width ? height : width;
			fImg.gInfo.ScreenYsize = height > width ? height : width;
		}
		else
		{
			fImg.gInfo.ScreenXsize = height > width ? height : width;
			fImg.gInfo.ScreenYsize = height < width ? height : width;
		}
		fImg.gInfo.SetScale();

		float x1 = 0, y1 = 0;
		float xx = fImg.gInfo.ScreenXsize;
		float yy = fImg.gInfo.ScreenYsize;
		
		gl.glOrthof( x1, xx, yy, y1, 1.0f, -1.0f );
		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glViewport( 0, 0, (int)xx, (int)yy );
		
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		gl.glEnable( GL10.GL_BLEND );
		gl.glBlendFunc( GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA );
	}
	
	@Override
	public void onDrawFrame( GL10 gl )
	{
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );
		gl.glLoadIdentity();
		gl.glScalef( fImg.gInfo.ScreenXsize / fImg.gInfo.ScreenX, fImg.gInfo.ScreenYsize / fImg.gInfo.ScreenY, 1.0f );
		
		fImg.DoGame();
	}
}

