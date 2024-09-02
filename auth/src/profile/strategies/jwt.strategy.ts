import { Injectable, UnauthorizedException } from "@nestjs/common";
import { ConfigService } from "@nestjs/config";
import { PassportStrategy } from "@nestjs/passport";
import { PrismaClient, User } from ".prisma/client";
import "dotenv/config";
import { ExtractJwt, Strategy } from "passport-jwt";
import { IConstants } from "../../_shared/interfaces/constants.interface";
import { JwtPayload } from "../interfaces/jwt.payload.interface";

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(private configService: ConfigService<IConstants>) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      secretOrKey: configService.get("jwt").secret,
    });
  }

  /**
   * Validate jwt user
   * @param payload Jwt payload
   * @returns user
   */
  async validate(payload: JwtPayload): Promise<User> {
    const { id } = payload;
    const user = await new PrismaClient().user.findUnique({ where: { id } });
    if (!user) {
      throw new UnauthorizedException();
    }
    return user;
  }
}
