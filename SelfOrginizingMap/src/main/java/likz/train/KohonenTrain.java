package likz.train;

import likz.train.other.*;

public class KohonenTrain {

    private int numIterations;
    private double learnRate;

    public KohonenTrain(int numIterations, double learnRate) {
        this.numIterations = numIterations;
        this.learnRate = learnRate;
    }

    static class NodeRowCol {
        public int row;
        public int col;

        public NodeRowCol(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static double calcDistance(double[] w1, double[] w2) {
        double distance = 0d;

        for (int i = 0; i < w1.length; i++) {
            double diff = w1[i] - w2[i];
            distance += diff * diff;
        }

        return Math.sqrt(distance);
    }

    private NodeRowCol findBestMatchingUnit(FeatureMatrix matrix, double[] featureWeights) {
        NodeRowCol nrc = new NodeRowCol(0, 0);
        double nrcDistance = calcDistance(matrix.getFeatureVector(0, 0).getWeights(), featureWeights);

        int rows = matrix.getRows();
        int cols = matrix.getCols();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                FeatureVector fv = matrix.getFeatureVector(r, c);
                double featureDistance = calcDistance(fv.getWeights(), featureWeights);
                if (featureDistance < nrcDistance) {
                    nrc.row = r;
                    nrc.col = c;
                    nrcDistance = featureDistance;
                }
            }
        }

        return nrc;
    }

    public void train(FeatureMatrix matrix, ITrainSet trainSet, ITrainObserver observer) {
        int rows = matrix.getRows();
        int cols = matrix.getCols();
        double mapRadius = Math.max(rows, cols) / 2;
        double timeConstant = numIterations / Math.log(mapRadius);
        int iteration = 0;

        while (iteration < numIterations) {
            double neighbourHoodRadius = mapRadius * Math.exp(-(double) iteration / timeConstant);
            double neighbourHoodRadiusSqrd = neighbourHoodRadius * neighbourHoodRadius;

            trainSet.reset();

            double[] trainingWeights;
            while ((trainingWeights = trainSet.next()) != null) {

                NodeRowCol bestMatchingUnit = findBestMatchingUnit(matrix, trainingWeights);

                int rStart = Math.max(0, (int) (bestMatchingUnit.row - neighbourHoodRadius - 1.0d));
                int cStart = Math.max(0, (int) (bestMatchingUnit.col - neighbourHoodRadius - 1.0d));
                int rEnd = Math.min(rows, (int) (bestMatchingUnit.row + neighbourHoodRadius + 1.0d));
                int cEnd = Math.min(cols, (int) (bestMatchingUnit.col + neighbourHoodRadius + 1.0d));

                if (observer != null) {
                    observer.foundBestMatchingUnit(bestMatchingUnit.row, bestMatchingUnit.col, neighbourHoodRadius);
                }

                for (int r = rStart; r < rEnd; r++) {
                    for (int c = cStart; c < cEnd; c++) {
                        double rDelta = bestMatchingUnit.row - r;
                        double cDelta = bestMatchingUnit.col - c;
                        double distanceToNodeSqrd = rDelta * rDelta + cDelta * cDelta;

                        if (distanceToNodeSqrd <= neighbourHoodRadiusSqrd) {
                            double influence = Math.exp(-(distanceToNodeSqrd) / (2 * neighbourHoodRadiusSqrd));
                            matrix.getFeatureVector(r, c).adjustWeights(trainingWeights, learnRate, influence);
                        }
                    }
                }
            }

            iteration++;
            learnRate = learnRate * Math.exp(-(double) iteration / numIterations);

            if (observer != null) {
                observer.iterationComplete();
            }
        }
    }
}
