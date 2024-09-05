import { IsEmail, IsString } from "class-validator";
import { ResponseDto } from "../../_shared/dto/response.dto";
import { HttpStatus } from "@nestjs/common";

export class LoginDto {
  @IsEmail()
  email: string;
  @IsString()
  password: string;
}

export class LoginResultDto extends ResponseDto {
  constructor(accessToken: string, refreshToken: string) {
    super(HttpStatus.OK, "Successful login", { accessToken, refreshToken });
  }
}
