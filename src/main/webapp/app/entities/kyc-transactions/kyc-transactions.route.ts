import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKycTransactions, KycTransactions } from 'app/shared/model/kyc-transactions.model';
import { KycTransactionsService } from './kyc-transactions.service';
import { KycTransactionsComponent } from './kyc-transactions.component';
import { KycTransactionsDetailComponent } from './kyc-transactions-detail.component';
import { KycTransactionsUpdateComponent } from './kyc-transactions-update.component';

@Injectable({ providedIn: 'root' })
export class KycTransactionsResolve implements Resolve<IKycTransactions> {
  constructor(private service: KycTransactionsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKycTransactions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kycTransactions: HttpResponse<KycTransactions>) => {
          if (kycTransactions.body) {
            return of(kycTransactions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KycTransactions());
  }
}

export const kycTransactionsRoute: Routes = [
  {
    path: '',
    component: KycTransactionsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycTransactions.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KycTransactionsDetailComponent,
    resolve: {
      kycTransactions: KycTransactionsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycTransactions.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KycTransactionsUpdateComponent,
    resolve: {
      kycTransactions: KycTransactionsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycTransactions.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KycTransactionsUpdateComponent,
    resolve: {
      kycTransactions: KycTransactionsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycTransactions.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
