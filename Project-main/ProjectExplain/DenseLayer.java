import java.util.Random;  

/**
 * 활성화 함수, 모멘텀 SGD 업데이트
 */
public class DenseLayer {   

    private final int inputSize;     
    private final int outputSize; 

    // 레이어의 입력 차원과 출력 차원을 고정
    private final double[][] weights;    
    private final double[] biases;       

    // 모멘텀 배열 설정 
    private final double[][] velocityW;  
    private final double[] velocityB; 
    private final ActivationFunction activation; 
    private double learningRate;                
    private double momentum;                    
    // activation은 인터페이스 타입 learningRate, momentum은 향후 변경 가능하므로 non-final


    private double[] lastInput;                 
    private double[] lastOutput;                
    //  forward에서 계산 결과를 필드에 저장해 backward에서 사용
    
    private final Random random = new Random(); 

    public DenseLayer(int inputSize,
        int outputSize,
        ActivationFunction activation,
        double learningRate,
        double momentum) {
// 생성자: 레이어를 만들 때 필수 파라미터를 전달받음
// 레이어 구조, 활성화 함수, 학습률, 모멘텀 등을 설정해서 학습 가능한 층을 준비

this.inputSize = inputSize;           
this.outputSize = outputSize;
this.activation = activation;
this.learningRate = learningRate;
this.momentum = momentum;
// this.필드 = 생성자에서 필드 초기화

this.weights = new double[inputSize][outputSize];  
this.biases = new double[outputSize];              

this.velocityW = new double[inputSize][outputSize];
this.velocityB = new double[outputSize];
// 배열 초기화

initWeights();                     
initBiases();
}
private void initWeights() {           
    // 내부에서만 사용하는 메서드
    // 가중치 초기화 구현
    double range = Math.sqrt(6.0 / (inputSize + outputSize));  
    // 입력/출력 노드 수에 따라 초기값 범위를 조절해 기울기 소실/폭발 완화.
    for (int i = 0; i < inputSize; i++) {                      
        for (int o = 0; o < outputSize; o++) {
            weights[i][o] = (random.nextDouble() * 2.0 - 1.0) * range;
        }
    }
}
private void initBiases() {
    for (int o = 0; o < outputSize; o++) {
        biases[o] = 0.0;              
        // 명시적 0.0 설정
    }
}
    
    public double[] forward(double[] input) {       
        // 외부 호출 메서드
        // 레이어 연산 Wx + b 후 다음 레이어 입력으로 전달
        if (input.length != inputSize) {           
            throw new IllegalArgumentException("입력 크기가 잘못되었습니다. 예상=" 
                    + inputSize + ", 실제=" + input.length);
            // 입력 차원이 안 맞는 경우 사용자 정의예외
            // 레이어와 다른 차원의 데이터가 들어오지 않도록 방어
        }

        double[] output = new double[outputSize];  
        // 결과를 담을 배열

        for (int o = 0; o < outputSize; o++) {     
            double sum = biases[o];                
            // 바이어스값으로 초기화
            for (int i = 0; i < inputSize; i++) {  
                sum += input[i] * weights[i][o];   
                // 반복문 안에서 sum 누적
                // 입력과 가중치를 곱해 더함
            }
            output[o] = activation.activate(sum);  
            // 인터페이스 타입 activation을 통해 구현(SigmoidActivation) 메서드 호출
        }

        // 역전파에서 사용하기 위해 복사
        this.lastInput = input.clone();            
        this.lastOutput = output.clone();
        // clone(): 깊은 복사. 원본 변경과 분리

        return output;                             
        // 다음 레이어나 최종 출력으로 전달
    }
    // 역전파
    
    public double[] backward(double[] gradOutput) {   
        // 레이어에서 넘어온 기울기
        // 입력: 각 출력 뉴런에 대한 기울기 벡터  
        //  출력: 이전 레이어에 넘길 기울기
        if (gradOutput.length != outputSize) {
            throw new IllegalArgumentException("gradOutput 크기가 잘못되었습니다.");
        }

        double[] gradInput = new double[inputSize];     
        double[] gradNet = new double[outputSize];      

        // 활성화 미분 적용
        for (int o = 0; o < outputSize; o++) {
            double dAct = activation.derivative(lastOutput[o]);   
            // activation 인터페이스로 도함수 호출
            gradNet[o] = gradOutput[o] * dAct;                    
        
        }

        // 입력에 대한 기울기 계산
        for (int i = 0; i < inputSize; i++) {
            double sum = 0.0;
            for (int o = 0; o < outputSize; o++) {
                sum += gradNet[o] * weights[i][o];                 
                // 이전 레이어에 전파
            }
            gradInput[i] = sum;
        }

        // 가중치 기울기 계산, 모멘텀 업데이트
        for (int i = 0; i < inputSize; i++) {
            for (int o = 0; o < outputSize; o++) {
                double gradW = lastInput[i] * gradNet[o];          
                // 가중치에 대한 기울기 계산
                velocityW[i][o] = momentum * velocityW[i][o] - learningRate * gradW;
        
                weights[i][o] += velocityW[i][o];                  
                // 가중치를 모멘텀 방향으로 이동
            }
        }

        for (int o = 0; o < outputSize; o++) {
            double gradB = gradNet[o];                             
            velocityB[o] = momentum * velocityB[o] - learningRate * gradB;
            biases[o] += velocityB[o];                             
            //바이어스도 모멘텀으로 업데이트
        }

        return gradInput;                                          
        // 이전 레이어로 넘길 기울기
        // 이 값이 이전 레이어의 출력이 되어 연쇄적으로 backward 호출
    }
    public void setLearningRate(double learningRate) {     
        this.learningRate = learningRate;
        // setter: 외부에서 변경 가능
    }

    public void setMomentum(double momentum) {             
        this.momentum = momentum;

    }
}








