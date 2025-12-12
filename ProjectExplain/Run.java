public class Run {               
    // [Java] main 메서드를 가진 실행용 클래스.
    // [NN] XOR 데이터로 우리가 만든 신경망을 실제로 학습/테스트하는 예제.
    public static void main(String[] args) {   
        // [Java] 자바 프로그램의 진입점. static: 객체 생성 없이 호출 가능.
        // [NN] 여기서 네트워크 구조를 만들고, 데이터를 준비하고, 학습을 호출.
        // 하이퍼파라미터
        int inputSize = 2;                     
        int hiddenSize = 4;
        int outputSize = 1;
        double learningRate = 0.5;             
        double momentum = 0.9;
        int epochs = 10000;
        // [Java] 지역 변수들. 기본 타입(int, double).
        // [NN] XOR 문제: 입력 2개(0/1), 은닉층 노드 4개, 출력 1개.  
        //      학습률/모멘텀/에폭 수는 최적화 알고리즘의 동작에 큰 영향을 줌.
        ActivationFunction sigmoid = new SigmoidActivation(); 
        // [Java] 인터페이스 타입 변수에 구현체를 대입 → 다형성.
        // [NN] 네트워크 레이어들이 사용할 활성화 함수로 시그모이드 선택.
        LossFunction mse = new MeanSquaredError();            
        // [NN] 손실 함수로 MSE 선택(XOR 같은 작은 문제에 충분).
        NeuralNetwork nn = new NeuralNetwork(mse);            
        // [Java] 생성자에 mse 전달(의존성 주입).
        // [NN] 이 네트워크는 MSE 기준으로 가중치를 조정하게 됨.
        // 2 -> 4 -> 1 구조의 MLP
        nn.addLayer(new DenseLayer(inputSize, hiddenSize, sigmoid, learningRate, momentum));
        // [Java] new DenseLayer(...)로 객체 생성 후 addLayer로 리스트에 추가.
        // [NN] 입력층(2) → 은닉층(4)로 가는 DenseLayer. 활성화는 시그모이드, 옵티마이저는 SGD+모멘텀.
        nn.addLayer(new DenseLayer(hiddenSize, outputSize, sigmoid, learningRate, momentum));
        // [NN] 은닉층(4) → 출력층(1)으로 가는 DenseLayer.  
        //     이렇게 두 레이어가 합쳐져 2-4-1 MLP가 됨.
        // XOR 데이터셋
        double[][] inputs = {
            {0.0, 0.0},
            {0.0, 1.0},
            {1.0, 0.0},
            {1.0, 1.0}
    };                                      
    // [Java] 2차원 배열 리터럴 초기화. double[][] 타입.
    // [NN] XOR 문제 입력: (0,0), (0,1), (1,0), (1,1).

    double[][] targets = {
            {0.0},
            {1.0},
            {1.0},
            {0.0}
    };
    // [NN] XOR 타깃: 0 XOR 0 = 0, 0 XOR 1 = 1, 1 XOR 0 = 1, 1 XOR 1 = 0.
    System.out.println("=== 학습 시작 ==="); 
    nn.train(inputs, targets, epochs);      
    // [Java] train 호출로 학습 시작.
    // [NN] 위에서 정의한 순전파/역전파/모멘텀 업데이트가 에폭 동안 반복되며 XOR 패턴을 학습.
    System.out.println("=== 학습 종료 ===\n");
    System.out.println("=== XOR 테스트 ===");
    for (int i = 0; i < inputs.length; i++) {   
        double[] x = inputs[i];
        double[] y = nn.forward(x);             
        // [Java] 학습된 네트워크에 대해 forward만 호출(역전파 X).
        // [NN] inference 단계: 학습된 가중치로 XOR 결과를 예측.
        System.out.printf("입력: (%.1f, %.1f) -> 출력: %.5f%n", x[0], x[1], y[0]);
        // [Java] printf로 포맷된 출력. %.1f: 소수 첫째자리, %.5f: 소수 다섯째 자리까지.
        // [NN] 네트워크가 (0,0),(0,1),(1,0),(1,1)에 대해 0/1에 가깝게 출력하는지 확인.
    }
}
}
// [Java] XorExample 클래스 끝.
// [NN] 이 파일만 실행하면 전체 신경망 코드가 동작하고, XOR 문제 학습 결과를 볼 수 있음.
