/**
* @author Niklas Adams
*Ein Objekt der Klasse Feld hält die Informationen zu einem Element 
* des Gitters, aus dem die Map besteht. Diese Informationen sind Populationen
* der Spezies, die Angabe der Sichtbarkeit dieses Feldes für jede Spezies,
* die migrationen, aus anderen Feldern kommend, die in dieser Runde hinzugerechnet
* müssen und die Area zu der dieses Feld gehört
*/
public class Field {
private final Area area;
private int[] population;
private boolean[] isVisible;
private int[] migrations;

/**
 * Field anlegen, Area muss bekannt sein.
 * Man kann mit Angabe der Area (diese muss bekannt sein) ein Field Objekt erstellen,
 * populationen und migrationen werden auf null gesetzt, die sichtbarkeit auf false
 * @param area
 */
public Field(Area area){
	this.area=area;
	for(int i=0;i<=3;i++){
		population[i]=0;
		isVisible[i]=false;
		migrations[i]=0;
	}
}
public Area getArea() {
	return area;
}
public int[] getPopulation() {
	return population;
}
public void setPopulation(int[] population) {
	this.population = population;
}
public boolean[] getIsVisible() {
	return isVisible;
}
public void setIsVisible(boolean[] isVisible) {
	this.isVisible = isVisible;
}
public int[] getMigrations() {
	return migrations;
}
public void setMigrations(int[] migrations) {
	this.migrations = migrations;
}
public Change refreshField(IMapLogic logic){
	//entwicklung simulieren
	logic.simulateGrowth(this);
	//neue Population ausrechnen
	return new FieldChange(population);
}
public Change changePopulationByPercentage(float percentage){
	//alte population speichern
	for (int i=0; i<=3;i++){
			population[i]*=percentage;
			
	}
	return new FieldChange(population);
}
}
