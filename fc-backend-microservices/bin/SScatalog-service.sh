echo $$ > $1.pid
exec java -Xmx384m -jar fcg-catalog-service.jar
