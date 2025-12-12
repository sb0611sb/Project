public interface LossFunction {                     
    // 손실 함수 인터페이스 다양한 손실함수를 쉽게 이용
   
    // 단일 샘플에 대한 손실값
     
    double loss(double[] target, double[] output);  

    //target과 output을 비교해서 오차 계산

    // 출력에 대한 손실의 기울기
    double[] derivative(double[] target, double[] output);
    // 출력 배열과 정답 배열을 받아 손실의 기울기를 배열로 반환
    //역전파 시작점: 출력 레이어에서 기울기를 받아 감
}

