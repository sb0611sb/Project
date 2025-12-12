import java.util.ArrayList;  
import java.util.List;       
// [Java] List 인터페이스와 그 구현체 ArrayList를 사용. 제네릭 타입 사용.
// [NN] 여러 레이어를 순서대로 담기 위한 컨테이너. 레이어 스택 구조.
/**
 * 여러 DenseLayer를 순서대로 연결한 간단한 MLP 네트워크
 */
public class NeuralNetwork {                         
    // [Java] 신경망 전체를 표현하는 클래스.
    // [NN] 레이어들을 모아 “네트워크”로 관리하고, 학습 루프(train)까지 담당.
    private final List<DenseLayer> layers = new ArrayList<>();  
    // [Java] ArrayList로 DenseLayer 인스턴스들을 순서대로 보관.
    // [NN] input → hidden → output 순으로 레이어를 쌓아 MLP 구조를 만든다.
    private final LossFunction lossFunction;                    
    // [Java] 인터페이스 타입 필드. 어떤 구현체(MSE, CE)를 쓰든 가능.
    // [NN] 네트워크의 손실을 계산하고, 역전파의 시작점 기울기를 제공.
    public NeuralNetwork(LossFunction lossFunction) {           
        this.lossFunction = lossFunction;
        // [Java] 생성자에서 LossFunction 주입(Dependency Injection).
        // [NN] 학습할 때 어떤 손실 기준으로 최적화할지 결정.
    }
    public void addLayer(DenseLayer layer) {                    
        layers.add(layer);                                      
        // [Java] 리스트에 레이어 추가.
        // [NN] 네트워크의 구조를 확장. 보통 input→hidden→output 순으로 호출.
    }
    /**
     * 네트워크 전체 순전파
     */
    public double[] forward(double[] input) {          
        double[] output = input;                       
        // [Java] output을 input으로 초기화. 첫 레이어의 입력값.
        // [NN] 처음에는 네트워크 입력 x가 첫 레이어의 입력.
        for (DenseLayer layer : layers) {              
            output = layer.forward(output);            
            // [Java] 향상된 for 문으로 각 레이어에 대해 forward 호출.
            // [NN] y = L_n(...L_2(L_1(x))) 형태로 각 레이어를 통과시키며 출력 갱신.
        }
        return output;                                 
        // [NN] 마지막 레이어 출력 = 네트워크의 최종 예측 값.
    }
    /**
     * train: 온라인 학습 (샘플 단위 업데이트, 모멘텀 사용)
     * XOR 같은 작은 문제에는 충분히 효과적
     */
    public void train(double[][] inputs, double[][] targets, int epochs) {  
        // [Java] inputs: [샘플][특징], targets: [샘플][타깃 차원]
        // [NN] 에폭 수만큼 전체 데이터셋을 반복하며, 각 샘플로 순전파+역전파+파라미터 업데이트 수행.
        if (inputs.length != targets.length) {          
            throw new IllegalArgumentException("입력 샘플 수와 타깃 샘플 수가 다릅니다.");
            // [NN] 샘플 수가 다르면 학습 불가능. 데이터셋 무결성 체크.
        }

        int nSamples = inputs.length;                   

        for (int epoch = 1; epoch <= epochs; epoch++) { 
            double totalLoss = 0.0;                     
            // [NN] 한 에폭 전체에 대한 손실 합을 누적해서 평균 손실을 출력하기 위함.

            // 단순히 순서대로; 필요하면 여기에서 셔플 구현 가능
            for (int n = 0; n < nSamples; n++) {        
                double[] x = inputs[n];
                double[] t = targets[n];
                // [Java] 배열에서 한 샘플씩 꺼냄.
                // [NN] x: 입력 벡터, t: 해당 샘플의 정답 벡터.

                // 1. 순전파
                double[] y = forward(x);                
                // [NN] 네트워크 전체를 통과하여 예측 y = f(x; W)를 얻는다.

                // 2. 손실 계산
                double loss = lossFunction.loss(t, y);  
                totalLoss += loss;
                // [NN] 현재 샘플의 손실을 계산해 누적. 학습이 잘 되면 이 값이 점점 줄어들어야 함.

                // 3. 손실에 대한 출력 기울기
                double[] grad = lossFunction.derivative(t, y); 
                // [NN] dL/dy: 출력 레이어에서의 기울기. 역전파의 시작점.

                // 4. 역전파: 마지막 레이어부터 첫 레이어까지
                for (int layerIndex = layers.size() - 1; layerIndex >= 0; layerIndex--) {
                    grad = layers.get(layerIndex).backward(grad);  
                    // [Java] 인덱스 기반 for문, 뒤에서 앞으로.
                    // [NN] L_n.backward → L_(n-1).backward → ... → L_1.backward  
                    //     각 backward는 dL/dx_(이전 층 출력)를 반환해 grad를 “한 단계씩 뒤로” 전달.
                }
            }

            double avgLoss = totalLoss / nSamples;     
            // [NN] 에폭당 평균 손실. 학습 상태를 모니터링하는 지표.

            if (epoch == 1 || epoch % 500 == 0 || epoch == epochs) { 
                System.out.printf("Epoch %d, 평균 손실: %.6f%n", epoch, avgLoss);
                // [Java] printf로 포맷된 문자열 출력.
                // [NN] 너무 자주 출력하면 느리니 몇 에폭마다 출력. 손실이 내려가는지 체크.
            }
        }
    }
}
    // [Java] NeuralNetwork 클래스 끝.
    // [NN] 이 클래스는 “레이어 관리 + 순전파 + 학습 루프 + 역전파 연결”을 담당하는 상위 컨트롤러.
