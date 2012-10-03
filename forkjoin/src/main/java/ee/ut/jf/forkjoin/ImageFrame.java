package ee.ut.jf.forkjoin;

import javax.swing.*;
import java.awt.image.BufferedImage;

class ImageFrame extends JFrame {
  public ImageFrame(String title, BufferedImage image) {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(image.getWidth(), image.getHeight());
    add(new ImagePanel(image));
    setLocationByPlatform(true);
    setVisible(true);
  }
}
