package Main;


	import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

	public class Window extends Canvas {

		private static final long serialVersionUID = -240840600533728354L;

		public Window(int width, int height, String title, Main game) {
			JFrame frame = new JFrame("Himori's Adventure");

			frame.setPreferredSize(new Dimension(width, height));
			frame.setMaximumSize(new Dimension(width, height));
			frame.setMinimumSize(new Dimension(width, height));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.add(game);
			frame.pack();
			frame.setVisible(true);
			frame.requestFocus();
			

		}

	}

