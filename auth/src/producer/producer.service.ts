import { Injectable } from "@nestjs/common";
import { Kafka, Producer } from "kafkajs";
// import { constants } from "../_shared/config/config";

@Injectable()
export class KafkaProducerService {
  private readonly kafkaInstance: Kafka;
  private producer: Producer;

  constructor() {
    this.kafkaInstance = new Kafka({
      clientId: "AUTH_SERVICE",
      brokers: ["localhost:9092"],
    });

    this.producer = this.kafkaInstance.producer();
  }

  async publish(topic: string, message: any): Promise<void> {
    await this.producer.connect();
    await this.producer.send({
      topic,
      messages: [{ value: JSON.stringify(message) }],
    });
  }
}
