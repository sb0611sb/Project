public class SigmoidActivation implements ActivationFunction {  
    // [Java] class: 실제 구현 클래스. implements ActivationFunction: 인터페이스를 구현하겠다는 선언.
    // [NN] 이 클래스는 “시그모이드 활성화 함수”를 신경망에서 쓰기 위한 구체 구현체.
    @Override                                      
    public double activate(double x) {             
        // [Java] @Override: ActivationFunction의 activate를 재정의. 시그니처가 틀리면 컴파일 에러.
        // [NN] 순전파에서 z값(가중합)을 받아 sigmoid(z)를 계산하는 역할.
        return 1.0 / (1.0 + Math.exp(-x));         
        // [Java] Math.exp(-x): e^-x. 1.0 / (1.0 + ...) double 연산.
        // [NN] sigmoid(x) = 1 / (1 + e^-x). 출력 범위를 (0,1)로 만드는 비선형 함수.
    }
    @Override                                      
    public double derivative(double y) {           
        // [Java] derivative 구현. 매개변수 이름 y: 의미를 위해 이렇게 지음.
        // [NN] y는 이미 activate 결과(sigmoid(x))라고 가정 → 도함수를 간단히 계산.
        return y * (1.0 - y);                      
        // [Java] 곱셈/뺄셈. 단순 산술.
        // [NN] sigmoid'(x) = sigmoid(x) * (1 - sigmoid(x)) = y * (1 - y).  
        //      역전파에서 dL/dz = dL/dy * sigmoid'(x)를 계산하는 데 사용.
    }
}
    // [Java] 클래스 정의 끝.
    // [NN] 이 클래스 인스턴스를 DenseLayer에 넘기면, 레이어가 시그모이드 활성화로 동작하게 됨.
