import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITopUp } from 'app/shared/model/top-up.model';

type EntityResponseType = HttpResponse<ITopUp>;
type EntityArrayResponseType = HttpResponse<ITopUp[]>;

@Injectable({ providedIn: 'root' })
export class TopUpService {
  public resourceUrl = SERVER_API_URL + 'api/top-ups';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/top-ups';

  constructor(protected http: HttpClient) {}

  create(topUp: ITopUp): Observable<EntityResponseType> {
    return this.http.post<ITopUp>(this.resourceUrl, topUp, { observe: 'response' });
  }

  update(topUp: ITopUp): Observable<EntityResponseType> {
    return this.http.put<ITopUp>(this.resourceUrl, topUp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITopUp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITopUp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITopUp[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
