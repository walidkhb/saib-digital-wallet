import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWallet, Wallet } from 'app/shared/model/wallet.model';
import { WalletService } from './wallet.service';
import { ITopUp } from 'app/shared/model/top-up.model';
import { TopUpService } from 'app/entities/top-up/top-up.service';
import { IRefund } from 'app/shared/model/refund.model';
import { RefundService } from 'app/entities/refund/refund.service';
import { IPeerToPeer } from 'app/shared/model/peer-to-peer.model';
import { PeerToPeerService } from 'app/entities/peer-to-peer/peer-to-peer.service';

type SelectableEntity = ITopUp | IRefund | IPeerToPeer;

@Component({
  selector: 'jhi-wallet-update',
  templateUrl: './wallet-update.component.html',
})
export class WalletUpdateComponent implements OnInit {
  isSaving = false;
  topups: ITopUp[] = [];
  refunds: IRefund[] = [];
  peertopeers: IPeerToPeer[] = [];

  editForm = this.fb.group({
    id: [],
    walletId: [],
    status: [],
    statusUpdateDateTime: [],
    currency: [],
    accountType: [],
    accountSubType: [],
    description: [],
    schemeName: [],
    identification: [],
    topUp: [],
    refund: [],
    peerToPeer: [],
  });

  constructor(
    protected walletService: WalletService,
    protected topUpService: TopUpService,
    protected refundService: RefundService,
    protected peerToPeerService: PeerToPeerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wallet }) => {
      this.updateForm(wallet);

      this.topUpService.query().subscribe((res: HttpResponse<ITopUp[]>) => (this.topups = res.body || []));

      this.refundService.query().subscribe((res: HttpResponse<IRefund[]>) => (this.refunds = res.body || []));

      this.peerToPeerService.query().subscribe((res: HttpResponse<IPeerToPeer[]>) => (this.peertopeers = res.body || []));
    });
  }

  updateForm(wallet: IWallet): void {
    this.editForm.patchValue({
      id: wallet.id,
      walletId: wallet.walletId,
      status: wallet.status,
      statusUpdateDateTime: wallet.statusUpdateDateTime,
      currency: wallet.currency,
      accountType: wallet.accountType,
      accountSubType: wallet.accountSubType,
      description: wallet.description,
      schemeName: wallet.schemeName,
      identification: wallet.identification,
      topUp: wallet.topUp,
      refund: wallet.refund,
      peerToPeer: wallet.peerToPeer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wallet = this.createFromForm();
    if (wallet.id !== undefined) {
      this.subscribeToSaveResponse(this.walletService.update(wallet));
    } else {
      this.subscribeToSaveResponse(this.walletService.create(wallet));
    }
  }

  private createFromForm(): IWallet {
    return {
      ...new Wallet(),
      id: this.editForm.get(['id'])!.value,
      walletId: this.editForm.get(['walletId'])!.value,
      status: this.editForm.get(['status'])!.value,
      statusUpdateDateTime: this.editForm.get(['statusUpdateDateTime'])!.value,
      currency: this.editForm.get(['currency'])!.value,
      accountType: this.editForm.get(['accountType'])!.value,
      accountSubType: this.editForm.get(['accountSubType'])!.value,
      description: this.editForm.get(['description'])!.value,
      schemeName: this.editForm.get(['schemeName'])!.value,
      identification: this.editForm.get(['identification'])!.value,
      topUp: this.editForm.get(['topUp'])!.value,
      refund: this.editForm.get(['refund'])!.value,
      peerToPeer: this.editForm.get(['peerToPeer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWallet>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
