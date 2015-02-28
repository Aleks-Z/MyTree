import javax.swing.JFrame;

public class Frame extends JFrame {
    private Panel panel;
    public Frame() {
        panel = new Panel();
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);

    }
    public void repaintPanel() {
        panel.repaintPanel();
    }
}
