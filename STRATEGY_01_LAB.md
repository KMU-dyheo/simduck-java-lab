# 전략 패턴 1 실습

## 현재 단계

**4단계 - Flyable과 Quackable 분리**

상속받은 행동을 취소하는 대신 필요한 기능만 선택하도록 바꿨다.

```text
MallardDuck  ── implements Flyable, Quackable
RedheadDuck  ── implements Flyable, Quackable
RubberDuck   ── implements Quackable
DecoyDuck    ── 기능 인터페이스 없음
```

## 확인할 것

1. MallardDuck을 선택하고 날기와 울기를 실행한다.
2. 화면에 비행 구조가 `Flyable 구현`으로 표시되는지 확인한다.
3. RubberDuck을 선택하고 날기를 실행한다.
4. RubberDuck에는 비행 메서드 자체가 없다는 것을 확인한다.
5. MallardDuck.java와 RedheadDuck.java의 `fly()`, `quack()` 코드를 비교한다.

## 핵심 문제

기능 선택은 명확해졌지만 **행동 구현을 재사용할 수 없다.**

같은 비행 코드를 여러 오리 클래스에 복사하면 행동을 수정할 때 여러 클래스를 함께 고쳐야 한다.

## 다음 시도

변하는 행동을 `FlyBehavior`, `QuackBehavior` 객체로 분리하고 Duck이 그 객체에 행동을 위임하도록 바꾼다.
