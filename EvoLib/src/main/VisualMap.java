package main;

import java.awt.Dimension;

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
	
}
