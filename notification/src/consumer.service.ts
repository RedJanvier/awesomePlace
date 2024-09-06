import * as fs from "fs";
import { Injectable, OnApplicationShutdown } from "@nestjs/common";
import { Kafka, Consumer } from "kafkajs";
import { SendGridService } from "./sendgrid.service";
import { constants } from "./config/config";
import { IConstants } from "./interfaces/constants.interface";

/**
 * Service responsible for consuming messages from Kafka and sending activation emails.
 */
@Injectable()
export class KafkaConsumerService implements OnApplicationShutdown {
  CONSTANTS: IConstants = constants();
  /**
   * Constructor for KafkaConsumerService.
   * @param {SendGridService} sendGridService - Service responsible for sending emails using SendGrid.
   */
  constructor(private readonly sendGridService: SendGridService) {}

  /**
   * Kafka instance for connecting to Kafka brokers.
   */
  private readonly kafka = new Kafka({
    brokers: [this.CONSTANTS.brokerUrl],
  });

  /**
   * Kafka consumer instance.
   */
  private consumer: Consumer;

  /**
   * Lifecycle hook that gets invoked when the Nest application is shutting down.
   * Disconnects the Kafka consumer.
   */
  async onApplicationShutdown() {
    await this.consumer.disconnect();
  }

  /**
   * Start consuming messages from Kafka.
   */
  async consume() {
    this.consumer = this.kafka.consumer({ groupId: "notifications-consumer" });
    await this.consumer.connect();
    await this.consumer.subscribe({ topic: "notifications.register.evt" });

    await this.consumer.run({
      eachMessage: async ({ topic, partition, message }) => {
        console.log(
          `Received message from Kafka { topic: ${topic}, partition: ${partition}`,
        );
        const { user, link: activationLink } = JSON.parse(
          message.value.toString(),
        );
        this.sendActivationEmail(user.name, activationLink, user.email);
      },
    });
  }

  /**
   * Sends an activation email to the specified recipient.
   * @param {string} customerName - The name of the customer.
   * @param {string} activationLink - The Activation link associated with the account.
   * @param {string} recipientEmail - The email address of the recipient.
   */
  async sendActivationEmail(
    customerName: string,
    activationLink: string,
    recipientEmail: string,
  ) {
    const emailData = {
      to: recipientEmail,
      from: this.CONSTANTS.sendgridMail,
      subject: `Verify your email for ${this.CONSTANTS.instanceName}`,
      html: this.formatTemplate(
        {
          customerName,
          activationLink,
          instanceName: this.CONSTANTS.instanceName,
        },
        "EmailVerification.html",
      ),
    };

    try {
      await this.sendGridService.send(emailData);
      console.log("EMAIL SUCCESSFULLY SENT TO: " + recipientEmail);
    } catch (error) {
      console.error(
        `Error sending activation email of ${recipientEmail}:`,
        error,
      );
    }
  }

  formatTemplate(params: object, template: string): string {
    const templateUrl = "/templates/" + template;
    let htmlFile = fs.readFileSync(__dirname + templateUrl, "utf-8");

    for (const key in params) {
      if (Object.prototype.hasOwnProperty.call(params, key)) {
        const param = params[key];
        htmlFile = htmlFile.replaceAll(`{{${key}}}`, param);
      }
    }
    return htmlFile;
  }
}
