import * as SendGrid from '@sendgrid/mail';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

/**
 * Service for sending emails using SendGrid.
 */
@Injectable()
export class SendGridService {
  /**
   * Constructor for SendGridService.
   * @param {ConfigService} configService - NestJS ConfigService for accessing environment variables.
   */
  constructor(private readonly configService: ConfigService) {
    SendGrid.setApiKey(this.configService.get<string>('SEND_GRID_KEY'));
  }

  /**
   * Sends an email using SendGrid.
   * @param {SendGrid.MailDataRequired} mail - The email data to be sent.
   * @returns {Promise<any>} A promise that resolves with the SendGrid response upon successful sending.
   */
  async send(mail: SendGrid.MailDataRequired): Promise<any> {
    try {
      // Log the email data before sending
      console.log(mail);

      // Set the 'from' field of the email
      mail.from = this.configService.get<string>('SEND_GRID_EMAIL');

      // Send the email using SendGrid
      const transport = await SendGrid.send(mail);

      // Return the SendGrid transport information
      return transport;
    } catch (error) {
      // Log any errors that occur during email sending
      console.error(error);
    }
  }
}
