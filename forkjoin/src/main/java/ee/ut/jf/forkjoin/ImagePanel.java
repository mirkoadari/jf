package ee.ut.jf.forkjoin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class ImagePanel extends JPanel {
  BufferedImage mImage;

  public ImagePanel(BufferedImage image) {
    mImage = image;
  }

  protected void paintComponent(Graphics g) {
    int x = (getWidth() - mImage.getWidth())/2;
    int y = (getHeight() - mImage.getHeight())/2;
    g.drawImage(mImage, x, y, this);
  }
}
