package likz;

import static likz.MathFunc.*;

public class Perceptron {

    int input_nodes;
    int hidden_nodes;
    int output_nodes;
    int epochs;

    double lr;
    double[][] output;

    double[][] synapse0;
    double[][] synapse1;

    Perceptron(int input, int hidden, int output) {

        this.input_nodes = input;
        this.hidden_nodes = hidden;
        this.output_nodes = output;

        this.epochs = 5000;
        //this.activation = Functions.sigmoid();
        this.lr = .5;

        synapse0 = new double[input_nodes][hidden_nodes];
        synapse1 = new double[hidden_nodes][output_nodes];

        //generate synapses [-1;1] random values
        for (int i = 0; i < input_nodes; i++) {
            for (int j = 0; j < hidden_nodes; j++) {
                synapse0[i][j] = (Math.random() * 2) - 1;
            }
        }

        for (int i = 0; i < hidden_nodes; i++) {
            for (int j = 0; j < output_nodes; j++) {
                synapse1[i][j] = (Math.random()) - 0.5;
            }
        }
    }

    public void setEpochs(int numEpochs) {
        this.epochs = numEpochs;
    }

    public void setLearningRate(double lr) {
        this.lr = lr;
    }

    public void train(double[][] input, double[][][] target) {
        for (int k = 0; k < target.length; k++)
            for (int i = 0; i < epochs; i++) {

                double[][] synapse1_k = new double[synapse1.length][1];
                for (int S = 0; S < synapse1_k.length; S++) {
                    synapse1_k[S][0] = synapse1[S][k];
                }

                double[][] input_layer = input.clone();
                double[][] hidden_layer_logics = multiplyMatrix(input_layer, synapse0);
                //исполнение activate
                double[][] hidden_layer_activated = activate(hidden_layer_logics.clone(), false);
                double[][] output_layer_logics = multiplyMatrix(hidden_layer_activated, synapse1_k);
                //исполнение activate
                double[][] output_layer_activated = activate(output_layer_logics.clone(), false);

                double[][] output_error = substract(target[k], output_layer_activated);

                //исполнение activate
                double[][] output_layer_logics_clone = activate(output_layer_logics.clone(), true);
                double[][] output_delta = dotMultiply(output_error, output_layer_logics_clone);
                double[][] hidden_error = multiplyMatrix(output_delta, transposeMatrix(synapse1_k));
                //исполнение activate
                double[][] hidden_layer_logics_clone = activate(hidden_layer_logics.clone(), true);
                double[][] hidden_delta = dotMultiply(hidden_error, hidden_layer_logics_clone);

                double[][] output_delta_lr = activateLR(output_delta.clone(), lr);
                double[][] hidden_delta_lr = activateLR(hidden_delta.clone(), lr);

                synapse0 = add(synapse0, multiplyMatrix(transposeMatrix(input_layer), hidden_delta_lr));
                synapse1_k = add(synapse1_k, multiplyMatrix(transposeMatrix(hidden_layer_activated), output_delta_lr));
                output = output_layer_activated;

                for (int S = 0; S < synapse1_k.length; S++) {
                    synapse1[S][k] = synapse1_k[S][0];
                }

                if (i % 10001 == 0) {
                    System.out.println("avg error : " + averageMatrixValue(output_error));
                }
            }

    }

    public double[][] predict(double[][] input) {
        double[][] input_layer = input.clone();

        double[][] hidden_layer = multiplyMatrix(input_layer, synapse0);
        hidden_layer = activate(hidden_layer.clone(), false);

        double[][] output_layer = multiplyMatrix(hidden_layer, synapse1);
        output_layer = activate(output_layer.clone(), false);

        return output_layer;
    }

    public static double[][] activate(double[][] m, boolean derivative) {
        double[][] res = new double[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = ActivateFunctions.sigmoid(m[i][j], derivative);
            }
        }
        return res;
    }

    public static double[][] activateLR(double[][] m, double lr) {
        double[][] res = new double[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                res[i][j] = m[i][j] * lr;
            }
        }
        return res;
    }

}
