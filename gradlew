#!/bin/sh
# Gradle start up script for Unix

# Attempt to set APP_HOME
APP_HOME=$( cd "${BASH_SOURCE[0]%/*}" && pwd )

# Use the maximum available, or set MAX_FD -> maximum available
MAX_FD=maximum

warn () {
    echo "$*"
} >&2

die () {
    echo "$*"
    exit 1
} >&2

# OS specific support
cygwin=false
msys=false
darwin=false
nonstop=false
case "$( uname )" in
    CYGWIN* )         cygwin=true ;;
    Darwin* )         darwin=true ;;
    MSYS* | MINGW* )  msys=true ;;
    NONSTOP* )        nonstop=true ;;
esac

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        JAVACMD=$JAVA_HOME/jre/sh/java
    else
        JAVACMD=$JAVA_HOME/bin/java
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME"
    fi
else
    JAVACMD=java
    if ! command -v java >/dev/null 2>&1
    then
        die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
    fi
fi

# Escape application args
save () {
    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/" ; done
    echo " "
}
APP_ARGS=$(save "$@")

# Collect all arguments for the java command
set -- \
        "-Dorg.gradle.appname=gradlew" \
        -classpath "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
        org.gradle.wrapper.GradleWrapperMain \
        "$@"

exec "$JAVACMD" "$@"
