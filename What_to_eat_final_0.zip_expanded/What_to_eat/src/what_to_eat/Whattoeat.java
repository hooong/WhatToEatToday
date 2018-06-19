package what_to_eat;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import what_to_eat.Restaurants;

public class Whattoeat extends JFrame {

   int backinfo;
   // 0 : 로그인에서 메인화면으로
   // 1 : 골라줘에서 메인화면으로
   // 2 : 골라줘로 back

   public Image screenImage;
   public Graphics screenGraphic;

   static Login login = new Login();
   static Signup signup = new Signup();
   Result result = new Result();
   // 음식종류 배열
   String food_style[] = { "한식", "중식", "양식", "일식" };
   
   ImageIcon backButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/backButtonEntered.png"));
   ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));
   ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitEntered.png"));
   static ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitBasic.png"));
   ImageIcon loginButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/loginEntered.png"));
   static ImageIcon loginButtonBasicImage = new ImageIcon(Main.class.getResource("../images/loginBasic.png"));
   ImageIcon signupButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/signupEntered.png"));
   static ImageIcon signupButtonBasicImage = new ImageIcon(Main.class.getResource("../images/signupBasic.png"));
   ImageIcon chooseButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/chooseEntered.png"));
   static ImageIcon chooseButtonBasicImage = new ImageIcon(Main.class.getResource("../images/chooseBasic.png"));
   static ImageIcon logoutButtonBasicImage = new ImageIcon(Main.class.getResource("../images/logoutBasic.png"));
   ImageIcon logoutButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/logoutEntered.png"));

   // 지도버튼이미지
   ImageIcon jungmoonptImage = new ImageIcon(Main.class.getResource("../images/정문pointer.png"));
   ImageIcon humoonptImage = new ImageIcon(Main.class.getResource("../images/후문pointer.png"));
   ImageIcon dongmoonptImage = new ImageIcon(Main.class.getResource("../images/동문pointer.png"));
   ImageIcon jamoonptImage = new ImageIcon(Main.class.getResource("../images/자쪽pointer.png"));
   ImageIcon jungmoonptEnteredImage = new ImageIcon(Main.class.getResource("../images/정문pointerEntered.png"));
   ImageIcon humoonptEnteredImage = new ImageIcon(Main.class.getResource("../images/후문pointerEntered.png"));
   ImageIcon dongmoonptEnteredImage = new ImageIcon(Main.class.getResource("../images/동문pointerEntered.png"));
   ImageIcon jamoonptEnteredImage = new ImageIcon(Main.class.getResource("../images/자쪽pointerEntered.png"));

   private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar_1.png")));
   private JLabel selposition = new JLabel("어디서 먹을지 골라주세요!");

   private Image background = new ImageIcon(Main.class.getResource("../images/intro.png")).getImage();
   private Image basicbackground = new ImageIcon(Main.class.getResource("../images/basicbackground.png")).getImage();

   JButton backButton = new JButton(backButtonBasicImage);
   JButton exitButton = new JButton(exitButtonBasicImage);
   static JButton loginButton = new JButton(loginButtonBasicImage);
   static JButton signupButton = new JButton(signupButtonBasicImage);
   static JButton chooseButton = new JButton(chooseButtonBasicImage);
   static JButton logincloseButton = new JButton(exitButtonBasicImage);
   static JButton logoutButton = new JButton(logoutButtonBasicImage);

   // 지도버튼
   private JButton jungmoonButton = new JButton(jungmoonptImage);
   private JButton humoonButton = new JButton(humoonptImage);
   private JButton dongmoonButton = new JButton(dongmoonptImage);
   private JButton jamoonButton = new JButton(jamoonptImage);

   // 두번째 선택화면(종류,가격)
   JComboBox<String> style = new JComboBox<String>(food_style);
   JLabel style_lb = new JLabel("음식 종류를 골라주세요!");
   JLabel won_lb = new JLabel("감당 할 수 있는 금액을 입력해주세요!");
   JTextField won = new JTextField();
   JButton last_chooseButton = new JButton(chooseButtonBasicImage);

   private int mouseX, mouseY;

   public Whattoeat() {

      // 프레임
      setUndecorated(true);
      setTitle("오늘 뭐 먹지?");
      setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
      setResizable(false);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      setBackground(new Color(0, 0, 0, 0));
      setLayout(null);

      // 위치를 선택해주세요 레이블
      selposition.setBounds(535, 0, 400, 30);
      selposition.setFont(new Font("굴림", Font.BOLD, 20));
      selposition.setForeground(Color.WHITE);
      selposition.setVisible(false);
      add(selposition);

      // 로그인창 회원가입창 닫기버튼
      logincloseButton.setBounds(918, 182, 30, 30);
      logincloseButton.setBorderPainted(false);
      logincloseButton.setContentAreaFilled(false);
      logincloseButton.setFocusPainted(false);
      logincloseButton.setVisible(false);
      logincloseButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            logincloseButton.setIcon(exitButtonEnteredImage);
            logincloseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            logincloseButton.setIcon(exitButtonBasicImage);
            logincloseButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            mainback_0();
         }
      });
      add(logincloseButton);

      // 로그아웃버튼
      logoutButton.setBounds(550, 480, 170, 60);
      logoutButton.setBorderPainted(false);
      logoutButton.setContentAreaFilled(false);
      logoutButton.setFocusPainted(false);
      logoutButton.setVisible(false);
      logoutButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            logoutButton.setIcon(logoutButtonEnteredImage);
            logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            logoutButton.setIcon(logoutButtonBasicImage);
            logoutButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {

            loginButton.setVisible(true);
            signupButton.setVisible(true);
            logoutButton.setVisible(false);
         }
      });
      add(logoutButton);

      // 뒤로가기버튼
      backButton.setBounds(0, 0, 30, 30);
      backButton.setBorderPainted(false);
      backButton.setContentAreaFilled(false);
      backButton.setFocusPainted(false);
      backButton.setVisible(false);
      backButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            backButton.setIcon(backButtonEnteredImage);
            backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            backButton.setIcon(backButtonBasicImage);
            backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            if (backinfo == 0)
               mainback_0();
            else if (backinfo == 1)
               mainback_1();
            else if (backinfo == 2)
               mainback_2();
            else if (backinfo == 3)
               mainback_3();
            // TODO 뒤로가기 버튼 기능 (if문으로 뒤로가기지정 전역변수로 지정 switch??)
            // 각각의 뒤로가기 메소드를 만들고 변수 화면으로 넘어갈때마다 전역변수로 switch에 넘겨줄 값을 저장,
         }
      });
      add(backButton);

      // 종료버튼
      exitButton.setBounds(1245, 0, 30, 30);
      exitButton.setBorderPainted(false);
      exitButton.setContentAreaFilled(false);
      exitButton.setFocusPainted(false);
      exitButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            exitButton.setIcon(exitButtonEnteredImage);
            exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            exitButton.setIcon(exitButtonBasicImage);
            exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            System.exit(0);
         }
      });
      add(exitButton);

      // 로그인버튼
      loginButton.setBounds(460, 480, 170, 60);
      loginButton.setBorderPainted(false);
      loginButton.setContentAreaFilled(false);
      loginButton.setFocusPainted(false);
      loginButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            loginButton.setIcon(loginButtonEnteredImage);
            loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            loginButton.setIcon(loginButtonBasicImage);
            loginButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            loginButton.setVisible(false);
            signupButton.setVisible(false);
            chooseButton.setVisible(false);
            logincloseButton.setVisible(true);
            // TODO 로그인 화면 만들기
            login.setVisible(true);
            backinfo = 0;
         }
      });
      add(loginButton);

      // 회원가입버튼
      signupButton.setBounds(650, 480, 170, 60);
      signupButton.setBorderPainted(false);
      signupButton.setContentAreaFilled(false);
      signupButton.setFocusPainted(false);
      signupButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            signupButton.setIcon(signupButtonEnteredImage);
            signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            signupButton.setIcon(signupButtonBasicImage);
            signupButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            backinfo = 0;
            loginButton.setVisible(false);
            signupButton.setVisible(false);
            chooseButton.setVisible(false);
            logincloseButton.setVisible(true);
            // TODO 회원가입창 만들기
            signup.setVisible(true);
         }
      });
      add(signupButton);

      // 결과창에서 다시하기 버튼
      result.againbt.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            result.againbt.setIcon(new ImageIcon(Main.class.getResource("../images/againButtonEntered.png")));
            result.againbt.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            result.againbt.setIcon(new ImageIcon(Main.class.getResource("../images/againButtonBasic.png")));
            result.againbt.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            again();
         }
      });

      // 골라줘버튼
      chooseButton.setBounds(510, 560, 250, 100);
      chooseButton.setBorderPainted(false);
      chooseButton.setContentAreaFilled(false);
      chooseButton.setFocusPainted(false);
      chooseButton.setVisible(false);
      chooseButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            chooseButton.setIcon(chooseButtonEnteredImage);
            chooseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            chooseButton.setIcon(chooseButtonBasicImage);
            chooseButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            backinfo = 1;
            loginButton.setVisible(false);
            signupButton.setVisible(false);
            chooseButton.setVisible(false);
            backButton.setVisible(true);
            // TODO 골라줘로 화면 전환
            background = new ImageIcon(Main.class.getResource("../images/chooseBackground.png")).getImage();

            jungmoonButton.setVisible(true);
            dongmoonButton.setVisible(true);
            humoonButton.setVisible(true);
            jamoonButton.setVisible(true);
            selposition.setVisible(true);
            logoutButton.setVisible(false);

         }
      });
      add(chooseButton);

      // 자쪽 버튼
      jamoonButton.setBounds(416, 66, 120, 140);
      jamoonButton.setBorderPainted(false);
      jamoonButton.setContentAreaFilled(false);
      jamoonButton.setFocusPainted(false);
      jamoonButton.setVisible(false);
      jamoonButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            jamoonButton.setIcon(jamoonptEnteredImage);
            jamoonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            jamoonButton.setIcon(jamoonptImage);
            jamoonButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            Filter.setPosition("자쪽");
            choose_dtail();
         }
      });
      add(jamoonButton);

      // 후문 버튼
      humoonButton.setBounds(652, 28, 120, 140);
      humoonButton.setBorderPainted(false);
      humoonButton.setContentAreaFilled(false);
      humoonButton.setFocusPainted(false);
      humoonButton.setVisible(false);
      humoonButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            humoonButton.setIcon(humoonptEnteredImage);
            humoonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            humoonButton.setIcon(humoonptImage);
            humoonButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            Filter.setPosition("후문");
            choose_dtail();
         }
      });
      add(humoonButton);

      // 동문 버튼
      dongmoonButton.setBounds(935, 462, 120, 140);
      dongmoonButton.setBorderPainted(false);
      dongmoonButton.setContentAreaFilled(false);
      dongmoonButton.setFocusPainted(false);
      dongmoonButton.setVisible(false);
      dongmoonButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            dongmoonButton.setIcon(dongmoonptEnteredImage);
            dongmoonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            dongmoonButton.setIcon(dongmoonptImage);
            dongmoonButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            Filter.setPosition("동문");
            choose_dtail();
         }
      });
      add(dongmoonButton);

      // 정문 버튼
      jungmoonButton.setBounds(269, 405, 120, 140);
      jungmoonButton.setBorderPainted(false);
      jungmoonButton.setContentAreaFilled(false);
      jungmoonButton.setFocusPainted(false);
      jungmoonButton.setVisible(false);
      jungmoonButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            jungmoonButton.setIcon(jungmoonptEnteredImage);
            jungmoonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            jungmoonButton.setIcon(jungmoonptImage);
            jungmoonButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            Filter.setPosition("정문");
            choose_dtail();
         }
      });
      add(jungmoonButton);

      // 로그인 화면에서의 로그인 버튼
      login.loginbt.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            login.loginbt.setIcon(new ImageIcon(Main.class.getResource("../images/login_loginButtonEntered.png")));
            login.loginbt.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            login.loginbt.setIcon(new ImageIcon(Main.class.getResource("../images/login_loginButtonBasic.png")));
            login.loginbt.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
//             main_logined();
         }
      });

      signup.signupbt.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            signup.signupbt
                  .setIcon(new ImageIcon(Main.class.getResource("../images/signup_signupButtonEntered.png")));
            signup.signupbt.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            signup.signupbt
                  .setIcon(new ImageIcon(Main.class.getResource("../images/signup_signupButtonBasic.png")));
            signup.signupbt.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            // mainback_0();
            // JOptionPane.showMessageDialog(login, "회원가입 성공!", " ",
            // JOptionPane.INFORMATION_MESSAGE, null);
            // JOptionPane.showMessageDialog(login, "로그인 실패", " ",
            // JOptionPane.INFORMATION_MESSAGE, null); //로그인 실패시 띄우고 다시 로그인창띄우기
         }
      });

      // 두번째 선택화면(종류,가격)
      last_chooseButton.setBounds(536, 456, 250, 100);
      last_chooseButton.setBorderPainted(false);
      last_chooseButton.setContentAreaFilled(false);
      last_chooseButton.setFocusPainted(false);
      last_chooseButton.setVisible(false);
      last_chooseButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            last_chooseButton.setIcon(chooseButtonEnteredImage);
            last_chooseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            last_chooseButton.setIcon(chooseButtonBasicImage);
            last_chooseButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         @Override
         public void mousePressed(MouseEvent e) {
            // 가격이랑 음식종류 넘겨줌
            Filter f = new Filter();
            Filter.setPrice(won.getText());
            Filter.setFood((String) style.getSelectedItem());
            System.out.println(won.getText());
            System.out.println((String) style.getSelectedItem());
            //clientQuery cq = new clientQuery();
            //Restaurants restaurant = new Restaurants(f.getPosition(),f.getFood(),f.getPrice());
            //res= (f.getPosition(),f.getFood(),f.getPrice());

            //int rows = cq.getRows(f.getPosition(), f.getFood(), f.getPrice());
            //System.out.println("WhattoeatClass_rows = " + rows);
            //int idx = cq.getIndex(f.getPosition(), f.getFood(), f.getPrice());
            //System.out.println("clientQuery_idx = " + idx);
            result.setValue(f.getPosition(),f.getFood(),f.getPrice());
            //-result.setIndex(idx);
            result.showView(f.getPosition(),f.getFood(), f.getPrice());
            selected(); // 다음화면으로 넘김
         }
      });
      add(last_chooseButton);

      // 스타일레이블
      style_lb.setFont(new Font("Lucida Grande", Font.BOLD, 25));
      style_lb.setBounds(276, 175, 300, 58);
      style_lb.setVisible(false);
      add(style_lb);

      // 가격 레이블
      won_lb.setFont(new Font("Lucida Grande", Font.BOLD, 25));
      won_lb.setBounds(722, 171, 450, 65);
      won_lb.setVisible(false);
      add(won_lb);

      // 가격텍스트필드
      won.setBounds(816, 254, 200, 58);
      won.setVisible(false);
      add(won);

      // 콤보박스
      style.setBounds(276, 267, 241, 46);
      style.setVisible(false);
      add(style);

      // 메뉴바
      menuBar.setBounds(0, 0, 1280, 30);
      menuBar.addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
         }
      });
      menuBar.addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseDragged(MouseEvent e) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            setLocation(x - mouseX, y - mouseY);
         }
      });
      add(menuBar);

      // 로그인화면
      login.setBounds(350, 180, 600, 400);
      login.setVisible(false);
      add(login);

      // 회원가입화면
      signup.setBounds(350, 180, 600, 400);
      signup.setVisible(false);
      add(signup);

      // 결과창화면
      result.setVisible(false);
      add(result);
   }

   protected void restaurant(String position, String food, String price) {
      // TODO Auto-generated method stub
      
   }

   // login,signup -> main screen
   void mainback_0() {
      login.setVisible(false);
      loginButton.setVisible(true);
      signupButton.setVisible(true);
      signup.setVisible(false);
      logincloseButton.setVisible(false);
   }

   // choosepos -> main screen
   void mainback_1() {
      backButton.setVisible(false);
      chooseButton.setVisible(true);
      logoutButton.setVisible(true);
      jungmoonButton.setVisible(false);
      humoonButton.setVisible(false);
      jamoonButton.setVisible(false);
      dongmoonButton.setVisible(false);
      selposition.setVisible(false);
      background = new ImageIcon(Main.class.getResource("../images/intro.png")).getImage();
   }

   // choose_detail -> choosepos
   void mainback_2() {
      backinfo = 1;
      background = new ImageIcon(Main.class.getResource("../images/chooseBackground.png")).getImage();
      jungmoonButton.setVisible(true);
      dongmoonButton.setVisible(true);
      humoonButton.setVisible(true);
      jamoonButton.setVisible(true);
      selposition.setVisible(true);

      last_chooseButton.setVisible(false);
      style_lb.setVisible(false);
      won_lb.setVisible(false);
      won.setVisible(false);
      style.setVisible(false);
   }

   // 고른화면 -> choose_detail
   void mainback_3() {
      backinfo = 2;

      last_chooseButton.setVisible(true);
      style_lb.setVisible(true);
      won_lb.setVisible(true);
      won.setVisible(true);
      style.setVisible(true);
      result.setVisible(false);

      // 고른화면 요소들 false
   }

   // 로그인 되었을때 화면
   static void main_logined() {
      chooseButton.setVisible(true);
      login.setVisible(false);
      signup.setVisible(false);
      logincloseButton.setVisible(false);
      loginButton.setVisible(false);
      signupButton.setVisible(false);
      logoutButton.setVisible(true);
   }

   // choose_detail
   void choose_dtail() {
      background = basicbackground;
      logoutButton.setVisible(false);
      jungmoonButton.setVisible(false);
      humoonButton.setVisible(false);
      jamoonButton.setVisible(false);
      dongmoonButton.setVisible(false);
      selposition.setVisible(false);

      last_chooseButton.setVisible(true);
      style_lb.setVisible(true);
      won_lb.setVisible(true);
      won.setVisible(true);
      style.setVisible(true);

      backinfo = 2;
   }

   // 마지막 메뉴 골라준화면
   void selected() {
      background = basicbackground;

      logoutButton.setVisible(false);
      last_chooseButton.setVisible(false);
      style_lb.setVisible(false);
      won_lb.setVisible(false);
      won.setVisible(false);
      style.setVisible(false);
      result.setVisible(true);

      backinfo = 3;
   }

   public void paint(Graphics g) {
      screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
      screenGraphic = screenImage.getGraphics();
      screenDraw(screenGraphic);
      g.drawImage(screenImage, 0, 0, null);
   }

   public void screenDraw(Graphics g) {
      g.drawImage(background, 0, 0, null);
      paintComponents(g);

      this.repaint();
   }

   void again() {
      background = new ImageIcon(Main.class.getResource("../images/intro.png")).getImage();

      result.setVisible(false);
      main_logined();

   }
}