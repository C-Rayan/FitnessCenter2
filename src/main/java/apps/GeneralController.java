package apps;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GeneralController {
    public static class generalFunctions{
        public static void beautifyTexts(JTextField[] texts){
            for (JTextField buttonOf : texts){
                buttonOf.setOpaque(false);
                buttonOf.addMouseListener(new MouseListener() {
                    @Override public void mouseClicked(MouseEvent e) {
                        if (!buttonOf.isOpaque()) {
                            buttonOf.setOpaque(true);
                            buttonOf.setText("");
                        }
                    }
                    @Override public void mousePressed(MouseEvent e) {
                        if (!buttonOf.isOpaque()) {
                            buttonOf.setOpaque(true);
                            buttonOf.setText("");
                        }
                    }
                    @Override public void mouseReleased(MouseEvent e) {}
                    @Override public void mouseEntered(MouseEvent e) {}
                    @Override public void mouseExited(MouseEvent e) {}
                });
            }
        }

    }
}
