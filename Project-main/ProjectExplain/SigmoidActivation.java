public class SigmoidActivation implements ActivationFunction {  
    // 시그모이드 활성화 함수 구현
    @Override                                      
    public double activate(double x) {             
        // 순전파에서 가중합을 받아 sigmoid를 계산
        return 1.0 / (1.0 + Math.exp(-x));         
      
        // sigmoid(x) = 1 / (1 + e^-x) 출력 범위를 (0,1)로 만드는 함수
    }
    @Override                                      
    public double derivative(double y) {           
        return y * (1.0 - y);                      
    }
}
