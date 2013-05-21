package paper.data;

import java.util.Vector;


import paper.gameActivity.Stage;

public class CStageData {
	private static CStageData instance;
	public Vector<Stage> list = new Vector<Stage>();
	
	private CStageData() {
		Stage temp = new Stage(0, null, null);
		temp.name = "AFD";
		list.add(temp);
		list.add(temp);
		list.add(temp);
		list.add(temp);
		list.add(temp);
		list.add(temp);
		list.add(temp);
		list.add(temp);
	}
	
	public static CStageData createInstance(){
		if (instance == null)
			instance = new CStageData();
		return instance;
	}
	public static CStageData getInstance(){
			return instance;
	}
	public Stage getStage(int index){
		return list.get(index);
	}
	public void setStageLock(){
		for(int i=4; i<list.size()-1; i=i+2){
			if( (list.get(i-1).score >= 80) && (list.get(i-2).score >= 80)){
				list.get(i).locked = false;
				list.get(i+1).locked = false;
			}
		}
	}
}
