package paper.gameActivity;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * �������� �ٰ����� �����ϱ� ���� Ŭ����
 * @author ������
 *
 */
public class Polygon {
	/**
	 * �ٰ����� �� ������ �����ϴ� ����
	 * 0�� �ε��� ���� ������ ���������� �������� �̾�����
	 */
	public Vector<PointF> pointVector;
	/**
	 * ������
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
	 * �ٰ����� ���ο� �Էµ� ���� ��ġ�ϴ��� Ȯ���Ѵ�.
	 * @param x Ȯ���� ���� x��ǥ
	 * @param y Ȯ���� ���� y��ǥ
	 * @return ���ο� ������ true, �ܺο� ������ false ��ȯ
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
	 * int�� �Է��� float�� ����ȯ �Ͽ� contains �Լ��� ȣ��
	 * @param x x��ǥ
	 * @param y y��ǥ
	 * @return contains(float, float)�� ���
	 */
	 public boolean contains(int x, int y) {
	        return contains((float) x, (float) y);
	    }
	/**
	 * �ٰ����� ��ġ �Է¿� ���� ���⿡ �����Ͽ� ��������, �������� �κ��� ���Ѵ�
	 * @param tlStart	��ġ�Է� ������
	 * @param tlEnd   ��ġ�Է� ����
	 * @return   �������� ���� ������ �ٰ���
	 */
	public Polygon pullPolygon (PointF tlStart, PointF tlEnd){
		if(pointVector.size() < 1)
			return null;	
		
		Polygon result = new Polygon();  //������ ���
		
		for (int i = 0; i<(pointVector.size()); i++){
			PointF hereP = pointVector.get(i);    //���Ϳ��� �� �ϳ��� �����´�
			PointF nextP;
			if (i != (pointVector.size()-1)){        //������ ���� ������ �ƴϸ�
				nextP = pointVector.get(i+1);     //�������� �����ͼ� �ؽ�Ʈ�� ����
			}
			else{                                         //������ ���� �����̸� �ؽ�Ʈ ���� 0����. �� �������� �ȴ�
				nextP = pointVector.get(0);
			}
					
			if(containsPoint(hereP, tlStart, tlEnd) == true){           //���� ���� ��Ī�ؾߵ� ���̶��
				result.add(getSymmetryPoint(hereP, tlStart, tlEnd)); //��Ī ��Ų�� ��� ���Ϳ� �߰�
			}
			PointF crossPoint = getCrossPoint(hereP, nextP, tlStart, tlEnd);  //���� ���� �������� �մ� ������ ���������ϴ� ������ ������
			if(isInline(pointVector.get(i), nextP, crossPoint) == true){        //���� ���� �������� �������̷� �������� ���Ⱑ �������ٸ�
				result.add(crossPoint);                                                   //�� �������� ���Ϳ� �߰�
			}
		}
		return result;
	}
	
	/**
	 * �ٰ����� ��ġ �Է¿� ���� ���⿡ �����Ͽ� ��������, �������� �ʴ� ������ �κ��� ���Ѵ�
	 * @param tlStart  ��ġ�Է� ������
	 * @param tlEnd   ��ġ�Է� ����
	 * @return  �������� �ٰ���
	 */
	public Polygon cutPolygon (PointF tlStart, PointF tlEnd){
		if(pointVector.size() < 1)
			return null;	
		
		Polygon result = new Polygon();
		
		for (int i = 0; i<(pointVector.size()); i++){
			PointF hereP = pointVector.get(i);        //���Ϳ��� ���� �ϳ� ������
			PointF nextP;
			if (i != (pointVector.size()-1)){
				nextP = pointVector.get(i+1);         
			}                                                   //���Ϳ��� ���� ���� �ϳ� �������µ� 
			else{                                             //������ �����̸� 0���� (������)�� ������
				nextP = pointVector.get(0);
			}
					
			if(containsPoint(hereP, tlStart, tlEnd) == false){  //���� ���� �������� �Ѱ����� ���� �ƴϸ�
				result.add(hereP);                                       //��� ���Ϳ� ����
			}
			PointF crossPoint = getCrossPoint(hereP, nextP, tlStart, tlEnd);  
			if(isInline(pointVector.get(i), nextP, crossPoint) == true){       //���� ���� ���� ���� �մ� ������ ���¼��� ����ϸ�
				result.add(crossPoint);                                                  //�� �������� ã�Ƽ� ��� ���Ϳ� ����
			}
		}
		return result;
	}
	
	/**
	 * �ٰ����� �� ������ �����ϴ� ���͸� ����
	 */
	public void clear(){
		pointVector.clear();
	}
	
	/**
	 * ���Ϳ� �� ������ �߰��Ѵ�
	 * @param p  �߰��� PointF ��ü
	 */
	public void add(PointF p){
		pointVector.add(p);
	}
	
	/**
	 * �ٰ����� ȭ�鿡 �׸���
	 * @param canvas  �׷��� ĵ���� ��ü
	 */
	public void draw(Canvas canvas, int ARGB){
		Paint Pnt = new Paint();
		Path path = new Path();
		path.reset();
		
		for(int i = 0; i<pointVector.size(); i++){              //���Ϳ��� �� �ΰ��� �̾Ƽ� ���� �׷���
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
	 * ��ġ �Է��� �߰����� ����ϴ� ���� ���⿡ �Է��� ���� ��Ī��Ų ���� ���Ѵ�
	 * @param targetPoint   ��Ī��Ų ���� ���� ��
	 * @param tlStart         ��ġ �Է� ������
	 * @param tlEnd           ��ġ �Է� ����
	 * @return ��Ī�� ��� ��
	 */
	private PointF getSymmetryPoint(PointF targetPoint, PointF tlStart, PointF tlEnd){
		PointF result;
		result = new PointF();
		
		float gradient = getGradient(tlStart, tlEnd);
		gradient = -1 / gradient;
		
		PointF center = new PointF();                                        
		center = getCenterPoint(tlStart, tlEnd); 
		
		float intercept = center.y - (gradient * center.x);
		
		if ((tlStart.y-tlEnd.y)==0){   //���Ⱑ ������ ���� x�� �� ��Ī�����ָ� ��
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
		else {  //���Ⱑ ������ �ƴ� ��
			      //http://blog.naver.com/terry422?Redirect=Log&logNo=130145856818 
		          //���� �̿�
			result.x = (targetPoint.x * ((1) - (gradient*gradient))) - ((2*gradient) * (-1 * targetPoint.y + intercept));
			result.x = result.x / ((gradient * gradient) + (1));
			
			result.y = (targetPoint.y * ((gradient*gradient)-1)) - ((2*-1)*(gradient*targetPoint.x + intercept));
			result.y = result.y / ((gradient * gradient) + (1));
		}
		return result;
	}
	
	/**
	 * ��ġ �Է��� �߰����� ����ϴ� ��������� �Է��� ������ �����ϴ� ���� ���Ѵ�
	 * @param lStart  ������ ������
	 * @param lEnd    ������ ����
	 * @param tlStart ��ġ �Է��� ������
	 * @param tlEnd   ��ġ �Է��� ����
	 * @return           ������ ������ ������. �������� �������� null�� ��ȯ
	 */
	private PointF getCrossPoint(PointF lStart, PointF lEnd, PointF tlStart, PointF tlEnd){
		PointF result;
		result = new PointF();
		
		PointF center = new PointF();                                          //��ġ ������ ��� ��
		center = getCenterPoint(tlStart, tlEnd);                             
		
		float tlGradient = getGradient(tlStart, tlEnd);	  //��ġ ������ �����̵Ǵ� ����
		tlGradient = -1 / tlGradient;
		
		float lGradient = getGradient(lStart, lEnd);
		float lIntercept = getIntercept(lStart, lGradient);
		
		if (((lStart.x-lEnd.x)==0) && ((tlStart.y-tlEnd.y)==0))           //������ ����&��ġ ���Ⱑ ����
			return null;
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.x-tlEnd.x)==0))    //������ ����&��ġ ���Ⱑ ����
			return null;
		else if (((lStart.x-lEnd.x)==0) && ((tlStart.x-tlEnd.x)==0)){   //������ ����&��ġ ���Ⱑ ����
			result.x = lStart.x;
			result.y = center.y;
		}
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.y-tlEnd.y)==0)){  //������ ����&��ġ ���Ⱑ ����
			result.x = center.x;
			result.y = lStart.y;
		}
		else if ((lStart.y-lEnd.y)==0){                                          //������ ����&��ġ ���Ⱑ �밢��
			result.y = lStart.y;
			result.x = (result.y + (tlGradient*center.x) - center.y) / tlGradient;
		}
		else if ((lStart.x-lEnd.x)==0){                                          //������ ����&��ġ ���Ⱑ �밢��
			result.x = lStart.x;
			result.y = (tlGradient * (result.x-center.x)) + center.y;
		}
		else if ((tlStart.x-tlEnd.x)==0){                                       //������ �밢��&��ġ ���Ⱑ ����
			result.y = center.y;
			result.x = (result.y - lIntercept) / lGradient; 			
		}
		else if ((tlStart.y-tlEnd.y)==0){                                      //������ �밢��&��ġ ���Ⱑ ����
			result.x = center.x;
			result.y = lGradient * result.x + lIntercept;
		}
		else{                                                                          //������ �밢��&��ġ ���Ⱑ �밢��
			result.x = (center.y - (tlGradient*center.x) - lIntercept) / (lGradient - tlGradient);
			result.y = lGradient * result.x + lIntercept;
		}
		return result;
	}
	public static PointF getCrossPointFromLine(PointF lStart, PointF lEnd, PointF tlStart, PointF tlEnd){
		PointF result;
		result = new PointF();
		
		PointF center = new PointF();                                          //��ġ ������ ��� ��
		center = getCenterPoint(tlStart, tlEnd);                             
		
		float tlGradient = getGradient(tlStart, tlEnd);	  //��ġ ������ �����̵Ǵ� ����
		
		float lGradient = getGradient(lStart, lEnd);
		float lIntercept = getIntercept(lStart, lGradient);
		if((lStart.equals(tlStart.x, tlStart.y))||(lStart.equals(tlEnd.x, tlEnd.y))
				||(lEnd.equals(tlStart.x, tlStart.y))|| (lEnd.equals(tlEnd.x, tlEnd.y)))
				return null;
		if (((lStart.x-lEnd.x)==0) && ((tlStart.x-tlEnd.x)==0))           //������ ����&��ġ ���Ⱑ ����
			return null;
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.y-tlEnd.y)==0))    //������ ����&��ġ ���Ⱑ ����
			return null;
		else if (((lStart.x-lEnd.x)==0) && ((tlStart.y-tlEnd.y)==0)){   //������ ����&��ġ ���Ⱑ ����
			result.x = lStart.x;
			result.y = center.y;
		}
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.x-tlEnd.x)==0)){  //������ ����&��ġ ���Ⱑ ����
			result.x = center.x;
			result.y = lStart.y;
		}
		else if ((lStart.y-lEnd.y)==0){                                          //������ ����&��ġ ���Ⱑ �밢��
			result.y = lStart.y;
			result.x = (result.y + (tlGradient*center.x) - center.y) / tlGradient;
		}
		else if ((lStart.x-lEnd.x)==0){                                          //������ ����&��ġ ���Ⱑ �밢��
			result.x = lStart.x;
			result.y = (tlGradient * (result.x-center.x)) + center.y;
		}
		else if ((tlStart.y-tlEnd.y)==0){                                       //������ �밢��&��ġ ���Ⱑ ����
			result.y = center.y;
			result.x = (result.y - lIntercept) / lGradient; 			
		}
		else if ((tlStart.x-tlEnd.x)==0){                                      //������ �밢��&��ġ ���Ⱑ ����
			result.x = center.x;
			result.y = lGradient * result.x + lIntercept;
		}
		else{                                                                          //������ �밢��&��ġ ���Ⱑ �밢��
			result.x = (center.y - (tlGradient*center.x) - lIntercept) / (lGradient - tlGradient);
			result.y = lGradient * result.x + lIntercept;
		}
		return result;
	}
	/**
	 * �������� ������ ������ ���ԵǴ��� Ȯ���Ѵ�
	 * @param lStart  ������ ������
	 * @param lEnd    ������ ����
	 * @param cPoint Ȯ���� ������
	 * @return   ���ԵǸ� true �������� ������ false ��ȯ
	 */
	public static boolean isInline(PointF lStart, PointF lEnd, PointF cPoint){
		float big, small, mid;
		
		if(cPoint == null)
			return false;
		
		if((lStart.x-lEnd.x)==0){    //�����϶��� y������ ��
			big = lStart.y;
			small = lEnd.y;
			mid = cPoint.y;
		}
		else{                            //���Ⱑ ������ x������ ��
			big = lStart.x;
			small = lEnd.x;
			mid =cPoint.x;
		}
		
		if(big<small){              //big���� ū �� �ְ� small�� ���� �� �ֱ�
			float temp = big;
			big = small;
			small = temp;
		}
		
		if((big>mid) && (small<mid)){ //ū�ź��� �۰� ������ ���� ũ�� ��� ������ ������ �����Ѵ�
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
		
		if((lStart.x-lEnd.x)==0){    //�����϶��� y������ ��
			big = lStart.y;
			small = lEnd.y;
			mid = cPoint.y;
		}
		else{                            //���Ⱑ ������ x������ ��
			big = lStart.x;
			small = lEnd.x;
			mid =cPoint.x;
		}
		
		if(big<small){              //big���� ū �� �ְ� small�� ���� �� �ֱ�
			float temp = big;
			big = small;
			small = temp;
		}
		
		if((big>=mid) && (small<=mid)){ //ū�ź��� �۰� ������ ���� ũ�� ��� ������ ������ �����Ѵ�
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * Ư�� ���� ��ġ �Է��� �������� ���⸦ �������� �������� �� ������ ���� ������ �ִ��� Ȯ���Ѵ�
	 * @param point     Ȯ�� �ϰ��� �ϴ� �� 
	 * @param tlStart   ��ġ �Է��� ���� ��. �񱳴���� �ȴ�.
	 * @param tlEnd     ��ġ �Է��� ����
	 * @return  ���� ������ ������ true. �ٸ� ������ ������ false ��ȯ
	 */
	private boolean containsPoint(PointF point, PointF tlStart, PointF tlEnd){
		float tlGradient = getGradient(tlStart, tlEnd);	  //��ġ ������ �����̵Ǵ� ����
		tlGradient = -1 / tlGradient;
		
		PointF center = new PointF();                                          //��ġ ������ ��� ��
		center = getCenterPoint(tlStart, tlEnd);  
		
		//point�� tlStart(��ġ �Է½�����)�� ����� �������� �� �����߿��� ���������� �ִ��� Ȯ��
		if ((tlStart.y-tlEnd.y)==0){   //�Է��� ����. ���� ������� ����� ����. �����϶� ���Ѱ��̶� ����� �ȵǼ� ���� ���� ���.
			if(((point.x>center.x)&&(tlStart.x>center.x)) || ((point.x<center.x)&&(tlStart.x<center.x))){
				return true;      //point�� tlStart�� x���� �Ѵ� ���� ������ x������ Ŭ ����, �Ѵ� ���� ������ y������ ������ ���� ������ �ִٰ� �� �� �ִ�
			}
			else
				return false;
		}
		else{  //���Ⱑ ������ �ƴҶ��� point.x ���� tlStart.x ���� ���� ���Ŀ� �����Ͽ� ���⿡�� �ش� x���� �����ϴ� y���� ���Ѵ�.
			float pointOnGradY = tlGradient * (point.x - center.x) + center.y;  //���⿡ point.x���� �����ؼ� y�� ����
			float tlStartOnGradY = tlGradient * (tlStart.x - center.x) + center.y; //���⿡ tlStart.x���� �����ؼ� y�� ����
			if( ((point.y>pointOnGradY)&&(tlStart.y>tlStartOnGradY)) || ((point.y<pointOnGradY)&&(tlStart.y<tlStartOnGradY)) ){
				return true;    //������ y�� �� �� point.y, tlStart.y�� ���Ͽ� point.y, tlStart.y�� �Ѵ� ���� ���� ���� ������ 
			}                       //�ƴϸ� �Ѵ� ���� ���� �Ʒ� ������ ���� ������ �ִٰ� ���� Ʈ�簪�� ������.
			else                   
				return false; 
		}
	}
	/**
	 * �� ���� �մ� ������ ���⸦ ���Ѵ�. 
	 * @param start  ������ ������
	 * @param end    ������ ����
	 * @return          ���⸦ ��ȯ �Ѵ�
	 */
	public static float getGradient(PointF start, PointF end){
		return (start.y - end.y)/(start.x - end.x);
	}
	
	/**
	 * ����� ������ �߰� ������ ������ ���Ѵ�
	 * @param start  ������ �߰� ��
	 * @param gradient ����
	 * @return ������ ��ȯ �Ѵ�
	 */
	public static float getIntercept(PointF start, float gradient){
		return start.y - (gradient * start.x);
	}
	
	/**
	 * ������ �߰� ��ǥ�� ���Ѵ�
	 * @param start ������ ������
	 * @param end   ������ ����
	 * @return         ���� �߰���
	 */
	private static PointF getCenterPoint(PointF start, PointF end){
		PointF p = new PointF();
		p.x = (start.x + end.x) / 2;
		p.y = (start.y + end.y) / 2;
		return p;
	}
	
}
