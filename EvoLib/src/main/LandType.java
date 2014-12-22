package main;

import java.io.Serializable;

/**
 * 
 * @author Niklas Adams
 *Landtype hält Informationen über den Landschaftstypen einer Area und somit
 *auch der Felder, aus der eine Area besteht. Diese Informationen wie Temperatur
 *Ressources usw bestimmen die Überlebensfähigkeit der Spezies auf diesem LandType
 */
public class LandType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public LandType(LandType other) {
		super();
		this.minTemp = other.minTemp;
		this.maxTemp = other.maxTemp;
		this.type = other.type;
		this.naturalEnemies = other.naturalEnemies;
		this.resources = other.resources;
	}
	
	public LandType(int minTemp, int maxTemp, FieldType type,
				int naturalEnemies, int resources) {
			super();
			this.minTemp = minTemp;
			this.maxTemp = maxTemp;
			this.type = type;
			this.naturalEnemies = naturalEnemies;
			this.resources = resources;
		}
	private int minTemp;
	private int maxTemp;
	private FieldType type;
	private int naturalEnemies;
	private int resources;
	public int getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(int minTemp) {
		this.minTemp = minTemp;
	}
	public int getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(int maxTemp) {
		this.maxTemp = maxTemp;
	}
	public FieldType getType() {
		return type;
	}
	public void setType(FieldType type) {
		this.type = type;
	}
	public int getNaturalEnemies() {
		return naturalEnemies;
	}
	public void setNaturalEnemies(int naturalEnemies) {
		this.naturalEnemies = naturalEnemies;
	}
	public int getResources() {
		return resources;
	}
	public void setResources(int resources) {
		this.resources = resources;
	}
	public FieldType getFieldType(){
		return this.type;
	}
	
	//network-----------------------------------------------------------------
	public static LandType parseLandType(String str){
		String[] parts = str.split(">");
		int minTemp = Integer.parseInt(parts[0]);
		int maxTemp = Integer.parseInt(parts[1]);
		FieldType type = FieldType.valueOf(parts[2]);
		int naturalEnemies = Integer.parseInt(parts[3]);
		int resources = Integer.parseInt(parts[4]);
		return new LandType(minTemp, maxTemp, type, naturalEnemies, resources);
	}
	
	public String getNetwork(){
		StringBuilder sb = new StringBuilder();
		sb.append(minTemp+">");
		sb.append(maxTemp+">");
		sb.append(type+">");
		sb.append(naturalEnemies+">");
		sb.append(resources);
		return sb.toString();
	}
}
