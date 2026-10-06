# 이번 미션: Flyable과 Quackable 분리

이 브랜치는 **04단계 미완성 시작점**이다.

현재 코드는 03단계 정답 상태이므로 GitHub Actions가 실패하는 것이 정상이다.

## 해야 할 일

`lab/04-interface.md`를 읽고 다음을 구현한다.

- `Flyable` 인터페이스 추가
- `Quackable` 인터페이스 추가
- `Duck`에서 `fly()`, `quack()` 제거
- 필요한 오리만 각 인터페이스를 구현
- 같은 행동 코드가 여러 오리 클래스에 중복되는 것을 확인

## 권장 작업 방식

```bash
git switch mission/strategy-01-04-interface
git switch -c work/strategy-01-04-interface
./verify.sh
```

완료 후에만 `checkpoint/strategy-01-04-interface`와 비교한다.
