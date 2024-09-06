export class ResponseDto {
  constructor(status: number, message: string, data = null, success = true) {
    this.success = success;
    this.status = status;
    this.message = message;
    this.data = data;
  }

  status: number;
  success: boolean;
  message: string;
  data: object;
}
