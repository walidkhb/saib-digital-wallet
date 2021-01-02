import { IKyc } from 'app/shared/model/kyc.model';

export interface IKycPersonal {
  id?: number;
  primarySource?: string;
  primaryAmount?: number;
  pecondarySource?: string;
  pecondaryAmount?: number;
  kycPersonal?: IKyc;
}

export class KycPersonal implements IKycPersonal {
  constructor(
    public id?: number,
    public primarySource?: string,
    public primaryAmount?: number,
    public pecondarySource?: string,
    public pecondaryAmount?: number,
    public kycPersonal?: IKyc
  ) {}
}
