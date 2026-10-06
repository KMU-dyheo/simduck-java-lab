# 이번 미션: RubberDuck 문제 만들기

이 브랜치는 **02단계 미완성 시작점**이다.

현재 코드는 01단계 완료 상태이므로 GitHub Actions가 실패하는 것이 정상이다.

## 해야 할 일

`lab/02-rubber.md`를 읽고 다음을 구현한다.

- RubberDuck 추가
- `quack()`은 "삑삑!"으로 재정의
- **`fly()`는 재정의하지 않음**
- DuckFactory에서 RubberDuck 선택 가능하게 함
- 실행해서 고무 오리가 실제로 날아가는 문제를 확인

## 권장 작업 방식

```bash
git switch mission/strategy-01-02-rubber
git switch -c work/strategy-01-02-rubber
./verify.sh
```

완료 후에만 참고 구현인 `reference/strategy-01-02-rubber`와 비교한다.


## 참고 구현의 의미

`reference/*`는 유일한 정답을 뜻하지 않는다. 같은 설계 원칙을 만족하는 구현은 여러 형태가 가능하므로, 수업에서 제시하는 **참고 구현(reference implementation)** 과 비교하기 위한 브랜치다.
