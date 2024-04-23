package likz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LocalTest {
    //static Function<Double, Double> sgn = x -> (double) (x < 0 ? -1 : +1);

    public static void main(String[] args) {

        String zero = """
                XXXXXXXX
                X______X
                X______X
                X______X
                X______X
                X______X
                X______X
                XXXXXXXX
                """;
        String one = """
                ____X___
                ____X___
                ____X___
                ____X___
                ____X___
                ____X___
                ____X___
                ____X___
                """;
        String two = """
                XXXXXXXX
                _______X
                _______X
                XXXXXXXX
                X_______
                X_______
                XXXXXXXX
                ________
                """;
        String three = """
                ________
                XXXXXXXX
                _______X
                _______X
                XXXXXXXX
                _______X
                _______X
                XXXXXXXX
                """;

        int[] bipolar_0 = bi_vec_pattern2(zero);
        int[] bipolar_1 = bi_vec_pattern2(one);
        int[] bipolar_2 = bi_vec_pattern2(two);
        int[] bipolar_3 = bi_vec_pattern2(three);

        visualize2(bipolar_0);
        visualize2(bipolar_1);
        visualize2(bipolar_2);
        visualize2(bipolar_3);

        int[][] sample_patterns = new int[][]{bipolar_0, bipolar_1, bipolar_2, bipolar_3};
        double[][] T = network_train(sample_patterns);

        String zero_test_1 = """
                XXXXXXX_
                X______X
                X___X___
                X_______
                X______X
                _______X
                X______X
                XXXXXX_X
                """;

        String one_test_1 = """
                ___X_X__
                _X_X_X__
                ___X____
                ___X__X_
                ___X_X__
                _X_X____
                __XX____
                ___X____
                """;

        String two_test_1 = """
                XXXXXXXX
                ______X_
                _______X
                X__XXXXX
                X_______
                X_______
                XXXXXXXX
                ________
                """;

        String three_test_1 = """
                ________
                X___XXXX
                _______X
                _______X
                X_XXXXXX
                _______X
                __X____X
                XX_XXXXX
                """;

        int[][] test_patterns = new int[][]{bi_vec_pattern2(zero_test_1),
                bi_vec_pattern2(one_test_1),
                bi_vec_pattern2(two_test_1),
                bi_vec_pattern2(three_test_1)};

        for (int i = 0; i < test_patterns.length; i++) {
            visualize2(test_patterns[i]);
        }

        int[][] result_1 = classify(T, test_patterns, 1);
        int[][] result_2 = classify(T, test_patterns, 2);
        int[][] result_3 = classify(T, test_patterns, 3);

        for (int[] part : result_1) {
            visualize2(part);
        }
        for (int[] part : result_2) {
            visualize2(part);
        }
        for (int[] part : result_3) {
            visualize2(part);
        }

    }

    static int[] bi_vec_pattern(String numeral) {
        String numeralWithoutNewLine = numeral.replace("\n", "");
        int[] result = new int[numeralWithoutNewLine.length()];
        for (int i = 0; i < numeralWithoutNewLine.length(); i++) {
            if (numeralWithoutNewLine.charAt(i) == 'X') {
                result[i] = 1;
            } else {
                result[i] = -1;
            }
        }
        return result;
    }

    static void visualize(int[] pattern) {
        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < pattern.length; i++) {
            int x = i % 8;
            int y = i / 8;
            int color = pattern[i] == 0 ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
            image.setRGB(x, y, color);
        }
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }

    static int[] bi_vec_pattern2(String numeral) {
        String[] arr = numeral.replace("\n", "").split("");
        return IntStream.range(0, arr.length)
                .map(i -> arr[i].equals("X") ? 1 : -1)
                .toArray();
    }

    static void visualize2(int[] pattern) {
        int[][] reshaped = Stream.iterate(0, i -> i + 8)
                .limit(8)
                .map(i -> Arrays.copyOfRange(pattern, i, i + 8))
                .toArray(int[][]::new);
        for (int[] row : reshaped) {
            System.out.println(Arrays.stream(row)
                    .mapToObj(i -> i == 1 ? "+" : "-")
                    .collect(Collectors.joining(" ")));
        }
        System.out.println();
    }

    static double[][] network_train(int[][] sample_patterns) {
        int i = sample_patterns[0].length;
        int j = sample_patterns.length;
        double[][] T = new double[i][i];

        for (int k = 0; k < sample_patterns.length; k++) {
            int[] s = sample_patterns[k];
            for (int m = 0; m < s.length; m++) {
                for (int n = 0; n < s.length; n++) {
                    T[m][n] += s[m] * s[n];
                }
            }
        }

        for (int m = 0; m < i; m++) {
            T[m][m] = 0;
        }

        for (int m = 0; m < i; m++) {
            for (int n = 0; n < i; n++) {
                T[m][n] /= j;
            }
        }

        return T;
    }

//    double[][] network_train(double[][] sample_patterns) {
//        int i = sample_patterns[0].length;
//        int j = sample_patterns.length;
//        double[][] T = new double[i][i];
//        for (double[] s : sample_patterns) {
//            for (int k = 0; k < i; k++) {
//                for (int l = 0; l < i; l++) {
//                    T[k][l] += s[k] * s[l];
//                }
//            }
//        }
//        IntStream.range(0, i).forEach(k -> T[k][k] = 0);
//        return Arrays.stream(T).map(row -> Arrays.stream(row).map(val -> val / j).toArray()).toArray(double[][]::new);
//    }

    static int sgn(double x) {
        return x < 0 ? -1 : 1;
    }

    static int[][] classify(double[][] T, int[][] test_patterns, int iteration_steps) {
        int[][] result = test_patterns;
        for (int i = 0; i < iteration_steps; i++) {
            int[][] finalResult = result;
            result = IntStream.range(0, result.length).mapToObj(k -> {
                int[] row = new int[finalResult[0].length];
                for (int l = 0; l < finalResult[0].length; l++) {
                    int finalL = l;
                    row[l] = sgn(IntStream.range(0, finalResult[0].length).mapToDouble(m -> finalResult[k][m] * T[m][finalL]).sum());
                }
                return row;
            }).toArray(int[][]::new);
        }
        return result;
    }

//    double[][] classify(double[][] T, double[][] test_patterns, int iteration_steps) {
//        for (int i = 0; i < iteration_steps; i++) {
//            test_patterns = Arrays.stream(test_patterns)
//                    .map(pattern -> sgn.apply(Arrays.stream(pattern)
//                            .reduce(0.0, (acc, val) -> acc + val * T[i])))
//                    .toArray(double[][]::new);
//        }
//        return test_patterns;
//    }

//    static int[] classify(double[][] T, int[] test_patterns, int iteration_steps) {
//        for (int i = 0; i < iteration_steps; i++) {
//            int[] temp = new int[test_patterns.length];
//            for (int j = 0; j < test_patterns.length; j++) {
//                int sum = 0;
//                for (int k = 0; k < test_patterns.length; k++) {
//                    sum += test_patterns[k] * T[k][j];
//                }
//                temp[j] = (sum < 0) ? -1 : 1;
//            }
//            test_patterns = temp;
//        }
//        return test_patterns;
//    }

}