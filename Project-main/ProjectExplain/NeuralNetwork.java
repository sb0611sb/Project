import java.util.ArrayList;  
import java.util.List;       
// List 인터페이스와 그 구현체 ArrayList를 사용
// DenseLayer를 연결한 네트워크

public class NeuralNetwork {                         
    // 신경망 전체를 표현하는 클래스
    // 레이어들을 모아 관리 
    private final List<DenseLayer> layers = new ArrayList<>();  
    // ArrayList로 DenseLayer 값을 보관
    // input → hidden → output 순으로 레이어를 쌓음
    private final LossFunction lossFunction;                    
    // 인터페이스 타입 필드 어떤 구현체를 쓰든 가능

    public NeuralNetwork(LossFunction lossFunction) {           
        this.lossFunction = lossFunction;
        // 생성자에서 LossFunction 입력
    }
    public void addLayer(DenseLayer layer) {                    
        layers.add(layer);                                      
        // 리스트에 레이어 추가
    }
    // 전체 순전파
     
    public double[] forward(double[] input) {          
        double[] output = input;                       
        // output을 다음 input으로 초기화 
        
        for (DenseLayer layer : layers) {              
            output = layer.forward(output);            
            // 각 레이어에 대해 forward 호출
            // 각 레이어를 통과시키며 출력 갱신
        }
        return output;                                 
        // 네트워크의 최종 예측 값
    }
    
    public void train(double[][] inputs, double[][] targets, int epochs) {  
        // 에폭 수만큼 전체 데이터셋을 반복
        if (inputs.length != targets.length) {          
            throw new IllegalArgumentException("입력 샘플 수와 타깃 샘플 수가 다릅니다.");
            // 샘플 수가 다르면 학습 불가능
        }

        int nSamples = inputs.length;                   

        for (int epoch = 1; epoch <= epochs; epoch++) { 
            double totalLoss = 0.0;                     
            // 손실 합을 누적해서 평균 손실을 출력

            for (int n = 0; n < nSamples; n++) {        
                double[] x = inputs[n];
                double[] t = targets[n];
                // 배열에서 한 샘플씩 꺼냄
                // x: 입력 t: 샘플의 정답

                // 순전파
                double[] y = forward(x);                

                // 손실 계산
                double loss = lossFunction.loss(t, y);  
                totalLoss += loss;
                // 현재 샘플의 손실을 계산해 누적

                // 손실에 대한 출력 기울기
                double[] grad = lossFunction.derivative(t, y); 

                // 역전파
                for (int layerIndex = layers.size() - 1; layerIndex >= 0; layerIndex--) {
                    grad = layers.get(layerIndex).backward(grad);  
                    //     각 backward는 이전 층 출력을 반환
                }
            }

            double avgLoss = totalLoss / nSamples;     
            // 에폭당 평균 손실 학습 상태를 모니터링

            if (epoch == 1 || epoch % 500 == 0 || epoch == epochs) { 
                System.out.printf("Epoch %d, 평균 손실: %.6f%n", epoch, avgLoss);
    
            }
        }
    }
}

