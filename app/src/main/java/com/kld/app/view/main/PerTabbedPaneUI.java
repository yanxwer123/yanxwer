package com.kld.app.view.main;
 
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.kld.app.util.Constant;

public class PerTabbedPaneUI extends BasicTabbedPaneUI {

	private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);
	private ColorSet selectedColorSet;
	private ColorSet defaultColorSet;
	private ColorSet hoverColorSet;
	private boolean contentTopBorderDrawn = true;
	private Color lineColor = new Color(158, 158, 158);
	private Color dividerColor = new Color(200, 200, 200);
	private Insets contentInsets = new Insets(1, 1, 1, 1);
	private int lastRollOverTab = -1;
	private static int Hight = 30;//tab选项卡的高度

	public static ComponentUI createUI(JComponent c) {
		return new PerTabbedPaneUI();
	}

	protected LayoutManager createLayoutManager() {
		if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
			return super.createLayoutManager();
		} else { 
			return new TabbedPaneLayout();
		}
	}


	public PerTabbedPaneUI() {

		selectedColorSet = new ColorSet();
//		selectedColorSet.topGradColor1 = new Color(233, 237, 248);
//		selectedColorSet.topGradColor2 = new Color(158, 199, 240);
//
//		selectedColorSet.bottomGradColor1 = new Color(112, 173, 239);
//		selectedColorSet.bottomGradColor2 = new Color(183, 244, 253);
		String backcolorstr=Constant.TAB_SELECTED_COCLER;
		Color backcolor=new Color(Integer.decode(backcolorstr));
		selectedColorSet.topGradColor1 = backcolor;
		selectedColorSet.topGradColor2 = backcolor;

		selectedColorSet.bottomGradColor1 = backcolor;
		selectedColorSet.bottomGradColor2 = backcolor;
		
		defaultColorSet = new ColorSet();
//		defaultColorSet.topGradColor1 = new Color(253, 253, 253);
//		defaultColorSet.topGradColor2 = new Color(237, 237, 237);
//
//		defaultColorSet.bottomGradColor1 = new Color(222, 222, 222);
//		defaultColorSet.bottomGradColor2 = new Color(255, 255, 255);
		String topcolorstr=Constant.TAB_TOP_BG;
		Color topcolor=new Color(Integer.decode(topcolorstr));
		defaultColorSet.topGradColor1 = topcolor;
		defaultColorSet.topGradColor2 = topcolor;

		defaultColorSet.bottomGradColor1 = topcolor;
		defaultColorSet.bottomGradColor2 = topcolor;
		
		hoverColorSet = new ColorSet();
		hoverColorSet.topGradColor1 = new Color(244, 244, 244);
		hoverColorSet.topGradColor2 = new Color(223, 223, 223);

		hoverColorSet.bottomGradColor1 = new Color(211, 211, 211);
		hoverColorSet.bottomGradColor2 = new Color(235, 235, 235);

		maxTabHeight = Hight;

		setContentInsets(1);
	}

	public void setContentTopBorderDrawn(boolean b) {
		contentTopBorderDrawn = b;
	}

	public void setContentInsets(Insets i) {
		contentInsets = i;
	}

	public void setContentInsets(int i) {
		contentInsets = new Insets(i, i, i, i);
	}

	public int getTabRunCount(JTabbedPane pane) {
		return 1;
	}

	protected void installDefaults() {
		super.installDefaults();

		RollOverListener l = new RollOverListener();
		tabPane.addMouseListener(l);
		tabPane.addMouseMotionListener(l);

		tabAreaInsets = NO_INSETS; // tabɫinsets�߿�
		tabInsets = new Insets(0, 0, 50, 50);
	}

	protected boolean scrollableTabLayoutEnabled() {
		return false;
	}

	protected Insets getContentBorderInsets(int tabPlacement) {
		return contentInsets;
	}
	protected int calculateTabHeight(int tabPlacement, int tabIndex,
			int fontHeight) {
		int h = super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
		return h*2;
	}

	protected int calculateTabWidth(int tabPlacement, int tabIndex,
			FontMetrics metrics) {
		int w = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
		int wid = metrics.charWidth('M');
		w += wid-20;
		return w;
	}

	protected int calculateMaxTabHeight(int tabPlacement) {
		return Hight;
	}

	protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintTabArea(g, tabPlacement, selectedIndex);

		if (contentTopBorderDrawn) {
			g2d.setColor(lineColor);
			g2d.drawLine(0, Hight, tabPane.getWidth() - 1, Hight);
		}
	}
	Color curtagColor= new Color(Integer.decode("#999999"));
	protected void paintTabBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		Graphics2D g2d = (Graphics2D) g;
		ColorSet colorSet;

		Rectangle rect = rects[tabIndex];

		if (isSelected) {
			colorSet = selectedColorSet;
			tabPane.setForeground(Color.BLACK);
		} else if (getRolloverTab() == tabIndex) {
			colorSet = hoverColorSet;
			tabPane.setForeground(Color.BLACK);
		} else {
			colorSet = defaultColorSet;
			tabPane.setForeground(curtagColor);
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		int width = rect.width;
		int xpos = rect.x;
		int yPos = rect.y;
		if (tabIndex > -1) {
			width--;
			xpos++;
			yPos += 2;
		}

		g2d.setPaint(new GradientPaint(xpos, 0, colorSet.topGradColor1, xpos,
				h / 2, colorSet.topGradColor2));
		g2d.fill(this.getUpArea(xpos, yPos, width, h - 2));

		g2d.setPaint(new GradientPaint(0, h / 2, colorSet.bottomGradColor1, 0,
				h, colorSet.bottomGradColor2));
		g2d.fill(this.getDownArea(xpos, yPos, width, h - 2));

		if (contentTopBorderDrawn) {
			g2d.setColor(lineColor);
			g2d.drawLine(rect.x, Hight, rect.x + rect.width - 1, Hight);
		}
	}

	private Shape getArea(int x, int y, int w, int h) {
		RoundRectangle2D rect = new RoundRectangle2D.Float(x, y, w, h, 15, 15);
		Area a = new Area(rect);
		Rectangle2D rec = new Rectangle2D.Float(x, y + h / 2, w, h / 2);
		Area b = new Area(rec);
		a.add(b);
		return a;
	}

	/**
	 * �ϰ벿��
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	private Shape getUpArea(int x, int y, int w, int h) {
		Rectangle2D rec = new Rectangle2D.Float(x, y, w, h / 2 + 1);
		Area a = new Area(rec);
		RoundRectangle2D rect = new RoundRectangle2D.Float(x, y, w, h, 10, 10);
		Area b = new Area(rect);
		a.intersect(b);
		return a;
	}

	/**
	 * �°벿��
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	private Shape getDownArea(int x, int y, int w, int h) {
		Rectangle2D rec = new Rectangle2D.Float(x, y + h / 2, w, h / 2 + 1);
		Area a = new Area(rec);
		RoundRectangle2D rect = new RoundRectangle2D.Float(x, y, w, h, 15, 15);
		return a;
	}

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
			int x, int y, int w, int h, boolean isSelected) {
		Rectangle rect = getTabBounds(tabIndex, new Rectangle(x, y, w, h));
		// g.setColor(dividerColor);
		Graphics2D g2 = (Graphics2D) g;
		Composite old = g2.getComposite();
		AlphaComposite comp = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.0f);
		g2.setComposite(comp);
		g2.setColor(dividerColor);
		g2.drawLine(rect.x + rect.width, 0, rect.x + rect.width, Hight);
		g2.setComposite(old);
	}

	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
	}

	protected void paintContentBorderRightEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}

	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}

	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}

	protected void paintFocusIndicator(Graphics g, int tabPlacement,
			Rectangle[] rects, int tabIndex, Rectangle iconRect,
			Rectangle textRect, boolean isSelected) {
		// Do nothing
	}

	protected int getTabLabelShiftY(int tabPlacement, int tabIndex,
			boolean isSelected) {
		return 0;
	}

	private class ColorSet {
		Color topGradColor1;
		Color topGradColor2;

		Color bottomGradColor1;
		Color bottomGradColor2;
	}

	private class RollOverListener implements MouseMotionListener,
			MouseListener {

		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
			checkRollOver();
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			checkRollOver();
		}

		public void mouseExited(MouseEvent e) {
			try{
				tabPane.repaint();
			}
			catch(Exception ee){}
			
		}

		private void checkRollOver() {
			int currentRollOver = getRolloverTab();
			if (currentRollOver != lastRollOverTab) {
				lastRollOverTab = currentRollOver;
				Rectangle tabsRect = new Rectangle(0, 0, tabPane.getWidth(), Hight);
				tabPane.repaint(tabsRect);
			}
		}
	}

	public class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout {

		public TabbedPaneLayout() {
			PerTabbedPaneUI.this.super();

		}

		protected void calculateTabRects(int tabPlacement, int tabCount) {
			super.calculateTabRects(tabPlacement, tabCount);
			for (int i = 0; i < rects.length; i++) {
				rects[i].x = rects[i].x + (5 * i);
			}
		}

 
	}
}
