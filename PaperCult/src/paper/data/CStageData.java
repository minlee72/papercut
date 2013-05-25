package paper.data;

import java.util.Vector;



public class CStageData {
	private static CStageData instance;
	public Vector<Stage> list = new Vector<Stage>();
	
	private CStageData() {
		Stage temp = new Stage(0, null);
		temp.name = "";
		temp.score = 0;
		list.add(temp);
		list.add(temp);
		list.add(temp);
		list.add(temp);
	}
	
	public static void createInstance(){
		if (instance == null)
			instance = new CStageData();
	}
	public static CStageData getInstance(){
		if (instance == null)
			instance = new CStageData();
		return instance;
	}
	public void addStage(Stage stg){
		list.add(list.size()-2, stg);
	}
	public Stage getStage(int index){
		return list.get(index);
	}
}
