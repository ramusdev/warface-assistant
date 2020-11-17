package belev.org.warface_app;

public class Maps {

	private String name;
	private String about;
	private String type;
	private int thumbnail;
	
	public Maps(String name, String about, String type, int thumbnail){
		super();
		this.name = name;
		this.about = about;
		this.type = type;
		this.thumbnail = thumbnail;
	}
	
	public String getName() {
		return name;
	}

	public String getAbout() {
		return about;
	}

	public String getType() {
		return type;
	}

	public int getThumbnail() {
		return thumbnail;
	}
}
