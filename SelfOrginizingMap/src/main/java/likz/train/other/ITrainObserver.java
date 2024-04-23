package likz.train.other;


public interface ITrainObserver {

    void foundBestMatchingUnit(int row, int col, double neighborHoodRadius);

    void iterationComplete();

}
