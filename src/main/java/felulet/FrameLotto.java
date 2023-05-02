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
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
//주요 시스템설계부분
public class FrameLotto extends JFrame implements ActionListener{
    
    private JPanel pnlFoablak, pnlMenu, pnlAllapotSav, pnlBeallitasok, pnlSzelveny;
    private JButton bttnSorsol, bttnRendez;
    private JLabel lblAllapot;
    private JMenuItem sorsol;
    private JMenuItem kilepes;
    
    private List<Integer> lottoSzamok;
    
    public FrameLotto(){
        initComponents();
    }
    
    private void initComponents(){
        lottoSzamok = new ArrayList<>();
        
        this.setTitle("Lotto 1.0");
        this.setSize(1024, 768);
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
        this.bttnSorsol.setText("추첨");
        this.pnlFoablak.add(this.bttnSorsol, BorderLayout.WEST);
        this.bttnSorsol.addActionListener(this);
        
        this.bttnRendez = new JButton();
        this.bttnRendez.setText("마련?");
        this.pnlFoablak.add(this.bttnRendez, BorderLayout.EAST);
        this.bttnRendez.addActionListener(this);
        
        /* CENTER - SZELVÉNY KEZDET*/ //센터 구성 시작
        
        this.pnlSzelveny = new JPanel();
        this.pnlSzelveny.setLayout(new GridLayout(9, 10, 4, 4));
        //Exception in thread "AWT-EventQueue-0" java.lang.ArrayIndexOutOfBoundsException: No such child: 85
        //45로 줄이니까 난 오류
        for (int i = 0; i < 45; i++) {   //90개의 숫자버튼들..  
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
                JOptionPane.showMessageDialog(this, "5개의 숫자를 선택하지 않았습니다.", "잘못", JOptionPane.ERROR_MESSAGE);
            }else{
            this.lottoSzamok.sort(Integer :: compareTo);  //정리
            this.lblAllapot.setText(this.lottoSzamok.toString());
            }
        }
        else if (e.getSource().equals(bttnSorsol) || e.getSource().equals(sorsol)) {
            //Component[] gombok = this.pnlSzelveny.getComponents();
            Random rnd = new Random();
            for (int i = 0; i < 5; i++) {
                int szam;
                do {
                    szam = rnd.nextInt(45) + 1;  //45로 줄였을때 오류가난 이유 랜덤값이 1~90임
                } while (this.lottoSzamok.contains(szam)); //노래?
                this.lottoSzamok.add(szam);  //이건 고른 수랑 공유하는 리스트일텐데...이걸 같이 쓰면 오류가...?
                                             //일단은 수를 추첨하는게 아니라, 수를 추천해주는 시스템으로 보임
            }
            
            for (int i = 0; i < this.lottoSzamok.size(); i++) {  //리스트 목록에 있는 수들을 노랑색으로 강조시켜주는 시스템...
                int index = this.lottoSzamok.get(i) - 1;         //리스트를 비우지않고 무한히 추천하는 수를 넣으니 리스트가 터질 수 밖에
                Gomb g = ((Gomb)this.pnlSzelveny.getComponent(index));
                g.setBackground(Color.YELLOW);
                g.setKijelolt();
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
