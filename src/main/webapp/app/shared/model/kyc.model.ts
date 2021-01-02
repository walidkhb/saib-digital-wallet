import { ICustomer } from 'app/shared/model/customer.model';

export interface IKyc {
  id?: number;
  kycInfo?: ICustomer;
}

export class Kyc implements IKyc {
  constructor(public id?: number, public kycInfo?: ICustomer) {}
}
