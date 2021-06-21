#!/usr/bin/env bash
set -Euxo pipefail

lein with-profile -user,-dev,+eastwood-plugin install || exit 1

cd .circleci/example-red-project || exit 1

rm -rf .eastwood*
if lein with-profile -user eastwood > output; then
  echo "Should have failed! Emitted output:"
  cat output
  exit 1
fi

grep --silent "== Warnings: 1 (not including reflection warnings)  Exceptions thrown: 0" output  || exit 1

rm -rf .eastwood*
if lein with-profile -user run -m eastwood.lint > output; then
  echo "Should have failed! Emitted output:"
  cat output
  exit 1
fi

grep --silent "== Warnings: 1 (not including reflection warnings)  Exceptions thrown: 0" output  || exit 1

cd ../example-green-project || exit 1

rm -rf .eastwood*
if ! lein with-profile -user eastwood > output; then
  echo "Should have passed!"
  exit 1
fi

grep --silent "== Warnings: 0 (not including reflection warnings)  Exceptions thrown: 0" output  || exit 1

rm -rf .eastwood*
if ! lein with-profile -user run -m eastwood.lint > output; then
  echo "Should have passed!"
  exit 1
fi

grep --silent "== Warnings: 0 (not including reflection warnings)  Exceptions thrown: 0" output  || exit 1

git submodule update --init --recursive

cd ../core.async || exit 1

if lein with-profile +test update-in :plugins conj "[jonase/eastwood \"RELEASE\"]" -- eastwood > output; then
  echo "Should have failed! Emitted output:"
  cat output
  exit 1
fi

grep --silent "== Warnings: 39 (not including reflection warnings)  Exceptions thrown: 0" output  || exit 1

exit 0
