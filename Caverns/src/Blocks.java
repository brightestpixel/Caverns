import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Blocks extends JFrame {
	private static final long serialVersionUID = 1L;
	private int blockXY = 9;
    private int Thing = 0;
    private Vector Center = new Vector(0, 0);
    private boolean timer1 = false;
    private float timer01 = 0;
    private Vector mapMax = new Vector(5000,5000);
	private Vector mapMin = new Vector(5000,5000);
    private Vector mapMaxCurrent = new Vector(5000,5000);
    private Vector mapMinCurrent = new Vector(5000,5000);
    private boolean colOn = true;
    private int stability = 100;
    private int speed = 500;
    
    //private Stats stat = new Stats();

    public Inventory inventory = new Inventory();
    
    //stats
    
    String stone_file = "Resources\\blocks\\stone.png";
    BufferedImage stone;
    String ground_file = "Resources\\blocks\\ground.png";
    BufferedImage ground;
    String iron_file = "Resources\\blocks\\iron.png";
    BufferedImage iron;
    String gold_file = "Resources\\blocks\\gold.png";
    BufferedImage gold;
    String copper_file = "Resources\\blocks\\copper.png";
    BufferedImage copper;
    


    public String[][] StoreB = new String[10000][10000];

    public Blocks() {
		StoreB[5000][5000] = "a";
		ground = loadTexture(ground_file);
		stone = loadTexture(stone_file);
		iron = loadTexture(iron_file);
		gold = loadTexture(gold_file);
		copper = loadTexture(copper_file);
    }

    public void draw(Graphics2D g, Vector p, Vector v, int pSize, boolean Space, String Key, float dt) {
		mapMinCurrent.setX((int) Math.ceil((-p.y - 250 + Center.y) / 100));
		mapMinCurrent.setY((int) Math.ceil((-p.x - 250 + Center.x) / 100));
		mapMaxCurrent.setX(blockXY + (int) Math.ceil((-p.y - 250 + Center.y) / 100));
		mapMaxCurrent.setY(blockXY + (int) Math.ceil((-p.x - 250 + Center.x) / 100));
		

		if(mapMinCurrent.ix <= mapMin.ix) {
			mapMin.setX(mapMinCurrent.ix);
		}
		if(mapMinCurrent.iy <= mapMin.iy) {
			mapMin.setY(mapMinCurrent.iy);
		}
		if(mapMaxCurrent.ix >= mapMax.ix) {
			mapMax.setX(mapMaxCurrent.ix);
		}
		if(mapMaxCurrent.iy >= mapMax.iy) {
			mapMax.setY(mapMaxCurrent.iy);
		}



		for (int e = Thing; e < blockXY + Thing; e++) {
			for (int i = Thing; i < blockXY + Thing; i++) {
				float rand = Math.round(Math.random() * 9999 + 1);

				int e1 = e + (int) Math.ceil((-p.y - 150 + Center.y) / 100);
				int i1 = i + (int) Math.ceil((-p.x - 150 + Center.x) / 100);
				//percentages
				float per0 = 0;
				float per1 = 80;
				float per2 = 10;
				float per3 = 5;
				float per4 = 5;

				float per01 = per0 + per1;
				float per02 = per01 + per2;
				float per03 = per02 + per3;
				float per04 = per03 + per4;

				if (rand / 100 <= per01 && rand / 100 > per0 && StoreB[e1][i1] == null) {
					StoreB[e1][i1] = "b";
				}
				if (rand / 100 <= per02 && rand / 100 > per01 && StoreB[e1][i1] == null) {
					StoreB[e1][i1] = "c";
				}
				if (rand / 100 <= per03 && rand / 100 > per02 && StoreB[e1][i1] == null) {
					StoreB[e1][i1] = "d";
				}
				if (rand / 100 <= per04 && rand / 100 > per03 && StoreB[e1][i1] == null) {
					/*
					for (int k = 0; k < 2; k++) {
						for (int l = 0; l < 2; l++) {
							StoreB[e1 + k][i1 + l] = "e";
						}
					}
					*/
					StoreB[e1][i1] = "e";
				}
				//g.rotate(Math.toRadians(359.8));


				if (StoreB[e1][i1].equals("a")) {
					g.drawImage(ground, p.ix + i1 * 100, p.iy + e1 * 100, pSize, pSize, null);
				}
				if (StoreB[e1][i1].equals("b")) {
					g.drawImage(stone, p.ix + i1 * 100, p.iy + e1 * 100, pSize, pSize, null);
				}
				if (StoreB[e1][i1].equals("c")) {
					g.drawImage(iron, p.ix + i1 * 100, p.iy + e1 * 100, pSize, pSize, null);
				}
				if (StoreB[e1][i1].equals("d")) {
					g.drawImage(gold, p.ix + i1 * 100, p.iy + e1 * 100, pSize, pSize, null);
				}
				if (StoreB[e1][i1].equals("e")) {
					g.drawImage(copper, p.ix + i1 * 100, p.iy + e1 * 100, pSize, pSize, null);
				}
			}
		}
		inventory.inventory(g);
	}
    public void test(Vector p, Vector v, boolean Space, String Key, float dt) {
		if(Space) {
			if (inventory.slotID[inventory.highlight0 + 3 * inventory.page] == inventory.drillI) Break(p, Space, Key, dt);
			else Place(p, Space, Key);
		}
    	else {
	    	switch(Key) {
	    		case "space":
	    			break;
	    			
				case "down":
					if(colOn) {
						if (!StoreB[(int) Math.ceil((-p.y + 180 + Center.y) / 100) + 1]
								[(int) Math.ceil((-p.x + 250 + Center.x) / 100)].equals("a")) {
							v.setX(0);
							v.setY(0);
						} else {
							v.setY(-speed);
						}
					}
					else v.setY(-speed);
					break;
				case "up":
					if(colOn) {
						if (!StoreB[(int) Math.ceil((-p.y + 320 + Center.y) / 100) - 1]
								[(int) Math.ceil((-p.x + 250 + Center.x) / 100)].equals("a")) {
							v.setX(0);
							v.setY(0);
						} else {
							v.setY(speed);
						}
					}
					else v.setY(speed);
					break;
	
				case "right":
					if(colOn) {
						if (!StoreB[(int) Math.ceil((-p.y + 250 + Center.y) / 100)]
								[(int) Math.ceil((-p.x + 180 + Center.x) / 100) + 1].equals("a")) {
							v.setX(0);
							v.setY(0);
						} else {
							v.setX(-speed);
						}
					}
					else v.setX(-speed);
					break;
				case "left":
					if(colOn) {
						if (!StoreB[(int) Math.ceil((-p.y + 250 + Center.y) / 100)]
								[(int) Math.ceil((-p.x + 320 + Center.x) / 100) - 1].equals("a")) {
							v.setX(0);
							v.setY(0);
						} else {
							v.setX(speed);
						}
					}
					else v.setX(speed);
					break;
			}
    	}
	}
	public void Break(Vector p, boolean Space, String Key, float dt) {

    	timer01 += dt;
    	if(timer01 >= 0.5f) {timer1 = true;}

    	if(timer1) {
			int a = (int) Math.ceil((-p.y + 250 + Center.y) / 100);
			int b = (int) Math.ceil((-p.x + 250 + Center.x) / 100);
			switch (Key) {
				case "down":
					inventory.stat.JustBroke = StoreB[a + 1][b];
					StoreB[a + 1][b] = "a";
					break;
				case "up":
					inventory.stat.JustBroke = StoreB[a - 1][b];
					StoreB[a - 1][b] = "a";
					break;
				case "right":
					inventory.stat.JustBroke = StoreB[a][b + 1];
					StoreB[a][b + 1] = "a";
					break;
				case "left":
					inventory.stat.JustBroke = StoreB[a][b - 1];
					StoreB[a][b - 1] = "a";
					break;
			}
			stability -= (int) Math.round(Math.random() * 2 + 1);
			if(stability <= 0) ;
			timer1 = false;
			timer01 = 0.00f;
			//System.out.println(statJustBroke);
		}
	}
	public void Place(Vector p, boolean Space, String Key) {
		int a = (int) Math.ceil((-p.y + 250 + Center.y) / 100);
		int b = (int) Math.ceil((-p.x + 250 + Center.x) / 100);
		switch (Key) {
			case "down":
				if(StoreB[a + 1][b] == "a") {
					StoreB[a + 1][b] = inventory.stat.SelectedBlock;
					inventory.slots[inventory.highlight0 + 3 * inventory.page]--;
				}
				break;
			case "up":
				if(StoreB[a - 1][b] == "a") {
					StoreB[a - 1][b] = inventory.stat.SelectedBlock;
					inventory.slots[inventory.highlight0 + 3 * inventory.page]--;
				}
				break;
			case "right":
				if(StoreB[a][b + 1] == "a") {
					StoreB[a][b + 1] = inventory.stat.SelectedBlock;
					inventory.slots[inventory.highlight0 + 3 * inventory.page]--;
				}
				break;
			case "left":
				if(StoreB[a][b - 1] == "a") {
					StoreB[a][b - 1] = inventory.stat.SelectedBlock;
					inventory.slots[inventory.highlight0 + 3 * inventory.page]--;
				}
				break;
		}
	}
	public void save(Vector p) {
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Resources\\maps\\map.txt")));
			String output = "";
			//for (int o = 0; o < 5; o++) {
				for (int i = mapMin.ix + 1; i < mapMax.ix + 1; i++) {
					for (int e = mapMin.iy + 1; e < mapMax.iy + 1; e++) {
						if(StoreB[i][e] != null) output = output + StoreB[i][e] + ",";
						else output = output + "N,";
					}
					output = output + "\n";
				}
				output = (mapMin.ix + 1) + "," + (mapMin.iy + 1) + "," + mapMax.ix + "," + mapMax.iy + "," + (int) Math.ceil((-p.x + 250 + Center.x) / 100) + "," + (int) Math.ceil((-p.y + 250 + Center.y) / 100) + "," + "\n" + output;
			writer.write(output);
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void load(Vector p) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("Resources\\maps\\map.txt")));
			String[][] array = new String[10000][10000];
			int[] n = new int[6];
			int n3 = 0;
			int n2 = 0;
			String line = "";
			//String output = "";
			//String lengthX = "";
			for(int i = 0; (line = reader.readLine()) != null; i++) {
				String[] split = line.split(",");
				for(String s : split) {
					try {
						n[n2] = Integer.parseInt(s);
						n2++;
					}
					catch (Exception e) {
						if(!s.equals("N")) array[i + n[0] - 1][n3 + n[1]] = s;
						else array[i + n[0] - 1][n3 + n[1]] = null;
						n3++;
					}
				}
				n3 = 0;
			}
			mapMin = new Vector(n[0], n[1]);
			mapMax = new Vector(n[2], n[3]);
			p.setX(-100 * (n[4] - 3));
			p.setY(-100 * (n[5] - 3));
			StoreB = array;
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    public BufferedImage loadTexture(String filepath){
        try {
            //return ImageIO.read(getClass().getResource(filepath));
            return ImageIO.read(new File(filepath));
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    
    
    
}