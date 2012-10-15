package ee.ut.jf.forkjoin;

import java.util.concurrent.RecursiveAction;

public class ForkMirror extends RecursiveAction {
  private int[] mSource;
  private int mStart;
  private int mWidth;
  private int mEnd;

  public ForkMirror(int[] src, int start, int end, int width) {
    mSource = src;
    mStart = start;
    mWidth = width;
    mEnd = end;
  }

  protected void computeDirectly() {

    for (int index = mStart; index < mEnd; index++) {
      if (index % mWidth > mWidth / 2) {

        int c = mWidth - index % mWidth - 1;
        int mIndex = index / mWidth * mWidth + c;

        int pixel = mSource[index];
        int mirror = mSource[mIndex];

        mSource[index] = average(pixel, mirror);
      }
    }
  }

  protected int average(int pixel, int mirror) {
    float rt = 0, gt = 0, bt = 0, rtm = 0, gtm = 0, btm = 0;

    rt = (float) ((pixel & 0x00ff0000) >> 16);
    gt = (float) ((pixel & 0x0000ff00) >> 8);
    bt = (float) ((pixel & 0x000000ff) >> 0);

    rtm = (float) ((mirror & 0x00ff0000) >> 16);
    gtm = (float) ((mirror & 0x0000ff00) >> 8);
    btm = (float) ((mirror & 0x000000ff) >> 0);

    int dpixel = (0xff000000) |
      (((int) ((rt + rtm) / 2)) << 16) |
      (((int) ((gt + gtm) / 2)) << 8) |
      (((int) ((bt + btm) / 2)) << 0);

    return dpixel;
  }

  private static int threshold = 100000;

  protected void compute() {
    if (threshold > mEnd - mStart) {
      computeDirectly();
      return;
    }

    int split = (mEnd - mStart) / 2;
    invokeAll(new ForkMirror(mSource, mStart, mStart + split, mWidth),
      new ForkMirror(mSource, mStart + split + 1, mEnd, mWidth));
  }

}

