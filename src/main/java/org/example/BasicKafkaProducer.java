package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.Properties;

public class BasicKafkaProducer {
    public static void main(String[] args) {
        // Kafka configuration properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); // Adjust as needed
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Optional: Set memory heap size using JVM args when running, e.g. -Xmx512M

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        System.out.printf("Hello Kafka");
        String topic = "contracts-repo"; // Replace with your topic
        String message = "Sunny Side Ext 2025";

        try {
            // Create and send a message
            System.out.println("trying to send message");
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            RecordMetadata metadata = producer.send(record).get(); // Synchronous send
            System.out.printf("Sent message to topic %s partition %d offset %d%n",
                    metadata.topic(), metadata.partition(), metadata.offset());
            System.out.println("Done sending message");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close(); // Always close the producer
        }
    }
}