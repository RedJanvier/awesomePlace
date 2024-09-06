import { Global, Module } from "@nestjs/common";
import { ConfigModule, ConfigService } from "@nestjs/config";
import { JwtModule } from "@nestjs/jwt";
import { PassportModule } from "@nestjs/passport";
import { IConstants } from "../_shared/interfaces/constants.interface";
import { ProfileController } from "./profile.controller";
import { ProfileService } from "./profile.service";
import { JwtStrategy } from "./strategies/jwt.strategy";
import { JwtRefreshStrategy } from "./strategies/refresh-jwt.strategy";
import { KafkaProducerModule } from "src/producer/producer.module";

@Global()
@Module({
  imports: [
    PassportModule,
    JwtModule.registerAsync({
      imports: [ConfigModule],
      useFactory: async (configService: ConfigService<IConstants>) => ({
        secret: configService.get("jwt").secret,
        signOptions: {
          expiresIn: configService.get("jwt").expiresIn,
          issuer: "awesome-marketplace-api",
        },
      }),
      inject: [ConfigService],
    }),
    KafkaProducerModule,
  ],
  controllers: [ProfileController],
  providers: [ProfileService, JwtStrategy, JwtRefreshStrategy, ConfigService],
  exports: [JwtModule, ProfileService],
})
export class AuthModule {}
