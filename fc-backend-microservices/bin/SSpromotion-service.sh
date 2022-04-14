echo $$ > $1.pid
exec java -Xmx368m  -jar fcg-promotion-service.jar
