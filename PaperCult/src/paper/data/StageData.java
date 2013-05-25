package paper.data;

import java.util.Vector;

import com.example.papercult.R;


public class StageData {
	private static StageData instance;
	public Vector<Stage> list = new Vector<Stage>();
	
	private StageData(float scrWidth, float scrHeight) {
		StagePolygon dumP = new StagePolygon();
		dumP.add(0, 0);
		dumP.add((float)1, (float)0);
		dumP.add((float)0, (float)1);

		Stage dum = new Stage(4, dumP);
		dum.titleImage = R.drawable.invisible;
		dum.score = 0;
		dum.locked = true;
		
		StagePolygon stp0 = new StagePolygon();		
		stp0.add(0, 0);	
		stp0.add((float)0.54, (float)0.223);
		stp0.add((float)0.8, (float)0.218);
		stp0.add((float)0.86, (float)0.357);
		stp0.add((float)0.356, (float)0.866);
		stp0.add((float)0.223, (float)0.813);
		stp0.add((float)0.223, (float)0.532);
		
		Stage st0 = new Stage(3, stp0);
		st0.titleImage = R.drawable.s_name1;
		st0.titleClearImage = R.drawable.s_name1_clear;
		st0.score = 0;
		st0.locked = false;
		
		StagePolygon stp1 = new StagePolygon();
		stp1.add((float)0.18, (float)0.48);
		stp1.add((float)0.46, (float)0.48);
		stp1.add((float)0.54, (float)0.31);
		stp1.add((float)0.86, (float)0.19);
		stp1.add((float)0.96, (float)0.48);
		stp1.add((float)1, (float)0.48);
		stp1.add((float)1, (float)0.56);
		stp1.add((float)0.03, (float)0.73);
		stp1.add((float)0, (float)0.73);
		stp1.add((float)0, (float)0.66);
		
		Stage st1 = new Stage(5, stp1);
		st1.titleImage = R.drawable.s_name1;
		st1.titleClearImage = R.drawable.s_name1_clear;
		st1.score = 0;
		st1.locked = false;
		
		StagePolygon stp2 = new StagePolygon();
		stp2.add((float)0.3, 0);
		stp2.add((float)0.37, (float)-0.03);
		stp2.add((float)0.4, (float)-0.1);
		stp2.add((float)0.49, (float)-0.07);
		stp2.add((float)0.57, (float)-0.1);
		stp2.add((float)0.6, (float)-0.03);
		stp2.add((float)0.7, (float)0);
		stp2.add((float)0.515, (float)1);
		stp2.add((float)0.485, (float)1);

		Stage st2 = new Stage(5, stp2);
		st2.titleImage = R.drawable.s_name2;
		st2.titleClearImage = R.drawable.s_name2_clear;
		st2.score = 0;
		//st2.locked = true;
		
		StagePolygon stp3 = new StagePolygon();
		stp3.add((float)0.37, (float)0.36);
		stp3.add((float)0.45, (float)0.23);
		stp3.add((float)0.5, (float)0.2);
		stp3.add((float)0.62, (float)0.26);
		stp3.add((float)0.62, (float)0.47);
		stp3.add((float)0.86, (float)0.47);
		stp3.add((float)0.90, (float)0.40);
		stp3.add((float)1, (float)0.475);
		stp3.add((float)0.79, (float)0.60);
		stp3.add((float)0.62, (float)0.60);
		stp3.add((float)0.5, (float)0.47);
		stp3.add((float)0.5, (float)0.357);
		stp3.add((float)0.40, (float)0.41);

		Stage st3 = new Stage(7, stp3);
		st3.titleImage = R.drawable.s_name3;
		st3.titleClearImage = R.drawable.s_name3_clear;
		st3.score = 0;
		//st3.locked = true;
		
		StagePolygon stp4 = new StagePolygon();
		stp4.add((float)0.25, (float)0.25);
		stp4.add((float)0.5, 0);
		stp4.add((float)1, (float)0.5);
		stp4.add((float)0.75, (float)0.75);
		
		Stage st4 = new Stage(4, stp4);
		st4.titleImage = R.drawable.s_name4;
		st4.titleClearImage = R.drawable.s_name4_clear;
		st4.score = 0;
		//st4.locked = true;
		
		StagePolygon stp5 = new StagePolygon();
		stp5.add(0, (float)0.1923);
		stp5.add((float)0.3, (float)0.19);
		stp5.add((float)0.5, (float)0);
		stp5.add((float)0.7, (float)0.19);
		stp5.add((float)1, (float)0.19);
		stp5.add((float)0.84, (float)0.34);
		stp5.add((float)0.15, (float)0.34);
		
		Stage st5 = new Stage(4, stp5);
		st5.titleImage = R.drawable.s_name5;
		st5.titleClearImage = R.drawable.s_name5_clear;
		st5.score = 0;
		//st5.locked = true;
		
		StagePolygon stp6 = new StagePolygon();
		stp6.add((float)0.4, (float)0.28);
		stp6.add((float)0.62, (float)0.23);
		stp6.add((float)0.65, (float)0.35);
		stp6.add((float)0.62, (float)0.36);
		stp6.add((float)0.62, (float)1);
		stp6.add((float)0.5, (float)1);
		stp6.add((float)0.5, (float)0.4);
		stp6.add((float)0.42, (float)0.40);
		
		Stage st6 = new Stage(5, stp6);
		st6.titleImage = R.drawable.s_name6;
		st6.titleClearImage = R.drawable.s_name6_clear;
		st6.score = 0;
		//st6.locked = true;
		
		StagePolygon stp7 = new StagePolygon();
		stp7.add((float)0.42, (float)0.08);
		stp7.add((float)0.5, (float)0.081);
		stp7.add((float)0.62, (float)0.20);
		stp7.add((float)0.62, (float)0.8);
		stp7.add((float)0.7, (float)0.8);
		stp7.add((float)0.7, (float)0.92);
		stp7.add((float)0.62, (float)0.92);
		stp7.add((float)0.5, (float)0.8);
		stp7.add((float)0.5, (float)0.20);
		stp7.add((float)0.42, (float)0.20);
		
		Stage st7 = new Stage(5, stp7);
		st7.titleImage = R.drawable.s_name7;
		st7.titleClearImage = R.drawable.s_name7_clear;
		st7.score = 0;
		//st7.locked = true;
		
		StagePolygon stp8 = new StagePolygon();
		stp8.add((float)0.30, (float)0.3);
		stp8.add((float)0.5, (float)0.10);
		stp8.add((float)0.69, (float)0.30);
		stp8.add((float)0.60, (float)0.39);
		stp8.add((float)0.60, (float)0.60);
		stp8.add((float)0.5, (float)0.5);
		stp8.add((float)0.4, (float)0.60);
		stp8.add((float)0.4, (float)0.39);
		
		Stage st8 = new Stage(4, stp8);
		st8.titleImage = R.drawable.s_name8;
		st8.titleClearImage = R.drawable.s_name8_clear;
		st8.score = 0;
		//st8.locked = true;
		
		list.add(dum);
		list.add(dum);
		list.add(st0);
		list.add(st1);
		list.add(st2);
		list.add(st3);
		list.add(st4);
		list.add(st5);
		list.add(st6);
		list.add(st7);
		list.add(st8);
		list.add(dum);
		list.add(dum);
		
		for (int i=2; i<list.size()-2; i++){
			list.get(i).loadStage(new Paper(scrWidth, scrHeight));
		}
	}
	
	public static void createInstance(float scrWidth, float scrHeight){
		if (instance == null)
			instance = new StageData(scrWidth, scrHeight);
	}
	public static StageData getInstance(){
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

