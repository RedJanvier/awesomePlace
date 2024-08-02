import { IsEnum, IsString } from "class-validator";
import { LoginDto } from "./login.dto";
import { EStatus } from "../enums/status.enum";

export enum ESellerOrCustomer {
  SELLER = "SELLER",
  CUSTOMER = "CUSTOMER",
}

export class RegisterDto extends LoginDto {
  @IsString()
  username: string;

  @IsString()
  name: string;

  @IsEnum(ESellerOrCustomer)
  role: ESellerOrCustomer;

  status?: EStatus;
}
