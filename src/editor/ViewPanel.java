package editor;

import java.awt.*;

import javax.swing.*;

public class ViewPanel extends JPanel{
	private Image img;
	private int x,y;
	

	public void ViewPanel(){
	}
	
	public void setView(Image img, int x, int y){
		this.img=img;
		this.x=x;
		this.y=y;
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(img, 0, 0, 64, 64, x, y, x + 32, y + 32, null);
	}
	
}
