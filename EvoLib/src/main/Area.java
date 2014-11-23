package main;
//Fertig implementiert

import java.io.Serializable;
/**
 
* @author Niklas Adams
 * Eine Area ist eine zusammenhängender Bereich von Feldern mit gemeinsamen Eigenschaft, wie
 * eine Wüste oder ein Urwald. Area hält die zugehörigen Felder, seinen Landtypen
 * und die zugehörigen Populationen aller Spezies.
* Die Area gibt den Aufrauf zum Ausrechnen der Populaitonen an seine Felder weiter und kann sich
* im Laufe der Simulation durch zufällige Ereignisse ändern. 
*/
import java.util.LinkedList;

public class Area implements Serializable{
private Field[] fields;
private LandType landType;
private int[] population; //TODO wenn nicht gebraucht rausnehmen
private int number;

	public Area(int numberArea,LandType landType, Field[] fields){
		this.population = new int[4];
		this.number=numberArea;
		this.landType=landType;
		this.fields=fields;
	}

	public LandType getLandType() {
	return landType;
}
	public LinkedList<Change>refreshArea(IMapLogic logic){
		LinkedList<Change> toReturn= new LinkedList<Change>();
		//einzelne Felder der Area refreshen
		for(Field currentField:fields){
			//Changes in LinkedList packen
			Change toList=currentField.refreshField(logic);
			if(toList!=null)
				toReturn.addLast(toList);
		}
		//Changes der Population in der Area anhängnen und zurückgeben
		calculatePopulation();
		toReturn.addLast(new AreaPopulationChange(number,population.clone()));
		//Linked List zurückgeben
		return toReturn;
	
	}
	public Change setLandType(LandType newType){
		//Typ wechseln
		landType=newType;
		//TODO Objekt des obertypen change zurückgeben, welche Informationen
		//TODO zur darstellung des neuen FieldTyp für die Gui enthält
		return new AreaLandTypeChange(number, newType);
		
	}
	public LinkedList<Change>changePopulation(float percentage){
		LinkedList<Change> toReturn= new LinkedList<Change>();
		//einzelne Felder der Area mit Prozentsatz neu berechnen
		for(Field currentField:fields){
			//die Changes Objekte in LinkedList packen und zurückgeben
			toReturn.addLast(currentField.changePopulationByPercentage(percentage));
		}
		calculatePopulation();
		toReturn.addLast(new AreaPopulationChange(number,population.clone()));
		return toReturn;
	
	}
	private void calculatePopulation(){
		for(int i =0;i<4;i++){
			population[i]=0;
		}
		for(Field field : fields){
			for(int i =0;i<4;i++){
				population[i]+=field.getPopulation()[i];
			}
		}
		
	}

	public int getNumber() {
		return number;
	}
	
	public Field[] getFields() {
		return fields;
	}
	
	public int[] getPopulation(){
		calculatePopulation();
		return population;
	}
	
	public String toString(){
		return number+"";
	}
	
	//this method: Felix Kibellus
	public void spawnSpecies(int playerNumber, IMapLogic logic){
		logic.spawnSpecies(fields, playerNumber);
	}
	public void setNumber(int number){
		this.number=number;
	}
}
