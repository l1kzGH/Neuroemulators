package likz.train.other;


public interface ITrainSet {

    int getNumFeatures();

    double[] next();

    void reset();

}
