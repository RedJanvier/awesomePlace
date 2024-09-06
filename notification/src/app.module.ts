import { Module } from "@nestjs/common";
import { AppService } from "./app.service";
import { ConfigModule } from "@nestjs/config";
import { KafkaConsumerService } from "./consumer.service";
import { SendGridService } from "./sendgrid.service";

/**
 * The main module of the application.
 */
@Module({
  imports: [
    // Importing the ConfigModule to handle application configuration
    ConfigModule.forRoot({
      isGlobal: true, // Make the configuration module global
    }),
  ],
  controllers: [], // Array of controllers used by the module
  providers: [
    AppService, // Application service
    KafkaConsumerService, // Kafka consumer service
    SendGridService, // SendGrid service
  ],
})
export class AppModule {}
