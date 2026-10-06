# 04. Flyable과 Quackable 분리

## 목표

필요한 오리만 행동을 선택하게 만들면 상속 문제는 줄어들지만 행동 구현의 재사용 문제가 남는다는 것을 확인한다.

## 시작

```bash
git switch mission/strategy-01-04-interface
git switch -c work/strategy-01-04-interface
```

처음 `./verify.sh`를 실행하면 실패하는 것이 정상이다.

## 미션

1. `Flyable` 인터페이스와 `fly()`를 만든다.
2. `Quackable` 인터페이스와 `quack()`을 만든다.
3. `Duck`에서 `fly()`, `quack()`을 제거한다.
4. MallardDuck과 RedheadDuck은 `Flyable`, `Quackable`을 구현한다.
5. RubberDuck은 `Quackable`만 구현한다.
6. DecoyDuck은 두 인터페이스를 모두 구현하지 않는다.
7. MallardDuck과 RedheadDuck의 `fly()`, `quack()` 구현이 중복되는 것을 직접 확인한다.

## 수정 영역

`duck/`의 클래스와 인터페이스만 수정한다. Swing 및 에셋 코드는 수정하지 않는다. 오리 선택 목록은 현재 Duck 하위 클래스를 자동으로 반영한다.

## 완료 조건

- 잘못된 행동 상속이 사라진다.
- RubberDuck에는 `fly()` 자체가 없다.
- MallardDuck과 RedheadDuck에는 같은 행동 코드가 반복된다.
- `./verify.sh`가 통과한다.

## 참고 구현과 비교

```bash
git diff reference/strategy-01-04-interface
```

## 다음 질문

> 같은 행동을 여러 클래스에 복사하지 않고 하나의 구현으로 재사용하려면 어떻게 해야 할까?
