import { INestApplication, UnauthorizedException } from "@nestjs/common";
import { CorsOptions } from "@nestjs/common/interfaces/external/cors-options.interface";
import { ConfigService } from "@nestjs/config";
import { DocumentBuilder, SwaggerModule } from "@nestjs/swagger";
import { IConstants } from "../interfaces/constants.interface";

/**
 * Defines the application config variables
 * @returns the Application config variables
 */
export function constants(): IConstants {
  return {
    port: +process.env.PORT,
    databaseUrl: process.env.DATABASE_URL,
    allowedOrigins: process.env.ALLOWED_ORIGINS?.split(","),
    swaggerEnabled: process.env.SWAGGER_ENABLED === "true",
    env: process.env.NODE_ENV,
    jwt: {
      secret: process.env.JWT_SECRET,
      expiresIn: process.env.JWT_EXPIRES_IN,
    },
    adminEmail: process.env.ADMIN_EMAIL,
    adminPassword: process.env.ADMIN_PASSWORD,
    brokerUrl: process.env.BROKER_URL || "localhost:9092",
  };
}

/**
 * Configures and binds Swagger with the project's application
 * @param app The NestJS Application instance
 */
export function configureSwagger(app: INestApplication): void {
  const API_TITLE = "Awesome Marketplace";
  const API_DESCRIPTION = "API Doc. for Awesome Marketplace API";
  const API_VERSION = "1.0";
  const SWAGGER_URL = "api/auth/swagger";
  const options = new DocumentBuilder()
    .setTitle(API_TITLE)
    .setDescription(API_DESCRIPTION)
    .setVersion(API_VERSION)
    .addBearerAuth()
    .addApiKey(
      { name: "awesome_marketplace_refresh_jwt", in: "header", type: "apiKey" },
      "refresh",
    )
    .build();
  const document = SwaggerModule.createDocument(app, options);
  SwaggerModule.setup(SWAGGER_URL, app, document, {
    customSiteTitle: "Awesome Marketplace API",
    customCss: ".swagger-ui .topbar { display: none }",
    swaggerOptions: {
      docExpansion: "none",
      persistAuthorization: true,
      apisSorter: "alpha",
      operationsSorter: "method",
      tagsSorter: "alpha",
    },
  });
}

/**
 * Generates obj for the app's CORS configurations
 * @returns CORS configurations
 */
export function corsConfig(): CorsOptions {
  return {
    allowedHeaders:
      "Origin, X-Requested-With, Content-Type, Accept, Authorization, Set-Cookie, Cookies",
    credentials: true,
    origin: (origin, callback) => {
      const appConfiguration = constants();
      const { allowedOrigins = [] } = appConfiguration;
      const canAllowUndefinedOrigin =
        origin === undefined && appConfiguration.env !== "production";

      if (allowedOrigins.indexOf(origin) !== -1 || canAllowUndefinedOrigin) {
        callback(null, true);
      } else {
        callback(
          new UnauthorizedException(
            `Not allowed by CORS for origin:${origin} on ${appConfiguration.env}`,
          ),
        );
      }
    },
    methods: "GET,HEAD,PUT,PATCH,POST,DELETE,OPTIONS",
  };
}

/**
 * Configure app instance
 * @param {INestApplication} app - Application instance
 */
export function configure(app: INestApplication): void {
  app.setGlobalPrefix("api");
  app.enableCors(corsConfig());
  const configService = app.get(ConfigService<IConstants>);
  if (configService.get("swaggerEnabled")) configureSwagger(app);
}
