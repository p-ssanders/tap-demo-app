#   tap-demo-app

This application is used to demonstrate developer experience on the Tanzu Application Platform.

##  Build

```shell
./mvnw clean package
```

##  Run Locally

```shell
docker run --name my-mysql -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=demo -e MYSQL_USER=demo -e MYSQL_PASSWORD=demo -v $HOME/mysql-data:/var/lib/mysql -p 33060:3306 -d mysql:8.0
docker run --rm -d -p 5672:5672 -p 15672:15672 rabbitmq:3-management
SPRING_PROFILES_ACTIVE=local java -jar target/tap-demo-app-0.0.1-SNAPSHOT.jar
```

##  Run on TAP

```shell
tanzu apps workload create -f tap/workload.yaml
```