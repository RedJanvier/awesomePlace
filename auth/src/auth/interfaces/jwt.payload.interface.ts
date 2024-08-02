import { ERole } from "../enums/role.enum";

export interface JwtPayload {
  id: number;
  role: ERole;
}
