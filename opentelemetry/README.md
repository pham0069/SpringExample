1. Build jar
```
mvn clean install
```

2. Download opentelemetry-javaagent.jar
3. Run jar with agent
```
java -javaagent:opentelemetry-javaagent.jar -jar target/opentelemetry-1.0-SNAPSHOT.jar
```
This will fail as Spring boot by default sends logs to Otel collector at port 4318
but we did not setup anything to support this
4. Redirect the logs to console
```
export JAVA_TOOL_OPTIONS="-javaagent:opentelemetry-javaagent.jar" \
  OTEL_TRACES_EXPORTER=logging \
  OTEL_METRICS_EXPORTER=logging \
  OTEL_LOGS_EXPORTER=logging \
  OTEL_METRIC_EXPORT_INTERVAL=15000
```
```
java -jar target/opentelemetry-1.0-SNAPSHOT.jar
```
OR
```
java -javaagent:opentelemetry-javaagent.jar \
-Dotel.traces.exporter=logging \
-Dotel.metrics.exporter=logging \
-Dotel.logs.exporter=logging \
-jar target/opentelemetry-1.0-SNAPSHOT.jar
```
5. Test by accessing localhost:8080/orders/1
`[otel.javaagent 2024-03-14 23:39:52:103 +0700][http-nio-8080-exec-1] INFO io.opentelemetry.exporter.logging.LoggingSpanExporter- 'GET /orders/{id}' : f218a2fc369dcc7cb76539fac14c15f3 87f08e8bb34ce5df SERVER [tracer: io.opentelemetry.tomcat-10.0:2.1.0-alpha] 
AttributesMap{data={http.request.method=GET, network.peer.port=55204, http.route=/orders/{id}, server.address=localhost, 
client.address=0:0:0:0:0:0:0:1, network.peer.address=0:0:0:0:0:0:0:1, url.path=/orders/1, url.scheme=http, 
thread.name=http-nio-8080-exec-1, server.port=8080, network.protocol.version=1.1, 
user_agent.original=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36, http.response.status_code=200, thread.id=26}, capacity=128, totalAddedValues=14}
`