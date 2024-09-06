import { IsEmail, IsString } from "class-validator";

export class LoginDto {
  @IsEmail()
  email: string;
  @IsString()
  password: string;
}

export class LoginResultDto {
  constructor(accessToken: string, refreshToken: string) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.success = true;
  }
  accessToken: string;
  refreshToken: string;
  success: boolean;
}
