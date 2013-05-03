
package paper.data;

import java.util.Vector;

import com.example.papercult.R;

import paper.gameActivity.Stage;
import paper.gameActivity.StagePolygon;

public class StageData {
	private static StageData instance;
	public Vector<Stage> list = new Vector<Stage>();
	
	private StageData() {
		StagePolygon stp1 = new StagePolygon();
		stp1.add(0, 0);
		stp1.add((float)1, (float)0);
		stp1.add((float)0, (float)1);
		
		StagePolygon stp1_ = new StagePolygon();
		stp1_.add((float)-0.1, (float)-0.1);
		stp1_.add((float)1.2, (float)-0.1);
		stp1_.add((float)-0.1, (float)1.2);
		
		Stage dum = new Stage(4, stp1, stp1_);
		dum.titleImage = R.drawable.invisible;
		dum.score = 0;
		dum.locked = false;
		
		StagePolygon stp2 = new StagePolygon();		
		stp2.add(0, 0);	
		stp2.add((float)0.54, (float)0.223);
		stp2.add((float)0.8, (float)0.218);
		stp2.add((float)0.86, (float)0.357);
		stp2.add((float)0.356, (float)0.866);
		stp2.add((float)0.223, (float)0.813);
		stp2.add((float)0.223, (float)0.532);
		
		StagePolygon stp2_ = new StagePolygon();
		stp2_.add((float)-0.03, (float)-0.03);
		stp2_.add((float)0.54, (float)0.188);
		stp2_.add((float)0.83, (float)0.188);
		stp2_.add((float)0.89, (float)0.387);
		stp2_.add((float)0.356, (float)0.896);
		stp2_.add((float)0.193, (float)0.843);
		stp2_.add((float)0.193, (float)0.532);
		
		Stage st = new Stage(4, stp2, stp2_);
		st.titleImage = R.drawable.s_name1;
		st.titleClearImage = R.drawable.s_name1_clear;
		st.score = 0;
		st.locked = false;
		
		StagePolygon stp3 = new StagePolygon();
		stp3.add((float)0.183, (float)0.4802);
		stp3.add((float)0.466, (float)0.4802);
		stp3.add((float)0.5463, (float)0.3104);
		stp3.add((float)0.86, (float)0.1923);
		stp3.add((float)0.9662, (float)0.4802);
		stp3.add((float)1, (float)0.4802);
		stp3.add((float)1, (float)0.5634);
		stp3.add((float)0.0319, (float)0.7394);
		stp3.add((float)0, (float)0.7394);
		stp3.add((float)0, (float)0.662);
		
		StagePolygon stp3_ = new StagePolygon();
		stp3_.add((float)0.153, (float)0.4502);
		stp3_.add((float)0.436, (float)0.4502);
		stp3_.add((float)0.5263, (float)0.2804);
		stp3_.add((float)0.88, (float)0.1623);
		stp3_.add((float)0.9862, (float)0.4502);
		stp3_.add((float)1.03, (float)0.4502);
		stp3_.add((float)1.03, (float)0.5934);
		stp3_.add((float)0.0339, (float)0.7694);
		stp3_.add((float)-0.03, (float)0.7694);
		stp3_.add((float)-0.03, (float)0.632);
		
		
		Stage st3 = new Stage(5, stp3, stp3_);
		st3.titleImage = R.drawable.s_name2;
		st3.titleClearImage = R.drawable.s_name2_clear;
		st3.score = 0;
		st3.locked = false;
		
		StagePolygon stp4 = new StagePolygon();
		stp4.add((float)0.3, 0);
		stp4.add((float)0.37, (float)-0.03);
		stp4.add((float)0.4, (float)-0.1);
		stp4.add((float)0.495, (float)-0.075);
		stp4.add((float)0.57, (float)-0.1);
		stp4.add((float)0.6, (float)-0.035);
		stp4.add((float)0.7, (float)0);
		stp4.add((float)0.515, (float)1);
		stp4.add((float)0.485, (float)1);
		
		StagePolygon stp4_ = new StagePolygon();
		stp4_.add((float)0.27, (float)-0.03);
		stp4_.add((float)0.34, (float)-0.06);
		stp4_.add((float)0.4, (float)-0.13);
		stp4_.add((float)0.495, (float)-0.105);
		stp4_.add((float)0.57, (float)-0.13);
		stp4_.add((float)0.63, (float)-0.065);
		stp4_.add((float)0.73, (float)-0.01);
		stp4_.add((float)0.545, (float)1.03);
		stp4_.add((float)0.455, (float)1.03);
		
		
		Stage st4 = new Stage(5, stp4, stp4_);
		st4.titleImage = R.drawable.s_name3;
		st4.titleClearImage = R.drawable.s_name3_clear;
		st4.score = 0;
		st4.locked = false;
		
		StagePolygon stp5 = new StagePolygon();
		stp5.add((float)0.37, (float)0.36);
		stp5.add((float)0.459, (float)0.236);
		stp5.add((float)0.5, (float)0.2);
		stp5.add((float)0.627, (float)0.267);
		stp5.add((float)0.627, (float)0.475);
		stp5.add((float)0.865, (float)0.475);
		stp5.add((float)0.904, (float)0.403);
		stp5.add((float)1, (float)0.475);
		stp5.add((float)0.798, (float)0.602);
		stp5.add((float)0.6267, (float)0.602);
		stp5.add((float)0.5, (float)0.475);
		stp5.add((float)0.5, (float)0.357);
		stp5.add((float)0.407, (float)0.415);
		
		StagePolygon stp5_ = new StagePolygon();
		stp5_.add((float)0.33, (float)0.33);
		stp5_.add((float)0.449, (float)0.206);
		stp5_.add((float)0.5, (float)0.17);
		stp5_.add((float)0.657, (float)0.267);
		stp5_.add((float)0.657, (float)0.445);
		stp5_.add((float)0.835, (float)0.445);
		stp5_.add((float)0.904, (float)0.373);
		stp5_.add((float)1.03, (float)0.475);
		stp5_.add((float)0.828, (float)0.632);
		stp5_.add((float)0.5967, (float)0.632);
		stp5_.add((float)0.47, (float)0.475);
		stp5_.add((float)0.47, (float)0.397);
		stp5_.add((float)0.397, (float)0.445);
		
		Stage st5 = new Stage(7, stp5, stp5_);
		st5.titleImage = R.drawable.s_name4;
		st5.titleClearImage = R.drawable.s_name4_clear;
		st5.score = 0;
		st5.locked = false;
		StagePolygon stp6 = new StagePolygon();
		stp6.add((float)0.25, (float)0.25);
		stp6.add((float)0.5, 0);
		stp6.add((float)1, (float)0.5);
		stp6.add((float)0.75, (float)0.75);
		
		StagePolygon stp6_ = new StagePolygon();
		stp6_.add((float)0.22,(float)0.25);
		stp6_.add((float)0.50,(float)-0.03);
		stp6_.add((float)1.03,(float)0.50 );
		stp6_.add((float)0.75,(float)0.78);
		
		Stage st6 = new Stage(4, stp6, stp6_);
		st6.titleImage = R.drawable.s_name4;
		st6.titleClearImage = R.drawable.s_name4_clear;
		st6.score = 0;
		st6.locked = false;
		
		StagePolygon stp7 = new StagePolygon();
		stp7.add(0, (float)0.1923);
		stp7.add((float)0.3, (float)0.1923);
		stp7.add((float)0.5, (float)0);
		stp7.add((float)0.7, (float)0.1923);
		stp7.add((float)1, (float)0.1923);
		stp7.add((float)0.8432, (float)0.345);
		stp7.add((float)0.1568, (float)0.345);
		
		StagePolygon stp7_ = new StagePolygon();
		stp7_.add((float)-0.05, (float)0.1623);
		stp7_.add((float)0.27, (float)0.1623);
		stp7_.add((float)0.5, (float)-0.03);
		stp7_.add((float)0.73, (float)0.1623);
		stp7_.add((float)1.05, (float)0.1623);
		stp7_.add((float)0.8732, (float)0.375);
		stp7_.add((float)0.1268, (float)0.375);
		
		Stage st7 = new Stage(4, stp7, stp7_);
		st7.titleImage = R.drawable.s_name4;
		st7.titleClearImage = R.drawable.s_name4_clear;
		st7.score = 0;
		st7.locked = false;
		
		StagePolygon stp8 = new StagePolygon();
		stp8.add((float)0.4, (float)0.2818);
		stp8.add((float)0.6242, (float)0.2312);
		stp8.add((float)0.6559, (float)0.3501);
		stp8.add((float)0.6270, (float)0.3611);
		stp8.add((float)0.6270, (float)1);
		stp8.add((float)0.5, (float)1);
		stp8.add((float)0.5, (float)0.4);
		stp8.add((float)0.4265, (float)0.4042);
		
		StagePolygon stp8_ = new StagePolygon();
		stp8_.add((float)0.37, (float)0.2518);
		stp8_.add((float)0.6542, (float)0.2012);
		stp8_.add((float)0.6859, (float)3801);
		stp8_.add((float)0.6570, (float)0.3911);
		stp8_.add((float)0.6570, (float)1.03);
		stp8_.add((float)0.47, (float)1.03);
		stp8_.add((float)0.47, (float)0.43);
		stp8_.add((float)0.3965, (float)0.4342);
		
		Stage st8 = new Stage(5, stp8, stp8_);
		st8.titleImage = R.drawable.s_name4;
		st8.titleClearImage = R.drawable.s_name4_clear;
		st8.score = 0;
		st8.locked = false;
		
		StagePolygon stp9 = new StagePolygon();
		stp9.add((float)0.4215, (float)0.081);
		stp9.add((float)0.5, (float)0.081);
		stp9.add((float)0.6274, (float)0.2054);
		stp9.add((float)0.6274, (float)0.8);
		stp9.add((float)0.7, (float)0.8);
		stp9.add((float)0.7, (float)0.9281);
		stp9.add((float)0.6274, (float)0.9281);
		stp9.add((float)0.5, (float)0.8);
		stp9.add((float)0.5, (float)0.2054);
		stp9.add((float)0.4215, (float)0.2054);
		
		StagePolygon stp9_ = new StagePolygon();
		stp9_.add((float)0.3915, (float)0.051);
		stp9_.add((float)0.53, (float)0.051);
		stp9_.add((float)0.6574, (float)0.2054);
		stp9_.add((float)0.6574, (float)0.77);
		stp9_.add((float)0.73, (float)0.77);
		stp9_.add((float)0.73, (float)0.9581);
		stp9_.add((float)0.5974, (float)0.9581);
		stp9_.add((float)0.47, (float)0.8);
		stp9_.add((float)0.47, (float)0.2354);
		stp9_.add((float)0.3915, (float)0.2354);
		
		Stage st9 = new Stage(5, stp9, stp9_);
		st9.titleImage = R.drawable.s_name4;
		st9.titleClearImage = R.drawable.s_name4_clear;
		st9.score = 0;
		st9.locked = false;
		
		StagePolygon stp10 = new StagePolygon();
		stp10.add((float)0.3026, (float)0.3015);
		stp10.add((float)0.5, (float)0.1050);
		stp10.add((float)0.6979, (float)0.3015);
		stp10.add((float)0.605, (float)0.392);
		stp10.add((float)0.605, (float)0.603);
		stp10.add((float)0.5, (float)0.5);
		stp10.add((float)0.4, (float)0.603);
		stp10.add((float)0.4, (float)0.392);
		
		StagePolygon stp10_ = new StagePolygon();
		stp10_.add((float)0.2726, (float)0.3015);
		stp10_.add((float)0.5, (float)0.0750);
		stp10_.add((float)0.7279, (float)0.3015);
		stp10_.add((float)0.635, (float)0.392);
		stp10_.add((float)0.635, (float)0.633);
		stp10_.add((float)0.605, (float)0.633);
		stp10_.add((float)0.5, (float)0.53);
		stp10_.add((float)0.4, (float)0.633);
		stp10_.add((float)0.37, (float)633);
		stp10_.add((float)0.37, (float)0.392);
		
		Stage st10 = new Stage(4, stp10, stp10_);
		st10.titleImage = R.drawable.s_name4;
		st10.titleClearImage = R.drawable.s_name4_clear;
		st10.score = 0;
		st10.locked = false;
		
		list.add(dum);
		list.add(dum);
		list.add(st);
		list.add(st3);
		list.add(st4);
		list.add(st5);
		list.add(st6);
		list.add(st7);
		list.add(st8);
		list.add(st9);
		list.add(st10);
		list.add(dum);
		list.add(dum);
		
	}
	public static StageData getInstance(){
		if (instance == null)
			instance = new StageData();
		return instance;
	}
	public Vector<Stage>getList(){
		return list;
	}
	public void setStageLock(){
		for(int i=0; i<list.size()-1; i++){
			if(list.get(i).score > 80)
				list.get(i+1).locked = false;
		}
	}
}

