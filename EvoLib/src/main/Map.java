package main;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Queue;

import java.awt.Color;
import javax.swing.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Map {

	//attributes
	private Field[][] fields;
	private Species[] species;
	private Area[] areas;
	private IMapLogic logic;
	
	private Map(int width, int height, Species[] species){
		this.species =species;
		this.logic = new SimpleMapLogic(species);
	}
	
	/**
	 * Factory Method to load a map from a file
	 */
	public static Map fromFile() {
		//TODO
		return new Map(0,0, new Species[0]);
	}
	
	/**
	 * Factory Method to generate a random Map
	 */
	public static Map fromRandom(int width, int height, Species[] species, HashMap<FieldType, Double> pct) {
		Map res = new Map(width, height, species);
		
		/* parameters of the noise algorithm, change at will */
		int octaveCount = 8;
		double persistance = .2;
		
		double[][] noise = RandomNoise.getNoise(width, height, octaveCount, persistance);
		FieldType[][] fieldtypes = new FieldType[width][height];
		Field[][] fields = new Field[width][height];
		
		/* Wenn pct die Verteilungsdichte ist, ist accPct die Verteilungsfunktion.*/
		LinkedList<Tuple<FieldType, Double>> accPct = new LinkedList<Tuple<FieldType,Double>>();
		
		double accumulator = 0;
		for (FieldType ft : pct.keySet()) {
			accumulator += pct.get(ft);
			accPct.addLast(new Tuple<FieldType, Double>(ft, accumulator));
		}
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				fieldtypes[i][j] = null;
				for (Tuple<FieldType, Double> t : accPct) {
					if (fieldtypes[i][j] != null) break;
					if (noise[i][j] < t.t2) fieldtypes[i][j] = t.t1;
				}
				fields[i][j] = new Field(i, j);
			}
		}
		
		//***************** TODO: DELETE*****************************
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				switch (fieldtypes[i][j]) {
				case WATER:
					image.setRGB(i, j, new Color(255, 0, 0).getRGB());
					break;
				case DESERT:
					image.setRGB(i, j, new Color(0, 255, 0).getRGB());
					break;
				case ICE:
					image.setRGB(i, j, new Color(0, 0, 255).getRGB());
					break;
				case JUNGLE:
					image.setRGB(i, j, new Color(0, 0, 0).getRGB());
					break;
				case LAND:
					image.setRGB(i, j, new Color(255, 255, 255).getRGB());
					break;
				default:
					break;
				}				
			}
		}
		
		final JFrame frame = new JFrame();
    	frame.setSize(width, height);
    	
		JPanel pane = new JPanel() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, null);
			}
		};
		frame.add(pane);
		
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		        frame.setVisible(true);
		    }
		} );
		//***********************************************************
		
		LinkedList<Area> areas = new LinkedList<Area>();
		int numberArea = 0;
		Field[] fieldsInArea;
		HashSet<Field> done = new HashSet<Field>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (done.contains(fields[i][j]))
					continue;
				fieldsInArea = getFieldsInArea(i, j, fieldtypes, fields, done);
				LandType landType = new LandType(0, 0, fieldtypes[i][j], 0, 0);
				Area area = new Area(numberArea++, landType, fieldsInArea);
				areas.push(area);
				for (Field f : fieldsInArea) {
					f.setArea(area);
				}
			}
		}
		
		res.fields = fields;
		
		Area[] areaArray = new Area[areas.size()];
		
		for (int i = 0; i < areas.size(); i++) {
			areaArray[i] = areas.get(i);
		}
		res.areas = areaArray;
				
		return res;
	}
	
	private static Field[] getFieldsInArea(int x, int y, FieldType[][] fieldtypes,
			Field[][] fields, HashSet<Field> done) {
		
		LinkedList<Field> fieldsInArea = new LinkedList<Field>();
		FieldType type = fieldtypes[x][y];
		int width = fields.length;
		int height = fields[0].length;
		
		LinkedList<Field> queue = new LinkedList<Field>();
		queue.push(fields[x][y]);
		
		while (!queue.isEmpty()) {
			Field f = queue.pop();
			done.add(f);
			fieldsInArea.push(f);
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (Math.abs(i + j) != 1 || f.x + i < 0 || f.x + i >= width
							|| f.y + j < 0 || f.y + j >= height) continue;
					if (fieldtypes[f.x + i][f.y + j] == type
							&& !done.contains(fields[f.x + i][f.y + j]))
						queue.push(fields[f.x + i][f.y + j]);
				}
			}
		}
		
		Field[] result = new Field[fieldsInArea.size()];
		
		for (int i = 0; i < fieldsInArea.size(); i++) {
			result[i] = fieldsInArea.get(i);
		}
		
		return result;
	}
	
	public LinkedList<Change> refreshMap(){
		LinkedList<Change> changeList = new LinkedList<>();
		for(Area area : areas){
			changeList.addAll(area.refreshArea(logic));
		}
		return changeList;
	}
	
	public LinkedList<Change> updateCircumstances(MapEvent event){
		return event.doChange(areas[event.getArea()]);
	}
	
	public void simulateMigration(){
		logic.simulateMigrations(fields);
	}
	
	public LinkedList<Change> calculateVisibility(){
		LinkedList<Change> changeList = new LinkedList<>();
		//TODO: implement this algorithm here or in map-logic????
		return changeList;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Areas="+areas.length+"\n");
		sb.append(fields.length+" x "+fields[0].length+"\n");
		for (int i = 0; i < fields[0].length; i++) {
			for (int j = 0; j < fields.length; j++) {
				sb.append(fields[j][i].getArea());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
