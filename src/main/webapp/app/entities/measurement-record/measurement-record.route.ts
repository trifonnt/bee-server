import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMeasurementRecord, MeasurementRecord } from 'app/shared/model/measurement-record.model';
import { MeasurementRecordService } from './measurement-record.service';
import { MeasurementRecordComponent } from './measurement-record.component';
import { MeasurementRecordDetailComponent } from './measurement-record-detail.component';
import { MeasurementRecordUpdateComponent } from './measurement-record-update.component';

@Injectable({ providedIn: 'root' })
export class MeasurementRecordResolve implements Resolve<IMeasurementRecord> {
  constructor(private service: MeasurementRecordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeasurementRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((measurementRecord: HttpResponse<MeasurementRecord>) => {
          if (measurementRecord.body) {
            return of(measurementRecord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MeasurementRecord());
  }
}

export const measurementRecordRoute: Routes = [
  {
    path: '',
    component: MeasurementRecordComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'beeServerApp.measurementRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MeasurementRecordDetailComponent,
    resolve: {
      measurementRecord: MeasurementRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.measurementRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MeasurementRecordUpdateComponent,
    resolve: {
      measurementRecord: MeasurementRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.measurementRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MeasurementRecordUpdateComponent,
    resolve: {
      measurementRecord: MeasurementRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'beeServerApp.measurementRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
