package felulet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
//주요 시스템설계부분
public class FrameLotto extends JFrame implements ActionListener{
    
    private JPanel pnlFoablak, pnlMenu, pnlAllapotSav, pnlBeallitasok, pnlSzelveny, panelEast;
    private JButton bttnSorsol, bttnRendez, bttnCheck;
    private JLabel lblAllapot;
    private JMenuItem sorsol;
    private JMenuItem kilepes;
    
    private List<Integer> lottoSzamok;
    private List<Integer> lottoRand; //수를 추첨하기 위한 리스트
    private List<Integer> lottoChecked; //당첨된 수를 넣음
    
    public FrameLotto(){
        initComponents();
    }
    
    private void initComponents(){
        lottoSzamok = new ArrayList<>();
        lottoRand = new ArrayList<>();
        lottoChecked = new ArrayList<>();
        
        this.setTitle("Lotto 1.0");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        
        this.pnlFoablak = (JPanel)(this.getContentPane());
        this.pnlFoablak.setLayout(new BorderLayout());
        
        /*MENÜ KEZDÉS*/ // 메뉴 시작
        
        JMenuBar menu = new JMenuBar();
        JMenu fajlMenu = new JMenu();
        fajlMenu.setText("파일");
        sorsol = new JMenuItem();
        sorsol.setText("추첨");
        JSeparator elvalaszto = new JSeparator(); //분리
        kilepes = new JMenuItem();
        kilepes.setText("종료");
        
        menu.add(fajlMenu);
        fajlMenu.add(sorsol);
        fajlMenu.add(elvalaszto);
        fajlMenu.add(kilepes);
        
        this.pnlMenu = new JPanel();
       this.pnlMenu.setLayout(new FlowLayout((int) LEFT_ALIGNMENT));
        this.pnlMenu.add(menu);
        
        sorsol.addActionListener(this);
        kilepes.addActionListener(this);
        
        this.pnlFoablak.add(this.pnlMenu, BorderLayout.NORTH);
        
        /*MENÜ VÉGE*/  // 메뉴 끝
        
        
        this.bttnSorsol = new JButton();
        this.bttnSorsol.setText("추천");
        this.pnlFoablak.add(this.bttnSorsol, BorderLayout.WEST);
        this.bttnSorsol.addActionListener(this);

        this.panelEast = new JPanel();
        this.panelEast.setLayout(new GridLayout(2, 1, 4, 4));
        
        this.bttnRendez = new JButton();
        this.bttnRendez.setText("출력");
        this.panelEast.add(this.bttnRendez);
        this.bttnRendez.addActionListener(this);
        
        this.bttnCheck = new JButton();
        this.bttnCheck.setText("당첨확인");
        this.panelEast.add(this.bttnCheck);
        this.bttnCheck.addActionListener(this);
        
        this.pnlFoablak.add(this.panelEast, BorderLayout.EAST);
        
        /* CENTER - SZELVÉNY KEZDET*/ //센터 구성 시작
        
        this.pnlSzelveny = new JPanel();
        this.pnlSzelveny.setLayout(new GridLayout(5, 9, 4, 4));
        //Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: No such child: 85
        //45로 줄이니까 난 오류
        for (int i = 0; i < 45; i++) {   //숫자버튼들 
            Gomb bttn = new Gomb(i + 1);
            
            bttn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    gombKattintas(e);
                }
                
            });
            
            this.pnlSzelveny.add(bttn);
       }
        
        this.pnlFoablak.add(this.pnlSzelveny, BorderLayout.CENTER);
        
        /* CENTER - SZELVÉNY VÉG*/  //센터 구성 끝
        
        
        
        /* ÁLLAPOT SÁV KEZDÉS*/ //상태표시줄 시작?
        
        this.pnlAllapotSav = new JPanel();
        
        this.lblAllapot = new JLabel();
        this.lblAllapot.setText("무언가");
        
        
        this.pnlAllapotSav.add(this.lblAllapot);
        this.pnlFoablak.add(this.pnlAllapotSav, BorderLayout.SOUTH);
        
        /* ÁLLAPOT SÁV VÉG */ //상태 표시줄 끝
        
        this.setResizable(false);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {  //액션 이벤트... 
        if (e.getSource().equals(bttnRendez)) {
            if (this.lottoSzamok.size() != 5) {
                JOptionPane.showMessageDialog(this, "5개의 숫자를 선택하지 않았습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }else{
            this.lottoSzamok.sort(Integer :: compareTo);  //정리
            this.lblAllapot.setText(this.lottoSzamok.toString()); //결과?
            }
        }
        else if (e.getSource().equals(bttnSorsol) || e.getSource().equals(sorsol)) {
            //Component[] gombok = this.pnlSzelveny.getComponents();
            Random rnd = new Random();
            int nowsize = this.lottoSzamok.size();  //추첨수가 선택 수의 사이즈에 따라 변동되도록 하여 리스트가 벗어나는 오류 해결
            for (int i = 0; i < (5 - nowsize); i++) {  
                int szam;
                do {
                    szam = rnd.nextInt(45) + 1;  //1~45 랜덤
                } while (this.lottoSzamok.contains(szam)); //노래?
                this.lottoSzamok.add(szam);  
                                             //수를 추천함
            }
            
            for (int i = 0; i < this.lottoSzamok.size(); i++) {  //리스트 목록에 있는 수들을 노랑색으로 강조시켜주는 시스템...
                int index = this.lottoSzamok.get(i) - 1;         
                Gomb g = ((Gomb)this.pnlSzelveny.getComponent(index));
                g.setBackground(Color.YELLOW);
                g.setForeground(new Color(18, 117, 16));
                g.setBorder(new LineBorder(new Color(18, 117, 16), 3));
                g.setKijelolt();
            }
        }
        
        else if (e.getSource().equals(bttnCheck)){
            if (this.lottoSzamok.size() == 5) {
            Random rnd = new Random();
            lottoRand.clear();
            lottoChecked.clear();
        	for (int i = 0; i < 5; i++) {  
                int Rnum;
                do {
                    Rnum = rnd.nextInt(45) + 1;  //1~45 랜덤
                } while (this.lottoRand.contains(Rnum)); 
                this.lottoRand.add(Rnum);  
                                           
            }
        	for (int i = 0; i<5; i++) {
        		if (lottoRand.contains(lottoSzamok.get(i))) {
                    this.lottoChecked.add(lottoSzamok.get(i));  
        			
        		}
        		
        	}
            this.lottoChecked.sort(Integer :: compareTo);  //정리
            this.lottoRand.sort(Integer :: compareTo);  //정리
            this.lblAllapot.setText("맞춘 수 : " + this.lottoChecked.toString() + " / 이번추첨 수 : " + this.lottoRand.toString()); //결과출력
            }
            else {
                JOptionPane.showMessageDialog(this, "숫자 5개를 골라주세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (e.getSource().equals(kilepes)){
        System.exit(0);
        }
        
    }

    public void gombKattintas(MouseEvent e){  //위에 90개나 되는 버튼을 누를시 발생함 .. 오류는 추첨을 누른 후에 발생
        Gomb g = (Gomb)(e.getSource());
        if (g.getKijelolt()) { //선택한 값인가? 
            lottoSzamok.remove(lottoSzamok.indexOf(g.getErtek()));  //선택을 취소함
            g.setForeground(Color.RED);
            g.setBorder(new LineBorder(Color.GRAY, 1));
            g.setKijelolt();
        }else{ //선택한 값이 아니라면  
            if(lottoSzamok.size() < 5){ //총 고른 수가 5개 미만이라면  
                lottoSzamok.add(g.getErtek());    //해당 수를 고른 수에 추가함
                g.setForeground(new Color(18, 117, 16));
                g.setBorder(new LineBorder(new Color(18, 117, 16), 3));
                g.setKijelolt();
                }
            }
    }
    

}
