package likz;

public class Main {

    public static void main(String[] args) {

        double[][] input = {
                {1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1},
                {0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
                {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
                {1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1}
        };

        double[][][] extdTarget = {
                {{1}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
                {{0}, {1}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
                {{0}, {0}, {1}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
                {{0}, {0}, {0}, {1}, {0}, {0}, {0}, {0}, {0}, {0}},
                {{0}, {0}, {0}, {0}, {1}, {0}, {0}, {0}, {0}, {0}},
                {{0}, {0}, {0}, {0}, {0}, {1}, {0}, {0}, {0}, {0}},
                {{0}, {0}, {0}, {0}, {0}, {0}, {1}, {0}, {0}, {0}},
                {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {1}, {0}, {0}},
                {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {1}, {0}},
                {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {1}},
        };

        //double[][] target = {{0}, {0}, {0}, {0}, {1}, {0}, {0}, {0}, {0}, {0}};

        double[][] maybe_six = {{
                1, 1, 0,
                1, 0, 0,
                1, 1, 0,
                1, 0, 1,
                0, 1, 1
        }};

        Perceptron perceptron = new Perceptron(15, 16, 10);
        perceptron.setLearningRate(0.5);

        perceptron.train(input, extdTarget);
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " : " + perceptron.predict(maybe_six)[0][i]);
        }

//        Perceptron p0 = new Perceptron(15, 16, 1);
//        Perceptron p1 = new Perceptron(15, 16, 1);
//        Perceptron p2 = new Perceptron(15, 16, 1);
//        Perceptron p3 = new Perceptron(15, 16, 1);
//        Perceptron p4 = new Perceptron(15, 16, 1);
//        Perceptron p5 = new Perceptron(15, 16, 1);
//        Perceptron p6 = new Perceptron(15, 16, 1);
//        Perceptron p7 = new Perceptron(15, 16, 1);
//        Perceptron p8 = new Perceptron(15, 16, 1);
//        Perceptron p9 = new Perceptron(15, 16, 1);
//
//        p0.train(input, extdTarget[0]);
//        p1.train(input, extdTarget[1]);
//        p2.train(input, extdTarget[2]);
//        p3.train(input, extdTarget[3]);
//        p4.train(input, extdTarget[4]);
//        p5.train(input, extdTarget[5]);
//        p6.train(input, extdTarget[6]);
//        p7.train(input, extdTarget[7]);
//        p8.train(input, extdTarget[8]);
//        p9.train(input, extdTarget[9]);
//
//        System.out.println("0 = " + p0.predict(test)[0][0]);
//        System.out.println("1 = " + p1.predict(test)[0][0]);
//        System.out.println("2 = " + p2.predict(test)[0][0]);
//        System.out.println("3 = " + p3.predict(test)[0][0]);
//        System.out.println("4 = " + p4.predict(test)[0][0]);
//        System.out.println("5 = " + p5.predict(test)[0][0]);
//        System.out.println("6 = " + p6.predict(test)[0][0]);
//        System.out.println("7 = " + p7.predict(test)[0][0]);
//        System.out.println("8 = " + p8.predict(test)[0][0]);
//        System.out.println("9 = " + p9.predict(test)[0][0]);

    }


}
