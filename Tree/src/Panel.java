import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Tree tree;
    private static final int N = 500;
    private static final double x = 5.9;
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.BLACK);
        paintTree(g, tree, tree);
    }

    private float paintTree(Graphics graphics, Tree node, Tree parent) {
    	
    	Graphics2D g = (Graphics2D) graphics;
    	
    	float sum = 0.0f, self = 1.0f;

        for (Tree i : node.childs) {
            sum += Math.pow(paintTree(g, i, node), x);
        }
        //System.out.println("childs.size =" +node.childs.size());
        
        if (sum > 0.0f) {
        	self = (float) Math.pow(sum, 1.0 / x);
        }

        /* draw */
        int x1 = translateX(node);
        int y1 = translateY(node);

        int x2 = translateX(parent);
        int y2 = translateY(parent);
        
        /*g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON);
        */
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(self));
        g.drawLine(x1, y1, x2, y2);
        
        return self;
    }

    private int translateX(Tree t) {
        int width = getWidth();
        return (int) (width / 2 + width / 2 * t.getCurrentVertex().x);
    }

    private int translateY(Tree t) {
        int height = getHeight();
        return (int) (height - height * t.getCurrentVertex().y);
    }

    public void repaintPanel() {
        repaint();
    }

    public Panel() {
        tree = Tree.createTree(N);
    }
}
