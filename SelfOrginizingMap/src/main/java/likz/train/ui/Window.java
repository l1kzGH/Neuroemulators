package likz.train.ui;

import likz.train.KohonenTrain;
import likz.train.other.FeatureMatrix;
import likz.train.other.ITrainSet;

import javax.swing.*;

public class Window extends JFrame {

    final static private int WIDTH = 600;
    final static private int HEIGHT = 600;

    public Window(FeatureMatrix matrix, ITrainSet trainSet, KohonenTrain trainer) {
        this.add(new DrawPanel(matrix, trainSet, trainer));

        setSize(WIDTH, HEIGHT);
        setTitle("som");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setVisible(true);
    }


}
