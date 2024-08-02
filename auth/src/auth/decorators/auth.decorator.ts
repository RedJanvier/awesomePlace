import { applyDecorators, UseGuards } from "@nestjs/common";
import { ApiBearerAuth } from "@nestjs/swagger";
import { ERole } from "../enums/role.enum";
import { JwtGuard } from "../guards/jwt.guard";
import { RolesGuard } from "../guards/roles.guard";
import { AllowRoles } from "./roles.decorator";

/**
 * Apply authentication decorators
 * @param roles array of roles
 * @returns
 */
export function Auth(...roles: ERole[]) {
  return applyDecorators(
    ApiBearerAuth(),
    UseGuards(JwtGuard, RolesGuard),
    AllowRoles(...roles),
  );
}
