package what_to_eat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Result extends JPanel implements ActionListener {

   // 테이블과 스크롤페인
   JTable table = new JTable();
   JScrollPane scrollpane = new JScrollPane();
   // 가게이름 레이블
   JLabel restaurantName = new JLabel();
   // JTable 모델
   DefaultTableModel model = new DefaultTableModel();
   // JTable이 들어갈 패널
   JPanel panelTable = new JPanel();

   JButton againbt = new JButton();
   String url = "jdbc:sqlite:C:\\Users\\tjdxk\\Desktop\\DB\\WhatToEat.db"; // 사용할 Database의 위치

   int index = 0;
   int rows = 0;
   String place = null;
   String style = null;
   String price = null;
   clientQuery cq = new clientQuery();

   public void setValue(String place, String style, String price) {
      this.place = place;
      this.style = style;
      this.price = price;
   }

   public Result() {
      setBounds(0, 30, 1280, 690);
      setBackground(new Color(149, 56, 38));
      setLayout(null);

      JLabel foodName = new JLabel("음식 이름"); // 음식 이름 불러오면 됨.
      foodName.setBounds(551, 18, 158, 59);
      foodName.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
      add(foodName);

      JLabel foodprice = new JLabel("가격"); // 음식 가격 불러오면 됨.
      foodprice.setBounds(450, 89, 61, 16);
      add(foodprice);

      JLabel restaurant = new JLabel("가게이름"); // 가게이름 불러오고.
      restaurant.setBounds(163, 203, 61, 16);
      add(restaurant);

      JLabel label_2 = new JLabel("메뉴 테이블"); // JTable로 음식점 메뉴들 불러와서 띄어주기
      label_2.setBounds(299, 203, 61, 16);
      add(label_2);

      // 가게이름 레이블
      restaurantName.setBounds(163, 233, 123, 16);
      add(restaurantName);

      // TODO 다시하기 버튼이미지 만들고 클릭 액션 만들기 againbt.setBorderPainted(false);
      againbt.setContentAreaFilled(false);
      againbt.setFocusPainted(false);
      againbt.setIcon(new ImageIcon(Main.class.getResource("../images/againButtonBasic.png")));
      againbt.setBounds(1060, 560, 186, 95);
      againbt.addActionListener(this);
      add(againbt);

   }

   public void showView(String place, String style, String price) {

      // JTable과 Scrollpane 생성
      table = new JTable(null);
      scrollpane = new JScrollPane(null);

      // index는 입력받은 장소, 음식종류, 가격대에 따라 행들을 받아오고 임의의 행에 들어있는 id를 저장
      // 즉, index는 임의의 가계id
      index = cq.getIndex(place, style, price);
      // rows는 해당하는 행의 값 저장
      rows = cq.getIdxRows("" + index);

      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;

      // 가계이름
      String name = null;
      // JTable의 헤더(제목)과 콘텐츠
      String header[] = { "음식 종류", "가격", "이름" };
      String contents[][] = new String[rows][3];

      // JTable표 안에 들어갈 데이터
      String foodStyle[] = new String[rows];
      String menu[] = new String[rows];
      String priceRange[] = new String[rows];

      try {
         conn = DriverManager.getConnection(url);

         // id에 해당하는 모든 값 불러오는 쿼리문
         sql = "SELECT style, menu, price, restaurant FROM restaurants WHERE id = ?";
         System.out.println(sql);
         pstmt = conn.prepareStatement(sql);

         // ?에 index값 대입
         pstmt.setString(1, "" + index);
         System.out.println(index);
         rs = pstmt.executeQuery();

         for (int count = 0; count < rows; count++) {
            foodStyle[count] = rs.getString("style");
            System.out.println("name([" + count + "]) = " + foodStyle[count]);
            menu[count] = rs.getString("menu");
            System.out.println("menu([" + count + "]) = " + menu[count]);
            priceRange[count] = rs.getString("price");
            System.out.println("price([" + count + "]) = " + priceRange[count]);
            name = rs.getString("restaurant");

            rs.next();
         }

         restaurantName.setText(name);

         for (int i = 0; i < rows; i++) {
            contents[i][0] = foodStyle[i];
            contents[i][1] = menu[i];
            contents[i][2] = priceRange[i];
            System.out.println("contents([" + i + "][0]) = " + contents[i][0]);
            System.out.println("contents([" + i + "][1]) = " + contents[i][1]);
            System.out.println("contents([" + i + "][2]) = " + contents[i][2]);
         }

         model.setDataVector(contents, header);
         table = new JTable(model);

         // JTable 한 셀의 폭 설정
         table.getColumn("음식 종류").setPreferredWidth(60);
         table.getColumn("가격").setPreferredWidth(80);
         table.getColumn("이름").setPreferredWidth(50);

         // JTable 한 셀의 높이 설정
         table.setRowHeight(30);

         // JTable 전체 높이 설정
         // 450pixel을 넘으면 450으로 고정되고 스크롤바로 이동
         int tableHeight = 30 * rows;
         if (tableHeight > 450)
            tableHeight = 450;

         // scrollpane에 table을 넣고 패널에 추가
         scrollpane = new JScrollPane(table);
         panelTable.add(scrollpane);

         // 테이블패널의 위치 설정
         panelTable.setBounds(300, 233, 400, tableHeight);
         panelTable.setVisible(true);
         add(panelTable);

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (conn != null)
               conn.close();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == againbt) {
         //restaurantName.setText("");
         //model.setNumRows(0);
         remove(panelTable);
      }
   }

}