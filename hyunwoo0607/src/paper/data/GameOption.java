package paper.data;

public class GameOption {
	private static GameOption instance = null;
	
	public int vibePower;
	public float soundVolume;
	public boolean musicPlay;
	
	private GameOption(){
		vibePower = 30;
		soundVolume = 0.5f;
		musicPlay = true;
	}
	
	public static GameOption getInstance(){
		if(instance == null)
			instance = new GameOption();
		return instance;
	}
	
}
