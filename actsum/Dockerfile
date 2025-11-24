FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/actsum-0.0.1-SNAPSHOT.jar app.jar

COPY wallet/Wallet_dba1 /opt/oracle/wallet/Wallet_dba1

ENV TNS_ADMIN=/opt/oracle/wallet/Wallet_dba1

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
