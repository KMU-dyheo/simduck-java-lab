# 06. main 완성본

## 위치

```bash
git switch main
```

## 의미

`main`은 전략 패턴 1에서 끝나는 코드가 아니다.

05단계의 행동 객체 분리에 더해 전략 패턴 2에서 사용할 실행 중 전략 교체까지 포함한다.

## 포함된 내용

- `Duck.setFlyBehavior()`
- `Duck.setQuackBehavior()`
- `ModelDuck`
- `FlyRocketPowered`
- 스윙 화면의 비행 전략 선택
- 스윙 화면의 울음 전략 선택
- **전략 교체 적용** 기능
- 로켓 비행 애니메이션
- 전체 수영, 걷기, 이륙, 착륙, 낚시 시뮬레이션

## 확인 미션

1. ModelDuck을 선택한다.
2. 기본 상태에서 날기를 실행해 `FlyNoWay`를 확인한다.
3. 비행 전략을 `FlyRocketPowered`로 바꾼다.
4. **전략 교체 적용**을 누른다.
5. 다시 날기를 실행해 같은 ModelDuck 객체의 행동이 바뀌는 것을 확인한다.

## 완료 조건

- ModelDuck의 초기 비행 전략은 `FlyNoWay`다.
- `setFlyBehavior()`로 실행 중 전략을 바꿀 수 있다.
- `FlyRocketPowered`로 교체한 뒤 로켓 비행이 실행된다.

## 검증

```bash
./verify.sh 06
```

이 단계는 전략 패턴 2의 수업 내용에 해당한다.
