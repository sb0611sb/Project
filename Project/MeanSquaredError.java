public class MeanSquaredError implements LossFunction {

    @Override
    public double loss(double[] target, double[] output) {
        if (target.length != output.length) {
            throw new IllegalArgumentException("타깃과 출력의 길이가 다릅니다.");
        }
        double sum = 0.0;
        for (int i = 0; i < target.length; i++) {
            double diff = output[i] - target[i]; 
            sum += diff * diff;
        }
        return 0.5 * sum; 
    }

    @Override
    public double[] derivative(double[] target, double[] output) {
        if (target.length != output.length) {
            throw new IllegalArgumentException("타깃과 출력의 길이가 다릅니다.");
        }
        double[] grad = new double[target.length];
        for (int i = 0; i < target.length; i++) {
            grad[i] = output[i] - target[i]; 
        }
        return grad;
    }
}
