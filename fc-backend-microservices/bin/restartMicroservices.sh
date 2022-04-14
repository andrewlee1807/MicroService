nohup ./runAService.sh restart SSconfig &>config.out &
sleep "20"
echo "restarting Registry"
nohup ./runAService.sh restart SSregistry &>registry.out &
sleep "15"
echo "restarting services"
nohup ./runAService.sh restart SSauth-service &>auth-service.out &
sleep "5"
nohup ./runAService.sh restart SSaccount-service &>account-service.out &
sleep "5"
nohup ./runAService.sh restart SSorder-service &>order-service.out &
sleep "5"
nohup ./runAService.sh restart SSemail-service &>email-service.out &
sleep "5"
nohup ./runAService.sh restart SSlocation-service &>location-service.out &
sleep "5"
nohup ./runAService.sh restart SScatalog-service &>catalog-service.out &
sleep "5"
nohup ./runAService.sh restart SSfile-service &>file-service.out &
sleep "5"
nohup ./runAService.sh restart SSpromotion-service &>promotion-service.out &
sleep "5"
nohup ./runAService.sh restart SSpayment-service &>payment-service.out &
sleep "40"
echo "restarting gateway"
nohup ./runAService.sh restart SSgateway &>gateway.out &
