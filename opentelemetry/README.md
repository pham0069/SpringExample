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
[otel.javaagent 2024-03-14 23:41:51:903 +0700] [PeriodicMetricReader-1] INFO io.opentelemetry.exporter.logging.LoggingMetricExporter - metric: ImmutableMetricData{resource=Resource{schemaUrl=https://opentelemetry.io/schemas/1.23.1, attributes={host.arch="x86_64", host.name="Dieps-MacBook-Pro.local", os.description="Mac OS X 12.0.1", os.type="darwin", process.command_args=[/Users/diep/.sdkman/candidates/java/17.0.10-oracle/bin/java, -jar, target/opentelemetry-1.0-SNAPSHOT.jar], process.executable.path="/Users/diep/.sdkman/candidates/java/17.0.10-oracle/bin/java", process.pid=15178, process.runtime.description="Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 17.0.10+11-LTS-240", process.runtime.name="Java(TM) SE Runtime Environment", process.runtime.version="17.0.10+11-LTS-240", service.name="opentelemetry-1.0-SNAPSHOT", telemetry.distro.name="opentelemetry-java-instrumentation", telemetry.distro.version="2.1.0", telemetry.sdk.language="java", telemetry.sdk.name="opentelemetry", telemetry.sdk.version="1.35.0"}}, instrumentationScopeInfo=InstrumentationScopeInfo{name=io.opentelemetry.tomcat-10.0, version=2.1.0-alpha, schemaUrl=null, attributes={}}, name=http.server.request.duration, description=Duration of HTTP server requests., unit=s, type=HISTOGRAM, data=ImmutableHistogramData{aggregationTemporality=CUMULATIVE, points=[ImmutableHistogramPointData{getStartEpochNanos=1710434316807975000, getEpochNanos=1710434511820972000, getAttributes={http.request.method="GET", http.response.status_code=200, http.route="/orders/{id}", network.protocol.version="1.1", url.scheme="http"}, getSum=0.248688524, getCount=1, hasMin=true, getMin=0.248688524, hasMax=true, getMax=0.248688524, getBoundaries=[0.005, 0.01, 0.025, 0.05, 0.075, 0.1, 0.25, 0.5, 0.75, 1.0, 2.5, 5.0, 7.5, 10.0], getCounts=[0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0], getExemplars=[]}]}}
