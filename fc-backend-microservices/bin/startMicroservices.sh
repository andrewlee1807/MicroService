nohup ./runAService.sh start SSconfig &>config.out &
sleep "20"
echo "starting Registry"
nohup ./runAService.sh start SSregistry &>registry.out &
sleep "15"
echo "starting services"
nohup ./runAService.sh start SSauth-service &>auth-service.out &
sleep "5"
nohup ./runAService.sh start SSaccount-service &>account-service.out &
sleep "5"
nohup ./runAService.sh start SSorder-service &>order-service.out &
sleep "5"
nohup ./runAService.sh start SSemail-service &>email-service.out &
sleep "5"
nohup ./runAService.sh start SSlocation-service &>location-service.out &
sleep "5"
nohup ./runAService.sh start SScatalog-service &>catalog-service.out &
sleep "5"
nohup ./runAService.sh start SSfile-service &>file-service.out &
sleep "5"
nohup ./runAService.sh start SSpromotion-service &>promotion-service.out &
sleep "5"
nohup ./runAService.sh start SSpayment-service &>payment-service.out &
sleep "5"
nohup ./runAService.sh start SSdelivery-service &>delivery-service.out &
sleep "40"
echo "starting gateway"
nohup ./runAService.sh start SSgateway &>gateway.out &
