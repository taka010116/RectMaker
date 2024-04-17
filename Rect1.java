import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.text.DecimalFormat;
import java.awt.image.*;
import javax.imageio.*;

public class Rect1 extends JPanel {
    ArrayList<Figure> figs = new ArrayList<Figure>();
    Figure sel = null;

    public Rect1() {

	setOpaque(false);
	//figs.add(new Rect(Color.RED, 200, 100, 50, 50));

	addMouseListener(new MouseAdapter(){
		public void mousePressed(MouseEvent evt){
		    sel = pick(evt.getX(), evt.getY());
		    if( sel != null){
			figs.remove(sel);
			figs.add(sel);
			repaint();
		    }else{
			Color c = Color.getHSBColor((float)Math.random(), 1f, 1f);
			sel = new Rect( c, evt.getX(), evt.getY(), 0, 0);
			figs.add(sel);
			repaint();
		    }
		}
	    });
	

	addMouseMotionListener(new MouseMotionAdapter(){
		public void mouseDragged(MouseEvent evt) {
		    if( sel == null){return;}
		    sel.moveTo(evt.getX(), evt.getY());
		    repaint();
		}
	    });
    }


    private Figure pick(int x, int y){
	Figure p = null;
	for( Figure f: figs) {
	    if( f.hit(x,y)){
		p = f; }
	}
	return p;
    }

    public void paintComponent(Graphics g) {
		for( Figure f: figs){ f.draw(g);}
    }

	


    public static void main(String[] args) {
		JFrame app = new JFrame();
		app.add(new Rect1());
		app.setSize(400, 800);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}


    interface Figure{
		public void draw(Graphics g);
		public boolean hit(int x, int y);
		public void moveTo(int x, int y);
    }
    
static class Rect implements Figure {
    Color col;
    int xpos, ypos, width, height;
    public Rect(Color c, int x, int y, int w, int h) {
	col = c; xpos = x; ypos = y; width = w; height = h;
   }

    public boolean hit(int x, int y){
		return (xpos - x)*(xpos - x) + (ypos - y)*(ypos - y) <= width * height;
    }
    
    public void moveTo(int x, int y){
        width = x - xpos;
		height = y - ypos;
    }

    public void draw(Graphics g){
		g.setColor(col);
		g.fillRect(xpos, ypos, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(xpos, ypos, width, height);
    }
}

}
