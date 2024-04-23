package likz.train;


import likz.train.other.ITrainSet;

public class MemoryTrainSet implements ITrainSet {
  protected double[][] weights;
  private int nextRow = 0;

  public MemoryTrainSet(int rows, int cols) {
    this(new double[rows][cols]);
  }

  public MemoryTrainSet(double[][] weights) {
    this.weights = weights;
  }

  @Override
  public double[] next() {
    if (nextRow >= weights.length) {
      return null;
    }

    return weights[nextRow++];
  }

  @Override
  public void reset() {
    nextRow = 0;
  }

  @Override
  public int getNumFeatures() {
    return weights[0].length;
  }
}
