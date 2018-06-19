package what_to_eat;

import static what_to_eat.clientQuery.dupliID;
import static what_to_eat.clientQuery.emptyID;
import static what_to_eat.clientQuery.emptyPW;
import static what_to_eat.clientQuery.lengthID;
import static what_to_eat.clientQuery.lengthPW;
import static what_to_eat.clientQuery.valID;
import static what_to_eat.clientQuery.valPWAlpha;
import static what_to_eat.clientQuery.valPWSymbol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Signup extends JPanel implements ActionListener, KeyListener {
	private JTextField signup_id;
	private JTextField signup_pw;
	private JTextField signup_pw_confirm;
	private JTextField signup_email;
	private JTextField signup_name;
	boolean checkid = false;
	private JButton checkidbt = new JButton("중복확인");
	private JButton checkpwbt = new JButton("비밀번호확인");
	JButton signupbt = new JButton();

	public Signup() {
		setLayout(null);

		JLabel idlb = new JLabel("ID");
		idlb.setBounds(186, 66, 106, 16);
		add(idlb);

		JLabel pwlb = new JLabel("PW");
		pwlb.setBounds(186, 111, 61, 16);
		add(pwlb);

		JLabel pw_1lb = new JLabel("PW 확인");
		pw_1lb.setBounds(186, 156, 61, 16);
		add(pw_1lb);

		JLabel usernamelb = new JLabel("UserName");
		usernamelb.setBounds(186, 237, 72, 16);
		add(usernamelb);

		JLabel emaillb = new JLabel("Email");
		emaillb.setBounds(186, 196, 61, 16);
		add(emaillb);

		signup_id = new JTextField();
		signup_id.setBounds(295, 61, 130, 26);
		add(signup_id);
		signup_id.setColumns(10);

		signup_pw = new JTextField();
		signup_pw.setColumns(10);
		signup_pw.setBounds(295, 106, 130, 26);
		add(signup_pw);

		signup_pw_confirm = new JTextField();
		signup_pw_confirm.setColumns(10);
		signup_pw_confirm.setBounds(295, 151, 130, 26);
		add(signup_pw_confirm);

		signup_email = new JTextField();
		signup_email.setColumns(10);
		signup_email.setBounds(295, 191, 130, 26);
		add(signup_email);

		signup_name = new JTextField();
		signup_name.setColumns(10);
		signup_name.setBounds(295, 232, 130, 26);
		add(signup_name);

		signupbt.setBorderPainted(false);
		signupbt.setContentAreaFilled(false);
		signupbt.setFocusPainted(false);
		signupbt.setIcon(new ImageIcon(Main.class.getResource("../images/signup_signupButtonBasic.png")));
		signupbt.setBounds(244, 302, 117, 29);
		signupbt.addActionListener(this);
		add(signupbt);

		checkidbt.setBounds(400, 66, 130, 26);
		checkidbt.addActionListener(this);
		checkidbt.addKeyListener(this);
		add(checkidbt);
		
		checkpwbt.setBounds(400, 106, 130, 26);
		checkpwbt.addActionListener(this);
		checkpwbt.addKeyListener(this);
		add(checkpwbt);

	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		checkid = false;
		System.out.println(checkid);
	}

	// 중복확인버튼 리턴값 구현
	public void actionPerformed(ActionEvent e) {
		// id중복확인버튼 동작
		if (e.getSource() == checkidbt) {
			clientQuery cq = new clientQuery();
			String checkId = new String(signup_id.getText());
			int check = cq.dbCheckId(checkId);
			if (check == emptyID) {
				JOptionPane.showMessageDialog(checkidbt, "아이디를 입력해주세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
				checkid = false;
			} else if (check == lengthID) {
				JOptionPane.showMessageDialog(checkidbt, "아이디를 5자 이상 입력하세요", " ", JOptionPane.INFORMATION_MESSAGE,
						null);
				checkid = false;
			} else if (check == valID) {
				JOptionPane.showMessageDialog(checkidbt, "아이디는 영문숫자만 포함됩니다", " ", JOptionPane.INFORMATION_MESSAGE,
						null);
				checkid = false;
			} else if (check == dupliID) {
				JOptionPane.showMessageDialog(checkidbt, "이미 사용중인 아이디!", " ", JOptionPane.INFORMATION_MESSAGE, null);
				signup_id.setText("");
				checkid = false;
			} else if (check == 1) {
				JOptionPane.showMessageDialog(checkidbt, "아이디 사용가능!", " ", JOptionPane.INFORMATION_MESSAGE, null);
				checkid = true;
			} else {
				JOptionPane.showMessageDialog(checkidbt, "아이디 중복확인 실패", " ", JOptionPane.INFORMATION_MESSAGE, null);
				checkid = false;
			}
		}
		// 회원가입버튼 동작
		if (e.getSource() == signupbt) {
			if (checkid == false) {
				JOptionPane.showMessageDialog(checkidbt, "아이디 중복확인을 하시오", " ", JOptionPane.INFORMATION_MESSAGE, null);
				// return;
			}

			Whattoeat wte = new Whattoeat();
			Signup signup = new Signup();

			int check = signupCheck();
			wte.mainback_0();
			
			if (check == emptyID) {
				JOptionPane.showMessageDialog(signup, "아이디를 입력해주세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
			} else if (check == emptyPW) {
				JOptionPane.showMessageDialog(signup, "비밀번호를 입력해주세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
			} else if (check == lengthID) {
				JOptionPane.showMessageDialog(signup, "아이디를 5자 이상 입력하세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
			} else if (check == lengthPW) {
				JOptionPane.showMessageDialog(signup, "비밀번호를 5자 이상 입력하세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
			} else if (check == valID) {
				JOptionPane.showMessageDialog(signup, "아이디는 영문숫자만 포함됩니다", " ", JOptionPane.INFORMATION_MESSAGE, null);
			} else if (check == valPWSymbol) {
				JOptionPane.showMessageDialog(signup, "비밀번호는 숫자와 특수문자가 포함되야합니다", " ", JOptionPane.INFORMATION_MESSAGE,
						null);
			} else if (check == valPWAlpha) {
				JOptionPane.showMessageDialog(signup, "비밀번호는 영문자와 대문자가 적어도 하나씩은 포함되야합니다", " ",
						JOptionPane.INFORMATION_MESSAGE, null);
			} else if (check == 1) {
				JOptionPane.showMessageDialog(signup, "회원가입 성공!", " ", JOptionPane.INFORMATION_MESSAGE, null);
				wte.mainback_0();
			} else
				JOptionPane.showMessageDialog(signup, "회원가입 실패!", " ", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}

	int signupCheck() {
		clientQuery cq = new clientQuery();

		int check = 1;
		Client newClient = new Client();
		String newId = new String(signup_id.getText());
		String newPw = new String(signup_pw.getText());
		String newEmail = new String(signup_email.getText());
		String newName = new String(signup_name.getText());
		newClient.setId(newId);
		newClient.setPw(newPw);
		newClient.setName(newName);
		newClient.setEmail(newEmail);
		
		check = cq.dbCheckId(newId);
		
		if (check == emptyID || check == lengthID || check == valID )
			return check;
		
		check = cq.dbCheckPw(newPw);
		
		if (check == emptyPW || check == lengthPW || check == valPWSymbol || check == valPWAlpha)
			return check;
		
		check = cq.dbRegist(newClient);
		return check;
	}

}