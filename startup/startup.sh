#!/bin/bash

APP_NAME="c2v_mice_backoffice_api"
PIDFILE="/$APP_NAME/startup/$APP_NAME.pid"

LIB_PATH="/$APP_NAME/lib/$APP_NAME.jar"
CONFIG_PATH="/$APP_NAME/conf/application.yml"
COMM_CONFIG="--spring.config.location=$CONFIG_PATH"

JAVA_OPTS="-Xms1024m -Xmx1024m"

COMMAND="java $JAVA_OPTS -jar $LIB_PATH $COMM_CONFIG"
BACK_COMMAND="nohup $COMMAND 1>/dev/null 2>&1 &"

pid=""


getpid() {
        pid=""
        if [ -f "$PIDFILE" ]
        then
                if [ -r "$PIDFILE" ]
                then
                        pid=$(cat $PIDFILE)
                fi
        fi
}

console() {
        cd "$APP_NAME"
        echo "Running $APP_NAME..."
        getpid
        if [ "X$pid" = "X" ]
        then
                eval "$COMMAND"
        else
                echo "$APP_NAME is already running."
                exit 1
        fi
}

start() {
        cd "$APP_NAME"
        echo "Starting $APP_NAME..."
        getpid
        if [ "X$pid" = "X" ]
        then
                eval "$BACK_COMMAND"

                sleep 1

                ps -aux | grep "$COMMAND" | head -1 | awk '{print $2}' > "$PIDFILE"
        else
                echo "$APP_NAME is already running."
                exit 1
        fi

        echo "$APP_NAME started."
}

stop() {
        echo "Stopping $APP_NAME..."
        getpid
        if [ "X$pid" = "X" ]
        then
                if [ "X$1" = "X0" ]
                then
                        echo "$APP_NAME was not running."
                        exit 1
                fi
        else
                kill -9 $pid

                sleep 1

                rm -f "$PIDFILE"
        fi

        echo "$APP_NAME stopped."
}

status() {
        getpid
        if [ "X$pid" = "X" ]
        then
                echo "$APP_NAME is not running."
                exit 1
        else
                echo "$APP_NAME is running (PID:$pid)."
        fi
        exit 0
}

case "$1" in
        'start')
                start
                ;;
        'stop')
                stop "0"
                ;;
        'restart')
                stop "1"
                start
                ;;
        'console')
                console
                ;;
        'status')
                status
                ;;
        *)
                echo "Usage: $0 { console | start | stop | restart | status }"
                exit 1
                ;;
esac

exit 0

