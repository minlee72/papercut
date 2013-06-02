package paper.data;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SurfaceClass implements android.opengl.GLSurfaceView.Renderer{
	public GameMain sImg;

	public SurfaceClass( GameMain dImg )
	{
		sImg = dImg;
	}
	
	@Override
	public void onSurfaceCreated( GL10 gl, EGLConfig config )
	{
		sImg.getGInfo().BackB = 1f;
		sImg.getGInfo().BackG = 1f;
		sImg.getGInfo().BackR = 1f;
		gl.glClearColor( sImg.getGInfo().BackR, sImg.getGInfo().BackG, sImg.getGInfo().BackB, 0.0f );
		gl.glClearDepthf( 1.0f );
		
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glHint( GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST );
		
		sImg.setGl(gl);
		sImg.LoadGameData();
	}
	
	@Override
	public void onSurfaceChanged( GL10 gl, int width, int height )
	{
		if ( sImg.getGInfo().ScreenX < sImg.getGInfo().ScreenY )
		{
			sImg.getGInfo().ScreenXsize = height < width ? height : width;
			sImg.getGInfo().ScreenYsize = height > width ? height : width;
		}
		else
		{
			sImg.getGInfo().ScreenXsize = height > width ? height : width;
			sImg.getGInfo().ScreenYsize = height < width ? height : width;
		}
		sImg.getGInfo().SetScale();

		float x1 = 0, y1 = 0;
		float xx = sImg.getGInfo().ScreenXsize;
		float yy = sImg.getGInfo().ScreenYsize;
		
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
		gl.glScalef( sImg.getGInfo().ScreenXsize / sImg.getGInfo().ScreenX, sImg.getGInfo().ScreenYsize / sImg.getGInfo().ScreenY, 1.0f );
		
		sImg.DoGame();
	}
}
