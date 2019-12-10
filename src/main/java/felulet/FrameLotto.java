package felulet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
public class FrameLotto extends JFrame implements ActionListener{
    
    private JPanel pnlFoablak, pnlMenu, pnlAllapotSav, pnlBeallitasok, pnlSzelveny;
    private JButton bttnSorsol, bttnRendez;
    private JLabel lblAllapot;
    
    private List<Integer> lottoSzamok;
    
    public FrameLotto(){
        initComponents();
    }
    
    private void initComponents(){
        lottoSzamok = new ArrayList<>();
        
        this.setTitle("Lottó 1.0");
        this.setSize(1024, 768);
        this.setLocationRelativeTo(null);
        
        this.pnlFoablak = (JPanel)(this.getContentPane());
        this.pnlFoablak.setLayout(new BorderLayout());
        
        this.bttnSorsol = new JButton();
        this.bttnSorsol.setText("Sorsol");
        this.pnlFoablak.add(this.bttnSorsol, BorderLayout.WEST);
        this.bttnSorsol.addActionListener(this);
        
        this.bttnRendez = new JButton();
        this.bttnRendez.setText("Rendez");
        this.pnlFoablak.add(this.bttnRendez, BorderLayout.EAST);
        this.bttnRendez.addActionListener(this);
        
        /* CENTER - SZELVÉNY KEZDET*/
        
        this.pnlSzelveny = new JPanel();
        this.pnlSzelveny.setLayout(new GridLayout(9, 10));
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
        
        /* CENTER - SZELVÉNY VÉG*/
        
        
        
        /* ÁLLAPOT SÁV KEZDÉS*/
        
        this.pnlAllapotSav = new JPanel();
        
        this.lblAllapot = new JLabel();
        this.lblAllapot.setText("Valami");
        
        
        this.pnlAllapotSav.add(this.lblAllapot);
        this.pnlFoablak.add(this.pnlAllapotSav, BorderLayout.SOUTH);
        
        /* ÁLLAPOT SÁV VÉG */
        
        this.setResizable(false);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(bttnRendez)) {
            this.lottoSzamok.sort(Integer :: compareTo);
            this.lblAllapot.setText(this.lottoSzamok.toString());
        }
        else if (e.getSource().equals(bttnSorsol)) {
            this.lblAllapot.setText(this.lblAllapot.getText() + "sajt");    
        }
        
    }

    public void gombKattintas(MouseEvent e){
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
