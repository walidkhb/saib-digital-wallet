import { ICustomer } from 'app/shared/model/customer.model';
import { ITopUp } from 'app/shared/model/top-up.model';
import { IRefund } from 'app/shared/model/refund.model';
import { IPeerToPeer } from 'app/shared/model/peer-to-peer.model';

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
  wallets?: ICustomer[];
  topUp?: ITopUp;
  refund?: IRefund;
  peerToPeer?: IPeerToPeer;
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
    public wallets?: ICustomer[],
    public topUp?: ITopUp,
    public refund?: IRefund,
    public peerToPeer?: IPeerToPeer
  ) {}
}
