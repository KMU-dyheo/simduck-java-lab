# 전략 패턴 1 실습

## 현재 단계

**5단계 - 변하는 행동을 객체로 분리**

앞 단계의 문제는 행동의 선택이 아니라 **행동 구현의 재사용**이었다.

비행과 울음을 오리 클래스 밖으로 분리한다.

```text
Duck
 ├─ FlyBehavior
 │   ├─ FlyWithWings
 │   └─ FlyNoWay
 │
 └─ QuackBehavior
     ├─ Quack
     ├─ Squeak
     └─ MuteQuack
```

Duck은 실제 비행 방법을 알 필요가 없다.

```java
public void performFly() {
    flyBehavior.fly();
}
```

각 오리는 자신에게 필요한 행동 객체를 조합한다.

```java
public MallardDuck() {
    flyBehavior = new FlyWithWings();
    quackBehavior = new Quack();
}
```

```java
public RubberDuck() {
    flyBehavior = new FlyNoWay();
    quackBehavior = new Squeak();
}
```

## 확인할 것

1. MallardDuck을 선택한다.
2. 화면의 비행 구조가 `FlyWithWings`으로 표시되는지 확인한다.
3. RubberDuck을 선택한다.
4. 비행 구조가 `FlyNoWay`로 바뀌는지 확인한다.
5. MallardDuck과 RedheadDuck이 같은 `FlyWithWings` 구현을 재사용하는지 코드를 확인한다.
6. RubberDuck과 DecoyDuck이 같은 `FlyNoWay` 구현을 재사용하는지 확인한다.

## 전략 패턴 1 정리

- 상속 자체가 항상 나쁜 것은 아니다.
- 문제는 자주 달라지는 행동이 상속 계층에 고정된 것이다.
- 변하는 부분을 찾아 별도 객체로 분리한다.
- Duck은 행동 객체를 가지고 있으며 실제 실행을 그 객체에 위임한다.
- 같은 행동 구현을 여러 오리가 재사용할 수 있다.

## 다음 수업

전략 패턴 2에서는 ModelDuck과 `setFlyBehavior()`를 추가하고 실행 중 `FlyRocketPowered`로 비행 전략을 교체한다.
