package main;

import java.util.HashMap;

public class CircumstancesGenerator {
	// Verteilungsfunktion für die Mapevents
	private double pNot = 0.965;
	private double pClimate = 0.975;
	private double pLandType = 0.985;
	private double pRadioactive = 0.996;
	private double pMeteorite = 1.0;


	MapEvent generateMapEvent(int numberOfAreas) {
		double eventNumber = Math.random();
		MapEvent event;
		int areaNumber = (int) (Math.random() * numberOfAreas);
		if (eventNumber <= pNot) {
			event = new MapEventNot(areaNumber);
		} else if (eventNumber <= pClimate) {
			// TODO neue Temperatur bekommen
			int minTemp = 0;
			int maxTemp = 40;
			event = new MapEventClimate(areaNumber, minTemp, maxTemp);
		} else if (eventNumber <= pLandType) {
			HashMap<FieldType, Double> dichte = Map.getRandomFieldTypes();
			// Verteilung aufbauen
			HashMap<FieldType, Double> verteilung = new HashMap<FieldType, Double>();
			double last = 0;
			for (FieldType t : dichte.keySet()) {
				verteilung.put(t, last);
				last += dichte.get(t);
			}
			// Zufllswert auf den nächsthöheren Wert der Verteilungsfunktion

			double p = Math.random();
			FieldType newType = FieldType.JUNGLE;
			double max = -1;
			for (FieldType t : verteilung.keySet()) {
				if (verteilung.get(t) < p) {
					if (verteilung.get(t) > max) {
						newType = t;
						max = verteilung.get(t);
					}
				}
			}
			LandType newLandType = SimpleMapLogic.randomLandType(newType);
			event = new MapEventToLandType(areaNumber, newLandType);
		} else if (eventNumber <= pRadioactive) {
			event = new MapEventRadioactive(areaNumber);
		} else {
			event = new MapEventMeteorite(areaNumber);
		}

		return event;
	}

	public static void main(String args[]) {
		while (true) {
			HashMap<FieldType, Double> dichte = Map.getRandomFieldTypes();
			// Verteilung aufbauen
			HashMap<FieldType, Double> verteilung = new HashMap<FieldType, Double>();
			double last = 0;
			for (FieldType t : dichte.keySet()) {
				verteilung.put(t, last);
				last += dichte.get(t);
			}
			// Zufllswert auf den nächsthöheren Wert der Verteilungsfunktion

			double p = Math.random();
			FieldType newType = FieldType.JUNGLE;
			double max = -1;
			for (FieldType t : verteilung.keySet()) {
				if (verteilung.get(t) < p) {
					if (verteilung.get(t) > max) {
						newType = t;
						max = verteilung.get(t);
					}
				}
			}

			System.out.println(verteilung);
			System.out.println(newType);
		}

	}
}
