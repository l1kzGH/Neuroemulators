package likz;

import likz.train.KohonenTrain;
import likz.train.MainColorTrainSet;
import likz.train.other.FeatureMatrix;
import likz.train.other.ITrainSet;
import likz.train.ui.Window;

public class Start {

    private static final int NUM_ITERATIONS = 100;
    private static final double LEARN_RATE = 0.3;
    private static final int WIDTH = 50, HEIGHT = 50;

    public static void main(String[] args) {

        ITrainSet trainSet = new MainColorTrainSet();

        KohonenTrain trainer = new KohonenTrain(NUM_ITERATIONS, LEARN_RATE);
        FeatureMatrix matrix = new FeatureMatrix(WIDTH, HEIGHT, trainSet.getNumFeatures());
        new Window(matrix, trainSet, trainer);

    }


}
