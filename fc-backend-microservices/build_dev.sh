APP_DIR=./bin
git pull
mvn clean -U install
mvn package -P dev
cp config/target/fcg-config.jar $APP_DIR
cp registry-service/target/fcg-registry-service.jar $APP_DIR
cp gateway-service/target/fcg-gateway-service.jar $APP_DIR
cp auth-service/target/fcg-auth-service.jar $APP_DIR
cp account-service/target/fcg-account-service.jar $APP_DIR
cp promotion-service/target/fcg-promotion-service.jar $APP_DIR
cp order-service/target/fcg-order-service.jar $APP_DIR
cp email-service/target/fcg-email-service.jar $APP_DIR
cp location-service/target/fcg-location-service.jar $APP_DIR
cp catalog-service/target/fcg-catalog-service.jar $APP_DIR
cp payment-service/target/fcg-payment-service.jar $APP_DIR
cp file-service/target/fcg-file-service.jar $APP_DIR
cp delivery-service/target/fcg-delivery-service.jar $APP_DIR