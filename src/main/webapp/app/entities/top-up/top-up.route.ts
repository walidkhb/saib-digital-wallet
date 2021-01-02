import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITopUp, TopUp } from 'app/shared/model/top-up.model';
import { TopUpService } from './top-up.service';
import { TopUpComponent } from './top-up.component';
import { TopUpDetailComponent } from './top-up-detail.component';
import { TopUpUpdateComponent } from './top-up-update.component';

@Injectable({ providedIn: 'root' })
export class TopUpResolve implements Resolve<ITopUp> {
  constructor(private service: TopUpService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITopUp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((topUp: HttpResponse<TopUp>) => {
          if (topUp.body) {
            return of(topUp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TopUp());
  }
}

export const topUpRoute: Routes = [
  {
    path: '',
    component: TopUpComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.topUp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TopUpDetailComponent,
    resolve: {
      topUp: TopUpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.topUp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TopUpUpdateComponent,
    resolve: {
      topUp: TopUpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.topUp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TopUpUpdateComponent,
    resolve: {
      topUp: TopUpResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.topUp.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
