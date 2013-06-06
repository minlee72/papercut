package paper.data;

public class GameOption {
	private static GameOption instance = null;
	
	public int vibePower;
	public float soundVolume;
	
	private GameOption(){
		vibePower = 30;
		soundVolume = 0.5f;
	}
	
	public static GameOption getInstance(){
		if(instance == null)
			instance = new GameOption();
		return instance;
	}
}
