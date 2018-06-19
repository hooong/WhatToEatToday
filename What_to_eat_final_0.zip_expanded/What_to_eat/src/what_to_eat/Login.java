package what_to_eat;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import what_to_eat.Whattoeat;
import static what_to_eat.clientQuery.*;

public class Login extends JPanel implements ActionListener {
   // 로그인 id와 pw의 텍스트필드
   private static JTextField txtUsername = new JTextField();
   private static JPasswordField passwordField = new JPasswordField();;
   // 로그인 버튼
   JButton loginbt = new JButton();

   public Login() {
      
      // 로그인 UI 설정
      setLayout(new GridLayout(3, 1, 0, 0));

      JPanel panel = new JPanel();
      add(panel);
      panel.setLayout(new BorderLayout(0, 0));

      JPanel panel_9 = new JPanel();
      FlowLayout flowLayout_2 = (FlowLayout) panel_9.getLayout();
      flowLayout_2.setVgap(15);
      panel.add(panel_9, BorderLayout.NORTH);

      JLabel lblNewLabel = new JLabel("");
      lblNewLabel.setIcon(new ImageIcon(Main.class.getResource("../images/loginpic.png")));
      panel.add(lblNewLabel, BorderLayout.CENTER);

      JPanel panel_5 = new JPanel();
      FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
      flowLayout.setHgap(125);
      flowLayout.setVgap(250);
      panel.add(panel_5, BorderLayout.WEST);

      JPanel panel_1 = new JPanel();
      add(panel_1);
      panel_1.setLayout(new GridLayout(2, 1, 0, 0));

      JPanel panel_4 = new JPanel();
      panel_1.add(panel_4);
      panel_4.setLayout(new BorderLayout(0, 0));

      JPanel panel_6 = new JPanel();
      panel_4.add(panel_6, BorderLayout.CENTER);

      JLabel label = new JLabel("");
      label.setIcon(new ImageIcon(Main.class.getResource("../images/idicon.png")));
      panel_6.add(label);

      panel_6.add(txtUsername);
      txtUsername.setColumns(10);

      JPanel panel_8 = new JPanel();
      FlowLayout flowLayout_1 = (FlowLayout) panel_8.getLayout();
      flowLayout_1.setVgap(10);
      panel_4.add(panel_8, BorderLayout.NORTH);

      JPanel panel_3 = new JPanel();
      panel_1.add(panel_3);
      panel_3.setLayout(new BorderLayout(0, 0));

      JPanel panel_7 = new JPanel();
      panel_3.add(panel_7, BorderLayout.CENTER);

      JLabel lblNewLabel_1 = new JLabel("");
      lblNewLabel_1.setIcon(new ImageIcon(Main.class.getResource("../images/pwicon.png")));
      panel_7.add(lblNewLabel_1);

      passwordField.setColumns(10);
      panel_7.add(passwordField);

      JPanel panel_2 = new JPanel();
      add(panel_2);
      panel_2.setLayout(null);

      loginbt.setBorderPainted(false);
      loginbt.setContentAreaFilled(false);
      loginbt.setFocusPainted(false);
      loginbt.setBounds(246, 15, 120, 40);
      loginbt.setIcon(new ImageIcon(Main.class.getResource("../images/login_loginButtonBasic.png")));
      panel_2.add(loginbt);
      
      // 로그인 버튼에 액션이벤트 추가
      loginbt.addActionListener(this);
   }

   // actionPerformed 구현
   public void actionPerformed(ActionEvent e) {
      Login log = new Login();
      
      // 로그인버튼이 눌려졌다면
      if (e.getSource() == loginbt) {
//         Whattoeat wte = new Whattoeat();
         clientQuery cq = new clientQuery();
         
         // 문자열에 id와 pw의 값을 저장
         String id = new String(txtUsername.getText());
         String pw = new String(passwordField.getPassword());
         
         // 로그인 상수 호출하여 check에 저장
         int check = 1;
         
         if (check == emptyID) {   // id를 입력하지 않았으면
            JOptionPane.showMessageDialog(log, "아이디를 입력해주세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
         } else if (check == emptyPW) {   // pw를 입력하지 않았으면
            JOptionPane.showMessageDialog(log, "비밀번호를 입력해주세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
         } else if (check == lengthID) {   // id가 5자 이상이 아니라면
            JOptionPane.showMessageDialog(log, "아이디를 5자 이상 입력하세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
         } else if (check == lengthPW) {   // pw가 5자 이상이 아니라면
            JOptionPane.showMessageDialog(log, "비밀번호를 5자 이상 입력하세요", " ", JOptionPane.INFORMATION_MESSAGE, null);
            passwordField.setText("");
         } else if (check == valID) {   // id에 영문숫자가 포함되어있지 않다면
            JOptionPane.showMessageDialog(log, "아이디는 영문숫자만 포함됩니다", " ", JOptionPane.INFORMATION_MESSAGE, null);
         } else if (check == valPWSymbol) {   // pw에 숫자와 특수문자가 포함되어있지않다면
            JOptionPane.showMessageDialog(log, "비밀번호는 숫자와 특수문자가 포함되야합니다", " ", JOptionPane.INFORMATION_MESSAGE,
                  null);
            passwordField.setText("");
         } else if (check == valPWAlpha) {   // pw에 영문자와 대문자가 적어도 1개 이상 포함되어있지 않다면
            JOptionPane.showMessageDialog(log, "비밀번호는 영문자와 대문자가 적어도 하나씩은 포함되야합니다", " ",
                  JOptionPane.INFORMATION_MESSAGE, null);
            passwordField.setText("");
         } else if (check == 1) {   // 정상적으로 로그인이 되었다면
            JOptionPane.showMessageDialog(log, "로그인 성공!", " ", JOptionPane.INFORMATION_MESSAGE, null);
            // id와 pw필드는 지운다
            txtUsername.setText("");
            passwordField.setText("");
            // 로그인화면
            Whattoeat.main_logined();
            
            // 로그인 실패
         } else {
            JOptionPane.showMessageDialog(log, "로그인 실패!", " ", JOptionPane.INFORMATION_MESSAGE, null);
         }
      }
   }
}