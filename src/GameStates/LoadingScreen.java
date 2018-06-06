package GameStates;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import Graphical.Texture;

public class LoadingScreen extends JWindow {

	private BorderLayout borderLayout;
	private JLabel imageLabel;
	private JPanel southPanel;
	private FlowLayout southFlow;
	private JProgressBar loadingBar;
	private ImageIcon imageIcon;
	private JPanel tipPanel;
	private FlowLayout tipFlow;
	private JLabel tip;

	public LoadingScreen(Texture texture) {
		this.imageIcon = new ImageIcon(texture.getImage());
		borderLayout = new BorderLayout();
		imageLabel = new JLabel();
		southPanel = new JPanel();
		southFlow = new FlowLayout();
		tipPanel = new JPanel();
		tipFlow = new FlowLayout();
		loadingBar = new JProgressBar();
		loadingBar.setStringPainted(true);
		initialize();
	}
	
	public void initialize() {
				imageLabel.setIcon(imageIcon);
				getContentPane().setLayout(borderLayout);
				southPanel.setLayout(southFlow);
				southPanel.setBackground(Color.BLACK);
				tipPanel.setLayout(tipFlow);
				tipPanel.setBackground(new Color(0xFFE0BD96));
				tipPanel.add(writeTip());
				tipPanel.setBorder(new LineBorder(Color.BLACK));
				getContentPane().add(imageLabel, BorderLayout.CENTER);
				getContentPane().add(southPanel, BorderLayout.SOUTH);
				southPanel.add(loadingBar, null);
				getContentPane().add(tipPanel, BorderLayout.NORTH);
				pack();
	}

	public void setMaxLoad(int maxLoad) {
		loadingBar.setMaximum(maxLoad);
	}

	public void setMinimumLoad() {
		loadingBar.setMinimum(0);
	}

	public void setLoad(int load) {
		float loadpercent = ((float) load / (float) loadingBar.getMaximum()) * 100;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				loadingBar.setValue(load);
				loadingBar.setString("LOADING: " + (int) loadpercent + "%");
			}
		});
	}
	
	public JLabel writeTip() {
		String[] tips = new String[3];
		tips[0] = "Mr. Forster is a bitchin' "
				+ "\n" + "teacher";
		tips[1] = "Gumby is god himself";
		tips[2] = "Give me an A, Mr. Forster?";
		int pick = (int) (Math.random() * 3);
		tip = new JLabel("TIP: " + tips[pick]);
		tip.setFont(new Font("Verdana", 1, 14));
		return tip;
	}
	
}
