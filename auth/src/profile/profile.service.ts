import { BadRequestException, Injectable } from "@nestjs/common";
import { JwtService } from "@nestjs/jwt";
import { User } from ".prisma/client";
import * as bcrypt from "bcryptjs";
import { PrismaService } from "../prisma.service";
import { LoginDto } from "./dto/login.dto";
import { RegisterDto } from "./dto/register.dto";
import { ERole } from "./enums/role.enum";
import { JwtPayload } from "./interfaces/jwt.payload.interface";
import { EStatus } from "./enums/status.enum";
import { KafkaProducerService } from "../producer/producer.service";

@Injectable()
export class ProfileService {
  constructor(
    private readonly jwtService: JwtService,
    private readonly prismaService: PrismaService,
    private readonly KafkaProducerService: KafkaProducerService,
  ) {}

  publishMessage(message: any): Promise<void> {
    return this.KafkaProducerService.publish(
      "notifications.register.evt",
      message,
    );
  }

  /**
   * Register user
   * @param dto REGISTER DTO
   */
  async register(dto: RegisterDto) {
    const { email, password, username, role, name: fullname, status } = dto;
    const user = await this.prismaService.user.findFirst({
      where: {
        email,
      },
    });
    if (user) {
      throw new BadRequestException("User already exists");
    }
    const hashedPassword = await this.hashPassword(password);
    const newUser = await this.prismaService.user.create({
      data: {
        email,
        password: hashedPassword,
        username,
        role: role != null ? role : ERole.CUSTOMER,
        status: status != null ? status : EStatus.NEW,
      },
    });
    await this.prismaService.userProfile.create({
      data: {
        name: fullname,
        userId: newUser.id,
      },
    });
    const { refreshToken } = await this.generateTokens({
      id: newUser.id,
      role: newUser.role as ERole,
    });
    await this.prismaService.user.update({
      where: { id: newUser.id },
      data: { refreshToken },
    });

    if (!status || (status && status != EStatus.VERIFIED))
      this.publishMessage({
        link: "http://localhost:8080/api/auth/verify?token=" + refreshToken,
        user: {
          name: newUser.username,
          email: newUser.email,
        },
      });
    return `Check your email (${newUser.email}) to verify your account.`;
  }

  /**
   * LOGIN
   * @param dto LOGIN DTO
   * @returns
   */
  async login(dto: LoginDto) {
    const { email, password } = dto;
    const user = await this.prismaService.user.findFirst({
      where: {
        email,
      },
    });
    if (!user)
      throw new BadRequestException("The email or password is incorrect");
    const isMatch = this.comparePassword(password, user.password);
    if (!isMatch) {
      throw new BadRequestException("The email or password is incorrect");
    }
    const { accessToken, refreshToken } = await this.generateTokens({
      id: user.id,
      role: user.role as ERole,
    });
    await this.prismaService.user.update({
      where: { id: user.id },
      data: { refreshToken },
    });

    return {
      accessToken,
      refreshToken,
    };
  }

  /**
   * VERIFY
   * @param dto LOGIN DTO
   * @returns
   */
  async verify(reToken: string) {
    const user = await this.prismaService.user.findFirst({
      where: { refreshToken: reToken },
    });
    if (!user)
      throw new BadRequestException("The user to verify doesn't exist!");

    await this.prismaService.user.update({
      where: { id: user.id },
      data: { status: EStatus.VERIFIED },
    });
    const { accessToken, refreshToken } = await this.generateTokens({
      id: user.id,
      role: user.role as ERole,
    });
    await this.prismaService.user.update({
      where: { id: user.id },
      data: { refreshToken, status: EStatus.VERIFIED },
    });

    return {
      accessToken,
      refreshToken,
    };
  }

  /**
   * Get user profile
   * @param user logged in user
   * @returns user profile
   */
  async getProfile(user: User): Promise<Partial<User>> {
    const profile = await this.prismaService.user.findFirst({
      where: {
        id: user.id,
      },
    });
    delete profile.password;
    return profile;
  }

  /**
   * Refresh token
   * @param param0 user
   * @returns tokens
   */
  async refreshToken({
    id,
    role,
    refreshToken,
  }: User): Promise<{ accessToken: string; refreshToken: string }> {
    return {
      accessToken: await this.jwtService.signAsync({
        id,
        role,
      }),
      refreshToken,
    };
  }

  /**
   * Log out
   * @param param0 user
   * @returns
   */
  async logout({ id }: User): Promise<{ accessToken: string }> {
    await this.prismaService.user.update({
      where: { id },
      data: { refreshToken: null },
    });
    return;
  }

  /**
   * Generate access and refresh tokens
   * @param param0 Jwt payload
   * @returns tokens
   */
  private async generateTokens({ id, role }: JwtPayload) {
    const accessToken = await this.jwtService.signAsync({
      id,
      role,
    });
    const refreshToken = await this.jwtService.signAsync({
      id,
    });
    return { accessToken, refreshToken };
  }

  /**
   * Hash password
   * @param password Password to hash
   * @returns password hash
   */
  hashPassword(password: any) {
    const salt = bcrypt.genSaltSync();
    const hashedPassword = bcrypt.hashSync(password, salt);
    return hashedPassword;
  }
  /**
   * Compare password with hash
   * @param password Password to compare
   * @param hash Password hash
   * @returns boolean result
   */
  comparePassword(password: string, hash: string) {
    return bcrypt.compareSync(password, hash);
  }
}
