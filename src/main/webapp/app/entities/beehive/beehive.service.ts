import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBeehive } from 'app/shared/model/beehive.model';

type EntityResponseType = HttpResponse<IBeehive>;
type EntityArrayResponseType = HttpResponse<IBeehive[]>;

@Injectable({ providedIn: 'root' })
export class BeehiveService {
  public resourceUrl = SERVER_API_URL + 'api/beehives';

  constructor(protected http: HttpClient) {}

  create(beehive: IBeehive): Observable<EntityResponseType> {
    return this.http.post<IBeehive>(this.resourceUrl, beehive, { observe: 'response' });
  }

  update(beehive: IBeehive): Observable<EntityResponseType> {
    return this.http.put<IBeehive>(this.resourceUrl, beehive, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBeehive>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeehive[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
