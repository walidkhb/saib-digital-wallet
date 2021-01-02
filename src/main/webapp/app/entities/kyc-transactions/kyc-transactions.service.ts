import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IKycTransactions } from 'app/shared/model/kyc-transactions.model';

type EntityResponseType = HttpResponse<IKycTransactions>;
type EntityArrayResponseType = HttpResponse<IKycTransactions[]>;

@Injectable({ providedIn: 'root' })
export class KycTransactionsService {
  public resourceUrl = SERVER_API_URL + 'api/kyc-transactions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/kyc-transactions';

  constructor(protected http: HttpClient) {}

  create(kycTransactions: IKycTransactions): Observable<EntityResponseType> {
    return this.http.post<IKycTransactions>(this.resourceUrl, kycTransactions, { observe: 'response' });
  }

  update(kycTransactions: IKycTransactions): Observable<EntityResponseType> {
    return this.http.put<IKycTransactions>(this.resourceUrl, kycTransactions, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKycTransactions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKycTransactions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKycTransactions[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
