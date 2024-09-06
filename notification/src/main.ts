import { NestFactory } from "@nestjs/core";
import { AppModule } from "./app.module";
import { ConfigService } from "@nestjs/config";
import { IConstants } from "./interfaces/constants.interface";

/**
 * Bootstrap function for initializing the Nest application.
 * Creates an instance of the application and listens on port 4000.
 */
async function bootstrap() {
  // Create an instance of the Nest application
  const app = await NestFactory.create(AppModule);

  const configService = app.get(ConfigService<IConstants>);
  // Start listening on port
  await app.listen(configService.get("port"));
}

// Call the bootstrap function to start the application
bootstrap();
