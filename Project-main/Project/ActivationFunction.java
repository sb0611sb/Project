public interface ActivationFunction {
    /**
     * 활성화 함수
     */
    double activate(double x);

    /**
     * 활성화 함수 도함수
     */
    double derivative(double activatedValue);
}
