import { IConstants } from "../interfaces/constants.interface";

/**
 * Defines the application config variables
 * @returns the Application config variables
 */
export function constants(): IConstants {
  return {
    port: +process.env.PORT,
    instanceName: process.env.INSTANCE_NAME,
    brokerUrl: process.env.BROKER_URL,
    sendgridKey: process.env.SEND_GRID_KEY,
    sendgridMail: process.env.SEND_GRID_EMAIL,
    serverUrl: process.env.SERVER_URL || "http://localhost:8080",
  };
}
