import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuiceKiosk {
    int[] count; // 각 음료의 수량을 저장할 배열
    int[] price; // 각 음료의 가격을 저장할 배열
    int totalOrderAmount = 0; // 주문 총액

    public JuiceKiosk() {
        JFrame frame = new JFrame("카페><");
        frame.setBounds(0,0,1200,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 30);

        // 북쪽1 패널
        JPanel pNorth1 = new JPanel();
        pNorth1.setBackground(new Color(178,162,162));
        pNorth1.setPreferredSize(new Dimension(0,50));
        pNorth1.setFont(font1);

        // 북쪽 맨 위 버튼
        JButton button1 = new JButton("매장");
        JButton button2 = new JButton("포장");
        JButton button3 = new JButton("캐리어");
        JButton button4 = new JButton("보냉백");
        button1.setPreferredSize(new Dimension(200,40));
        button2.setPreferredSize(new Dimension(200,40));
        button3.setPreferredSize(new Dimension(75,40));
        button4.setPreferredSize(new Dimension(75,40));
        button1.setBackground(new Color(178,162,162));
        button1.setForeground(Color.WHITE);
        button2.setBackground(new Color(178,162,162));
        button2.setForeground(Color.WHITE);
        pNorth1.add(button1);
        pNorth1.add(button2);
        pNorth1.add(button3);
        pNorth1.add(button4);

        // 배열 설정
        String menu[] = {"아메리카노", "아이스티", "카페모카", "딸기쉐이크", "망고스무디", "흑당버블티", "유자차", "빵set"};
        int price[] = { 2000, 2500, 3000, 4000, 4500, 4500, 5000, 6500 };
        JButton bt[] = new JButton[menu.length];
        ImageIcon icon[] = new ImageIcon[menu.length];

        TextField suja[] = new TextField[menu.length];
        Button minus[] = new Button[menu.length];
        Button plus[] = new Button[menu.length];
        JButton ok[] = new JButton[menu.length];
        Label l[] = new Label[menu.length];

        count = new int[menu.length];

        // 북쪽2 패널
        JPanel pNorth2 = new JPanel();
        pNorth2.setBackground(new Color(238,223,223));
        pNorth2.setLayout(new GridLayout(2,4,10,10));
        pNorth2.setPreferredSize(new Dimension(0,500));
        pNorth2.setFont(font1);


        // 버튼 설정
        for (int i = 0; i < menu.length; i++) {
            // 음료 버튼
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BorderLayout());

            bt[i] = new JButton();
            bt[i].setLayout(new BorderLayout());
            bt[i].setBackground(Color.WHITE);

            icon[i] = new ImageIcon(i + ".jpg");
            Image img = icon[i].getImage();
            Image newimg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            icon[i] = new ImageIcon(newimg);

            JLabel label = new JLabel(menu[i], JLabel.CENTER);
            label.setFont(font1);
            label.setPreferredSize(new Dimension(100, 30));
            bt[i].add(new JLabel(icon[i]), BorderLayout.CENTER);
            bt[i].add(label, BorderLayout.SOUTH);

            itemPanel.add(bt[i], BorderLayout.NORTH);

            // 숫자 버튼 패널
            JPanel controls = new JPanel();
            controls.setLayout(new FlowLayout());

            JButton sizeUp = new JButton("사이즈 업");
            l[i] = new Label(price[i] + "원");
            suja[i] = new TextField("0", 2);
            suja[i].setEditable(false);
            minus[i] = new Button("-");
            plus[i] = new Button("+");

            controls.add(sizeUp);
            controls.add(l[i]);
            controls.add(minus[i]);
            controls.add(suja[i]);
            controls.add(plus[i]);


            itemPanel.add(controls, BorderLayout.SOUTH);

            pNorth2.add(itemPanel);

            // 각 음료 버튼에 대한 이벤트 처리
            int j = i;
            bt[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    minus[j].setEnabled(true);
                    plus[j].setEnabled(true);
                    bt[j].setEnabled(false);
                    ok[j].setEnabled(true);
                    count[j]++; // 해당 음료의 수량 증가
                    suja[j].setText(String.valueOf(count[j]));
                    updateTotalOrderAmount();
                }
            });

            // "-" 버튼 이벤트
            minus[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (count[j] > 0) {
                        count[j]--;
                        suja[j].setText(String.valueOf(count[j]));
                        if (count[j] == 0) {
                            minus[j].setEnabled(false);
                        }
                        updateTotalOrderAmount();
                    }
                }
            });

            // "+" 버튼 이벤트
            plus[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    count[j]++;
                    suja[j].setText(String.valueOf(count[j]));
                    minus[j].setEnabled(true); // 숫자가 0보다 크면 "-" 버튼 활성화
                    updateTotalOrderAmount();
                }
            });
        }

        // 남쪽1 패널
        JPanel pSouth1 = new JPanel();
        pSouth1.setBackground(new Color(255,255,25));
        pSouth1.setPreferredSize(new Dimension(0,285));
        pSouth1.setFont(font1);
        //
        TextArea ta = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
        ta.setText("                            상품명              가격              수량              합계\n\n");
        ta.setBackground(new Color(243, 243, 243));
        ta.setEditable(false);
        ta.setFont(font1);
        pSouth1.add(ta);

        // 주문하기 버튼
        JButton b1 = new JButton("주문하기");
        pSouth1.add(b1);

        // 주문버튼 리스너
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 주문 내역을 보여주는 창 추가
                StringBuilder orderDetails = new StringBuilder();
                orderDetails.append("주문 내역:\n");
                orderDetails.append("상품명     \t가격     \t수량     \t합계 \n");

                // 주문 내역을 문자열로 구성
                for (int i = 0; i < menu.length; i++) {
                    if (count[i] > 0) { // 주문된 상품만 추가
                        String productName = menu[i];
                        int unitPrice = price[i];
                        int quantity = count[i];
                        int subtotal = unitPrice * quantity;

                        orderDetails.append(productName).append("\t ")
                                .append(unitPrice).append("원\t   ")
                                .append(quantity).append("\t   ")
                                .append(subtotal).append("원\n");
                        totalOrderAmount += subtotal;
                    }
                }

                // 주문 총액 추가
                orderDetails.append("\n총 주문 금액: ").append(totalOrderAmount).append("원");

                // 주문 내역을 다이얼로그로 표시
                JOptionPane.showMessageDialog(null, orderDetails.toString());

                // 주문 완료 후 초기화
                for (int i = 0; i < menu.length; i++) {
                    bt[i].setEnabled(true);
                    count[i] = 0; // 수량 초기화
                    suja[i].setText("0");
                }
                ta.setText("                            상품명              가격              수량              합계\n\n");
                totalOrderAmount = 0; // 주문 완료 후 주문 총액 초기화
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(pNorth1, BorderLayout.NORTH);
        frame.add(pNorth2, BorderLayout.CENTER);
        frame.add(pSouth1, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // 주문 총액 업데이트 메서드
    private void updateTotalOrderAmount() {
        totalOrderAmount = 0;
        for (int i = 0; i < count.length; i++) {
            totalOrderAmount += count[i] * price[i];
        }
        // 화면에 주문 총액 출력
        JOptionPane.showMessageDialog(null, "현재 주문 총액: " + totalOrderAmount + "원");
    }



    public static void main(String[] args) {
        new JuiceKiosk();
    }

}

