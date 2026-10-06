#!/usr/bin/env sh
set -eu

ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
MAIN="$ROOT/out/main"
TEST="$ROOT/out/test"

resolve_stage() {
    if [ "${1:-}" != "" ]; then
        printf '%s' "$1"
        return
    fi

    if [ "${LAB_STAGE:-}" != "" ]; then
        printf '%s' "$LAB_STAGE"
        return
    fi

    BRANCH="${GITHUB_HEAD_REF:-${GITHUB_REF_NAME:-}}"
    if [ "$BRANCH" = "" ]; then
        BRANCH=$(git -C "$ROOT" branch --show-current 2>/dev/null || true)
    fi

    case "$BRANCH" in
        main) printf '06' ;;
        *strategy-01-start*) printf '00' ;;
        *strategy-01-01-fly*) printf '01' ;;
        *strategy-01-02-rubber*) printf '02' ;;
        *strategy-01-03-override*) printf '03' ;;
        *strategy-01-04-interface*) printf '04' ;;
        *strategy-01-05-strategy*) printf '05' ;;
        *)
            echo "검증 단계를 자동으로 찾을 수 없습니다: $BRANCH" >&2
            echo "예: ./verify.sh 03" >&2
            exit 2
            ;;
    esac
}

STAGE=$(resolve_stage "${1:-}")

rm -rf "$ROOT/out"
mkdir -p "$MAIN" "$TEST"

find "$ROOT/simduck/src/main/java" -name '*.java' > "$ROOT/out/main-sources.txt"
find "$ROOT/simduck/src/test/java" -name '*.java' > "$ROOT/out/test-sources.txt"

javac --release 17 -encoding UTF-8 -d "$MAIN" @"$ROOT/out/main-sources.txt"
cp -R "$ROOT/simduck/src/main/resources/." "$MAIN/"
javac --release 17 -encoding UTF-8 -cp "$MAIN" -d "$TEST" @"$ROOT/out/test-sources.txt"

java -Djava.awt.headless=true -cp "$MAIN:$TEST" edu.kmu.simduck.CoreDesignVerification
java -Djava.awt.headless=true -cp "$MAIN:$TEST" edu.kmu.simduck.LabMissionVerification "$STAGE"
