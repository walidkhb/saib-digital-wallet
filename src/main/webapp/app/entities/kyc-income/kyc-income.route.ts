import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKycIncome, KycIncome } from 'app/shared/model/kyc-income.model';
import { KycIncomeService } from './kyc-income.service';
import { KycIncomeComponent } from './kyc-income.component';
import { KycIncomeDetailComponent } from './kyc-income-detail.component';
import { KycIncomeUpdateComponent } from './kyc-income-update.component';

@Injectable({ providedIn: 'root' })
export class KycIncomeResolve implements Resolve<IKycIncome> {
  constructor(private service: KycIncomeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKycIncome> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kycIncome: HttpResponse<KycIncome>) => {
          if (kycIncome.body) {
            return of(kycIncome.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KycIncome());
  }
}

export const kycIncomeRoute: Routes = [
  {
    path: '',
    component: KycIncomeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycIncome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KycIncomeDetailComponent,
    resolve: {
      kycIncome: KycIncomeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycIncome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KycIncomeUpdateComponent,
    resolve: {
      kycIncome: KycIncomeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycIncome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KycIncomeUpdateComponent,
    resolve: {
      kycIncome: KycIncomeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycIncome.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
