import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMeasurementRecord } from 'app/shared/model/measurement-record.model';

type EntityResponseType = HttpResponse<IMeasurementRecord>;
type EntityArrayResponseType = HttpResponse<IMeasurementRecord[]>;

@Injectable({ providedIn: 'root' })
export class MeasurementRecordService {
  public resourceUrl = SERVER_API_URL + 'api/measurement-records';

  constructor(protected http: HttpClient) {}

  create(measurementRecord: IMeasurementRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(measurementRecord);
    return this.http
      .post<IMeasurementRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(measurementRecord: IMeasurementRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(measurementRecord);
    return this.http
      .put<IMeasurementRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMeasurementRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMeasurementRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(measurementRecord: IMeasurementRecord): IMeasurementRecord {
    const copy: IMeasurementRecord = Object.assign({}, measurementRecord, {
      recordedAt:
        measurementRecord.recordedAt && measurementRecord.recordedAt.isValid() ? measurementRecord.recordedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.recordedAt = res.body.recordedAt ? moment(res.body.recordedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((measurementRecord: IMeasurementRecord) => {
        measurementRecord.recordedAt = measurementRecord.recordedAt ? moment(measurementRecord.recordedAt) : undefined;
      });
    }
    return res;
  }
}
