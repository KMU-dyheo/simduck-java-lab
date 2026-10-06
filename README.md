# 심덕 자바 실습

`simduck-java-lab`은 디자인 패턴 수업에서 자바 코드의 구조 변화와 실행 결과를 함께 확인하기 위한 실습 저장소다.

첫 번째 실습 모듈은 교재의 심덕 예제를 기반으로 한 전략 패턴 실습이다. 핵심 설계 코드는 교재의 구조를 최대한 단순하게 유지하고, 스윙 시뮬레이터 코드는 별도 패키지로 분리했다.

## 현재 포함된 실습

- 전략 패턴: `simduck`
- 이후 다른 패턴 실습 모듈을 같은 저장소에 추가할 수 있도록 다중 프로젝트 구조로 구성

## 학습 목표

이 프로젝트에서는 다음 흐름을 직접 실습한다.

1. `Duck`의 공통 행동과 하위 클래스를 확인한다.
2. 비행과 울음처럼 변하기 쉬운 행동을 별도 인터페이스로 분리한다.
3. `FlyBehavior`, `QuackBehavior` 구현체를 조합한다.
4. `Duck`이 구체적인 행동을 직접 구현하지 않고 전략 객체에 위임하는 구조를 확인한다.
5. `setFlyBehavior()`와 `setQuackBehavior()`로 실행 중 전략을 교체한다.
6. 자바 코드를 수정하고 다시 컴파일한 뒤 화면에서 실제 행동 변화를 확인한다.

## 핵심 클래스 구조

```text
Duck
 ├─ MallardDuck
 ├─ RedheadDuck
 ├─ RubberDuck
 ├─ DecoyDuck
 └─ ModelDuck

FlyBehavior
 ├─ FlyWithWings
 ├─ FlyNoWay
 └─ FlyRocketPowered

QuackBehavior
 ├─ Quack
 ├─ Squeak
 └─ MuteQuack
```

## 교재와 같은 콘솔 실습

`MiniDuckSimulator`에서는 교재의 핵심 흐름을 그대로 확인할 수 있다.

```java
Duck mallard = new MallardDuck();
mallard.performQuack();
mallard.performFly();

Duck model = new ModelDuck();
model.performFly();
model.setFlyBehavior(new FlyRocketPowered());
model.performFly();
```

## 스윙 시뮬레이터

화면에서는 다음 기능을 직접 실행할 수 있다.

- 오리 종류 변경
- 물, 물고기가 있는 물, 초원, 모래 지형 변경
- `FlyBehavior` 실행 중 교체
- `QuackBehavior` 실행 중 교체
- `display()` 호출
- `performQuack()` 호출
- 수영과 걷기 애니메이션
- `performFly()` 호출
- 물과 육지에서의 푸드득 이륙
- 비행 애니메이션
- 물과 육지에 파드득 착륙
- 물고기를 잡기 위해 머리를 물속에 넣는 과정
- 물고기 잡기 성공 과정
- 물고기 잡기 실패 뒤 머리를 터는 과정
- 로켓 비행

전략 패턴의 핵심 코드는 `behavior`, `duck` 패키지에 두고, 화면 표현과 애니메이션은 `simulator` 패키지에 분리했다. 따라서 학생은 화면 구현을 이해하지 않아도 전략 패턴 코드를 수정할 수 있다.

## 실행 환경

- 자바 17 이상
- 스윙을 사용할 수 있는 데스크톱 환경
- 선택 사항: 그래들 8 이상

## 가장 간단한 실행 방법

리눅스 또는 맥에서는 저장소 루트에서 다음을 실행한다.

```bash
./run.sh
```

윈도우에서는 다음을 실행한다.

```text
run.bat
```

실행 스크립트는 외부 라이브러리 없이 `javac`로 다시 컴파일한 뒤 스윙 시뮬레이터를 실행한다. 학생이 자바 코드를 수정한 뒤 스크립트를 다시 실행하면 변경 결과를 바로 확인할 수 있다.

## 그래들을 사용하는 경우

```bash
gradle :simduck:run
```

교재의 콘솔 예제만 실행하려면 다음을 사용한다.

```bash
gradle :simduck:runBookDemo
```

## 핵심 설계 검증

리눅스 또는 맥에서는 다음 명령으로 기본 전략 연결과 실행 중 전략 교체를 검증한다.

```bash
./verify.sh
```

윈도우에서는 다음을 실행한다.

```text
verify.bat
```

## 권장 실습 순서

### 1. 기본 행동 확인

`MallardDuck`을 선택하고 `performFly()`, `performQuack()`의 결과를 확인한다.

### 2. 날 수 없는 오리 확인

`RubberDuck`, `DecoyDuck`, `ModelDuck`의 기본 비행 전략을 비교한다.

### 3. 실행 중 전략 교체

`ModelDuck`에서 다음 코드를 실행한다.

```java
model.setFlyBehavior(new FlyRocketPowered());
model.performFly();
```

스윙 화면에서도 `ModelDuck`을 선택한 뒤 `FlyRocketPowered`로 전략을 교체하고 날기 동작을 실행하면 같은 변화를 애니메이션으로 확인할 수 있다.

### 4. 새로운 전략 추가

새로운 `FlyBehavior` 구현체를 만든 뒤 기존 `Duck` 클래스를 수정하지 않고 사용할 수 있는지 확인한다.

### 5. 새로운 오리 추가

새로운 `Duck` 하위 클래스를 만들고 생성자에서 원하는 행동 객체를 조합한다.

## 에셋 구성

수업 시연을 위해 만든 오리, 물, 물고기, 초원, 모래, 걷기, 비행, 이륙, 착륙, 물결, 첨벙임, 낚시 에셋을 사용한다. 저장소 크기를 줄이기 위해 여러 이미지를 작은 이미지 묶음으로 정리했으며 실행 중 필요한 프레임을 자바 코드에서 분리한다.

오리 종류별 시각 차이는 기본 오리 이미지를 바탕으로 실행 중 색상을 변환하여 표현한다. 전략 패턴의 핵심 코드와 화면 표현 코드는 독립적으로 유지된다.

## 저장소 확장 방향

이 저장소는 전략 패턴에 한정하지 않는다. 이후 수업에서는 다음과 같이 별도 모듈을 추가할 수 있다.

```text
simduck
observer-weather
decorator-starbuzz
factory-pizza
```

각 모듈은 독립적으로 실행할 수 있도록 구성하는 것을 기본 원칙으로 한다.


## 전략 패턴 1 단계별 브랜치

전략 패턴 1은 **미션 브랜치에서 시작하고 reference 브랜치의 참고 구현과 비교하는 방식**으로 진행한다.

| 단계 | 학생 시작 브랜치 | 참고 구현 브랜치 |
|---|---|---|
| 시작 | `lab/strategy-01-start` | 동일 |
| 1단계 | `mission/strategy-01-01-fly` | `reference/strategy-01-01-fly` |
| 2단계 | `mission/strategy-01-02-rubber` | `reference/strategy-01-02-rubber` |
| 3단계 | `mission/strategy-01-03-override` | `reference/strategy-01-03-override` |
| 4단계 | `mission/strategy-01-04-interface` | `reference/strategy-01-04-interface` |
| 5단계 | `mission/strategy-01-05-strategy` | `reference/strategy-01-05-strategy` |

`mission/*` 브랜치는 의도적으로 미완성이다. 처음 GitHub Actions가 실패하는 것이 정상이며, 학생은 이 브랜치에서 개인 작업 브랜치를 만든다.

### 왜 `reference/*`인가?

이 브랜치는 학생 코드의 **유일한 정답(solution)** 을 뜻하지 않는다. 디자인 문제에는 같은 원칙을 만족하는 여러 구현이 가능하다. 따라서 수업에서 제시하는 한 가지 **기준 구현(reference implementation)** 과 비교한다는 의미로 `reference/*`를 사용한다.

기존 `checkpoint/*`라는 이름은 단순히 "진행 중 특정 시점"이라는 의미가 강해서 교수자가 제공하는 비교용 구현이라는 역할이 명확하지 않았다. 앞으로 문서와 GitHub Actions에서는 `reference/*`만 사용한다.

```bash
git switch mission/strategy-01-02-rubber
git switch -c work/strategy-01-02-rubber
```

코드를 수정하면서 `./verify.sh`를 반복 실행하고, 검증이 통과한 뒤에만 해당 `reference/*` 브랜치의 참고 구현과 비교한다.

전체 단계와 미션 설명은 `Lab.md`, 세부 미션은 `lab/NN-*.md`에 있다.

`main`은 전략 패턴 2의 실행 중 행동 교체와 `FlyRocketPowered`까지 포함한 완성 상태다.


## 자동 실습 검증

단계별 미션과 검증 방법은 `Lab.md`에서 확인한다.

현재 브랜치의 목표는 다음 명령으로 검사할 수 있다.

```bash
./verify.sh
```

특정 단계를 직접 지정하려면 단계 번호를 전달한다.

```bash
./verify.sh 05
```

푸시와 풀 리퀘스트에서는 GitHub Actions가 같은 검증을 자동으로 실행한다.
