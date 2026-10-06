# 03. 맞지 않는 행동 재정의

## 목표

잘못 상속된 행동을 하위 클래스에서 재정의하는 해결책과 그 한계를 확인한다.

## 시작

```bash
git switch mission/strategy-01-03-override
git switch -c work/strategy-01-03-override
```

처음 `./verify.sh`를 실행하면 실패하는 것이 정상이다.

## 미션

1. RubberDuck에서 `fly()`를 재정의한다.
2. RubberDuck의 `fly()`는 아무 동작도 하지 않게 한다.
3. `DecoyDuck`을 추가한다.
4. DecoyDuck은 `fly()`, `quack()`을 모두 재정의해 아무 동작도 하지 않게 한다.
5. `DuckFactory`에서 DecoyDuck을 선택할 수 있게 한다.
6. 실행해서 RubberDuck과 DecoyDuck이 더 이상 날지 않는지 확인한다.

## 완료 조건

- RubberDuck은 `fly()`를 직접 재정의한다.
- DecoyDuck은 `fly()`, `quack()`을 직접 재정의한다.
- 잘못된 화면 동작은 사라진다.
- 대신 행동을 취소하기 위한 빈 메서드가 여러 클래스에 생긴다.
- `./verify.sh`가 통과한다.

## 참고 구현과 비교

```bash
git diff reference/strategy-01-03-override
```

## 다음 질문

> 필요한 오리만 비행과 울음 기능을 선택해서 구현하도록 만들 수 없을까?
