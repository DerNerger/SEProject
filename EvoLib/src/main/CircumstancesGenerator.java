package main;

public class CircumstancesGenerator {
	//Verteilungsfunktion für die Mapevents
	private double pNot=0.92;
	private double pClimate=0.97;
	private double pLandType=0.985;
	private double pRadioactive=0.995;
	private double pMeteorite=1.0;
	MapEvent generateMapEvent(int numberOfAreas){
		double eventNumber=Math.random();
		MapEvent event;
		int areaNumber= (int) (Math.random()*numberOfAreas); //TODO nummer der Areas bekommen
		if(eventNumber<=pNot){
			event=new MapEventNot(areaNumber);
		}else if(eventNumber<=pClimate){
			//TODO neue Temperatur bekommen
			int minTemp=0;
			int maxTemp=40;
			event= new MapEventClimate(areaNumber, minTemp, maxTemp);
		}else if(eventNumber<=pLandType){
			//TODO zwischen verschiedenen Landtype auswählen und je nachdem setzen
			LandType newLandType= new LandType(0,0,FieldType.ICE,10,10);
			event=new MapEventToLandType(areaNumber, newLandType);
		}else if(eventNumber<=pRadioactive){
			event= new MapEventRadioactive(areaNumber);
		}else {
			event=new MapEventMeteorite(areaNumber);
		}
		
		
		return event;
	}
}
