import { applyDecorators, UseGuards } from "@nestjs/common";
import { ApiBearerAuth } from "@nestjs/swagger";
import { ERole } from "../../auth/enums/role.enum";
import { JwtGuard } from "../../auth/guards/jwt.guard";
import { RolesGuard } from "../../auth/guards/roles.guard";
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
