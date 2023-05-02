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
        JSeparator elvalaszto = new JSeparator();
        kilepes = new JMenuItem();
        kilepes.setText("출구");
        
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
        for (int i = 0; i < 90; i++) {
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
    public void actionPerformed(ActionEvent e) {  //액션 이벤트... 선택부분
        if (e.getSource().equals(bttnRendez)) {
            if (this.lottoSzamok.size() != 5) {
                JOptionPane.showMessageDialog(this, "5개의 숫자를 선택하지 않았습니다.", "잘못", JOptionPane.ERROR_MESSAGE);
            }else{
            this.lottoSzamok.sort(Integer :: compareTo);
            this.lblAllapot.setText(this.lottoSzamok.toString());
            }
        }
        else if (e.getSource().equals(bttnSorsol) || e.getSource().equals(sorsol)) {
            //Component[] gombok = this.pnlSzelveny.getComponents();
            Random rnd = new Random();
            for (int i = 0; i < 5; i++) {
                int szam;
                do {
                    szam = rnd.nextInt(90) + 1;
                } while (this.lottoSzamok.contains(szam));
                this.lottoSzamok.add(szam);
            }
            
            for (int i = 0; i < this.lottoSzamok.size(); i++) {
                int index = this.lottoSzamok.get(i) - 1;
                Gomb g = ((Gomb)this.pnlSzelveny.getComponent(index));
                g.setBackground(Color.YELLOW);
                g.setKijelolt();
            }
        }
        else if (e.getSource().equals(kilepes)){
        System.exit(0);
        }
        
    }

    public void gombKattintas(MouseEvent e){  //버튼클릭가능 이벤트..?
        Gomb g = (Gomb)(e.getSource());
        if (g.getKijelolt()) {
            lottoSzamok.remove(lottoSzamok.indexOf(g.getErtek()));
            g.setForeground(Color.RED);
            g.setBorder(new LineBorder(Color.GRAY, 1));
            g.setKijelolt();
        }else{
            if(lottoSzamok.size() < 5){
                lottoSzamok.add(g.getErtek());
                g.setForeground(new Color(18, 117, 16));
                g.setBorder(new LineBorder(new Color(18, 117, 16), 3));
                g.setKijelolt();
                }
            }
    }
    

}
