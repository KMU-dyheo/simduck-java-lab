# 01. Duck에 fly() 추가

## 목표

새 요구사항을 가장 단순한 상속 방식으로 구현한다.

## 시작

```bash
git switch mission/strategy-01-01-fly
git switch -c work/strategy-01-01-fly
```

처음 `./verify.sh`를 실행하면 실패하는 것이 정상이다.

## 미션

1. `Duck`에 `public void fly()`를 추가한다.
2. `fly()`는 "날고 있습니다."를 출력한다.
3. MallardDuck과 RedheadDuck에는 `fly()`를 따로 구현하지 않는다.
4. 재컴파일하고 두 오리가 같은 `Duck.fly()`를 상속받아 비행하는지 확인한다.

## 수정 대상

- `simduck/src/main/java/edu/kmu/simduck/duck/Duck.java`

시뮬레이터 코드는 수정하지 않는다.

## 완료 조건

- `Duck.fly()`가 존재한다.
- MallardDuck과 RedheadDuck은 `fly()`를 재정의하지 않는다.
- 두 오리가 화면에서 실제로 이륙하고 비행한다.
- `./verify.sh`가 통과한다.

## 정답 비교

미션을 완료한 뒤에만 비교한다.

```bash
git diff checkpoint/strategy-01-01-fly
```

## 다음 질문

> 날 수 없는 고무 오리가 Duck을 상속하면 어떻게 될까?
