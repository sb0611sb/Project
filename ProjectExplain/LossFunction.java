public interface LossFunction {                     
    // [Java] 손실 함수용 인터페이스. 다양한 손실(MSE, CrossEntropy 등)을 같은 타입으로 다루기 위함.
    // [NN] “출력과 정답의 차이를 숫자로 표현하는 방법”을 추상화. 학습의 목표를 정의.
    /**
     * 단일 샘플에 대한 손실값
     */
    double loss(double[] target, double[] output);  
    // [Java] double[] 배열 두 개를 인자로 받아 double(스칼라) 하나를 반환.
    // [NN] target(정답)과 output(모델 출력)을 비교해서 “얼마나 틀렸는지”를 하나의 값으로 계산(MSE 등).
    /**
     * 출력에 대한 손실의 기울기 dL/d(output)
     */
    double[] derivative(double[] target, double[] output);
    // [Java] 출력 배열과 정답 배열을 받아, 각 출력 원소에 대한 손실의 기울기를 배열로 반환.
    // [NN] 역전파 시작점: 출력 레이어에서 dL/dy(각 출력 뉴런에 대한 기울기)를 여기서 얻어 감.
}
    // [Java] 인터페이스 끝.
    // [NN] 이 인터페이스 덕분에 네트워크는 LossFunction 타입만 알고, 구체 손실(MSE, CE)은 몰라도 됨.
