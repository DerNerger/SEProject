import java.util.LinkedList;

public class Area {
private Field[] fields;
private LandType landType;
private int[] population;

	public LinkedList<Change>refreshArea(IMapLogic logic){
		LinkedList<Change> toReturn= new LinkedList<Change>();
		//einzelne Felder der Area refreshen
		for(Field currentField:fields){
			//Changes in LinkedList packen
			LinkedList.add(currentField.refreshField(logic));
		}
		//Linked List zurückgeben
		return toReturn;
	
	}
	public Change setLandType(LandType newType){
		//Typ wechseln
		landType=newType;
		//TODO Objekt des obertypen change zurückgeben, welche Informationen
		//TODO zur darstellung des neuen FieldTyp für die Gui enthält
		
	}
	public LinkedList<Change>(float percentage){
		LinkedList<Change> toReturn= new LinkedList<Change>();
		//einzelne Felder der Area mit Prozentsatz neu berechnen
		for(Field currentField:fields){
			//die Changes Objekte in LinkedList packen und zurückgeben
			LinkedList.add(currentField.changePopulationByPercentage(percentage));
		}
		return toReturn;
	
	}
}
