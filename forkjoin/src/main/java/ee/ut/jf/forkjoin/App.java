package ee.ut.jf.forkjoin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class App {

  public static void main(String[] args) throws Exception {
    File file = getFile(args);

    BufferedImage image = ImageIO.read(file);

    new ImageFrame("Original", image);

    BufferedImage mirroredImage = mirror(image);

    new ImageFrame("Mirrored", mirroredImage);
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

    int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);

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
