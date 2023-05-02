package felulet;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

//선택된 값들을 모아놓는 곳
public class Gomb extends JButton{
    
    private int ertek;  //값
    private boolean kijelolt; //지정된 값?
    
    public Gomb(int ertek){
        super();
        
        this.ertek = ertek;
        this.kijelolt = false;
        
        initComponent();
    }
    public void setKijelolt(){                //프리바이트변수 겟, 셋등 기본적인 함수들..
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
            
            this.addMouseListener(new MouseAdapter(){      //마우스리스너 오버라이드식
                @Override
                public void mouseEntered(MouseEvent e){
                    setBackground(Color.red);               //마우스가 올라가면 빨강색으로
                }

                @Override
                public void mouseExited(MouseEvent e) {  //포인터가 벗어나면 하양색으로 되돌림
                    setBackground(Color.white);          //이로 인해 노랑색이 하양색으로 돌아가는 버그가 생기는걸로 추정됨  
                } 
            });
            
    }
}
