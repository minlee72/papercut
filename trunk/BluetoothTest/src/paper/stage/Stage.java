package paper.stage;

import java.io.Serializable;
import java.util.Vector;

import android.content.Context;
import android.graphics.PointF;

public class Stage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	transient public boolean selected;
	
	public String name;
	public int limit;
	public int current;
	public int titleImage;
	public int titleClearImage;
	public int score;
	
	Vector<PointF>pt;
	
	public Stage(String name, int limit, Vector<PointF>pt){
		this.name = name;
		this.selected = false;
		this.limit = limit;
		this.current = limit;
		this.pt = new Vector<PointF>();
		this.pt = pt;
	}
}
