import { Injectable, OnModuleInit } from '@nestjs/common';
import { KafkaConsumerService } from './consumer.service';

/**
 * Service responsible for initializing the application and consuming messages from Kafka.
 */
@Injectable()
export class AppService implements OnModuleInit {
  /**
   * Constructor for AppService.
   * @param {KafkaConsumerService} consumerService - Service responsible for consuming messages from Kafka.
   */
  constructor(private readonly consumerService: KafkaConsumerService) {}

  /**
   * Lifecycle hook that gets invoked when the module has been initialized.
   * Starts consuming messages from Kafka.
   */
  async onModuleInit() {
    await this.consumerService.consume();
  }
}
