/**
 * @author Niklas Adams
 * Eine Area ist eine zusammenhängender Bereich von Feldern mit gemeinsamen Eigenschaft, wie
 * eine Wüste oder ein Urwald. Area hält die zugehörigen Felder, seinen Landtypen
 * und die zugehörigen Populationen aller Spezies.
* Die Area gibt den Aufrauf zum Ausrechnen der Populaitonen an seine Felder weiter und kann sich
* im Laufe der Simulation durch zufällige Ereignisse ändern. 
*/
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
			LinkedList.addLast(currentField.refreshField(logic));
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
	public LinkedList<Change>changePopulation(float percentage){
		LinkedList<Change> toReturn= new LinkedList<Change>();
		//einzelne Felder der Area mit Prozentsatz neu berechnen
		for(Field currentField:fields){
			//die Changes Objekte in LinkedList packen und zurückgeben
			LinkedList.addLast(currentField.changePopulationByPercentage(percentage));
		}
		return toReturn;
	
	}
}
