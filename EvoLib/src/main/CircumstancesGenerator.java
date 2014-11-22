package main;

import java.util.HashMap;

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
		int areaNumber= (int) (Math.random()*numberOfAreas); 
		if(eventNumber<=pNot){
			event=new MapEventNot(areaNumber);
		}else if(eventNumber<=pClimate){
			//TODO neue Temperatur bekommen
			int minTemp=0;
			int maxTemp=40;
			event= new MapEventClimate(areaNumber, minTemp, maxTemp);
		}else if(eventNumber<=pLandType){
			//Verteilungsfunktion auf Basis von Dichtefunktion mit gleichen Werten erstellen
			HashMap<Integer,FieldType> pF=new HashMap<Integer,FieldType>();
			for(int i =0;i<FieldType.values().length;i++){
				pF.put( (i+1),FieldType.values()[i]);
			}
			//Zufllswert auf den nächsthöheren Wert der Verteilungsfunktion 
			LandType newLandType= SimpleMapLogic.randomLandType(pF.get(((int)(Math.random()*(FieldType.values().length)+1))));
			event=new MapEventToLandType(areaNumber, newLandType);
		}else if(eventNumber<=pRadioactive){
			event= new MapEventRadioactive(areaNumber);
		}else {
			event=new MapEventMeteorite(areaNumber);
		}
		
		
		return event;
	}
	public static void main(String args[]){
		//Verteilungsfunktion auf Basis von Dichtefunktion mit gleichen Werten erstellen
		double p=1./(double)FieldType.values().length;
		HashMap<Double,FieldType> pF=new HashMap<Double,FieldType>();
		for(int i =0;i<FieldType.values().length;i++){
			pF.put( (i+1)*p,FieldType.values()[i]);
		}
		while(true){
		System.out.println(((int)(Math.random()*(FieldType.values().length)+1))*p);
		System.out.println(pF.toString());
		}
		//LandType newLandType= SimpleMapLogic.randomLandType(pF.get(((int)(Math.random()*FieldType.values().length))*p));
		
	}
}
