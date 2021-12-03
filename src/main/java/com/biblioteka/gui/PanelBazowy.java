package com.biblioteka.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelBazowy extends JPanel {
  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    Graphics2D graphics2d = (Graphics2D) graphics;
    graphics2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    int width = getWidth();
    int height = getHeight();

    // gradient od lewego górnego rogu do prawego dolnego
    GradientPaint paint =
        new GradientPaint(0, 0, new Color(0xe5dcc3), 0, height, new Color(0xaaa492));
    graphics2d.setPaint(paint);
    graphics2d.fillRect(0, 0, width, height);
  }
}
