import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApiary, Apiary } from 'app/shared/model/apiary.model';
import { ApiaryService } from './apiary.service';
import { ApiaryComponent } from './apiary.component';
import { ApiaryDetailComponent } from './apiary-detail.component';
import { ApiaryUpdateComponent } from './apiary-update.component';

@Injectable({ providedIn: 'root' })
export class ApiaryResolve implements Resolve<IApiary> {
  constructor(private service: ApiaryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApiary> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((apiary: HttpResponse<Apiary>) => {
          if (apiary.body) {
            return of(apiary.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Apiary());
  }
}

export const apiaryRoute: Routes = [
  {
    path: '',
    component: ApiaryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'beeServerApp.apiary.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ApiaryDetailComponent,
    resolve: {
      apiary: ApiaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.apiary.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ApiaryUpdateComponent,
    resolve: {
      apiary: ApiaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.apiary.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ApiaryUpdateComponent,
    resolve: {
      apiary: ApiaryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.apiary.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
