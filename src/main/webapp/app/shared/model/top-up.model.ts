import { IWallet } from 'app/shared/model/wallet.model';

export interface ITopUp {
  id?: number;
  amount?: number;
  currency?: string;
  transactionType?: string;
  narativeLine1?: string;
  narativeLine2?: string;
  narativeLine3?: string;
  narativeLine4?: string;
  clientRefNumber?: string;
  paymentDetails?: string;
  topups?: IWallet[];
}

export class TopUp implements ITopUp {
  constructor(
    public id?: number,
    public amount?: number,
    public currency?: string,
    public transactionType?: string,
    public narativeLine1?: string,
    public narativeLine2?: string,
    public narativeLine3?: string,
    public narativeLine4?: string,
    public clientRefNumber?: string,
    public paymentDetails?: string,
    public topups?: IWallet[]
  ) {}
}
