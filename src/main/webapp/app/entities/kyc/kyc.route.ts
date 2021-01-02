import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKyc, Kyc } from 'app/shared/model/kyc.model';
import { KycService } from './kyc.service';
import { KycComponent } from './kyc.component';
import { KycDetailComponent } from './kyc-detail.component';
import { KycUpdateComponent } from './kyc-update.component';

@Injectable({ providedIn: 'root' })
export class KycResolve implements Resolve<IKyc> {
  constructor(private service: KycService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKyc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kyc: HttpResponse<Kyc>) => {
          if (kyc.body) {
            return of(kyc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Kyc());
  }
}

export const kycRoute: Routes = [
  {
    path: '',
    component: KycComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kyc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KycDetailComponent,
    resolve: {
      kyc: KycResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kyc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KycUpdateComponent,
    resolve: {
      kyc: KycResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kyc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KycUpdateComponent,
    resolve: {
      kyc: KycResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kyc.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
