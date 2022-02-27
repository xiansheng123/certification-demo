# Read Me First

### 1 Generate keystone

keytool -genkeypair -alias springboot -keyalg RSA -keysize 4096 -storetype JKS -keystore springboot.jks -validity 3650
-storepass password -ext san=dns:localhost,ip:127.0.0.1

### 2 View cert

keytool -list -v -keystore springboot.jks password is “password”

### 3 Generate cert by keystore

keytool -export -keystore springboot.jks -alias springboot -file myCertificate.crt

### 4 http
Server client : 
GET https://localhost:8443/server/test

http client
GET http://localhost:8444/client/test

