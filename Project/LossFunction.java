public interface LossFunction {
    /**
     * 단일 손실값
     */
    double loss(double[] target, double[] output);

    /**
     * 출력손실의 기울기
     */
    double[] derivative(double[] target, double[] output);
}
