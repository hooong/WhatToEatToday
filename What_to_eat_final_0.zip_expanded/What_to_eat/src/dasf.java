import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class dasf extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dasf frame = new dasf();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public dasf() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280,720);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setFont(new Font("±¼¸²", Font.BOLD, 18));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\hsj05\\Desktop\\\uB3D9\uBB38pointer.png"));
		btnNewButton.setBounds(269, 405, 120, 140);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("asdfasdf");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(12, 10, 1280, 720);
		contentPane.add(lblNewLabel);
	}
}
