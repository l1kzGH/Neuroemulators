package likz.train.ui;

import likz.train.KohonenTrain;
import likz.train.other.FeatureMatrix;
import likz.train.other.ITrainObserver;
import likz.train.other.ITrainSet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawPanel extends JPanel implements ActionListener {

    final private FeatureMatrix matrix;
    final private ITrainSet trainSet;
    final private KohonenTrain trainer;
    final private MatrixPanel matrixPanel;

    private JButton trainButton;

    public DrawPanel(FeatureMatrix matrix, ITrainSet trainSet, KohonenTrain trainer) {
        this.matrix = matrix;
        this.trainSet = trainSet;
        this.trainer = trainer;

        trainButton = new JButton("Train");
        trainButton.addActionListener(this);
        add(trainButton);

        matrixPanel = new MatrixPanel(matrix);
        add(matrixPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == trainButton) {

            trainer.train(matrix, trainSet, new ITrainObserver() {
                public void iterationComplete() {
                    matrixPanel.paint();
                    sleep(100);
                }

                public void foundBestMatchingUnit(int row, int col, double neighbourHoodRadius) {
                    matrixPanel.drawCircle(row, col, neighbourHoodRadius);
                    matrixPanel.drawSquare(row, col, 1, 1);
                }
            });

        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }


}
