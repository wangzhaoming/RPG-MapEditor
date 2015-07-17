package editor;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;


public class SPanel extends JPanel implements MouseListener{
	private Image img;
	private int x;
	private int y;
	private MapEditor me;
	
	public SPanel(MapEditor me){
		img=Toolkit.getDefaultToolkit().getImage("src\\res\\building.png");
		this.me=me;
		addMouseListener(this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 0, 0, 480, 256, 0, 0, 960, 512, null);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		x=e.getX()/16*32;
		y=e.getY()/16*32;
		me.setShape(img,x,y);
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
