# 이번 미션: Duck에 fly() 추가

이 브랜치는 **01단계 미완성 시작점**이다.

현재 코드는 00단계 정답 상태이므로 GitHub Actions가 실패하는 것이 정상이다.

## 해야 할 일

`lab/01-fly.md`를 읽고 다음을 구현한다.

- `Duck.fly()` 추가
- MallardDuck과 RedheadDuck은 `fly()`를 재정의하지 않음
- 두 오리가 `Duck.fly()`를 상속받아 비행하도록 함

## 권장 작업 방식

```bash
git switch mission/strategy-01-01-fly
git switch -c work/strategy-01-01-fly
./verify.sh
```

검증 실패 메시지를 보고 코드를 수정한다. 완료 후에만 `checkpoint/strategy-01-01-fly`와 비교한다.
