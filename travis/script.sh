#!/usr/bin/env bash

if [[ $TRAVIS_BRANCH == 'master' && $TRAVIS_PULL_REQUEST == 'false' ]]; then
  ./gradlew build check publishApkRelease
else
  ./gradlew build check
fi
