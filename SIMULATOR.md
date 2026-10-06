# Swing 시뮬레이터 사용 안내

이 프로젝트의 Swing 화면은 **학생이 만든 Duck과 행동 Strategy를 눈으로 확인하기 위한 실행 도구**다.

Swing, 이미지 처리, 애니메이션 자체는 이번 디자인 패턴 실습의 대상이 아니다.

## 단계별 화면 구성

시뮬레이터의 애니메이션 엔진은 모든 단계에서 같은 코드를 재사용한다. 그러나 학생에게 보이는 기능은 현재 실습 단계에 맞게 제한한다.

| 단계 | 화면에 노출되는 핵심 기능 |
|---|---|
| 00 시작 | 오리 선택, 모습, 울기, 수영 |
| 01 fly 추가 | 00 + 날기 |
| 02 RubberDuck | 날기를 통해 RubberDuck이 잘못 날아가는 문제 확인 |
| 03 재정의 | RubberDuck과 DecoyDuck이 더 이상 날지 않는지 확인 |
| 04 인터페이스 | 현재 행동 구조에 Flyable, Quackable 구현 여부 표시 |
| 05 Strategy | 현재 행동 구조에 FlyBehavior, QuackBehavior 구현 이름 표시 |
| Strategy 2 / main | 비행·울음 전략 선택과 실행 중 전략 교체 기능 추가 |

전략 패턴 1에서는 걷기, 착륙, 낚시와 같은 부가 애니메이션을 화면에서 숨긴다. 이 기능들은 Strategy 설계 변화를 설명하는 데 필요하지 않기 때문이다.

즉, **엔진은 공통으로 재사용하되 UI는 학습 단계에 맞춰 점진적으로 공개한다.**

## 학생이 수정하는 영역

기본 실습에서는 다음 패키지만 다룬다.

```text
simduck/src/main/java/edu/kmu/simduck/

duck/        ← Duck과 Duck 하위 클래스
behavior/    ← FlyBehavior, QuackBehavior와 구현 클래스
```

단계에 따라 아직 `behavior/`가 존재하지 않을 수 있다.

## 수정하지 않는 영역

다음은 제공되는 시뮬레이터 인프라다.

```text
simulator/
resources/assets/
```

여기에는 Swing 화면, 애니메이션, 이미지 로딩, 에셋이 들어 있다.

**이번 실습에서는 이 코드를 이해하거나 수정할 필요가 없다.**

## 오리 외형은 추가하지 않는다

시뮬레이터가 제공하는 외형은 다음 기존 오리뿐이다.

- MallardDuck
- RedheadDuck
- RubberDuck
- DecoyDuck
- ModelDuck

새로운 Duck 클래스를 작성해도 새로운 이미지나 에셋을 만들지 않는다.

학생이 만든 새로운 Duck은 화면에서 **기본 Mallard 외형**으로 표시된다. 실습에서 확인하는 대상은 외형이 아니라 행동이다.

## 내가 만든 Duck을 화면에서 확인하는 방법

학생은 `Duck`을 상속한 public 클래스를 `duck` 패키지에 작성하면 된다.

조건은 두 가지다.

1. `Duck`을 상속할 것
2. public 기본 생성자를 가질 것

예:

```java
package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyWithWings;
import edu.kmu.simduck.behavior.Squeak;

public class MyDuck extends Duck {

    public MyDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("내가 만든 오리입니다.");
    }
}
```

그 다음 Swing 코드를 수정하지 않고 다시 실행한다.

```bash
./run.sh
```

Windows:

```text
run.bat
```

`MyDuck`은 오리 선택 목록에 자동으로 나타난다.

```text
MallardDuck
RedheadDuck
RubberDuck
DecoyDuck
MyDuck
```

`MyDuck`의 외형은 기본 Mallard를 사용하지만, `performFly()`, `performQuack()`은 학생이 구성한 Strategy를 실제로 실행한다.

## 새로운 행동 Strategy

전략 패턴 2의 완성 단계에서는 `behavior` 패키지에 public 기본 생성자를 가진 새로운 `FlyBehavior` 또는 `QuackBehavior` 구현을 추가하면 Swing의 전략 선택 목록에도 자동으로 나타난다.

예:

```java
package edu.kmu.simduck.behavior;

public class FlyFast implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("아주 빠르게 날아갑니다!");
    }
}
```

Swing 코드는 수정하지 않는다.

```text
FlyWithWings
FlyNoWay
FlyRocketPowered
FlyFast
```

처럼 자동으로 선택 목록에 추가된다.

## 콘솔과 Swing의 관계

두 실행 방법은 서로 다른 Strategy 구현이 아니다. **같은 학생 코드를 다른 방법으로 관찰한다.**

```text
                 학생이 만든 Duck
                       │
          ┌────────────┴────────────┐
          ▼                         ▼
 MiniDuckSimulator            Swing Simulator
   콘솔 출력 확인              화면 동작 확인
```

콘솔 예제는 `MiniDuckSimulator`에서 코드 흐름을 확인하고, Swing은 같은 행동을 애니메이션으로 확인한다.

## 핵심 규칙

- 새로운 오리 이미지를 만들지 않는다.
- `AssetManager`, `PondPanel`, `AnimationController`를 수정하지 않는다.
- 새로운 Duck은 자동 발견된다.
- Strategy 2에서는 새로운 행동 구현도 자동 발견된다.
- 학생이 집중할 대상은 **행동의 분리, 조합, 교체**다.
