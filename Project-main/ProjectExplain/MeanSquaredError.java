public class MeanSquaredError implements LossFunction {  
    // LossFunction 인터페이스 구현
    @Override
    public double loss(double[] target, double[] output) {           

        if (target.length != output.length) {                        
            // 배열 길이가 다르면 예외 발생 
            throw new IllegalArgumentException("타깃과 출력의 길이가 다름");
            
        }
        double sum = 0.0;                                            
        // 누적합을 담을 지역 변수. 0으로 초기화.
       
        for (int i = 0; i < target.length; i++) {                    
            // 출력 뉴런마다 손실을 더함
            double diff = output[i] - target[i];                     
            // 배열 인덱싱으로 원소 접근
            // 예측과 정답의 차이

            sum += diff * diff;                                      
        }
        return 0.5 * sum;                                            
    }
    @Override
    public double[] derivative(double[] target, double[] output) {   
        // 기울기를 계산해 배열로 반환
        if (target.length != output.length) {
            throw new IllegalArgumentException("타깃과 출력의 길이가 다릅니다.");
        }
        double[] grad = new double[target.length];                   
        // 결과용 배열 생성
        // 출력 뉴런의 기울기 값들을 담은 벡터.
        for (int i = 0; i < target.length; i++) {
            grad[i] = output[i] - target[i];                         
            // 배열 원소 대입  
            // DenseLayer.backward의 gradOutput으로 전달됨.
        }
        return grad;                                                 
        // 계산된 배열 반환.
        // 출력 레이어의 기울기로 사용.
    }
}
