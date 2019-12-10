package felulet;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class Gomb extends JButton{
    
    private int ertek;
    private boolean kijelolt;
    
    public Gomb(int ertek){
        super();
        
        this.ertek = ertek;
        this.kijelolt = false;
        
        initComponent();
    }
    public void setKijelolt(){
        this.kijelolt = !this.kijelolt;
    }
    public boolean getKijelolt(){
        return this.kijelolt;
    }
    
    public int getErtek(){
        return this.ertek;
    }
    
    private void initComponent(){
    this.setText(this.ertek + "");
            this.setBackground(Color.white);
            this.setForeground(Color.red);
            
            this.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e){
                    setBackground(Color.red);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Color.white);
                } 
            });
            
    }
}
