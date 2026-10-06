# 이번 미션: 변하는 행동을 객체로 분리

이 브랜치는 **05단계 미완성 시작점**이다.

현재 코드는 04단계 정답 상태이므로 GitHub Actions가 실패하는 것이 정상이다.

## 해야 할 일

`lab/05-strategy.md`를 읽고 다음을 구현한다.

- `FlyBehavior`, `QuackBehavior` 추가
- 구체 행동 클래스 추가
- `Duck`이 행동 객체를 필드로 가지게 함
- `performFly()`, `performQuack()`에서 행동 객체에 위임
- 각 오리가 필요한 행동 객체를 조합
- 기존 `Flyable`, `Quackable` 제거
- 아직 `ModelDuck`, `FlyRocketPowered`, setter는 추가하지 않음

## 권장 작업 방식

```bash
git switch mission/strategy-01-05-strategy
git switch -c work/strategy-01-05-strategy
./verify.sh
```

완료 후에만 `checkpoint/strategy-01-05-strategy`와 비교한다.
