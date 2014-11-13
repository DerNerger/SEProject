package main;
/**
 * 
 * @author Niklas Adams
 *Landtype hält Informationen über den Landschaftstypen einer Area und somit
 *auch der Felder, aus der eine Area besteht. Diese Informationen wie Temperatur
 *Ressources usw bestimmen die Überlebensfähigkeit der Spezies auf diesem LandType
 */
public class LandType {
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
}
