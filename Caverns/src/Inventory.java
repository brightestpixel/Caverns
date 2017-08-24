import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Inventory {
	private boolean skip[] = new boolean[100];
	private int nextOpen[] = new int[100];
    public int slots[] = new int[9];
    public int page = 0;
    public int highlight0 = 0;
    public BufferedImage slotID[] = new BufferedImage[9];
    
    public Stats stat = new Stats();
	
    String test_file = "Resources\\GUI\\test.png";
    BufferedImage test;
    String sidebar_file = "Resources\\GUI\\sidebar.png";
    BufferedImage sidebar;
    String sidebarGUI_file = "Resources\\GUI\\sidebarGUI.png";
    BufferedImage sidebarGUI;
    String highlight_file = "Resources\\GUI\\highlight.png";
    BufferedImage highlight;
    
    String stoneI_file = "Resources\\items\\stone.png";
    BufferedImage stoneI;
    String ironI_file = "Resources\\items\\iron.png";
    BufferedImage ironI;
    String drillI_file = "Resources\\items\\drill.png";
    BufferedImage drillI;
    String goldI_file = "Resources\\items\\gold.png";
    BufferedImage goldI;
    String copperI_file = "Resources\\items\\copper.png";
    BufferedImage copperI;
    
    public Inventory() {
    	slots[0] = 1;
    	slots[1] = 0;
    	slots[2] = 0;

		
		test = loadTexture(test_file);
		sidebar = loadTexture(sidebar_file);
		sidebarGUI = loadTexture(sidebarGUI_file);
		highlight = loadTexture(highlight_file);
		
		stoneI = loadTexture(stoneI_file);
		ironI = loadTexture(ironI_file);
		drillI = loadTexture(drillI_file);
		goldI = loadTexture(goldI_file);
		copperI = loadTexture(copperI_file);
		slotID[0] = drillI;
    }
	
	public void inventory(Graphics2D g) {
    	String Indent[] = new String[9];
		Font myFont = new Font("Franklin Gothic",Font.BOLD,24);
		g.setColor(Color.BLACK);
		g.setFont(myFont);
		
		//airspace
		for(int i = 0; i < 9; i++)
			for(int e = 0; e < 9; e++)
				if(slotID[i] == null && slotID[e] == null)
					if (i <= e) {
						nextOpen[0] = i;
						e = 10;
						i = 10;
					}
		
		//add new stacks
		for(int i = 8; i > -1; i--)
			for(int e = 8; e > -1; e--) {
				if(e == 0 && i == 0 && stat.JustBroke == "b")
					slotID[nextOpen[0]] = stoneI;
				if(e == 0 && i == 0 && stat.JustBroke == "c")
						slotID[nextOpen[0]] = ironI;
				if(e == 0 && i == 0 && stat.JustBroke == "d")
					slotID[nextOpen[0]] = goldI;
				if(e == 0 && i == 0 && stat.JustBroke == "e")
					slotID[nextOpen[0]] = copperI;
		}
		
		//add items
		for(int i = 0; i < 9; i++) {
			for(int e = 0; e < 9; e++) {
				//stone
				if(skip[1] == false && slotID[i] == stoneI && slotID[e] == stoneI)
					if (i <= e && slots[i] < 50)
						if(stat.JustBroke == "b") {
							slots[i]++;
							skip[1] = true;
						}
				//iron
				if(skip[2] == false && slotID[i] == ironI && slotID[e] == ironI)
					if (i <= e && slots[i] < 50)
						if(stat.JustBroke == "c") {
							slots[i]++;
							skip[2] = true;
						}
				//gold
				if(skip[3] == false && slotID[i] == goldI && slotID[e] == goldI)
					if (i <= e && slots[i] < 50)
						if(stat.JustBroke == "d") {
							slots[i]++;
							skip[3] = true;
						}
				//copper
				if(skip[4] == false && slotID[i] == copperI && slotID[e] == copperI)
					if (i <= e && slots[i] < 50)
						if(stat.JustBroke == "e") {
							slots[i]++;
							skip[4] = true;
						}
			}
		}
		stat.JustBroke = "";
		for(int i = 0; i < 9; i++) {
			if(slots[i] == 0) slotID[i] = null;
			skip[i] = false;
		}
		
		//indents for single digit #s
		for(int i = 0; i < 9; i++) {
			if (slots[i] < 10) Indent[i] = "  ";
			else Indent[i] = "";
			if (slots[i] == 0) Indent[i] = "              ";
		}
		
		//selected block & selected count
		
		if (slotID[highlight0 + 3 * page] == stoneI) stat.SelectedBlock = "b";
		if (slotID[highlight0 + 3 * page] == ironI) stat.SelectedBlock = "c";
		if (slotID[highlight0 + 3 * page] == goldI) stat.SelectedBlock = "d";
		if (slotID[highlight0 + 3 * page] == copperI) stat.SelectedBlock = "e";
		
		//statSelectedCount = slots[highlight0 + 3 * page];
		//System.out.println(slots[highlight0 + 3 * page]);

		//drawing to screen:
		
		g.drawImage(sidebar, 700, 0, 150, 700, null);
		g.drawImage(sidebarGUI, 700, 0, 150, 700, null);
		g.drawImage(test, 0, 0, 700, 700, null);
		g.drawImage(highlight, 700, 286 + highlight0 * 132, 150, 150, null);
		switch(page) {
			case 0:
				g.drawImage(slotID[0], 700, 286, 150, 150, null);
				g.drawImage(slotID[1], 700, 418, 150, 150, null);
				g.drawImage(slotID[2], 700, 550, 150, 150, null);
				g.drawString(Indent[0] + Long.toString(slots[0]), 796, 408);
				g.drawString(Indent[1] + Long.toString(slots[1]), 796, 540);
				g.drawString(Indent[2] + Long.toString(slots[2]), 796, 672);
				break;
			case 1:
				g.drawImage(slotID[3], 700, 286, 150, 150, null);
				g.drawImage(slotID[4], 700, 418, 150, 150, null);
				g.drawImage(slotID[5], 700, 550, 150, 150, null);
				g.drawString(Indent[3] + Long.toString(slots[3]), 796, 408);
				g.drawString(Indent[4] + Long.toString(slots[4]), 796, 540);
				g.drawString(Indent[5] + Long.toString(slots[5]), 796, 672);
				break;
			case 2:		
				g.drawImage(slotID[6], 700, 286, 150, 150, null);
				g.drawImage(slotID[7], 700, 418, 150, 150, null);
				g.drawImage(slotID[8], 700, 550, 150, 150, null);
				g.drawString(Indent[6] + Long.toString(slots[6]), 796, 408);
				g.drawString(Indent[7] + Long.toString(slots[7]), 796, 540);
				g.drawString(Indent[8] + Long.toString(slots[8]), 796, 672);
				break;
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