import { IWallet } from 'app/shared/model/wallet.model';

export interface IRefund {
  id?: number;
  amount?: number;
  currency?: string;
  narativeLine1?: string;
  narativeLine2?: string;
  refund?: IWallet;
}

export class Refund implements IRefund {
  constructor(
    public id?: number,
    public amount?: number,
    public currency?: string,
    public narativeLine1?: string,
    public narativeLine2?: string,
    public refund?: IWallet
  ) {}
}
