#!/bin/bash
PID=""
SERVICE_NAME="$2"
TOOL_COMMAND="./$SERVICE_NAME.sh $SERVICE_NAME"
function get_pid {
   PID=`ps ax | grep java | egrep "java \-Xmx[1-9]{1,4}. \-jar $SERVICE_NAME.jar" | awk -F" " '{print $1}'`
}
function stop {
   kill $(cat $SERVICE_NAME.pid)
   echo "Stop $SERVICE_NAME from $SERVICE_NAME.pid .. Done."
}
function start {
    echo  "Starting $SERVICE_NAME .."
    $TOOL_COMMAND &
    sleep 1
    echo "Done."
}
function restart {
   echo  "Restarting $SERVICE_NAME .."
   stop
   sleep 15
   start
}
function status {
   get_pid
   if [ -z  $PID ]; then
      echo "$SERVICE_NAME is not running."
      exit 1
   else
      echo "$SERVICE_NAME is running, PID=$PID"
	  ./showStatus.sh
   fi
}
case "$1" in
   start)
      start
   ;;
   stop)
      stop
   ;;
   restart)
      restart
   ;;
   status)
      status
   ;;
   *)
      echo "Usage: $0 {start|stop|restart|status}"
esac
