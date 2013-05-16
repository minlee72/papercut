package paper.gameActivity;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * 접혀지는 다각형을 관리하기 위한 클래스
 * @author 폴더폰
 *
 */
public class Polygon {
	/**
	 * 다각형의 점 정보를 저장하는 벡터
	 * 0번 인덱스 부터 끝까지 순차적으로 직선으로 이어진다
	 */
	public Vector<PointF> pointVector;
	/**
	 * 생성자
	 */
	public Polygon(){
		pointVector = new Vector<PointF>();
	}
	public Rect getBounds() {
		if (pointVector.size() < 3)
			return null;
        int boundsMinX = Integer.MAX_VALUE;
        int boundsMinY = Integer.MAX_VALUE;
        int boundsMaxX = Integer.MIN_VALUE;
        int boundsMaxY = Integer.MIN_VALUE;

        for (int i = 0; i < pointVector.size(); i++) {
            int x = (int)pointVector.get(i).x;
            boundsMinX = Math.min(boundsMinX, x);
            boundsMaxX = Math.max(boundsMaxX, x);
            int y = (int)pointVector.get(i).y;
            boundsMinY = Math.min(boundsMinY, y);
            boundsMaxY = Math.max(boundsMaxY, y);
        }
        return new Rect(boundsMinX, boundsMinY, boundsMaxX, boundsMaxY );
    }
	/**
	 * 다각형에 내부에 입력된 점이 위치하는지 확인한다.
	 * @param x 확인할 점의 x좌표
	 * @param y 확인할 점의 y좌표
	 * @return 내부에 있으면 true, 외부에 있으면 false 반환
	 */
	public boolean contains(float x, float y) {
		Vector<PointF> v = pointVector;
        if ( v.size() <= 2) {
            return false;
        }
        int hits = 0;

        int lastx = (int)v.get(v.size()-1).x;
        int lasty = (int)v.get(v.size()-1).y;
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < v.size(); lastx = curx, lasty = cury, i++) {
            curx = (int)v.get(i).x;
            cury = (int)v.get(i).y;

            if (cury == lasty) {
                continue;
            }

            int leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }
	public static boolean contains(Vector<PointF> v, float x, float y) {
        if ( v.size() <= 2) {
            return false;
        }
        
        int hits = 0;

        int lastx = (int)v.get(v.size()-1).x;
        int lasty = (int)v.get(v.size()-1).y;
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < v.size(); lastx = curx, lasty = cury, i++) {
            curx = (int)v.get(i).x;
            cury = (int)v.get(i).y;

            if (cury == lasty) {
                continue;
            }

            int leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }
	public static boolean containsEXP(Vector<PointF> v, float x, float y) {
        if ( v.size() <= 2) {
            return false;
        }
        PointF p = new PointF(x,y);
        for(int i=0; i<v.size(); i++){
        	int ni = i + 1;
        	if(ni==v.size())
        		ni = 0;
        	PointF ts = v.get(i);
        	PointF tn = v.get(ni);
        	if(Polygon.pointIsInLine(ts, tn, p))
        		return false;
        	/*
        	if(ts.x == tn.x){
        		float bigY, smallY;
        		if(ts.y > tn.y){
        			bigY = ts.y;
        			smallY = tn.y;
        		}
        		else{
        			bigY = tn.y;
        			smallY = ts.y;
        		}
        		
        		if((ts.x == x)&&(y <= bigY)&&(y >= smallY)){
        			return false;
        		}
        	}
        	else if(ts.y == tn.y){
        		float bigX, smallX;
        		if(ts.x > tn.x){
        			bigX = ts.x;
        			smallX = tn.x;
        		}
        		else{
        			bigX = tn.x;
        			smallX = ts.x;
        		}
        		
        		if((ts.y == y)&&(x <= bigX)&&(y >= smallX)){
        			return false;
        		}
        	}
        	else{
        		float bigX, smallX;
        		if(ts.x > tn.x){
        			bigX = ts.x;
        			smallX = tn.x;
        		}
        		else{
        			bigX = tn.x;
        			smallX = ts.x;
        		}
        		if((x <= bigX)&&(x >= smallX)){
	        		float gradient = getGradient(ts,tn);
	        		float intercept = getIntercept(ts, gradient);
	        		float ry = gradient * x + intercept;
	        		if(ry == y)
	        			return false;
        		}
        	}
        	*/
        }
        int hits = 0;

        int lastx = (int)v.get(v.size()-1).x;
        int lasty = (int)v.get(v.size()-1).y;
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < v.size(); lastx = curx, lasty = cury, i++) {
            curx = (int)v.get(i).x;
            cury = (int)v.get(i).y;

            if (cury == lasty) {
                continue;
            }

            int leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }
	public static boolean containsV2(Vector<PointF> v, PointF ip){
		float x = ip.x;
		float y = ip.y;
		int left = 0;
		int right = 0;
		int up = 0;
		int down = 0;
		
		float gradient;
		float intercept;
		PointF cp = new PointF();
		for(int i=0; i<v.size(); i++){
			int nexti = i+1;
			if(nexti==v.size())
				nexti=0;
			PointF sp = v.get(i);
			PointF ep = v.get(nexti);
			if(sp.x == ep.x){
				cp.x = sp.y;
				cp.y = y;
			}
			else{
				gradient = getGradient(sp,ep);
				intercept = getIntercept(ip,gradient);
				cp.y = y;
				cp.x = (cp.y - intercept) / gradient; 
			}
			if(isInlineExps(sp,ep,cp)){
				if(cp.x > x)
					right++;
				else
					left++;
			}
		}
		if(((right%2)==1) && ((left%2)==1)){
			return true;
		}
		else
			return false;
	}
	public static boolean pointIsInLine(PointF lsP, PointF leP, PointF cp){
		float x = cp.x;
		float y = cp.y;
    	
    	if(lsP.x == leP.x){
    		float bigY, smallY;
    		if(lsP.y > leP.y){
    			bigY = lsP.y;
    			smallY = leP.y;
    		}
    		else{
    			bigY = leP.y;
    			smallY = lsP.y;
    		}
    		
    		if((lsP.x == x)&&(y <= bigY)&&(y >= smallY)){
    			return true;
    		}
    	}
    	else if(lsP.y == leP.y){
    		float bigX, smallX;
    		if(lsP.x > leP.x){
    			bigX = lsP.x;
    			smallX = leP.x;
    		}
    		else{
    			bigX = leP.x;
    			smallX = lsP.x;
    		}
    		
    		if((lsP.y == y)&&(x <= bigX)&&(y >= smallX)){
    			return true;
    		}
    	}
    	else{
    		float bigX, smallX;
    		if(lsP.x > leP.x){
    			bigX = lsP.x;
    			smallX = leP.x;
    		}
    		else{
    			bigX = leP.x;
    			smallX = lsP.x;
    		}
    		if((x <= bigX)&&(x >= smallX)){
        		float gradient = getGradient(lsP,leP);
        		float intercept = getIntercept(lsP, gradient);
        		float ry = gradient * x + intercept;
        		if(ry == y)
        			return true;
    		}
    	}
    	return false;
	}
	/**
	 * int형 입력을 float로 형변환 하여 contains 함수를 호출
	 * @param x x좌표
	 * @param y y좌표
	 * @return contains(float, float)의 결과
	 */
	 public boolean contains(int x, int y) {
	        return contains((float) x, (float) y);
	    }
	/**
	 * 다각형이 터치 입력에 따른 기울기에 기준하여 접혀질때, 접혀지는 부분을 구한다
	 * @param tlStart	터치입력 시작점
	 * @param tlEnd   터치입력 끝점
	 * @return   접혀지지 않은 나머지 다각형
	 */
	public Polygon pullPolygon (PointF tlStart, PointF tlEnd){
		if(pointVector.size() < 1)
			return null;	
		
		Polygon result = new Polygon();  //리턴할 결과
		
		for (int i = 0; i<(pointVector.size()); i++){
			PointF hereP = pointVector.get(i);    //벡터에서 점 하나를 가져온다
			PointF nextP;
			if (i != (pointVector.size()-1)){        //가져온 점이 끝점이 아니면
				nextP = pointVector.get(i+1);     //다음점도 가져와서 넥스트에 저장
			}
			else{                                         //가져온 점이 끝점이면 넥스트 값은 0번점. 즉 시작점이 된다
				nextP = pointVector.get(0);
			}
					
			if(containsPoint(hereP, tlStart, tlEnd) == true){           //현재 점이 대칭해야될 점이라면
				result.add(getSymmetryPoint(hereP, tlStart, tlEnd)); //대칭 시킨후 결과 벡터에 추가
			}
			PointF crossPoint = getCrossPoint(hereP, nextP, tlStart, tlEnd);  //현재 점과 다음점을 잇는 직선과 접혀져야하는 기울기의 교차점
			if(isInline(pointVector.get(i), nextP, crossPoint) == true){        //현재 점과 다음점의 직선사이로 접혀지는 기울기가 지나간다면
				result.add(crossPoint);                                                   //그 교차점을 벡터에 추가
			}
		}
		return result;
	}
	
	/**
	 * 다각형이 터치 입력에 따른 기울기에 기준하여 접혀질때, 접혀지지 않는 나머지 부분을 구한다
	 * @param tlStart  터치입력 시작점
	 * @param tlEnd   터치입력 끝점
	 * @return  접혀지는 다각형
	 */
	public Polygon cutPolygon (PointF tlStart, PointF tlEnd){
		if(pointVector.size() < 1)
			return null;	
		
		Polygon result = new Polygon();
		
		for (int i = 0; i<(pointVector.size()); i++){
			PointF hereP = pointVector.get(i);        //벡터에서 점을 하나 가져옴
			PointF nextP;
			if (i != (pointVector.size()-1)){
				nextP = pointVector.get(i+1);         
			}                                                   //벡터에서 다음 점을 하나 가져오는데 
			else{                                             //지금이 끝점이면 0번점 (시작점)을 가져옴
				nextP = pointVector.get(0);
			}
					
			if(containsPoint(hereP, tlStart, tlEnd) == false){  //현재 점이 접혀질때 넘겨지는 점이 아니면
				result.add(hereP);                                       //결과 벡터에 넣음
			}
			PointF crossPoint = getCrossPoint(hereP, nextP, tlStart, tlEnd);  
			if(isInline(pointVector.get(i), nextP, crossPoint) == true){       //현재 점과 다음 점을 잇는 직선에 접는선이 통과하면
				result.add(crossPoint);                                                  //그 교차점을 찾아서 결과 벡터에 넣음
			}
		}
		return result;
	}
	
	/**
	 * 다각형의 점 정보를 저장하는 벡터를 비운다
	 */
	public void clear(){
		pointVector.clear();
	}
	
	/**
	 * 벡터에 점 정보를 추가한다
	 * @param p  추가될 PointF 객체
	 */
	public void add(PointF p){
		pointVector.add(p);
	}
	
	/**
	 * 다각형을 화면에 그린다
	 * @param canvas  그려질 캔버스 객체
	 */
	public void draw(Canvas canvas, int ARGB){
		Paint Pnt = new Paint();
		Path path = new Path();
		path.reset();
		
		for(int i = 0; i<pointVector.size(); i++){              //벡터에서 점 두개씩 뽑아서 직선 그려줌
			if(i == 0){
				path.moveTo(pointVector.get(i).x, pointVector.get(i).y);
			}
			
			if(i == (pointVector.size()-1)){
				path.lineTo(pointVector.get(0).x, pointVector.get(0).y);
			}
			else{
				path.lineTo(pointVector.get(i+1).x, pointVector.get(i+1).y);
			}
		}
		
		Pnt.setAntiAlias(true);
		Pnt.setStrokeWidth(1);
		Pnt.setColor(Color.BLACK);
		Pnt.setStyle(Paint.Style.STROKE);
		
		canvas.drawPath(path, Pnt);
		
		Pnt.setColor(ARGB);
		Pnt.setStyle(Paint.Style.FILL);
		
		canvas.drawPath(path, Pnt);
	}
	
	/**
	 * 터치 입력의 중간점을 통과하는 수직 기울기에 입력한 점을 대칭시킨 값을 구한다
	 * @param targetPoint   대칭시킨 값을 구할 점
	 * @param tlStart         터치 입력 시작점
	 * @param tlEnd           터치 입력 끝점
	 * @return 대칭된 결과 값
	 */
	private PointF getSymmetryPoint(PointF targetPoint, PointF tlStart, PointF tlEnd){
		PointF result;
		result = new PointF();
		
		float gradient = getGradient(tlStart, tlEnd);
		gradient = -1 / gradient;
		
		PointF center = new PointF();                                        
		center = getCenterPoint(tlStart, tlEnd); 
		
		float intercept = center.y - (gradient * center.x);
		
		if ((tlStart.y-tlEnd.y)==0){   //기울기가 수직일 때는 x값 만 대칭시켜주면 됨
			if(center.x > targetPoint.x){      
				result.x = targetPoint.x + ((center.x - targetPoint.x) * 2);
			}
			else if(center.x < targetPoint.x){
				result.x = targetPoint.x - ((targetPoint.x - center.x) * 2);
			}
			else{
				result.x = targetPoint.x;
			}
			result.y = targetPoint.y;
		}
		else {  //기울기가 수직이 아닐 때
			      //http://blog.naver.com/terry422?Redirect=Log&logNo=130145856818 
		          //공식 이용
			result.x = (targetPoint.x * ((1) - (gradient*gradient))) - ((2*gradient) * (-1 * targetPoint.y + intercept));
			result.x = result.x / ((gradient * gradient) + (1));
			
			result.y = (targetPoint.y * ((gradient*gradient)-1)) - ((2*-1)*(gradient*targetPoint.x + intercept));
			result.y = result.y / ((gradient * gradient) + (1));
		}
		return result;
	}
	
	/**
	 * 터치 입력의 중간점을 통과하는 수직기울기와 입력한 직선이 교차하는 점을 구한다
	 * @param lStart  직선의 시작점
	 * @param lEnd    직선의 끝점
	 * @param tlStart 터치 입력의 시작점
	 * @param tlEnd   터치 입력의 끝점
	 * @return           직선과 기울기의 교차점. 교차하지 않을때는 null값 반환
	 */
	private PointF getCrossPoint(PointF lStart, PointF lEnd, PointF tlStart, PointF tlEnd){
		PointF result;
		result = new PointF();
		
		PointF center = new PointF();                                          //터치 라인의 가운데 점
		center = getCenterPoint(tlStart, tlEnd);                             
		
		float tlGradient = getGradient(tlStart, tlEnd);	  //터치 라인의 수직이되는 기울기
		tlGradient = -1 / tlGradient;
		
		float lGradient = getGradient(lStart, lEnd);
		float lIntercept = getIntercept(lStart, lGradient);
		
		if (((lStart.x-lEnd.x)==0) && ((tlStart.y-tlEnd.y)==0))           //직선이 수직&터치 기울기가 수직
			return null;
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.x-tlEnd.x)==0))    //직선이 수평&터치 기울기가 수평
			return null;
		else if (((lStart.x-lEnd.x)==0) && ((tlStart.x-tlEnd.x)==0)){   //직선이 수직&터치 기울기가 수평
			result.x = lStart.x;
			result.y = center.y;
		}
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.y-tlEnd.y)==0)){  //직선이 수평&터치 기울기가 수직
			result.x = center.x;
			result.y = lStart.y;
		}
		else if ((lStart.y-lEnd.y)==0){                                          //직선이 수평&터치 기울기가 대각선
			result.y = lStart.y;
			result.x = (result.y + (tlGradient*center.x) - center.y) / tlGradient;
		}
		else if ((lStart.x-lEnd.x)==0){                                          //직선이 수직&터치 기울기가 대각선
			result.x = lStart.x;
			result.y = (tlGradient * (result.x-center.x)) + center.y;
		}
		else if ((tlStart.x-tlEnd.x)==0){                                       //직선이 대각선&터치 기울기가 수평
			result.y = center.y;
			result.x = (result.y - lIntercept) / lGradient; 			
		}
		else if ((tlStart.y-tlEnd.y)==0){                                      //직선이 대각선&터치 기울기가 수직
			result.x = center.x;
			result.y = lGradient * result.x + lIntercept;
		}
		else{                                                                          //직선이 대각선&터치 기울기가 대각선
			result.x = (center.y - (tlGradient*center.x) - lIntercept) / (lGradient - tlGradient);
			result.y = lGradient * result.x + lIntercept;
		}
		return result;
	}
	public static PointF getCrossPointFromLine(PointF lStart, PointF lEnd, PointF tlStart, PointF tlEnd){
		PointF result;
		result = new PointF();
		
		PointF center = new PointF();                                          //터치 라인의 가운데 점
		center = getCenterPoint(tlStart, tlEnd);                             
		
		float tlGradient = getGradient(tlStart, tlEnd);	  //터치 라인의 수직이되는 기울기
		
		float lGradient = getGradient(lStart, lEnd);
		float lIntercept = getIntercept(lStart, lGradient);
		if((lStart.equals(tlStart.x, tlStart.y))||(lStart.equals(tlEnd.x, tlEnd.y))
				||(lEnd.equals(tlStart.x, tlStart.y))|| (lEnd.equals(tlEnd.x, tlEnd.y)))
				return null;
		if (((lStart.x-lEnd.x)==0) && ((tlStart.x-tlEnd.x)==0))           //직선이 수직&터치 기울기가 수직
			return null;
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.y-tlEnd.y)==0))    //직선이 수평&터치 기울기가 수평
			return null;
		else if (((lStart.x-lEnd.x)==0) && ((tlStart.y-tlEnd.y)==0)){   //직선이 수직&터치 기울기가 수평
			result.x = lStart.x;
			result.y = center.y;
		}
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.x-tlEnd.x)==0)){  //직선이 수평&터치 기울기가 수직
			result.x = center.x;
			result.y = lStart.y;
		}
		else if ((lStart.y-lEnd.y)==0){                                          //직선이 수평&터치 기울기가 대각선
			result.y = lStart.y;
			result.x = (result.y + (tlGradient*center.x) - center.y) / tlGradient;
		}
		else if ((lStart.x-lEnd.x)==0){                                          //직선이 수직&터치 기울기가 대각선
			result.x = lStart.x;
			result.y = (tlGradient * (result.x-center.x)) + center.y;
		}
		else if ((tlStart.y-tlEnd.y)==0){                                       //직선이 대각선&터치 기울기가 수평
			result.y = center.y;
			result.x = (result.y - lIntercept) / lGradient; 			
		}
		else if ((tlStart.x-tlEnd.x)==0){                                      //직선이 대각선&터치 기울기가 수직
			result.x = center.x;
			result.y = lGradient * result.x + lIntercept;
		}
		else{                                                                          //직선이 대각선&터치 기울기가 대각선
			result.x = (center.y - (tlGradient*center.x) - lIntercept) / (lGradient - tlGradient);
			result.y = lGradient * result.x + lIntercept;
		}
		return result;
	}
	/**
	 * 교차점이 직선의 범위에 포함되는지 확인한다
	 * @param lStart  직선의 시작점
	 * @param lEnd    직선의 끝점
	 * @param cPoint 확인할 교차점
	 * @return   포함되면 true 포함하지 않으면 false 반환
	 */
	public static boolean isInline(PointF lStart, PointF lEnd, PointF cPoint){
		float big, small, mid;
		
		if(cPoint == null)
			return false;
		
		if((lStart.x-lEnd.x)==0){    //수직일때는 y값으로 비교
			big = lStart.y;
			small = lEnd.y;
			mid = cPoint.y;
		}
		else{                            //기울기가 있으면 x값으로 비교
			big = lStart.x;
			small = lEnd.x;
			mid =cPoint.x;
		}
		
		if(big<small){              //big에다 큰 값 넣고 small에 작은 값 넣기
			float temp = big;
			big = small;
			small = temp;
		}
		
		if((big>mid) && (small<mid)){ //큰거보다 작고 작은거 보다 크면 가운데 있으니 선위에 존재한다
			return true;
		}
		else{
			return false;
		}
	}
	public static boolean isInlineExps(PointF lStart, PointF lEnd, PointF cPoint){
		float big, small, mid;
		
		if(cPoint == null)
			return false;
		
		if((lStart.x-lEnd.x)==0){    //수직일때는 y값으로 비교
			big = lStart.y;
			small = lEnd.y;
			mid = cPoint.y;
		}
		else{                            //기울기가 있으면 x값으로 비교
			big = lStart.x;
			small = lEnd.x;
			mid =cPoint.x;
		}
		
		if(big<small){              //big에다 큰 값 넣고 small에 작은 값 넣기
			float temp = big;
			big = small;
			small = temp;
		}
		
		if((big>=mid) && (small<=mid)){ //큰거보다 작고 작은거 보다 크면 가운데 있으니 선위에 존재한다
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 특정 점과 터치 입력의 시작점이 기울기를 기준으로 나뉘어진 두 면적중 같은 공간에 있는지 확인한다
	 * @param point     확인 하고자 하는 점 
	 * @param tlStart   터치 입력의 시작 점. 비교대상이 된다.
	 * @param tlEnd     터치 입력의 끝점
	 * @return  같은 면적에 있으면 true. 다른 면적에 있으면 false 반환
	 */
	private boolean containsPoint(PointF point, PointF tlStart, PointF tlEnd){
		float tlGradient = getGradient(tlStart, tlEnd);	  //터치 라인의 수직이되는 기울기
		tlGradient = -1 / tlGradient;
		
		PointF center = new PointF();                                          //터치 라인의 가운데 점
		center = getCenterPoint(tlStart, tlEnd);  
		
		//point와 tlStart(터치 입력시작점)가 기울기로 나뉘어진 두 면적중에서 같은면적에 있는지 확인
		if ((tlStart.y-tlEnd.y)==0){   //입력이 수평. 따라서 만들어진 기울기는 수직. 수직일땐 무한값이라 계산이 안되서 따로 빼서 계산.
			if(((point.x>center.x)&&(tlStart.x>center.x)) || ((point.x<center.x)&&(tlStart.x<center.x))){
				return true;      //point와 tlStart의 x값이 둘다 수직 기울기의 x값보다 클 때나, 둘다 수직 기울기의 y값보다 작을때 같은 면적에 있다고 할 수 있다
			}
			else
				return false;
		}
		else{  //기울기가 수직이 아닐때는 point.x 값과 tlStart.x 값을 기울기 공식에 대입하여 기울기에서 해당 x값에 대응하는 y값을 구한다.
			float pointOnGradY = tlGradient * (point.x - center.x) + center.y;  //기울기에 point.x값을 대입해서 y값 구함
			float tlStartOnGradY = tlGradient * (tlStart.x - center.x) + center.y; //기울기에 tlStart.x값을 대입해서 y값 구함
			if( ((point.y>pointOnGradY)&&(tlStart.y>tlStartOnGradY)) || ((point.y<pointOnGradY)&&(tlStart.y<tlStartOnGradY)) ){
				return true;    //구해진 y값 들 과 point.y, tlStart.y를 비교하여 point.y, tlStart.y가 둘다 기울기 보다 위에 있을때 
			}                       //아니면 둘다 기울기 보다 아래 있을때 같은 면적에 있다고 보고 트루값을 리턴함.
			else                   
				return false; 
		}
	}
	/**
	 * 두 점을 잇는 직선의 기울기를 구한다. 
	 * @param start  직선의 시작점
	 * @param end    직선의 끝점
	 * @return          기울기를 반환 한다
	 */
	public static float getGradient(PointF start, PointF end){
		return (start.y - end.y)/(start.x - end.x);
	}
	
	/**
	 * 기울기와 기울기의 중간 점으로 절편을 구한다
	 * @param start  기울기의 중간 점
	 * @param gradient 기울기
	 * @return 절편을 반환 한다
	 */
	public static float getIntercept(PointF start, float gradient){
		return start.y - (gradient * start.x);
	}
	
	/**
	 * 직선의 중간 좌표를 구한다
	 * @param start 직선의 시작점
	 * @param end   직선의 끝점
	 * @return         직선 중간점
	 */
	private static PointF getCenterPoint(PointF start, PointF end){
		PointF p = new PointF();
		p.x = (start.x + end.x) / 2;
		p.y = (start.y + end.y) / 2;
		return p;
	}
	
}
