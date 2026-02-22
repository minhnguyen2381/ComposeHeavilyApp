# Setup Lint & Pre-commit
### Add file lint.xml & pre-commit to base project
lint.xml sample
```
    <?xml version="1.0" encoding="UTF-8"?>
    <lint>
        <issue id="all" severity="ignore" />
        <issue id="UnusedResources" severity="error" />
        <issue id="DuplicateStrings" severity="error" />
        <issue id="DuplicateActivity" severity="error" />
        <issue id="IconDuplicates" severity="error" />
        <issue id="HardcodedText" severity="warning" />
    </lint>
```

pre-commit sample

```
    chmod +x gradlew
    ./gradlew clean
    echo "precommit - start lint check"
    ./gradlew lint
    RESULT=$?
    echo "precommit - start lint result $RESULT"
    if [[ "$RESULT" = 0 ]] ; then
        echo "precommit - lint success."
    else
        echo 1>&2 "precommit - lint fail"
        exit 1
    fi

```

### Setup gradle


app:gradle
```
    lintOptions {
        lintConfig file("lint.xml")
        checkReleaseBuilds false
        abortOnError true
    }
```
gradle
```
  task installGitHook(type: Copy) {
    from new File(rootProject.rootDir, 'pre-commit')
    into { new File(rootProject.rootDir, '.git/hooks') }
  }
```