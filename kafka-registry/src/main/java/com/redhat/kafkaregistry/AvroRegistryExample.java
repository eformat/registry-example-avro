package com.redhat.kafkaregistry;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
public class AvroRegistryExample {

    private Random random = new Random();
    private String[] symbols = new String[] { "RHT", "IBM", "MSFT", "AMZN" };

    @Outgoing("price-out")
    public Flowable<Record> generate() throws IOException {
        Schema schema = new Schema.Parser().parse(
                new File(getClass().getClassLoader().getResource("price-schema.avsc").getFile())
        );
        return Flowable.interval(1000, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .map(tick -> {
                    Record record = new GenericData.Record(schema);
                    record.put("symbol", symbols[random.nextInt(4)]);
                    record.put("price", String.format("%.2f", random.nextDouble() * 100));
                    //return KafkaRecord.of(record.get("symbol").toString(), record);
                    return record;
                });
    }
}
