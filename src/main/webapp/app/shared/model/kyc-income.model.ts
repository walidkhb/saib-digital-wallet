import { IKyc } from 'app/shared/model/kyc.model';

export interface IKycIncome {
  id?: number;
  primarySource?: string;
  primaryAmount?: number;
  pecondarySource?: string;
  pecondaryAmount?: number;
  kycIncome?: IKyc;
}

export class KycIncome implements IKycIncome {
  constructor(
    public id?: number,
    public primarySource?: string,
    public primaryAmount?: number,
    public pecondarySource?: string,
    public pecondaryAmount?: number,
    public kycIncome?: IKyc
  ) {}
}
