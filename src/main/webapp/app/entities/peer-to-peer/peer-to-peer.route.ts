import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPeerToPeer, PeerToPeer } from 'app/shared/model/peer-to-peer.model';
import { PeerToPeerService } from './peer-to-peer.service';
import { PeerToPeerComponent } from './peer-to-peer.component';
import { PeerToPeerDetailComponent } from './peer-to-peer-detail.component';
import { PeerToPeerUpdateComponent } from './peer-to-peer-update.component';

@Injectable({ providedIn: 'root' })
export class PeerToPeerResolve implements Resolve<IPeerToPeer> {
  constructor(private service: PeerToPeerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeerToPeer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((peerToPeer: HttpResponse<PeerToPeer>) => {
          if (peerToPeer.body) {
            return of(peerToPeer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PeerToPeer());
  }
}

export const peerToPeerRoute: Routes = [
  {
    path: '',
    component: PeerToPeerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.peerToPeer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeerToPeerDetailComponent,
    resolve: {
      peerToPeer: PeerToPeerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.peerToPeer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeerToPeerUpdateComponent,
    resolve: {
      peerToPeer: PeerToPeerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.peerToPeer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeerToPeerUpdateComponent,
    resolve: {
      peerToPeer: PeerToPeerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'saibDigitalWalletApp.peerToPeer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
