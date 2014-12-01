package main;

import java.awt.Dimension;
import java.util.HashMap;

import javax.crypto.spec.OAEPParameterSpec;

/**
 * @author Felix Kibellus
 * Visuelle repräsentation der map
 * */
public class VisualMap {

	private int[][] areaNumberOfFields;
	private LandType[] types;
	
	public VisualMap(int[][] areaNumberOfFields, LandType[] types){
		this.areaNumberOfFields = areaNumberOfFields;
		this.types = types;
	}
	
	/**
	 * Gibt die dimensionen (breite, hoehe) der map als dimension-objekt zurueck
	 * */
	public Dimension getDimension(){
		Dimension dim = new Dimension(areaNumberOfFields.length, areaNumberOfFields[0].length);
		return dim;
	}

	
	public int[][] getAreaNumberOfFields() {
		return areaNumberOfFields;
	}

	public LandType[] getTypes() {
		return types;
	}

	public void setAreaNumberOfFields(int[][] areaNumberOfFields) {
		this.areaNumberOfFields = areaNumberOfFields;
	}

	public void setTypes(LandType[] types) {
		this.types = types;
	}
	
	//network-----------------------------------------------------------------
	public String getNetwork(){
		StringBuilder sb = new StringBuilder();
		
		//add the areaNumberOfFields array
		for (int i = 0; i < areaNumberOfFields.length; i++) {
			for (int j = 0; j < areaNumberOfFields[i].length; j++) {
				sb.append(areaNumberOfFields[i][j]);
				if(j!=areaNumberOfFields[i].length-1)
					sb.append(",");
			}
			if(i!=areaNumberOfFields.length-1)
				sb.append("#");
		}
		
		//add the LandTypes
		sb.append("<"); //to split it
		for (int i = 0; i < types.length; i++) {
			sb.append(types[i].getNetwork());
			if(i != types.length-1)
				sb.append(",");
		}
		return sb.toString();
	}
	
	public static VisualMap parseVisualMap(String str, Dimension dim){
		String[] parts = str.split("<");
		int[][] areaNumberOfFields = parseAreaNumberOfFields(parts[0], dim);
		LandType[] types = parseLandType(parts[1]);
		return new VisualMap(areaNumberOfFields, types);
	}
	
	private static LandType[] parseLandType(String string) {
		String[] parts = string.split(",");
		LandType[] types = new LandType[parts.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = LandType.parseLandType(parts[i]);
		}
		return types;
	}

	private static int[][] parseAreaNumberOfFields(String string, Dimension dim){
		int[][] areaNumberOfFields = new int[dim.height][dim.width];
		String[] parts = string.split("#");
		for (int i = 0; i < parts.length; i++) {
			String[] parts2 = parts[i].split(",");
			for (int j = 0; j < parts2.length; j++) {
				areaNumberOfFields[i][j]=Integer.parseInt(parts2[j]);
			}
		}
		return areaNumberOfFields;
	}
	
	public static void main(String[] args){
		Species [] species = new Species[4];
		for (int i = 0; i < species.length; i++) {
			species[i] = new Species("Peter", 5, 5, 5, 5, 5, -5, 30, 5, 0.2, 1, true);
			//new Species(intelligence, agility, strength, social, procreation, minTemp, maxTemp, resourceDemand, movementChance, visibillity, water)
		} 
		
		HashMap<FieldType, Double> pct = new HashMap<>();
		pct.put(FieldType.WATER, 0.25);
		pct.put(FieldType.LAND, 0.25);
		pct.put(FieldType.ICE, 0.25);
		pct.put(FieldType.JUNGLE, 0.25);
		
		Map map = Map.fromRandom(100, 200, species, pct);
		VisualMap visMap = map.getVisualRepresentation();
		String a = visMap.getNetwork();
		VisualMap visMap2 = VisualMap.parseVisualMap(a, new Dimension(200,100));
	}
}
