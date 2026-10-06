# 심덕 자바 실습

이 브랜치는 전략 패턴 1의 **5단계**다.

비행과 울음처럼 오리 종류에 따라 달라지는 행동을 `Duck`의 상속 계층에서 분리하고 별도 행동 객체로 구성한다.

## 현재 확인할 내용

- `FlyBehavior`가 비행 행동의 공통 인터페이스다.
- `FlyWithWings`, `FlyNoWay`가 서로 다른 비행 행동을 구현한다.
- `QuackBehavior`가 울음 행동의 공통 인터페이스다.
- `Quack`, `Squeak`, `MuteQuack`이 서로 다른 울음 행동을 구현한다.
- `Duck.performFly()`와 `Duck.performQuack()`은 실제 행동을 행동 객체에 위임한다.
- MallardDuck과 RedheadDuck은 같은 행동 객체 구현을 재사용한다.
- RubberDuck과 DecoyDuck은 날 수 없는 행동을 재사용한다.

## 실행

```bash
./run.sh
```

윈도우에서는 `run.bat`를 실행한다.

## 전략 패턴 1의 도착점

이 단계에서는 **변하는 행동을 분리하고 조합하는 것**까지 확인한다.

실행 중 행동을 교체하는 `setFlyBehavior()`, `FlyRocketPowered`는 아직 등장하지 않는다. 그 내용은 전략 패턴 2에서 다룬다.
