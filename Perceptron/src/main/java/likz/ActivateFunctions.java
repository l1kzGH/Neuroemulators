package likz;

public class ActivateFunctions {

    static public double sigmoid(double x, boolean derivative){
        double fx = 1 / (1 + Math.exp(-x));
        if (derivative)
            return fx * (1 - fx);
        return fx;
    }

//    static public double tanh(double x, boolean derivative){
//        double fx = 2 / (1 + Math.exp(-2 * x)) - 1;
//        if (derivative)
//            return 1 - fx * fx;
//        return fx;
//    }
//
//    static public double relu(double x, boolean derivative){
//        if (derivative)
//            return x < 0 ? 0 : 1;
//        return x < 0 ? 0 : x;
//    }
//
//    static public double softPlus(double x, boolean derivative){
//        if (derivative)
//            return 1 / (1 + Math.exp(-x));
//        return Math.log(1 + Math.exp(x));
//    }

}
