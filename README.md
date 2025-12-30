# kafkabasics
kafka basics

## Features

### Business Date Management

The application manages business dates using a `SourceEntityBusinessDate` table that tracks the current business date for each unique source-entity combination.

**Key Features:**
- Source-entity combinations are loaded from `source-entity.txt` on application startup
- All combinations are initialized to the current date on startup
- Business dates are automatically derived when sending messages (no manual input required)
- EOD (End of Day) messages trigger a business date roll-forward to the next business day (skipping weekends)

**How it works:**
1. When the application starts, it reads `source-entity.txt` and initializes each source-entity combination with today's date
2. When a regular message is sent, the business date is automatically looked up from the `SourceEntityBusinessDate` table
3. When an EOD message (`isEod=true`) is received, instead of processing it as a regular message, the system rolls the business date forward to the next business day for that source-entity combination
4. Subsequent messages for the same source-entity will use the rolled-forward business date

### Kafka Guaranteed Sequencing for GDR Messages

The application implements guaranteed message sequencing for GDR messages using Kafka's partition-based ordering mechanism.

**Implementation Details:**

Kafka guarantees message ordering within a single partition. To ensure that messages with the same entity and source key are processed in order, we use a composite partition key consisting of `sourceTag + "-" + entity`.

**How Partition Keys Work in Kafka:**

1. **Partition Assignment**: When a message is sent to Kafka, the partition is determined by hashing the message key. Messages with the same key always go to the same partition.

2. **Ordering Guarantee**: Within a single partition, Kafka maintains strict ordering - messages are consumed in the exact order they were produced.

3. **Our Implementation**: By using `sourceTag-entity` as the message key (e.g., "Tbs1-MSIL", "Deposit-MSCO"), we ensure that:
   - All messages for a specific source-entity combination go to the same partition
   - These messages are processed in the exact order they were sent
   - Different source-entity combinations can be processed in parallel (they may go to different partitions)

**Code Implementation:**

In `KafkaProducerService.java`:
```java
String partitionKey = message.getSourceTag() + "-" + message.getEntity();
kafkaTemplate.send(topic, partitionKey, message);
```

**Benefits:**
- **Guaranteed Order**: Messages for the same source-entity are always processed in order
- **Parallel Processing**: Different source-entity combinations can be processed concurrently
- **Scalability**: Adding more partitions allows for better parallelism without breaking ordering guarantees
- **Simplicity**: No need for complex sequencing logic in the application layer

**Example Scenario:**

Consider two source-entity combinations: `Tbs1-MSIL` and `Deposit-MSCO`

Messages sent:
1. Tbs1-MSIL, messageId=1
2. Deposit-MSCO, messageId=2
3. Tbs1-MSIL, messageId=3
4. Deposit-MSCO, messageId=4

Processing guarantee:
- Message 1 will always be processed before Message 3 (same key: Tbs1-MSIL)
- Message 2 will always be processed before Message 4 (same key: Deposit-MSCO)
- Messages 1 and 2 may be processed in any order relative to each other (different keys)

This ensures business logic that depends on message ordering within a source-entity context is preserved while allowing maximum throughput for independent message streams.

## API Endpoints

- `GET /` - Web UI for sending messages and viewing processed messages
- `POST /send` - Send a GDR message to Kafka
- `GET /api/messages` - Get all processed messages (JSON)
- `GET /api/business-dates` - Get all source-entity business dates (JSON)

## Configuration

The source-entity mappings are configured in `src/main/resources/source-entity.txt` in CSV format:
```
sourceTag,entity
Tbs1,MSIL
Tsett,MSCO
Deposit,MSCO
Derivative,MSIL
Margin,MSCO
```
