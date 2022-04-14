echo $$ > $1.pid
exec java -Xmx368m  -jar fcg-file-service.jar
