import { ICustomer } from 'app/shared/model/customer.model';

export interface IWallet {
  id?: number;
  walletId?: string;
  status?: string;
  statusUpdateDateTime?: string;
  currency?: string;
  accountType?: string;
  accountSubType?: string;
  description?: string;
  schemeName?: string;
  identification?: string;
  wallet?: ICustomer;
}

export class Wallet implements IWallet {
  constructor(
    public id?: number,
    public walletId?: string,
    public status?: string,
    public statusUpdateDateTime?: string,
    public currency?: string,
    public accountType?: string,
    public accountSubType?: string,
    public description?: string,
    public schemeName?: string,
    public identification?: string,
    public wallet?: ICustomer
  ) {}
}
