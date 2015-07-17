package editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DPanel extends JPanel implements MouseListener {
	private MapEditor me;
	private int map[][][];
	private int mapWidth, mapHeight;
	private int x;
	private int y;
	private int sx, sy;
	private int offsetX;
	private int offsetY;
	private final int SIZE = 32;
	private Image img;

	public DPanel(MapEditor me) {
		this.me = me;
		sx = sy = 0;
		addMouseListener(this);
		offsetX = offsetY = 0;
		this.setVisible(false);
	}

	public int func1(int x) {
		return x >= 0 ? 0 : -x / SIZE;
	}

	public void setMap(int h, int w) {
		mapWidth = w;
		mapHeight = h;
		map = new int[h][w][3];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				map[i][j][0] = 0;
				map[i][j][1] = 8;
				map[i][j][2]=128;
			}
		}
	}

	public void setOffsetX(int x) {
		offsetX = x;
		this.repaint();
	}

	public void setOffsetY(int y) {
		offsetY = y;
		this.repaint();
	}

	public void openMap(int map[][][], int h, int w) {
		this.map = map;
		mapWidth = w;
		mapHeight = h;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, 640, 480);
		for (int i = func1(offsetY); i < func1(offsetY) + 15
				&& i < mapHeight; i++) {
			for (int j = func1(offsetX); j < func1(offsetX) + 20
					&& j < mapWidth; j++) {
				g.drawImage(img, offsetX + j * SIZE, offsetY + i * SIZE,
						offsetX + j * SIZE + SIZE, offsetY + i * SIZE + SIZE,
						map[i][j][0] * SIZE, map[i][j][1] * SIZE, map[i][j][0]
								* SIZE + SIZE, map[i][j][1] * SIZE + SIZE, null);
			}
		}
	}

	public void setView(Image img, int x, int y) {
		this.img = img;
		this.sx = x;
		this.sy = y;
	}

	public int[][][] getMap() {
		return map;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		x = e.getX() / SIZE * SIZE;
		y = e.getY() / SIZE * SIZE;
		map[(y - offsetY) / SIZE][(x - offsetX) / SIZE][0] = sx / SIZE;
		map[(y - offsetY) / SIZE][(x - offsetX) / SIZE][1] = sy / SIZE;
		map[(y - offsetY) / SIZE][(x - offsetX) / SIZE][2] = me.getFlag();
		this.repaint();
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
