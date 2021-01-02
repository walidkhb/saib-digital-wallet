import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IKycPersonal } from 'app/shared/model/kyc-personal.model';

type EntityResponseType = HttpResponse<IKycPersonal>;
type EntityArrayResponseType = HttpResponse<IKycPersonal[]>;

@Injectable({ providedIn: 'root' })
export class KycPersonalService {
  public resourceUrl = SERVER_API_URL + 'api/kyc-personals';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/kyc-personals';

  constructor(protected http: HttpClient) {}

  create(kycPersonal: IKycPersonal): Observable<EntityResponseType> {
    return this.http.post<IKycPersonal>(this.resourceUrl, kycPersonal, { observe: 'response' });
  }

  update(kycPersonal: IKycPersonal): Observable<EntityResponseType> {
    return this.http.put<IKycPersonal>(this.resourceUrl, kycPersonal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKycPersonal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKycPersonal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKycPersonal[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
