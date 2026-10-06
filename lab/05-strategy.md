# 05. 변하는 행동을 객체로 분리

## 목표

전략 패턴 1의 핵심인 **변하는 행동을 별도 객체로 분리하고 Duck이 행동 객체에 위임하는 구조**를 구현한다.

## 시작

```bash
git switch mission/strategy-01-05-strategy
git switch -c work/strategy-01-05-strategy
```

처음 `./verify.sh`를 실행하면 실패하는 것이 정상이다.

## 미션

1. `FlyBehavior` 인터페이스를 만든다.
2. `FlyWithWings`, `FlyNoWay`를 구현한다.
3. `QuackBehavior` 인터페이스를 만든다.
4. `Quack`, `Squeak`, `MuteQuack`을 구현한다.
5. `Duck`에 `flyBehavior`, `quackBehavior` 필드를 둔다.
6. `performFly()`, `performQuack()`이 행동 객체에 실행을 위임하도록 한다.
7. 각 오리의 생성자에서 자신에게 필요한 행동 객체를 조합한다.
8. 이전 단계의 `Flyable`, `Quackable`은 제거한다.
9. 스윙 화면에서 오리를 바꿀 때 `FlyWithWings`, `FlyNoWay` 등이 다르게 표시되고 행동도 달라지는지 확인한다.

## 완료 조건

- MallardDuck과 RedheadDuck은 `FlyWithWings`, `Quack`을 재사용한다.
- RubberDuck은 `FlyNoWay`, `Squeak`을 사용한다.
- DecoyDuck은 `FlyNoWay`, `MuteQuack`을 사용한다.
- `Duck`은 실제 비행과 울음 방법을 직접 구현하지 않는다.
- 아직 `setFlyBehavior()`, `setQuackBehavior()`는 없어야 한다.
- 아직 `ModelDuck`, `FlyRocketPowered`는 없어야 한다.
- `./verify.sh`가 통과한다.

## 참고 구현과 비교

```bash
git diff reference/strategy-01-05-strategy
```

## 전략 패턴 1 정리

상속 자체가 문제인 것이 아니다. **자주 변하는 행동을 상속 계층에 고정한 것이 문제**였다.

변하는 부분을 찾아 객체로 분리하면 행동 구현을 재사용하고 조합할 수 있다.

## 다음 수업

실행 중 행동 교체와 `FlyRocketPowered`는 전략 패턴 2에서 다룬다.
