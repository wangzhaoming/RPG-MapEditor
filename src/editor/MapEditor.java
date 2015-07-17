package editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class MapEditor extends JFrame implements ActionListener, ItemListener {
	// private MenuBar mb;
	private SPanel sp;
	private DPanel dp;
	private JScrollBar jsbH;
	private JScrollBar jsbV;
	private ViewPanel viewPan;
	private Button btnSave;
	private Button btnOpen;
	private Button btnSetMapSize;
	private JTextField tfWidth, tfHeight;
	private JFileChooser fileChooser;
	private JCheckBox[] cb;
	private int flag;

	public MapEditor() {
		setLayout(null);

		flag = 0;
		sp = new SPanel(this);
		add(sp);
		sp.setBounds(0, 406, 480, 256);
		sp.setBorder(BorderFactory.createLineBorder(Color.black));

		dp = new DPanel(this);
		add(dp);
		dp.setBounds(500, 50, 640, 480);
		dp.setBorder(BorderFactory.createLineBorder(Color.black));

		jsbV = new JScrollBar(JScrollBar.VERTICAL);
		jsbV.setBounds(1142, 50, 20, 480);
		jsbH = new JScrollBar(JScrollBar.HORIZONTAL);
		jsbH.setBounds(500, 532, 640, 20);
		add(jsbV);
		add(jsbH);
		jsbV.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int value = jsbV.getValue();
//				int maximumValue = jsbV.getMaximum();
				int newY = -value * (dp.getMapHeight() - 15) / 90
						* 32;
				dp.setOffsetY(newY);
			}
		});
		jsbH.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				int value = jsbH.getValue();
//				int maximumValue = jsbH.getMaximum();
				int newX = -value * (dp.getMapWidth() - 20) / 90 * 32;
				dp.setOffsetX(newX);
			}
		});

		viewPan = new ViewPanel();
		add(viewPan);
		viewPan.setBounds(0, 0, 64, 64);
		viewPan.setBorder(BorderFactory.createLineBorder(Color.black));

		btnSave = new Button("Save");
		add(btnSave);
		btnSave.setBounds(800, 600, 50, 30);
		btnSave.addActionListener(this);

		btnOpen = new Button("Open");
		add(btnOpen);
		btnOpen.setBounds(720, 600, 50, 30);
		btnOpen.addActionListener(this);

		tfWidth = new JTextField("128");
		tfWidth.setBounds(100, 100, 50, 20);
		tfHeight = new JTextField("128");
		tfHeight.setBounds(100, 140, 50, 20);
		btnSetMapSize = new Button("New Map");
		btnSetMapSize.setBounds(100, 180, 80, 30);
		add(tfHeight);
		add(tfWidth);
		add(btnSetMapSize);
		btnSetMapSize.addActionListener(this);

		cb = new JCheckBox[8];
		String[] str = { "可通行", "可破坏", "出生点", "传送门", "对话", "备用", "备用", "备用" };
		for (int i = 0; i < 8; i++) {
			cb[i] = new JCheckBox(str[i]);
			cb[i].setBounds(200, 50 + i * 30, 80, 30);
			add(cb[i]);
			cb[i].addItemListener(this);
		}

		fileChooser = new JFileChooser();

		this.setSize(1180, 690);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void setShape(Image img, int x, int y) {
		viewPan.setView(img, x, y);
		viewPan.repaint();
		dp.setView(img, x, y);
	}

	public ViewPanel getViewPan() {
		return viewPan;
	}

	public static void main(String[] args) {
		MapEditor m = new MapEditor();
	}

	public void save() {
		int map[][][] = dp.getMap();
		File file = new File("src\\res\\map.txt");
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			System.out.println(dp.getMapHeight());
			System.out.println(dp.getMapWidth());
			out.write(dp.getMapHeight() - 1);
			out.write(dp.getMapWidth() - 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < dp.getMapHeight(); i++) {
			for (int j = 0; j < dp.getMapWidth(); j++)
				for (int k = 0; k < 3; k++) {
					try {
						out.write(map[i][j][k]);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		}
		try {
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			save();
		} else if (e.getActionCommand().equals("Open")) {
			open();
			dp.repaint();
		} else if (e.getActionCommand().equals("New Map")) {
			int w = Integer.parseInt(tfWidth.getText());
			int h = Integer.parseInt(tfHeight.getText());
			dp.setMap(h, w);
			dp.setVisible(true);
			dp.repaint();
		}
	}

	private void open() {
		int option = fileChooser.showDialog(null, null);
		File file = null;
		if (option == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int map[][][] = null;
		int w = 0, h = 0;
		try {
			h = in.read() + 1;
			w = in.read() + 1;
			map = new int[h][w][3];
			System.out.println(h);
			System.out.println(w);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				for (int k = 0; k < 3; k++) {
					try {
						map[i][j][k] = in.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		dp.openMap(map, h, w);
		dp.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItem() == cb[0]) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				flag = flag | 0x80;
			} else {
				flag = flag & 0x7f;
			}
		} else if (e.getItem() == cb[1]) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				flag = flag | 0x40;
			} else {
				flag = flag & 0xbf;
			}
		} else if (e.getItem() == cb[2]) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				flag = flag | 0x20;
			} else {
				flag = flag & 0xdf;
			}
		} else if (e.getItem() == cb[3]) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				flag = flag | 0x10;
			} else {
				flag = flag & 0xef;
			}
		} else if (e.getItem() == cb[4]) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				flag = flag | 0x8;
			} else {
				flag = flag & 0xf7;
			}
		} else if (e.getItem() == cb[5]) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				flag = flag | 0x4;
			} else {
				flag = flag & 0xfb;
			}
		} else if (e.getItem() == cb[6]) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				flag = flag | 0x2;
			} else {
				flag = flag & 0xfd;
			}
		} else if (e.getItem() == cb[7]) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				flag = flag | 0x1;
			} else {
				flag = flag & 0xfe;
			}
		}
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
