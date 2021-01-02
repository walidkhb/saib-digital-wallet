import { IKyc } from 'app/shared/model/kyc.model';

export interface IKycTransactions {
  id?: number;
  creditCount?: number;
  creditAmount?: number;
  debitCount?: number;
  debitAmount?: number;
  remittanceCount?: number;
  remittanceAmount?: number;
  kycTransactions?: IKyc;
}

export class KycTransactions implements IKycTransactions {
  constructor(
    public id?: number,
    public creditCount?: number,
    public creditAmount?: number,
    public debitCount?: number,
    public debitAmount?: number,
    public remittanceCount?: number,
    public remittanceAmount?: number,
    public kycTransactions?: IKyc
  ) {}
}
