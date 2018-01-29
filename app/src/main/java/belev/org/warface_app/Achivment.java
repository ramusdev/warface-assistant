package belev.org.warface_app;

public class Achivment {
	
	private String name;
	private String about;
	//private Drawable iconID;
	private Integer achivmentid;
	private int thumbnail;
	
	public Achivment(String name, String about, Integer achivmentid, int thumbnail){
		super();
		this.name = name;
		this.about = about;
		//this.iconID = iconID;
		this.achivmentid = achivmentid;
		this.thumbnail = thumbnail;
	}
	
	public String getName() {
		return name;
	}
	public String getAbout() {
		return about;
	}
	//public Drawable getIconID() {
		//return iconID;
	//}
	public Integer getAchivmentid() { return achivmentid; }
	public int getThumbnail() { return thumbnail; }
}
