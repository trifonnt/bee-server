import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBeehive, Beehive } from 'app/shared/model/beehive.model';
import { BeehiveService } from './beehive.service';
import { BeehiveComponent } from './beehive.component';
import { BeehiveDetailComponent } from './beehive-detail.component';
import { BeehiveUpdateComponent } from './beehive-update.component';

@Injectable({ providedIn: 'root' })
export class BeehiveResolve implements Resolve<IBeehive> {
  constructor(private service: BeehiveService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBeehive> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((beehive: HttpResponse<Beehive>) => {
          if (beehive.body) {
            return of(beehive.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Beehive());
  }
}

export const beehiveRoute: Routes = [
  {
    path: '',
    component: BeehiveComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'beeServerApp.beehive.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BeehiveDetailComponent,
    resolve: {
      beehive: BeehiveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.beehive.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BeehiveUpdateComponent,
    resolve: {
      beehive: BeehiveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.beehive.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BeehiveUpdateComponent,
    resolve: {
      beehive: BeehiveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.beehive.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
