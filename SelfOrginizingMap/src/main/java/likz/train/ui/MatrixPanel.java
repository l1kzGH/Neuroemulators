package likz.train.ui;

import likz.train.other.FeatureMatrix;

import java.awt.*;

public class MatrixPanel extends Canvas {
    private FeatureMatrix matrix;

    public MatrixPanel(FeatureMatrix matrix) {
        this.matrix = matrix;
        setSize(500, 500);
        setBackground(Color.LIGHT_GRAY);
    }

    public void paint(){
        paint(getGraphics());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (matrix == null) {
            return;
        }

        int cellWidth = getSize().width / matrix.getCols();
        int cellHeight = getSize().height / matrix.getRows();

        if (cellWidth * matrix.getCols() < getSize().width || cellHeight * matrix.getRows() < getSize().height) {
            System.err.println("not full window MatrixPanel");
        }

        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                double[] weights = matrix.getFeatureVector(i, j).getWeights();
                g.setColor(new Color((int) (weights[0] * 255), (int) (weights[1] * 255), (int) (weights[2] * 255)));
                g.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
            }
        }
    }

    public void drawCircle(int row, int col, double radius) {
        int cellWidth = getSize().width / matrix.getCols();
        int cellHeight = getSize().height / matrix.getRows();
        int r = (int) radius;

        int x = (col - r) * cellWidth;
        int y = (row - r) * cellHeight;
        int w = r * 2 * cellWidth;
        int h = r * 2 * cellHeight;

        getGraphics().setColor(Color.RED);
        getGraphics().drawOval(x, y, w, h);
    }

    public void drawSquare(int row, int col, int width, int height) {
        int cellWidth = getSize().width / matrix.getCols();
        int cellHeight = getSize().height / matrix.getRows();

        getGraphics().setColor(Color.RED);
        getGraphics().fillRect(col * cellWidth - cellWidth / 2, row * cellHeight - cellHeight / 2, width * cellWidth, height * cellHeight);
    }

}
