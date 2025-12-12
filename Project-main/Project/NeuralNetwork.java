import java.util.ArrayList;
import java.util.List;


public class NeuralNetwork {

    private final List<DenseLayer> layers = new ArrayList<>();
    private final LossFunction lossFunction;

    public NeuralNetwork(LossFunction lossFunction) {
        this.lossFunction = lossFunction;
    }

    public void addLayer(DenseLayer layer) {
        layers.add(layer);
    }

    
    public double[] forward(double[] input) {
        double[] output = input;
        for (DenseLayer layer : layers) {
            output = layer.forward(output);
        }
        return output;
    }

   
    public void train(double[][] inputs, double[][] targets, int epochs) {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException("입력 샘플 수와 타깃 샘플 수가 다릅니다.");
        }

        int nSamples = inputs.length;

        for (int epoch = 1; epoch <= epochs; epoch++) {
            double totalLoss = 0.0;

            for (int n = 0; n < nSamples; n++) {
                double[] x = inputs[n];
                double[] t = targets[n];

                // 순전파
                double[] y = forward(x);

                // 손실 계산
                double loss = lossFunction.loss(t, y);
                totalLoss += loss;

                // 손실에 대한 출력 기울기
                double[] grad = lossFunction.derivative(t, y); 

                // 역전파
                for (int layerIndex = layers.size() - 1; layerIndex >= 0; layerIndex--) {
                    grad = layers.get(layerIndex).backward(grad);
                }
            }

            double avgLoss = totalLoss / nSamples;

            if (epoch == 1 || epoch % 500 == 0 || epoch == epochs) {
                System.out.printf("Epoch %d, 평균 손실: %.6f%n", epoch, avgLoss);
            }
        }
    }
}
