package paper.data;

import javax.microedition.khronos.opengles.GL10;

import bayaba.engine.lib.GameInfo;

public interface GameMain {
	abstract void setGl(GL10 gl);
	abstract void LoadGameData();
	abstract void DoGame();
	abstract GameInfo getGInfo();
}
