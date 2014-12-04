import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.FieldType;
import main.LandType;
import main.Map;
import main.VisualMap;

public class MapPanel extends JPanel {
	
	private VisualMap map;
	private int width, height;
	
	public MapPanel() {
		super();
		map = null;
		width = 0;
		height = 0;
		this.setSize(200, 100);
	}
	
	public void setMap(Map map) {
		this.map = map.getVisualRepresentation();
		height = this.map.getHeight();
		width = this.map.getWidth();
		this.setSize(width, height);
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (map == null) return;
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		int[][] fields = map.getAreaNumberOfFields();
		LandType[] landtypes = map.getTypes();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int fieldIndex = fields[i][j];
				FieldType field = landtypes[fieldIndex].getFieldType();
				img.setRGB(i, j, getRGB(field));
			}
		}
		
		g.drawImage(img, 0, 0, null);
	}
	
	private int getRGB(FieldType ft) {
		switch (ft) {
			case DESERT: return Color.YELLOW.getRGB();
			case ICE: return Color.WHITE.getRGB();
			case JUNGLE: return Color.RED.getRGB();
			case LAND: return Color.GREEN.getRGB();
			case WATER: return Color.BLUE.getRGB();
			default: return -1;
		}
	}
}
