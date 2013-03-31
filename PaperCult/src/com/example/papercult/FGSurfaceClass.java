package com.example.papercult;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FGSurfaceClass implements android.opengl.GLSurfaceView.Renderer
{
	public FGViewMain sImg;
	boolean blend;
	
	public FGSurfaceClass( FGViewMain dImg )
	{
		sImg = dImg;
	}
	
	
	@Override
	public void onSurfaceCreated( GL10 gl, EGLConfig config)
	{
		sImg.gInfo.BackB = 0f;
		sImg.gInfo.BackG = 0f;
		sImg.gInfo.BackR = 0f;
		gl.glClearColor( sImg.gInfo.BackR, sImg.gInfo.BackG, sImg.gInfo.BackB, 0f );
		gl.glClearDepthf( 1.0f );
		
		gl.glMatrixMode( GL10.GL_PROJECTION );
		gl.glHint( GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST );
		
		sImg.mGL = gl;
		sImg.LoadGameData();
	}
	
	@Override
	public void onSurfaceChanged( GL10 gl, int width, int height )
	{

		if ( sImg.gInfo.ScreenX < sImg.gInfo.ScreenY )
		{
			sImg.gInfo.ScreenXsize = height < width ? height : width;
			sImg.gInfo.ScreenYsize = height > width ? height : width;
		}
		else
		{
			sImg.gInfo.ScreenXsize = height > width ? height : width;
			sImg.gInfo.ScreenYsize = height < width ? height : width;
		}
		sImg.gInfo.SetScale();

		float x1 = 0, y1 = 0;
		float xx = sImg.gInfo.ScreenXsize;
		float yy = sImg.gInfo.ScreenYsize;
		
		gl.glOrthof( x1, xx, yy, y1, 1.0f, -1.0f );
		gl.glMatrixMode( GL10.GL_MODELVIEW );
		gl.glViewport( 0, 0, (int)xx, (int)yy );
		
		gl.glEnable( GL10.GL_TEXTURE_2D );
		gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
	}
	
	@Override
	public void onDrawFrame( GL10 gl )
	{
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );
		gl.glLoadIdentity();
		gl.glScalef( sImg.gInfo.ScreenXsize / sImg.gInfo.ScreenX, sImg.gInfo.ScreenYsize / sImg.gInfo.ScreenY, 1.0f );
		
		sImg.DoGame();
	}
}