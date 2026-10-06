# SimDuck 디자인 패턴 실습

이 문서는 SimDuck 프로젝트의 단계별 실습 안내서다.

전략 패턴 1은 완성된 코드를 한 번에 보는 방식이 아니라, 요구사항이 추가될 때 설계가 어떻게 흔들리고 어떻게 개선되는지를 직접 코드로 확인하는 방식으로 진행한다.

## 브랜치 역할

- `mission/*`: 학생이 시작하는 **미완성 상태**다. 해당 단계의 GitHub Actions는 처음에는 실패하는 것이 정상이다.
- `work/*`: 학생 개인 작업 브랜치다. `mission/*`에서 분기해 미션을 구현한다.
- `reference/*`: 해당 단계에서 수업이 제시하는 **참고 구현(reference implementation)** 이다. GitHub Actions가 통과한다.
- `lab/strategy-01-start`: 전략 패턴 1의 최초 출발점이다.
- `main`: 전략 패턴 2의 실행 중 전략 교체까지 포함한 완성본이다.

### 왜 `reference/*`라는 이름을 사용하는가?

`reference/*`는 "이 코드만이 정답"이라는 뜻이 아니다. 디자인 패턴 실습에서는 같은 설계 원칙을 만족하면서도 세부 구현이 달라질 수 있다. 그래서 `solution/*`보다 **교수자가 제공하는 기준 구현**이라는 의미의 `reference/*`가 더 적합하다.

기존 `checkpoint/*`는 "특정 진행 시점"이라는 의미가 강해 비교용 구현이라는 역할이 모호했다. 현재 실습 문서와 자동 검증에서는 `reference/*`를 공식 이름으로 사용한다.

## 단계 구성

| 단계 | 학생 시작 브랜치 | 참고 구현 브랜치 | 문서 | 핵심 내용 |
|---|---|---|---|---|
| 00 | `lab/strategy-01-start` | 동일 | `lab/00-start.md` | 기본 상속 구조 확인 |
| 01 | `mission/strategy-01-01-fly` | `reference/strategy-01-01-fly` | `lab/01-fly.md` | `Duck.fly()` 추가 |
| 02 | `mission/strategy-01-02-rubber` | `reference/strategy-01-02-rubber` | `lab/02-rubber.md` | RubberDuck이 잘못 날아가는 문제 |
| 03 | `mission/strategy-01-03-override` | `reference/strategy-01-03-override` | `lab/03-override.md` | 잘못 상속된 행동 재정의 |
| 04 | `mission/strategy-01-04-interface` | `reference/strategy-01-04-interface` | `lab/04-interface.md` | `Flyable`, `Quackable` 분리 |
| 05 | `mission/strategy-01-05-strategy` | `reference/strategy-01-05-strategy` | `lab/05-strategy.md` | `FlyBehavior`, `QuackBehavior`로 행동 분리 |
| 06 | `main` | 동일 | `lab/06-main-complete.md` | 실행 중 전략 교체와 로켓 비행까지 포함한 완성본 |

전략 패턴 1의 수업 범위는 **05단계까지**다. 06단계는 전략 패턴 2에서 다룰 내용까지 포함한다.

## 단계별 시뮬레이터

전략 패턴 1의 Swing 화면은 모든 단계에서 동일한 기능을 한꺼번에 보여주지 않는다.

```text
00  모습 / 울기 / 수영
01  + 날기
02  RubberDuck의 잘못된 비행 확인
03  RubberDuck / DecoyDuck의 재정의 결과 확인
04  + Flyable / Quackable 구조 표시
05  + FlyBehavior / QuackBehavior 구조 표시
06  + 실행 중 전략 선택과 교체
```

애니메이션과 에셋을 처리하는 내부 엔진은 공통으로 재사용하지만, 학생에게 보이는 UI는 현재 단계의 학습 목표에 맞게 제한한다.

## 학생 실습 절차

예를 들어 02단계를 수행하려면 다음과 같이 시작한다.

```bash
git fetch --all
git switch mission/strategy-01-02-rubber
git switch -c work/strategy-01-02-rubber
```

처음에는 검증이 실패해야 정상이다.

```bash
./verify.sh
```

코드를 수정하면서 같은 명령을 반복한다. 모든 조건을 만족하면 검증이 통과한다.

윈도우에서는 다음 명령을 사용한다.

```text
verify.bat
```

브랜치 이름으로 단계를 자동 판단할 수 없는 경우 단계 번호를 직접 전달한다.

```bash
./verify.sh 02
```

미션을 완료한 뒤에만 참고 구현과 비교한다.

```bash
git diff reference/strategy-01-02-rubber
```

## 화면 확인

자동 검증만 통과시키는 것이 목표가 아니다. 각 단계에서 스윙 시뮬레이터도 실행해 실제 행동 변화를 확인한다.

```bash
./run.sh
```

윈도우에서는 `run.bat`를 실행한다.

## GitHub Actions의 의미

`.github/workflows/lab-verify.yml`은 다음 브랜치에서 자동으로 실행된다.

- `mission/**`
- `work/**`
- `lab/**`
- `reference/**`
- `main`

의도한 상태는 다음과 같다.

```text
mission/*      → 처음에는 실패
work/*         → 구현 전 실패, 구현 완료 후 성공
reference/*   → 성공
main           → 성공
```

검증은 다음을 확인한다.

1. 자바 17로 전체 코드가 컴파일되는가
2. 시뮬레이터 핵심 코드와 에셋 로딩이 깨지지 않았는가
3. 현재 단계가 요구하는 클래스 구조가 구현되었는가
4. 현재 단계에서 기대하는 행동이 실제로 실행되는가
5. 아직 등장하면 안 되는 다음 단계 설계가 미리 들어오지 않았는가

실패 로그에는 해당 단계의 힌트가 함께 출력된다.

## 중요한 원칙

- 학생이 수정하는 기본 영역은 `duck/`, `behavior/`이다.
- `simulator/`, `resources/assets/`는 제공 코드이며 수정하지 않는다.
- 새로운 Duck은 `duck` 패키지에 public 기본 생성자로 작성하면 Swing 시뮬레이터가 자동으로 발견한다.
- 새로운 Duck 외형은 만들지 않는다. 등록되지 않은 Duck은 기존 Mallard 외형으로 표시된다.
- Strategy 2에서는 새로운 public `FlyBehavior`, `QuackBehavior` 구현도 자동으로 발견된다.
- 시뮬레이터 사용법은 `SIMULATOR.md`를 참고한다.
- 시뮬레이터 코드를 고쳐서 검증을 우회하지 않는다.
- 핵심 설계는 `duck`, `behavior` 패키지에서 구현한다.
- `reference/*`는 미션을 완료한 뒤 비교용으로 사용한다.
- 05단계 전에는 `FlyRocketPowered`, `ModelDuck`, 실행 중 전략 교체를 미리 구현하지 않는다.
- 05단계에서 전략 패턴 1을 마무리하고, 실행 중 전략 변경은 다음 수업으로 남긴다.
