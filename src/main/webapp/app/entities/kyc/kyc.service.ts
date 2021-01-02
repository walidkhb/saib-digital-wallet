import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IKyc } from 'app/shared/model/kyc.model';

type EntityResponseType = HttpResponse<IKyc>;
type EntityArrayResponseType = HttpResponse<IKyc[]>;

@Injectable({ providedIn: 'root' })
export class KycService {
  public resourceUrl = SERVER_API_URL + 'api/kycs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/kycs';

  constructor(protected http: HttpClient) {}

  create(kyc: IKyc): Observable<EntityResponseType> {
    return this.http.post<IKyc>(this.resourceUrl, kyc, { observe: 'response' });
  }

  update(kyc: IKyc): Observable<EntityResponseType> {
    return this.http.put<IKyc>(this.resourceUrl, kyc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKyc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKyc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKyc[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
