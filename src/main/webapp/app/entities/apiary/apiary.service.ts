import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApiary } from 'app/shared/model/apiary.model';

type EntityResponseType = HttpResponse<IApiary>;
type EntityArrayResponseType = HttpResponse<IApiary[]>;

@Injectable({ providedIn: 'root' })
export class ApiaryService {
  public resourceUrl = SERVER_API_URL + 'api/apiaries';

  constructor(protected http: HttpClient) {}

  create(apiary: IApiary): Observable<EntityResponseType> {
    return this.http.post<IApiary>(this.resourceUrl, apiary, { observe: 'response' });
  }

  update(apiary: IApiary): Observable<EntityResponseType> {
    return this.http.put<IApiary>(this.resourceUrl, apiary, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IApiary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IApiary[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
