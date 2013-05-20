package paper.gameActivity;

import java.util.Vector;
import paper.data.StageData;
import com.example.papercult.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;

public class PaperView extends View {
	int rgb;
	SoundTimer timer = new SoundTimer();
	Paper paper;
	Stage sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	BGViewMain bgMain;
	boolean click = false;
	Context con;
	
	private SoundPool SndPool;
	int soundBuf[] = new int[10];
	public PaperView(Context context, float scrWidth, float scrHeight, int stageIndex, BGViewMain bgvm) {
		super(context);
		rgb = 0x40FFFF00;
		con = context;
		bgMain = bgvm;
		SndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundBuf[0] = SndPool.load(getContext(), R.raw.fold0, 1);
		soundBuf[1] = SndPool.load(getContext(), R.raw.fold1, 1);
		soundBuf[2] = SndPool.load(getContext(), R.raw.fold2, 1);
		
		paper = new Paper(scrWidth, scrHeight);
		sObj = StageData.getInstance().getStage(stageIndex);
		sObj.current = sObj.limit;
		bgMain.remain = sObj.limit;
		paper.reset();
	}

	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				if(bgMain.checkRedrawBtn(event.getX(), event.getY())){
					rgb = bgMain.getPaperColor();
					this.resetPolygon();
					paper.initHistory();
					sObj.current = sObj.limit;
					bgMain.remain = sObj.limit;
					bgMain.motionInit();
					return true;
				}
				else if(bgMain.checkBackBtn(event.getX(), event.getY())){
					if(paper.history.size()<1)
						return true;
					
					int index = paper.history.size() - 1;
					paper.base = paper.history.get(index);
					paper.history.remove(index);
					paper.poly = (Vector<Polygon>)paper.base.clone();
					sObj.current++;
					bgMain.incRemain(sObj.current);
					this.invalidate();
					return true;
				}
				click = true;
				touchStart.x = event.getX();
				touchStart.y = event.getY();
				touchEnd.x = touchStart.x;
				touchEnd.y = touchStart.y;
				timer.setOn();
				timer.sendEmptyMessageDelayed(0, 500);
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(click == true){
				touchEnd.x = event.getX();
				touchEnd.y = event.getY();
				paper.foldStart(touchStart, touchEnd);
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(click == true){
				paper.foldEnd();
				if(sObj.current>0){
					sObj.current--;
					bgMain.decRemain(sObj.current);
				}
				timer.setOff();
				if (sObj.clearCheck(paper, 90, 20) > 80){
					sObj.score = sObj.clearCheck(paper, 90, 20);
				}
				else{
				
				}
				click = false;
			}
			return true;
		}
		return false;
	}
	public void resetPolygon(){
		paper.reset();
		this.invalidate();
	}
	
	public void onDraw(Canvas canvas){
		sObj.innerPolyDraw(canvas);
		//sObj.outerPolyDraw(canvas);
		paper.draw(canvas, rgb);
	}
	
	
	private class SoundTimer extends Handler{
		private boolean isON = false;
		private boolean isFirst = true;
		PointF start = new PointF();
		PointF end = new PointF();
		
		public void handleMessage(Message msg){
			if (isFirst == true){
				isFirst = false;
				start.x = touchStart.x;
				start.y = touchStart.y;
			}
			end.x = touchEnd.x;
			end.y = touchEnd.y;
			
			int x = (int)(start.x - end.x);
			int y = (int)(start.y - end.y);
			int result =  (int) Math.sqrt(x * x + y * y);
		
			if(result < 10){
		
			}
			else if(result < 30){
				SndPool.play(soundBuf[0], 1, 1, 0, 0, 1);
			}
			else if(result < 100){
				SndPool.play(soundBuf[1], 1, 1, 0, 0, 1);
			}
			else {
				SndPool.play(soundBuf[2], 1, 1, 0, 0, 1);
			}
			
			start.x = end.x;
			start.y = end.y;
			
			if(isON == true)
				this.sendEmptyMessageDelayed(0, 1000);
		}
		
		public void setOn(){
			isON = true;
		}
		
		public void setOff(){
			isON = false;
			isFirst = true;
		}
	}
	public Vector<PointF> polyExtPoint(Vector<PointF> v, float dst){
		Vector<PointF> result = new Vector<PointF>();
		PointF[] prevLine;
		PointF[] nextLine;
		PointF[] nLine;
		PointF inLine;
		PointF outLine;
		PointF nsp;
		PointF nep;
		
		for(int i=0; i<v.size(); i++){
			prevLine = lineEndExt( v.get(decIndex(i,v)), v.get(i), dst );
			nextLine = lineEndExt( v.get(incIndex(i,v)), v.get(i), dst );
			inLine = Polygon.getCenterPoint(prevLine[0], nextLine[0]);
			outLine = Polygon.getCenterPoint(prevLine[1], nextLine[1]);
			nsp = (Polygon.contains(v, inLine.x, inLine.y)) ? outLine : inLine ;
			nep = v.get(i);
			nLine = lineEndExt( nsp, nep, dst );
			if(Polygon.contains(v, nLine[0].x, nLine[0].y))
				result.add(nLine[1]);
			else
				result.add(nLine[0]);
		}
		return result;
	}
	public PointF[] lineEndExt(PointF sp, PointF ep, float dst){
		PointF[] result = new PointF[2];
		result[0] = new PointF();
		result[1] = new PointF();
		
		if(sp.x == ep.x){
			result[0].x = ep.x;
			result[0].y = ep.y+dst;
			
			result[1].x = ep.x;
			result[1].y = ep.y-dst;
		}
		else if(sp.y == ep.y){
			result[0].x = ep.x+dst;
			result[0].y = ep.y;
			
			result[1].x = ep.x-dst;;
			result[1].y = ep.y;
		}
		else{
			float grad = Polygon.getGradient(sp, ep);
			float inter = 0;
			float a = -2*grad*inter;
			float b = (4*grad*grad*inter*inter); 
			float d = 4*(grad*grad+1)*(inter*inter-dst*dst);
			b = FloatMath.sqrt(b-d);
			float c = 2*(grad*grad+1);
			result[0].x = (a + b) / c;
			result[1].x = (a - b) / c;
			
			result[0].y = grad * result[0].x + inter;
			result[1].y = grad * result[1].x + inter;
			
			result[0].x = result[0].x + ep.x;
			result[0].y = result[0].y + ep.y;
			result[1].x = result[1].x + ep.x;
			result[1].y = result[1].y + ep.y;
		}
		
		if(!Polygon.isInline(sp, ep, result[0])){
			PointF swap = result[0];
			result[0] = result[1];
			result[1] = swap;
		}
		return result;
	}
	
	public Vector<PointF> polySum(Vector<PointF> pv1, Vector<PointF> pv2){
		
		if((pv1.size()>=3)&&(pv2.size()<3))
			return pv1;
		else if((pv1.size()<3)&&(pv2.size()>=3))
			return pv2;
		else if((pv1.size()<3)&&(pv2.size()<3))
			return null;
		
		Vector<PointF> result = new Vector<PointF>();
		Vector<Vector<PointF>> ppp = getIncludeCrossPoint(pv1, pv2);
		Vector<PointF> cpv = ppp.get(0);
		Vector<PointF> ocpv = ppp.get(1);
		Vector<PointF> swap;
		
		if((cpv.size()-pv1.size()==0)&&(ocpv.size()-pv2.size()==0)){
			boolean inCheck = false;
			for(int i=0; i<cpv.size(); i++){
				if(Polygon.containsEXP(ocpv, cpv.get(i).x, cpv.get(i).y)){
					inCheck  = true;
					i = cpv.size();
				}
			}
			for(int i=0; i<ocpv.size(); i++){
				if(Polygon.containsEXP(cpv, ocpv.get(i).x, ocpv.get(i).y)){
					inCheck  = true;
					i = ocpv.size(); 
				}
			}
			if(inCheck == false){
				boolean samePoly = true;
				for(int i=0; i<cpv.size(); i++){
					boolean samePoint = false;
					for(int j=0; j<ocpv.size(); j++){
						if(cpv.get(i).equals(ocpv.get(j).x, ocpv.get(j).y))
							samePoint = true;
					}
					if(samePoint == false)
						samePoly = false;
				}
				if(samePoly == true)
					return cpv;
				else
					return null;
			}
		}
		
		int index = 0;
		int nextIndex;
		int prevIndex;
		PointF sp = null;
		PointF cp;
		PointF np;
		PointF pp;
		
		for(int i=0; i<cpv.size(); i++){
			if(!(Polygon.containsEXP(ocpv, cpv.get(i).x, cpv.get(i).y))){
				if(pointIsInPolygon(cpv.get(i), ocpv)==-1){
					index = i;
					sp = cpv.get(i);
					i=cpv.size();
				}
			}
		}
		if(sp==null){
			cpv = pv2;
			ocpv = pv1;
			for(int i=0; i<cpv.size(); i++){
				if(!(Polygon.containsEXP(ocpv, cpv.get(i).x, cpv.get(i).y))){
					if(pointIsInPolygon(cpv.get(i), ocpv)==-1){
						index = i;
						sp = cpv.get(i);
						i=cpv.size();
					}
				}
			}
		}
		if(sp==null)
			return pv1;
		
		boolean cpvInc = true;
		boolean ocpvInc = true;
		boolean swapInc = true;
		int c = 0;
		while(true)
		{
			c++;
			nextIndex = (cpvInc)? incIndex(index, cpv) : decIndex(index, cpv);
			prevIndex = (cpvInc)? decIndex(index, cpv) : incIndex(index, cpv);
			cp = cpv.get(index);
			np = cpv.get(nextIndex);
			pp = cpv.get(prevIndex);
			
			if(result.size()!=0){
				if(!(result.lastElement().equals(cp.x, cp.y)))
					result.add(cp);
			}
			else
				result.add(cp);

			
			if(result.size()>2){
				PointF ls = result.get(result.size()-3);
				PointF le = result.get(result.size()-1);
				PointF ip = result.get(result.size()-2);
				if(Polygon.pointIsInLine(ls, le, ip))
					result.remove(result.size()-2);
			}
			index = pointIsInPolygon(cp, ocpv);
			if(index == -1){
				index = nextIndex;
			}
			else{
				PointF t1 = ocpv.get(incIndex(index,ocpv));
				PointF t2 = ocpv.get(decIndex(index,ocpv));
				if( (np.equals(t1.x, t1.y)) || (np.equals(t2.x, t2.y)) )
						index = nextIndex;
				else{
					if(containsLine(ocpv, cp, np)){
						swap = cpv;
						cpv = ocpv;
						ocpv = swap;
						
						swapInc = cpvInc;
						cpvInc = ocpvInc;
						ocpvInc = swapInc;
						
						nextIndex = (cpvInc)? incIndex(index, cpv) : decIndex(index, cpv);
						if(containsLine(ocpv, cpv.get(index), cpv.get(nextIndex))
								||(cpv.get(nextIndex).equals(pp.x, pp.y)))
								cpvInc = !cpvInc;	
					}
					else
						index = nextIndex;
				}
			}
			if(cpv.get(index).equals(sp.x, sp.y))
				break;
			if(c>30)
				break;
		}
		if(result.size()>2){
			PointF ls = result.get(result.size()-2);
			PointF le = result.get(0);
			PointF ip = result.get(result.size()-1);
			if(Polygon.pointIsInLine(ls, le, ip))
				result.remove(result.size()-1);
		}
		return result;
	}
	public int incIndex(int index, Vector v){
		int nextIndex = index + 1;
		if(nextIndex == v.size())
			nextIndex = 0;
		return nextIndex;
	}
	public int decIndex(int index, Vector v){
		int nextIndex = index - 1;
		if(nextIndex < 0)
			nextIndex = v.size() - 1;
		return nextIndex;
	}
	public int pointIsInPolygon(PointF p, Vector<PointF> findv){
		for(int i=0; i<findv.size(); i++){
			if(findv.get(i).equals(p.x, p.y))
				return i;
		}
		return -1;
	}
	public Vector<Vector<PointF>> getIncludeCrossPoint(Vector<PointF> orgPv, Vector<PointF> addPv){
		Vector<PointF> resultOrg = new Vector<PointF>();
		Vector<PointF> resultAdd = new Vector<PointF>();
		addPv = polyDstCorrect(orgPv, addPv);
		Vector<Vector<PointF>> yResult = new Vector<Vector<PointF>>();
		for(int i=0; i<addPv.size(); i++){
			yResult.add(new Vector<PointF>());
		}
		for(int i=0; i<orgPv.size(); i++){
			resultOrg.add(orgPv.get(i));
			
			Vector<PointF> crsPoints = new Vector<PointF>();
			
			int nextIindex = incIndex(i,orgPv);
			PointF orgSP = orgPv.get(i);
			PointF orgEP = orgPv.get(nextIindex);
			
			for(int j=0; j<addPv.size(); j++){
				int nextJindex = j+1;
				if(nextJindex == addPv.size())
					nextJindex = 0;
				PointF addSP = addPv.get(j);
				PointF addEP = addPv.get(nextJindex);
				PointF crsP = Polygon.getCrossPointFromLine(orgSP, orgEP, addSP, addEP);
				
				if(crsP == null)
					continue;
				if((!(Polygon.isInlineExps(orgSP, orgEP, crsP)) || (!(Polygon.isInlineExps(addSP, addEP, crsP))) ))
					continue;
				
				crsP = Polygon.pointDstCorrect(orgSP,  crsP, 2);
				crsP = Polygon.pointDstCorrect(orgEP,    crsP, 2);
				crsP = Polygon.pointDstCorrect(addSP, crsP, 2);
				crsP = Polygon.pointDstCorrect(addEP,   crsP, 2);
				
				if(!((orgSP.equals(crsP.x, crsP.y))||(orgEP.equals(crsP.x, crsP.y)))){
					if(crsPoints.size()==0)
						crsPoints.add(crsP);
					else if(!(crsPoints.lastElement().equals(crsP.x, crsP.y)))
						crsPoints.add(crsP);
				}
					
				if(!((addSP.equals(crsP.x, crsP.y))||(addEP.equals(crsP.x, crsP.y)))){
					if(yResult.get(j).size()==0)
						yResult.get(j).add(crsP);
					else if(!(yResult.get(j).lastElement().equals(crsP.x, crsP.y)))
						yResult.get(j).add(crsP);
				}
			}

			if(crsPoints.size() == 0)
				continue;
			else if(crsPoints.size() == 1)
				resultOrg.add(crsPoints.get(0));
			else{
				crsPoints = getSortedCrossPoint(orgSP, orgEP, crsPoints);
				for(int j=0; j<crsPoints.size(); j++){
					resultOrg.add(crsPoints.get(j));
				}
			}
		}
		
		for(int i=0; i<yResult.size(); i++){
			Vector<PointF> temp = getSortedCrossPoint(addPv.get(i), addPv.get(incIndex(i,addPv)), yResult.get(i));
			resultAdd.add(addPv.get(i));
			if(temp != null){
				for(int j=0; j<temp.size(); j++)
					resultAdd.add(temp.get(j));
			}
		}
		
		Vector<Vector<PointF>> r = new Vector<Vector<PointF>>();
		r.add(resultOrg);
		r.add(resultAdd);
		return r;
	}
	public Vector<PointF> polyDstCorrect(Vector<PointF> bp, Vector<PointF> tp){
		Vector<PointF> result = new Vector<PointF>();
		PointF isp;
		for(int i=0; i<tp.size(); i++){
			isp = tp.get(i);
			for(int j=0; j<bp.size(); j++){
				isp = Polygon.pointDstCorrect(bp.get(j), isp, 2);
			}
			result.add(isp);
		}
		return result;
	}
	public Vector<PointF> getSortedCrossPoint(PointF stp, PointF edp, Vector<PointF> crsPoints){
		if((stp.equals(edp.x, edp.y)) || (crsPoints.size()==0))
			return null;
		if(crsPoints.size()==1)
			return crsPoints;
		
		int i = 0;
		if((stp.x-edp.x) == 0){ //수직
			if(stp.y > edp.y){ //y가 높은 값이 앞에 오도록
				while(true){
					if(i == (crsPoints.size()-1))
						break;
					if((crsPoints.get(i).y) < (crsPoints.get(i+1).y)){
						PointF temp = crsPoints.get(i);
						crsPoints.set(i, crsPoints.get(i+1));
						crsPoints.set(i+1, temp);
						i=0;
					}
					else
						i++;
				}
			}
			else{ //y가 낱은 값이 앞에 오도록
				while(true){
					if(i == (crsPoints.size()-1))
						break;
					if((crsPoints.get(i).y) > (crsPoints.get(i+1).y)){
						PointF temp = crsPoints.get(i);
						crsPoints.set(i, crsPoints.get(i+1));
						crsPoints.set(i+1, temp);
						i=0;
					}
					else
						i++;
				}
			}
		}
		else{ //수평
			if(stp.x > edp.x){ //x가 높은 값이 앞에 오도록
				while(true){
					if(i == (crsPoints.size()-1))
						break;
					if((crsPoints.get(i).x) < (crsPoints.get(i+1).x)){
						PointF temp = crsPoints.get(i);
						crsPoints.set(i, crsPoints.get(i+1));
						crsPoints.set(i+1, temp);
						i=0;
					}
					else
						i++;
				}
			}
			else{ //x가 낮은 값이 앞에 오도록
				while(true){
					if(i == (crsPoints.size()-1))
						break;
					if((crsPoints.get(i).x) > (crsPoints.get(i+1).x)){
						PointF temp = crsPoints.get(i);
						crsPoints.set(i, crsPoints.get(i+1));
						crsPoints.set(i+1, temp);
						i=0;
					}
					else
						i++;
				}
			}
		}
		return crsPoints;
	}
	public boolean containsLine(Vector<PointF> ocpv, PointF sp, PointF ep){
		PointF stp;
		PointF etp;
		PointF ctp = Polygon.getCenterPoint(sp, ep);
		if(Polygon.contains(ocpv, ctp.x, ctp.y))
			return true;
		return false;
	}
}


