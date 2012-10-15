package ee.ut.jf.forkjoin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {
  private static AtomicBoolean visited = new AtomicBoolean(false);

  public static void main(String[] args) throws Exception {
    File file = getFile(args);

    BufferedImage image = ImageIO.read(file);

    WindowAdapter adapter = new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        JFrame frame = (JFrame) e.getSource();
        if (visited.compareAndSet(false, true))
          frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        else
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }
    };

    new ImageFrame("Original", image).addWindowListener(adapter);

    BufferedImage mirroredImage = mirror(image);

    new ImageFrame("Mirrored", mirroredImage).addWindowListener(adapter);
  }

  public static File getFile(String[] args) {
    if (args.length == 0)
      exit();

    File file = new File(args[0]);

    if (!file.exists())
      exit();

    return file;
  }

  public static void exit() {
    System.out.println("Please provide a correct filename.");
    System.exit(1);
  }

  public static BufferedImage mirror(BufferedImage srcImage) {
    int w = srcImage.getWidth();
    int h = srcImage.getHeight();

    // Each worker modifies a separate area, but we still need visibility for results
    int[] src =
      srcImage.getRGB(0, 0, w, h, null, 0, w);

    System.out.println("Array size is " + src.length);

    int processors = Runtime.getRuntime().availableProcessors();
    System.out.println(Integer.toString(processors) + " processor" +
      (processors != 1 ? "s are " : " is ") +
      "available");

    ForkMirror fm = new ForkMirror(src, 0, src.length - 1, w);

    ForkJoinPool pool = new ForkJoinPool();

    long startTime = System.currentTimeMillis();
    pool.invoke(fm);
    long endTime = System.currentTimeMillis();

    System.out.println("Image mirror took " + (endTime - startTime) + " milliseconds.");

    BufferedImage dstImage =
      new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    dstImage.setRGB(0, 0, w, h, src, 0, w);

    return dstImage;
  }

}
