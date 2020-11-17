package belev.org.warface_app;

public class Achivment {
	
	private String name;
	private String about;
	private Integer achivmentid;
	private int thumbnail;
	
	public Achivment(String name, String about, Integer achivmentid, int thumbnail){
		super();
		this.name = name;
		this.about = about;
		this.achivmentid = achivmentid;
		this.thumbnail = thumbnail;
	}
	
	public String getName() {
		return name;
	}

	public String getAbout() {
		return about;
	}

	public Integer getAchivmentid() {
		return achivmentid;
	}

	public int getThumbnail() {
		return thumbnail;
	}
}
