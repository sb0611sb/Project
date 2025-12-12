public interface ActivationFunction {  
    // interface: 메서드 형태만 선언
    // 활성화 함수를 공통된 형태로 쓰기 위함

    /**
     * 활성화 함수 f(x)
     */
    double activate(double x);          
    // 인터페이스에서 선언된 메서드는 자동으로 public abstract
    //  가중합를 받아 f(x)를 계산하는 함수
    /**
     * 활성화 함수의 도함수 f'(y)
     */
    double derivative(double activatedValue);  
    // 역전파 때 필요

}
    // 이 인터페이스 덕분에 Sigmoid, ReLU 등 여러 함수로 쉽게 갈아끼울 수 있음


