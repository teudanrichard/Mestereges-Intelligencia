package ai.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author ÁdámRichárd
 */
public class CanvasPanel extends JComponent {

    //tárolt képek
    public static ArrayList<BufferedImage> bimage = new ArrayList<>();
    private static int index = 0;

    public ArrayList<BufferedImage> getBimage() {
        return bimage;
    }

    public static void setBimage(BufferedImage bimage) {
        CanvasPanel.bimage.add(bimage);
    }
    //minden letárolt képet töröl
    public static void removeAllImage(){
        for(int i=0;i<bimage.size();i++){
            CanvasPanel.bimage.remove(i);
            i--;
        }
    }

    public void setImageIndex(int index) {
        this.index = index;
    }

    public CanvasPanel() {
        initComponents();
    }

    @Override
    public void paint(Graphics g2) {
        Graphics g = g2;
        //csak akkor rajzoljuk ki a képet,hogyha a lista nem üres...
        if(!bimage.isEmpty())
            g.drawImage(bimage.get(index), 0, 0, this);
        else{
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getSize().width, this.getSize().height);
            g.setColor(Color.red);
            g.setFont(new Font("Comic Sans",Font.PLAIN,24));
            g.drawString("Tölts be egy pályát", this.getSize().width/2-100, this.getSize().height/2-15);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());
        add(jLabel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
