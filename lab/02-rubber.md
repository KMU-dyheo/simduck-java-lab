# 02. RubberDuck이 날아가는 문제 확인

## 목표

상속으로 공통 행동을 추가했을 때 잘못된 행동까지 상속되는 문제를 직접 확인한다.

## 시작

```bash
git switch mission/strategy-01-02-rubber
git switch -c work/strategy-01-02-rubber
```

처음 `./verify.sh`를 실행하면 실패하는 것이 정상이다.

## 미션

1. `RubberDuck`을 추가하고 `Duck`을 상속한다.
2. `display()`는 고무 오리임을 출력한다.
3. `quack()`을 재정의해 "삑삑!"을 출력한다.
4. **이 단계에서는 `fly()`를 재정의하지 않는다.**
5. `DuckFactory`에서 RubberDuck을 선택할 수 있게 한다.
6. 프로그램을 실행해 RubberDuck이 실제로 날아가는 잘못된 결과를 확인한다.

## 완료 조건

- RubberDuck은 `Duck.fly()`를 그대로 상속한다.
- RubberDuck의 울음은 "삑삑!"이다.
- 화면에서 RubberDuck을 선택할 수 있다.
- RubberDuck의 **날기**를 누르면 실제로 이륙한다.
- `./verify.sh`가 통과한다.

## 정답 비교

```bash
git diff checkpoint/strategy-01-02-rubber
```

## 다음 질문

> RubberDuck에서 `fly()`를 재정의해서 아무것도 하지 않게 하면 해결되는가?
