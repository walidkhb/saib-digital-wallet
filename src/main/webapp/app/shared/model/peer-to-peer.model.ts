import { IWallet } from 'app/shared/model/wallet.model';

export interface IPeerToPeer {
  id?: number;
  amount?: number;
  currency?: string;
  beneficiaryRelationship?: string;
  purposeOfTransfer?: string;
  transactionType?: string;
  paymentDetails?: string;
  peertopeers?: IWallet[];
}

export class PeerToPeer implements IPeerToPeer {
  constructor(
    public id?: number,
    public amount?: number,
    public currency?: string,
    public beneficiaryRelationship?: string,
    public purposeOfTransfer?: string,
    public transactionType?: string,
    public paymentDetails?: string,
    public peertopeers?: IWallet[]
  ) {}
}
