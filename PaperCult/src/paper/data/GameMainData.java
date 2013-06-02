package paper.data;

import paper.cgameActivity.CGViewMain;
import paper.cstageCreateActivity.CSCViewMain;
import paper.cstageSelectActivity.CSBViewMain;
import paper.gameActivity.BGViewMain;
import paper.stageSelectActivity.SBGViewMain;
import paper.startActivity.STViewMain;
import android.content.Context;
import bayaba.engine.lib.GameInfo;

public class GameMainData {
	private static GameMainData instance=null;
	
	public GameInfo gInfo;
	
	public CGViewMain cgMain;
	public CSCViewMain cscMain;
	public CSBViewMain csbMain;
	public BGViewMain bgMain;
	public SBGViewMain sbgMain;
	public STViewMain stMain;
	
	public SurfaceClass cgSuf;
	public SurfaceClass cscSuf;
	public SurfaceClass csbSuf;
	public SurfaceClass bgSuf;
	public SurfaceClass sbgSuf;
	public SurfaceClass stSuf;
	
	private GameMainData(Context con, float scrWidth, float scrHeight){
		gInfo = new GameInfo(800, 480);
		gInfo.ScreenXsize = scrWidth;
        gInfo.ScreenYsize = scrHeight;
        gInfo.SetScale();
        
        cgMain = new CGViewMain(con, gInfo);
        cscMain = new CSCViewMain(con, gInfo);
        csbMain = new CSBViewMain(con, gInfo);
        bgMain = new BGViewMain(con, gInfo);
        sbgMain = new SBGViewMain(con, gInfo);
        stMain = new STViewMain(con, gInfo);
        
        cgSuf = new SurfaceClass(cgMain);
        cscSuf = new SurfaceClass(cscMain);
        csbSuf = new SurfaceClass(csbMain);
        bgSuf = new SurfaceClass(bgMain);
        sbgSuf = new SurfaceClass(sbgMain);
        stSuf = new SurfaceClass(stMain);
	}
	
	public static void createInstance(Context con, float scrWidth, float scrHeight){
		if(instance == null)
			instance = new GameMainData(con, scrWidth, scrHeight);
	}
	
	public static GameMainData getInstance(){
		return instance;
	}
}
