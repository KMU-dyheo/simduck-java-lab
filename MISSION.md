# 이번 미션: 잘못 상속된 행동 재정의

이 브랜치는 **03단계 미완성 시작점**이다.

현재 코드는 02단계 완료 상태이므로 GitHub Actions가 실패하는 것이 정상이다.

## 해야 할 일

`lab/03-override.md`를 읽고 다음을 구현한다.

- RubberDuck에서 `fly()` 재정의
- RubberDuck은 날지 않게 함
- DecoyDuck 추가
- DecoyDuck에서 `fly()`, `quack()` 재정의
- DuckFactory에서 DecoyDuck 선택 가능하게 함

## 권장 작업 방식

```bash
git switch mission/strategy-01-03-override
git switch -c work/strategy-01-03-override
./verify.sh
```

완료 후에만 참고 구현인 `reference/strategy-01-03-override`와 비교한다.


## 참고 구현의 의미

`reference/*`는 유일한 정답을 뜻하지 않는다. 같은 설계 원칙을 만족하는 구현은 여러 형태가 가능하므로, 수업에서 제시하는 **참고 구현(reference implementation)** 과 비교하기 위한 브랜치다.
