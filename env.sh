if [[ -z "${JAVA_17_HOME}" ]]; then
    export JAVA_17_HOME=/opt/jdk-17.0.10+7
fi

if [[ -z "${JAVA_22_HOME}" ]]; then
    export JAVA_22_HOME=/opt/jdk-22.0.2+9
fi

export JAVA_VER=UNKNOWN

function java17jvmci() {
  export LD_LIBRARY_PATH=`pwd`/src/main/resources/17
  $JAVA_17_HOME/bin/java --add-opens java.base/java.nio=ALL-UNNAMED \
    --enable-preview --add-modules jdk.incubator.vector \
    -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI                \
    --add-exports jdk.internal.vm.ci/jdk.vm.ci.code=ALL-UNNAMED      \
    --add-exports jdk.internal.vm.ci/jdk.vm.ci.code.site=ALL-UNNAMED \
    --add-exports jdk.internal.vm.ci/jdk.vm.ci.hotspot=ALL-UNNAMED   \
    --add-exports jdk.internal.vm.ci/jdk.vm.ci.meta=ALL-UNNAMED      \
    --add-exports jdk.internal.vm.ci/jdk.vm.ci.runtime=ALL-UNNAMED   \
    "$@"
}

function java22panama() {
  export LD_LIBRARY_PATH=`pwd`/src/main/resources/22
  $JAVA_22_HOME/bin/java \
    --enable-native-access=ALL-UNNAMED \
    --enable-preview --add-modules jdk.incubator.vector \
    "$@"
}

export JAR_FILE="./target/vector-vapi-jni-jvmci-1.0-SNAPSHOT-shaded.jar"
export BENCH17="./vector-vapi-jni-jvmci-1.0-SNAPSHOT-shaded-jdk17.jar"
export BENCH22="./vector-vapi-jni-jvmci-1.0-SNAPSHOT-shaded-jdk22.jar"