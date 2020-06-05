# registry-example-avro

apicurio schema registry

https://developers.redhat.com/blog/2019/12/16/getting-started-with-red-hat-integration-service-registry/
https://github.com/hguerrero/amq-examples/tree/master/registry-example-avro

```bash
podman-compose up
curl -X POST -H "Content-type: application/json; artifactType=AVRO" -H "X-Registry-ArtifactId: prices-value" --data '{"type":"record","name":"price","namespace":"com.redhat","fields":[{"name":"symbol","type":"string"},{"name":"price","type":"string"}]}' http://localhost:8081/api/artifacts -s | jq
mvn compile quarkus:dev

kafkacat -b my-cluster-kafka-bootstrap.kafka:9092 -C -o end -q -u -t price -f 'key: %k, value: %s\n' -s key='i$'
```


http://localhost:8081/ui/artifacts
http://localhost:8081/api
