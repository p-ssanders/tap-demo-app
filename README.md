#   tap-demo-app

This application is used to demonstrate developer experience on the Tanzu Application Platform.

##  Build

```shell
./mvnw clean package
```

##  Run Locally

```shell
SPRING_PROFILES_ACTIVE=local java -jar target/tap-demo-app-0.0.1-SNAPSHOT.jar
```

##  Run on TAP

```shell
kubectl apply -f tap/workload.yaml
```