import java.util.Random;  
// [Java] java.util 패키지의 Random 클래스를 가져옴. 난수 생성에 사용.
// [NN] 가중치 초기화를 랜덤하게 하기 위해 사용(대칭 깨기, 학습 성공에 매우 중요).
/**
 * 완전연결(Dense) 레이어
 * inputSize -> outputSize
 * 활성화 함수, 모멘텀 포함 SGD 업데이트
 */
public class DenseLayer {   
    // [Java] 하나의 레이어를 표현하는 클래스. public: 패키지 밖에서도 사용 가능.
    // [NN] 입력 벡터를 받아 가중치와 바이어스를 곱해 활성화 함수를 적용하는 “완전연결 층” 구현체.
    private final int inputSize;     
    private final int outputSize;
    // [Java] final: 생성자에서 한 번 초기화 후 값 변경 불가. 캡슐화 & 불변성.
    // [NN] 이 레이어의 입력 차원과 출력 차원을 고정. 가중치 행렬 크기를 결정.
    private final double[][] weights;    
    private final double[] biases;       
    // [Java] 2차원 배열: weights[input][output]. 1차원 배열: biases[output].
    // [NN] w_ij는 입력 i에서 출력 j로 가는 가중치. b_j는 출력 뉴런 j의 바이어스.
    // 모멘텀을 위한 velocity (각 가중치/바이어스에 대해 하나씩)
    private final double[][] velocityW;  
    private final double[] velocityB; 
    private final ActivationFunction activation; 
    private double learningRate;                
    private double momentum;                    
    // [Java] activation은 인터페이스 타입(다형성). learningRate, momentum은 향후 변경 가능하므로 non-final.
    // [NN] activation: 시그모이드 등. learningRate: 파라미터 업데이트 스텝 크기. momentum: 과거 기울기 반영 정도.

    // [Java] 가중치와 동일한 크기의 배열. 모멘텀 알고리즘에서 “속도”를 저장.
    // [NN] v_ij, v_bj: 지난 gradient의 이동 방향 기억. SGD+Momentum 구현.
    // 역전파를 위해 캐시
    private double[] lastInput;                 
    private double[] lastOutput;                
    // [Java] forward에서 계산 결과를 필드에 저장해 backward에서 사용.
    // [NN] lastInput: dL/dW = x * dL/dnet에 필요. lastOutput: f'(output) 계산에 필요.
    private final Random random = new Random(); 
    // [Java] 난수 생성기 인스턴스를 필드로. 기본 시드.
    // [NN] initWeights에서 가중치 값을 랜덤으로 초기화할 때 사용.
    public DenseLayer(int inputSize,
        int outputSize,
        ActivationFunction activation,
        double learningRate,
        double momentum) {
// [Java] 생성자: 레이어를 만들 때 필수 파라미터를 전달받음.
// [NN] 레이어 구조, 활성화 함수, 학습률, 모멘텀 등을 설정해서 학습 가능한 층을 준비.

this.inputSize = inputSize;           
this.outputSize = outputSize;
this.activation = activation;
this.learningRate = learningRate;
this.momentum = momentum;
// [Java] this.필드 = 매개변수. 생성자에서 필드 초기화.
// [NN] 이 값들로 추후 forward/backward 동작이 결정됨.

this.weights = new double[inputSize][outputSize];  
this.biases = new double[outputSize];              
// [Java] new double[a][b]: 2차원 배열. 기본값 0.0.
// [NN] 아직 값은 0이지만 바로 아래 initWeights, initBiases에서 적절히 설정.

this.velocityW = new double[inputSize][outputSize];
this.velocityB = new double[outputSize];
// [Java] 모멘텀 속도도 0.0으로 초기화된 배열.

initWeights();                     
initBiases();
// [Java] 초기화용 private 메서드 호출.
// [NN] Xavier-ish 초기화로 고정 가중치가 아닌 랜덤 가중치 설정.
}
private void initWeights() {           
    // [Java] 내부에서만 사용하는 헬퍼 메서드. 외부에서 못 부름.
    // [NN] 가중치 초기화 전략 구현 부분.
    double range = Math.sqrt(6.0 / (inputSize + outputSize));  
    // [Java] Math.sqrt: 제곱근. double 나눗셈.
    // [NN] Xavier 초기화 유사: 입력/출력 노드 수에 따라 초기값 범위를 조절해 기울기 소실/폭발 완화.
    for (int i = 0; i < inputSize; i++) {                      
        for (int o = 0; o < outputSize; o++) {
            weights[i][o] = (random.nextDouble() * 2.0 - 1.0) * range;
            // [Java] random.nextDouble(): [0,1) → *2 -1 → (-1,1) 범위.
            // [NN] -range ~ +range 범위 내 랜덤 가중치. 각 연결의 초기 강도 설정.
        }
    }
}
private void initBiases() {
    for (int o = 0; o < outputSize; o++) {
        biases[o] = 0.0;              
        // [Java] 명시적으로 0.0 설정.
        // [NN] 바이어스는 보통 0에서 시작해도 무방. 학습 과정에서 업데이트됨.
    }
}
    /**
     * 순전파: input -> output
     */
    public double[] forward(double[] input) {       
        // [Java] 외부에서 호출하는 메서드. 입력 배열 → 출력 배열.
        // [NN] 레이어의 핵심 연산: y = f(Wx + b). 다음 레이어 입력으로 전달됨.
        if (input.length != inputSize) {           
            throw new IllegalArgumentException("입력 크기가 잘못되었습니다. expected=" 
                    + inputSize + ", actual=" + input.length);
            // [Java] 입력 차원이 안 맞을 경우 예외.
            // [NN] 레이어 구조와 다른 차원의 데이터가 들어오지 않도록 방어.
        }

        double[] output = new double[outputSize];  
        // [Java] 결과를 담을 배열.
        // [NN] 이 레이어의 출력 벡터 y.

        for (int o = 0; o < outputSize; o++) {     
            double sum = biases[o];                
            // [Java] 바이어스값으로 초기화.
            // [NN] z_j = b_j + Σ_i(x_i * w_ij)에서 b_j 부분.
            for (int i = 0; i < inputSize; i++) {  
                sum += input[i] * weights[i][o];   
                // [Java] 반복문 안에서 sum 누적.
                // [NN] z_j에 입력 x_i와 가중치 w_ij를 곱해 모두 더함.
            }
            output[o] = activation.activate(sum);  
            // [Java] 인터페이스 타입 activation을 통해 실제 구현(SigmoidActivation)의 메서드 호출.
            // [NN] y_j = f(z_j). 이 레이어의 최종 출력값.
        }

        // 역전파에서 사용하기 위해 캐시
        this.lastInput = input.clone();            
        this.lastOutput = output.clone();
        // [Java] clone(): 배열 깊은 복사. 원본 변경과 분리.
        // [NN] lastInput: dL/dW 계산에 필요. lastOutput: f'(output)를 계산해 gradNet에 사용.

        return output;                             
        // [Java] 계산된 출력 반환.
        // [NN] 다음 레이어나 최종 출력으로 전달.
    }
    /**
     * 역전파
     * @param gradOutput 손실의 출력에 대한 기울기 dL/dy (이 레이어의 출력 기준)
     * @return 이전 레이어로 전달할 dL/dx (이 레이어의 입력 기준)
     */
    public double[] backward(double[] gradOutput) {   
        // [Java] gradOutput: 다음(혹은 마지막) 레이어에서 넘어온 기울기.
        // [NN] 입력: dL/dy_j (각 출력 뉴런에 대한 기울기 벡터).  
        //    출력: dL/dx_i (이전 레이어에 넘길 기울기).
        if (gradOutput.length != outputSize) {
            throw new IllegalArgumentException("gradOutput 크기가 잘못되었습니다.");
        }

        double[] gradInput = new double[inputSize];     
        double[] gradNet = new double[outputSize];      
        // [Java] 새로운 배열 두 개 생성.
        // [NN] gradNet: dL/d(z_j). gradInput: dL/d(x_i).

        // 1. 활성화 미분 적용: gradNet = gradOutput * f'(output)
        for (int o = 0; o < outputSize; o++) {
            double dAct = activation.derivative(lastOutput[o]);   
            // [Java] activation 인터페이스로 f'(y) 호출.
            // [NN] f'(y_j) = sigmoid'(z_j) 등. 역전파의 chain rule 중 활성화 부분.
            gradNet[o] = gradOutput[o] * dAct;                    
            // [Java] 곱셈.
            // [NN] dL/dz_j = dL/dy_j * f'(y_j). 네트워크에서 pre-activation에 대한 기울기.
        }

        // 2. 입력에 대한 기울기 계산: gradInput = sum_j( gradNet[j] * W_ij )
        for (int i = 0; i < inputSize; i++) {
            double sum = 0.0;
            for (int o = 0; o < outputSize; o++) {
                sum += gradNet[o] * weights[i][o];                 
                // [NN] dL/dx_i = Σ_j dL/dz_j * w_ij. (체인 룰로 이전 레이어에 전파)
            }
            gradInput[i] = sum;
        }

        // 3. 가중치/바이어스 기울기 계산 및 모멘텀 업데이트
        for (int i = 0; i < inputSize; i++) {
            for (int o = 0; o < outputSize; o++) {
                double gradW = lastInput[i] * gradNet[o];          
                // [NN] dL/dW_ij = x_i * dL/dz_j. 가중치에 대한 기울기.
                // 모멘텀: v = m*v - lr*grad, w = w + v
                velocityW[i][o] = momentum * velocityW[i][o] - learningRate * gradW;
                // [Java] 기존 velocity에 모멘텀과 gradient를 반영해 업데이트.
                // [NN] 과거 방향은 유지하고, 현재 gradient 반대 방향으로 조금 더 나아감.
                weights[i][o] += velocityW[i][o];                  
                // [Java] w = w + v.
                // [NN] 실제 가중치를 모멘텀 방향으로 이동 → SGD보다 더 안정적이고 빠른 수렴.
            }
        }

        for (int o = 0; o < outputSize; o++) {
            double gradB = gradNet[o];                             
            // [NN] dL/db_j = dL/dz_j. 바이어스는 입력이 1인 가중치처럼 생각.
            velocityB[o] = momentum * velocityB[o] - learningRate * gradB;
            biases[o] += velocityB[o];                             
            // [NN] 바이어스도 모멘텀을 이용해 업데이트 → 학습 속도/안정성 향상.
        }

        return gradInput;                                          
        // [Java] 이전 레이어로 넘길 기울기 반환.
        // [NN] 이 값이 바로 이전 레이어의 gradOutput이 되어 연쇄적으로 backward 호출.
    }
    public void setLearningRate(double learningRate) {     
        this.learningRate = learningRate;
        // [Java] setter: 외부에서 학습률 변경 가능.
        // [NN] 학습 도중에 learning rate decay 전략 등을 적용할 때 사용 가능.
    }

    public void setMomentum(double momentum) {             
        this.momentum = momentum;
        // [Java] 모멘텀 계수 변경 가능.
        // [NN] 학습 상태에 따라 모멘텀 값을 조정해 최적화 조정 가능.
    }
}
    // [Java] DenseLayer 클래스 끝.
    // [NN] 이 레이어는 “Wx+b → 활성화 → backward + 모멘텀 업데이트”까지 담당하는 완전한 훈련 단위.






