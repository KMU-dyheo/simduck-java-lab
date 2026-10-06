# SimDuck 디자인 패턴 실습

이 문서는 SimDuck 프로젝트의 단계별 실습 안내서다.

전략 패턴 1은 완성된 코드를 한 번에 보는 방식이 아니라, 요구사항이 추가될 때 설계가 어떻게 흔들리고 어떻게 개선되는지를 직접 코드로 확인하는 방식으로 진행한다.

## 브랜치 구성

| 단계 | 기준 브랜치 | 문서 | 핵심 내용 |
|---|---|---|---|
| 00 | `lab/strategy-01-start` | `lab/00-start.md` | 기본 상속 구조 확인 |
| 01 | `checkpoint/strategy-01-01-fly` | `lab/01-fly.md` | `Duck.fly()` 추가 |
| 02 | `checkpoint/strategy-01-02-rubber` | `lab/02-rubber.md` | RubberDuck이 잘못 날아가는 문제 |
| 03 | `checkpoint/strategy-01-03-override` | `lab/03-override.md` | 잘못 상속된 행동 재정의 |
| 04 | `checkpoint/strategy-01-04-interface` | `lab/04-interface.md` | `Flyable`, `Quackable` 분리 |
| 05 | `checkpoint/strategy-01-05-strategy` | `lab/05-strategy.md` | `FlyBehavior`, `QuackBehavior`로 행동 분리 |
| 06 | `main` | `lab/06-main-complete.md` | 실행 중 전략 교체와 로켓 비행까지 포함한 완성본 |

전략 패턴 1의 수업 범위는 **05단계까지**다. 06단계는 전략 패턴 2에서 다룰 내용까지 포함한 완성본이다.

## 실습 방법

체크포인트 브랜치는 각 단계의 **정답 상태**다. 학생은 이전 단계에서 개인 작업 브랜치를 만든 뒤 다음 미션을 구현하는 방식을 권장한다.

예를 들어 02단계 RubberDuck 미션을 수행하려면 다음과 같이 시작한다.

```bash
git fetch --all
git switch checkpoint/strategy-01-01-fly
git switch -c work/strategy-01-02-rubber
```

코드를 수정한 뒤 다음 명령으로 목표 달성 여부를 검사한다.

```bash
./verify.sh 02
```

윈도우에서는 다음과 같이 실행한다.

```text
verify.bat 02
```

작업 브랜치 이름을 `work/strategy-01-02-rubber`처럼 만들면 인자를 생략해도 검증 단계가 자동으로 선택된다.

```bash
./verify.sh
```

## 화면 확인

검증 코드만 통과시키는 것이 목표가 아니다. 각 단계에서 반드시 스윙 시뮬레이터도 실행해 실제 행동 변화를 확인한다.

```bash
./run.sh
```

윈도우에서는 `run.bat`를 실행한다.

예를 들어 02단계에서는 RubberDuck을 선택하고 **날기**를 눌러 고무 오리가 실제로 이륙하는 잘못된 동작을 확인해야 한다.

## GitHub Actions

각 실습 브랜치에는 `.github/workflows/lab-verify.yml`이 들어 있다.

푸시하거나 풀 리퀘스트를 만들면 다음을 자동으로 검사한다.

1. 자바 17로 전체 코드가 컴파일되는가
2. 시뮬레이터 핵심 코드와 에셋 로딩이 깨지지 않았는가
3. 현재 단계가 요구하는 클래스 구조가 구현되었는가
4. 현재 단계에서 기대하는 행동이 실제로 실행되는가
5. 아직 등장하면 안 되는 다음 단계의 설계가 미리 들어오지 않았는가

검증이 실패하면 출력된 한국어 메시지를 보고 어떤 조건이 충족되지 않았는지 확인한다.

## 중요한 원칙

- 시뮬레이터 코드를 고쳐서 정답처럼 보이게 만들지 않는다.
- 각 단계의 핵심 설계는 `duck`, `behavior` 패키지에서 구현한다.
- 체크포인트는 정답 비교용이다. 먼저 직접 구현한 뒤 비교한다.
- 05단계 전에는 `FlyRocketPowered`, `ModelDuck`, 실행 중 전략 교체를 미리 구현하지 않는다.
- 05단계에서 전략 패턴 1을 마무리하고, 실행 중 전략 변경은 다음 수업으로 남긴다.
