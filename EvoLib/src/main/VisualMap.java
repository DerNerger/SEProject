package main;

import java.awt.Dimension;
import java.util.Arrays;
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
	public int getHeight(){
		return areaNumberOfFields[0].length;
	}

	public int getWidth(){
		return areaNumberOfFields.length;
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
	
	public static VisualMap parseVisualMap(String str, int width, int height){
		String[] parts = str.split("<");
		int[][] areaNumberOfFields = parseAreaNumberOfFields(parts[0], width, height);
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

	private static int[][] parseAreaNumberOfFields(String string, int width, int height){
		int[][] areaNumberOfFields = new int[width][height];
		String[] parts = string.split("#");
		for (int i = 0; i < parts.length; i++) {
			String[] parts2 = parts[i].split(",");
			for (int j = 0; j < parts2.length; j++) {
				areaNumberOfFields[i][j]=Integer.parseInt(parts2[j]);
			}
		}
		return areaNumberOfFields;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VisualMap other = (VisualMap) obj;
		if (!Arrays.deepEquals(areaNumberOfFields, other.areaNumberOfFields))
			return false;
		if (!Arrays.equals(types, other.types))
			return false;
		return true;
	}
	
	
}
