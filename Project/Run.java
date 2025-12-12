public class Run {

    public static void main(String[] args) {
        int inputSize = 2;
        int hiddenSize = 4;
        int outputSize = 1;
        double learningRate = 0.5;
        double momentum = 0.9;
        int epochs = 100000;

        ActivationFunction sigmoid = new SigmoidActivation();
        LossFunction mse = new MeanSquaredError();

        NeuralNetwork nn = new NeuralNetwork(mse);

        
        nn.addLayer(new DenseLayer(inputSize, hiddenSize, sigmoid, learningRate, momentum));
        nn.addLayer(new DenseLayer(hiddenSize, outputSize, sigmoid, learningRate, momentum));

        // XOR 데이터셋
        double[][] inputs = {
                {0.0, 0.0},
                {0.0, 1.0},
                {1.0, 0.0},
                {1.0, 1.0}
        };

        double[][] targets = {
                {0.0},
                {1.0},
                {1.0},
                {0.0}
        };

        System.out.println("=== 학습 시작 ===");
        nn.train(inputs, targets, epochs);
        System.out.println("=== 학습 종료 ===\n");

        System.out.println("=== XOR 테스트 ===");
        for (int i = 0; i < inputs.length; i++) {
            double[] x = inputs[i];
            double[] y = nn.forward(x);
            System.out.printf("입력: (%.1f, %.1f) -> 출력: %.5f%n", x[0], x[1], y[0]);
        }
    }
}

