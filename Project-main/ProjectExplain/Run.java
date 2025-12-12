public class Run {               
    // main 메서드를 가진 실행 클래스
    public static void main(String[] args) {  
        // 파라미터 설정
        int inputSize = 2;                     
        int hiddenSize = 4;
        int outputSize = 1;
        double learningRate = 0.5;             
        double momentum = 0.9;
        int epochs = 10000;
        // XOR 문제: 입력 2개(0/1), 은닉층 노드 4개, 출력 1개

        ActivationFunction sigmoid = new SigmoidActivation(); 
        // 인터페이스 타입 변수에 구현체를 대입
        // 사용할 활성화 함수로 시그모이드 선택

        LossFunction mse = new MeanSquaredError();            
        
        NeuralNetwork nn = new NeuralNetwork(mse);            
        // 생성자에 mse 전달

        nn.addLayer(new DenseLayer(inputSize, hiddenSize, sigmoid, learningRate, momentum));
        // new DenseLayer로 객체 생성
      
        nn.addLayer(new DenseLayer(hiddenSize, outputSize, sigmoid, learningRate, momentum));

        
        // XOR 데이터셋
        double[][] inputs = {
            {0.0, 0.0},
            {0.0, 1.0},
            {1.0, 0.0},
            {1.0, 1.0}
    };                                      
   
    // 정답 데이터셋
    double[][] targets = {
            {0.0},
            {1.0},
            {1.0},
            {0.0}
    };
    
    System.out.println("=== 학습 시작 ==="); 
    nn.train(inputs, targets, epochs);      
    // train 호출로 학습 시작

    System.out.println("=== 학습 종료 ===\n");
    System.out.println("=== XOR 테스트 ===");
    for (int i = 0; i < inputs.length; i++) {   
        double[] x = inputs[i];
        double[] y = nn.forward(x);             
  
        System.out.printf("입력: (%.1f, %.1f) -> 출력: %.5f%n", x[0], x[1], y[0]);

    }
}
}
