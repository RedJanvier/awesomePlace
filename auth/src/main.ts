import { ConfigService } from "@nestjs/config";
import { NestFactory } from "@nestjs/core";
import { AppModule } from "./app.module";
import { PrismaService } from "./prisma.service";
import { configure } from "./_shared/config/config";

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  const prismaService = app.get(PrismaService);
  await prismaService.enableShutdownHooks(app); // Enable prisma shutdown hooks to close db properly
  prismaService.applyPrismaMiddleware(); // Apply prisma middleware for delete and find methods,...

  configure(app);
  const port = app.get(ConfigService).get("port");
  await app.listen(port || 8000);
}
bootstrap();
