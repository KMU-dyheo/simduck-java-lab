#!/usr/bin/env sh
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
MAIN="$ROOT/out/main"
TEST="$ROOT/out/test"
rm -rf "$ROOT/out"
mkdir -p "$MAIN" "$TEST"
find "$ROOT/simduck/src/main/java" -name '*.java' > "$ROOT/out/main-sources.txt"
find "$ROOT/simduck/src/test/java" -name '*.java' > "$ROOT/out/test-sources.txt"
javac --release 17 -encoding UTF-8 -d "$MAIN" @"$ROOT/out/main-sources.txt"
cp -R "$ROOT/simduck/src/main/resources/." "$MAIN/"
javac --release 17 -encoding UTF-8 -cp "$MAIN" -d "$TEST" @"$ROOT/out/test-sources.txt"
java -Djava.awt.headless=true -cp "$MAIN:$TEST" edu.kmu.simduck.CoreDesignVerification
