import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';

/**
 * Bootstrap function for initializing the Nest application.
 * Creates an instance of the application and listens on port 4000.
 */
async function bootstrap() {
  // Create an instance of the Nest application
  const app = await NestFactory.create(AppModule);

  // Start listening on port 4000
  await app.listen(6000);
}

// Call the bootstrap function to start the application
bootstrap();
