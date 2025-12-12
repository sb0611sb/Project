public class MeanSquaredError implements LossFunction {  
    // [Java] LossFunction 인터페이스 구현. MSE 손실에 대한 실제 클래스.
    // [NN] 회귀/단순 이진 문제에서 자주 쓰는 (1/2) * Σ(y - t)^2 손실을 계산.
    @Override
    public double loss(double[] target, double[] output) {           
        // [Java] LossFunction.loss 구현. 같은 시그니처 유지 필요.
        // [NN] 한 샘플에서 출력과 타깃의 MSE 손실을 계산해 숫자 하나로 반환.
        if (target.length != output.length) {                        
            // [Java] 배열 길이가 다르면 예외 발생 → 방어적 프로그래밍.
            throw new IllegalArgumentException("타깃과 출력의 길이가 다릅니다.");
            // [NN] 타깃/출력 차원 불일치 버그를 빨리 발견하기 위함. 다르면 역전파도 의미 없음.
        }
        double sum = 0.0;                                            
        // [Java] 누적합을 담을 지역 변수. 0으로 초기화.
        // [NN] Σ(y - t)^2의 총합을 저장.
        for (int i = 0; i < target.length; i++) {                    
            // [Java] 전형적인 for 루프. i는 인덱스.
            // [NN] 각 출력 뉴런마다 손실을 계산해서 더함.
            double diff = output[i] - target[i];                     
            // [Java] 배열 인덱싱으로 원소 접근.
            // [NN] diff = (y_i - t_i) → 예측과 정답의 차이.
            sum += diff * diff;                                      
            // [Java] 제곱 후 sum에 누적.
            // [NN] 각 차원의 제곱 오차를 더해 전체 오차를 만든다.
        }
        return 0.5 * sum;                                            
        // [Java] 0.5 * sum: double 연산. 리턴.
        // [NN] MSE에서 1/2를 곱하는 이유: 미분할 때 편하게 2가 상쇄돼서 dL/dy = (y - t)가 됨.
    }
    @Override
    public double[] derivative(double[] target, double[] output) {   
        // [Java] MSE의 기울기(dL/dy)를 계산해 배열로 반환.
        // [NN] 역전파의 첫 단계: 출력 레이어의 각 뉴런에 대한 dL/dy_i.
        if (target.length != output.length) {
            throw new IllegalArgumentException("타깃과 출력의 길이가 다릅니다.");
        }
        double[] grad = new double[target.length];                   
        // [Java] 결과용 배열 생성. 자동으로 0.0으로 초기화.
        // [NN] 각 출력 뉴런에 대한 기울기 값들을 담은 벡터.
        for (int i = 0; i < target.length; i++) {
            grad[i] = output[i] - target[i];                         
            // [Java] 배열 원소 대입.
            // [NN] MSE에서 dL/dy_i = (y_i - t_i).  
            //     이 벡터가 DenseLayer.backward의 gradOutput으로 전달됨.
        }
        return grad;                                                 
        // [Java] 계산된 배열 반환.
        // [NN] 이 벡터는 출력 레이어의 “출력에 대한 기울기”로 사용됨(역전파의 시작점).
    }
}
    // [Java] 클래스 끝.
    // [NN] 다른 LossFunction(예: CrossEntropy)을 만들면, 네트워크 코드 수정 없이 교체 가능.
