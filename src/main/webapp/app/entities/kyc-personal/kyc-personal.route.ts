import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKycPersonal, KycPersonal } from 'app/shared/model/kyc-personal.model';
import { KycPersonalService } from './kyc-personal.service';
import { KycPersonalComponent } from './kyc-personal.component';
import { KycPersonalDetailComponent } from './kyc-personal-detail.component';
import { KycPersonalUpdateComponent } from './kyc-personal-update.component';

@Injectable({ providedIn: 'root' })
export class KycPersonalResolve implements Resolve<IKycPersonal> {
  constructor(private service: KycPersonalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKycPersonal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kycPersonal: HttpResponse<KycPersonal>) => {
          if (kycPersonal.body) {
            return of(kycPersonal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KycPersonal());
  }
}

export const kycPersonalRoute: Routes = [
  {
    path: '',
    component: KycPersonalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycPersonal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: KycPersonalDetailComponent,
    resolve: {
      kycPersonal: KycPersonalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycPersonal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: KycPersonalUpdateComponent,
    resolve: {
      kycPersonal: KycPersonalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycPersonal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: KycPersonalUpdateComponent,
    resolve: {
      kycPersonal: KycPersonalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.kycPersonal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
