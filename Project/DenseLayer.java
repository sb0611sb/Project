import java.util.Random;


public class DenseLayer {

    private final int inputSize;
    private final int outputSize;

    private final double[][] weights;   
    private final double[] biases;       

    
    private final double[][] velocityW;  
    private final double[] velocityB;   

    private final ActivationFunction activation;
    private double learningRate;
    private double momentum;

    private double[] lastInput;
    private double[] lastOutput;

    private final Random random = new Random();

    public DenseLayer(int inputSize,
                      int outputSize,
                      ActivationFunction activation,
                      double learningRate,
                      double momentum) {

        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.activation = activation;
        this.learningRate = learningRate;
        this.momentum = momentum;

        this.weights = new double[inputSize][outputSize];
        this.biases = new double[outputSize];

        this.velocityW = new double[inputSize][outputSize];
        this.velocityB = new double[outputSize];

        initWeights();
        initBiases();
    }

    private void initWeights() {
        double range = Math.sqrt(6.0 / (inputSize + outputSize));
        for (int i = 0; i < inputSize; i++) {
            for (int o = 0; o < outputSize; o++) {
                weights[i][o] = (random.nextDouble() * 2.0 - 1.0) * range;
            }
        }
    }

    private void initBiases() {
        for (int o = 0; o < outputSize; o++) {
            biases[o] = 0.0;
        }
    }

    /**
     * 순전파
     */
    public double[] forward(double[] input) {
        if (input.length != inputSize) {
            throw new IllegalArgumentException("입력 크기가 잘못되었습니다. expected=" 
                    + inputSize + ", actual=" + input.length);
        }

        double[] output = new double[outputSize];

        for (int o = 0; o < outputSize; o++) {
            double sum = biases[o];
            for (int i = 0; i < inputSize; i++) {
                sum += input[i] * weights[i][o];
            }
            output[o] = activation.activate(sum);
        }

        this.lastInput = input.clone();
        this.lastOutput = output.clone();

        return output;
    }

    /**
     * 역전파
     */
    public double[] backward(double[] gradOutput) {
        if (gradOutput.length != outputSize) {
            throw new IllegalArgumentException("gradOutput 크기가 잘못되었습니다.");
        }

        double[] gradInput = new double[inputSize];
        double[] gradNet = new double[outputSize]; 

        // 활성화 미분
        for (int o = 0; o < outputSize; o++) {
            double dAct = activation.derivative(lastOutput[o]);
            gradNet[o] = gradOutput[o] * dAct;
        }

        // 입력에 대한 기울기 계산
        for (int i = 0; i < inputSize; i++) {
            double sum = 0.0;
            for (int o = 0; o < outputSize; o++) {
                sum += gradNet[o] * weights[i][o];
            }
            gradInput[i] = sum;
        }

        for (int i = 0; i < inputSize; i++) {
            for (int o = 0; o < outputSize; o++) {
                double gradW = lastInput[i] * gradNet[o];
                velocityW[i][o] = momentum * velocityW[i][o] - learningRate * gradW;
                weights[i][o] += velocityW[i][o];
            }
        }

        for (int o = 0; o < outputSize; o++) {
            double gradB = gradNet[o];
            velocityB[o] = momentum * velocityB[o] - learningRate * gradB;
            biases[o] += velocityB[o];
        }

        return gradInput;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }
}

