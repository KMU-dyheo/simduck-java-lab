# SimDuck 디자인 패턴 실습

이 문서는 SimDuck 프로젝트의 단계별 실습 안내서다.

전략 패턴 1은 완성된 코드를 한 번에 보는 방식이 아니라, 요구사항이 추가될 때 설계가 어떻게 흔들리고 어떻게 개선되는지를 직접 코드로 확인하는 방식으로 진행한다.

## 브랜치 역할

- `mission/*`: 학생이 시작하는 **미완성 상태**다. 해당 단계의 GitHub Actions는 처음에는 실패하는 것이 정상이다.
- `work/*`: 학생 개인 작업 브랜치다. `mission/*`에서 분기해 미션을 구현한다.
- `checkpoint/*`: 해당 단계의 **정답 상태**다. GitHub Actions가 통과한다.
- `lab/strategy-01-start`: 전략 패턴 1의 최초 출발점이다.
- `main`: 전략 패턴 2의 실행 중 전략 교체까지 포함한 완성본이다.

## 단계 구성

| 단계 | 학생 시작 브랜치 | 정답 브랜치 | 문서 | 핵심 내용 |
|---|---|---|---|---|
| 00 | `lab/strategy-01-start` | 동일 | `lab/00-start.md` | 기본 상속 구조 확인 |
| 01 | `mission/strategy-01-01-fly` | `checkpoint/strategy-01-01-fly` | `lab/01-fly.md` | `Duck.fly()` 추가 |
| 02 | `mission/strategy-01-02-rubber` | `checkpoint/strategy-01-02-rubber` | `lab/02-rubber.md` | RubberDuck이 잘못 날아가는 문제 |
| 03 | `mission/strategy-01-03-override` | `checkpoint/strategy-01-03-override` | `lab/03-override.md` | 잘못 상속된 행동 재정의 |
| 04 | `mission/strategy-01-04-interface` | `checkpoint/strategy-01-04-interface` | `lab/04-interface.md` | `Flyable`, `Quackable` 분리 |
| 05 | `mission/strategy-01-05-strategy` | `checkpoint/strategy-01-05-strategy` | `lab/05-strategy.md` | `FlyBehavior`, `QuackBehavior`로 행동 분리 |
| 06 | `main` | 동일 | `lab/06-main-complete.md` | 실행 중 전략 교체와 로켓 비행까지 포함한 완성본 |

전략 패턴 1의 수업 범위는 **05단계까지**다. 06단계는 전략 패턴 2에서 다룰 내용까지 포함한다.

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

미션을 완료한 뒤에만 정답과 비교한다.

```bash
git diff checkpoint/strategy-01-02-rubber
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
- `checkpoint/**`
- `main`

의도한 상태는 다음과 같다.

```text
mission/*      → 처음에는 실패
work/*         → 구현 전 실패, 구현 완료 후 성공
checkpoint/*   → 성공
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

- 시뮬레이터 코드를 고쳐서 검증을 우회하지 않는다.
- 핵심 설계는 `duck`, `behavior` 패키지에서 구현한다.
- `checkpoint/*`는 미션을 완료한 뒤 비교용으로 사용한다.
- 05단계 전에는 `FlyRocketPowered`, `ModelDuck`, 실행 중 전략 교체를 미리 구현하지 않는다.
- 05단계에서 전략 패턴 1을 마무리하고, 실행 중 전략 변경은 다음 수업으로 남긴다.
