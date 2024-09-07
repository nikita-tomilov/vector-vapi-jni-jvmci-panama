if [[ -z "${JAVA_17_HOME}" ]]; then
    export JAVA_17_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64
fi

if [[ -z "${JAVA_22_HOME}" ]]; then
    export JAVA_22_HOME=/opt/jdk-22.0.2+9
fi

export JAVA_HOME=$JAVA_22_HOME

function java17jvmci() {
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
  $JAVA_22_HOME/bin/java \
    --enable-native-access=ALL-UNNAMED \
     --enable-preview --add-modules jdk.incubator.vector \
    "$@"
}


export LD_LIBRARY_PATH=`pwd`/src/main/resources
export BENCH="./target/vector-vapi-jni-jvmci-1.0-SNAPSHOT-shaded.jar"