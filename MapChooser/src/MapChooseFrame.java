import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Map;
import main.MapLoader;

public class MapChooseFrame extends JFrame {
	
	public static int WIDTH = 200;
	public static int HEIGHT = 100;
	
	private MapPanel mp;
	private Map map;
	
	public MapChooseFrame() {
		super("MapChooser");

		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		mp = new MapPanel();
		this.add(mp);

		JButton nextMapBtn = new JButton("Naechste");
		nextMapBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextMap();
			}
		});
		
		JButton geiloBtn = new JButton("Geilo");
		geiloBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveMap();
			}
		});
		
		JButton loadMapBtn = new JButton("Laden");
		loadMapBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadMap();
			}
		});
		
		JPanel p = new JPanel();
		p.add(nextMapBtn);
		p.add(geiloBtn);
		p.add(loadMapBtn);
		this.add(p);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(225, 300);
		this.setVisible(true);
	}
	
	private void nextMap() {
		//TODO: Add the possibility to change parameters at runtime
		map = Map.fromRandom(WIDTH, HEIGHT, null, Map.getRandomFieldTypes());
		mp.setMap(map);
	}
	
	private void saveMap() {
		try {
			byte[] mapData = MapLoader.savePureMap(map);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");
			Date now = new Date();
			FileOutputStream fos = new FileOutputStream(sdf.format(now) + ".em");
			fos.write(mapData);
			fos.close();
			System.out.println("Working Directory = " +
		              System.getProperty("user.dir"));
		} catch (IOException e) {
			// TODO actually do something...
			e.printStackTrace();
		}
	}
	
	private void loadMap() {
		FileDialog fd = new FileDialog(this, "Map waehlen", FileDialog.LOAD);
		fd.setFile("*.em");
		fd.setVisible(true);
		String filename = fd.getFile();
		if (filename != null) {
			try {
				RandomAccessFile f = new RandomAccessFile(filename, "rw");
				byte[] mapData = new byte[(int)f.length()];
				f.read(mapData);
				f.close();
				
				map = MapLoader.loadPureMap(null, null, mapData);
				mp.setMap(map);
			} catch (Exception e) {
				// TODO gotta love defensive programming
				e.printStackTrace();
			}
		}
	}
}
