package de.orchit.bmwcomparator;


import java.util.Map;
import java.util.TreeMap;

public class CarData {
	private String name;
	private Map<String,String> options = new TreeMap<>();
	private String image;
	private String price;
	private String link;
	private String color;
	private String km;

	public String getSoldOn() {
		return soldOn;
	}

	public void setSoldOn(String soldOn) {
		this.soldOn = soldOn;
	}

	private String soldOn;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getKm() {
		return km;
	}

	public void setKm(String km) {
		this.km = km;
	}
}
