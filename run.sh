#!/usr/bin/env sh
set -eu
ROOT=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
OUT="$ROOT/out/main"
rm -rf "$ROOT/out"
mkdir -p "$OUT"
find "$ROOT/strategy-simduck/src/main/java" -name '*.java' > "$ROOT/out/main-sources.txt"
javac --release 17 -encoding UTF-8 -d "$OUT" @"$ROOT/out/main-sources.txt"
cp -R "$ROOT/strategy-simduck/src/main/resources/." "$OUT/"
java -cp "$OUT" edu.kmu.simduck.simulator.SimDuckApplication
