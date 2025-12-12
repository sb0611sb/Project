public interface ActivationFunction {  
    // [Java] public: 어디서나 사용할 수 있는 인터페이스. interface: 메서드 시그니처(형태)만 선언하는 타입.
    // [NN] 신경망 레이어에서 사용하는 활성화 함수(시그모이드, ReLU 등)를 공통된 형태로 쓰기 위한 “규약”.
    /**
     * 활성화 함수 f(x)
     */
    double activate(double x);          
    // [Java] 반환 타입 double, 매개변수 x 하나. 인터페이스에서 선언된 메서드는 자동으로 public abstract.
    // [NN] 순전파 때 z(가중합)를 받아 f(z)를 계산하는 함수. 예: sigmoid(z). 뉴런의 “출력 값”을 정의.
    /**
     * 활성화 함수의 도함수 f'(y)
     * 보통 y = f(x) (이미 활성화 결과)를 넣어 사용하는 형태로 만들면 편함
     */
    double derivative(double activatedValue);  
    // [Java] double 반환, 매개변수 1개. 구현 클래스가 반드시 오버라이드해야 하는 추상 메서드.
    // [NN] 역전파 때 필요. 뉴런의 출력 y에 대한 f'(y)를 돌려줌. dL/dz = dL/dy * f'(y) 계산에 쓰임.

}
    // [Java] 인터페이스 선언 끝.
    // [NN] 이 인터페이스 덕분에 Sigmoid, ReLU 등 여러 함수로 쉽게 갈아끼울 수 있음(다형성).

