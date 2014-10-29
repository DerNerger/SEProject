
public class Field {
private int area;
private int[] population;
private boolean[] isVisible;
private int[] migrations;
	
public int getArea() {
	return area;
}
public void setArea(int area) {
	this.area = area;
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
	//alte population speichern
	int [] diff = population.clone();
	//entwicklung simulieren
	logic.simulateGroth(this);
	//neue Population ausrechnen
	for (int i=0; i<=3;i++){
		diff[i]=population[i]-diff[i];
	}
	//TODO Change Objekt mit den zugehörigen Daten erstellen und zurück geben;
}
public Change changePopulationByPercentage(float percentage){
	//alte population speichern
	int [] diff = population.clone();
	for (int i=0; i<=3;i++){
			population[i]*=percentage;
			diff[i]=population[i]-diff[i];
	}
	//TODO Change Objekt mit den zugehörigen Daten erstellen und zurück geben;
}
}
