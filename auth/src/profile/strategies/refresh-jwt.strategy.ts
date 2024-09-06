import { Injectable, UnauthorizedException } from "@nestjs/common";
import { ConfigService } from "@nestjs/config";
import { PassportStrategy } from "@nestjs/passport";
import { PrismaClient, User } from ".prisma/client";
import "dotenv/config";
import { Request } from "express";
import { ExtractJwt, Strategy } from "passport-jwt";
import { IConstants } from "../../_shared/interfaces/constants.interface";
import { JwtPayload } from "../interfaces/jwt.payload.interface";

@Injectable()
export class JwtRefreshStrategy extends PassportStrategy(
  Strategy,
  "jwt-refresh",
) {
  constructor(private configService: ConfigService<IConstants>) {
    super({
      jwtFromRequest: ExtractJwt.fromExtractors([
        (request: Request) => request?.headers?.classifieds_refresh_jwt,
      ]),
      secretOrKey: configService.get("jwt").secret,
      passReqToCallback: true,
      ignoreExpiration: true,
    });
  }
  /**
   * Validate refresh token
   * @param req Request
   * @param payload Jwt payload
   * @returns user
   */
  async validate(req: Request, payload: JwtPayload): Promise<User> {
    if (!req.headers?.classifieds_refresh_jwt)
      throw new UnauthorizedException();
    const { id } = payload;
    const user = await new PrismaClient().user.findFirst({
      where: {
        id,
        refreshToken: req.headers.classifieds_refresh_jwt as string,
      },
    });
    if (!user) throw new UnauthorizedException();
    return user;
  }
}
