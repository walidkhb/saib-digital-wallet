import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IKycIncome } from 'app/shared/model/kyc-income.model';

type EntityResponseType = HttpResponse<IKycIncome>;
type EntityArrayResponseType = HttpResponse<IKycIncome[]>;

@Injectable({ providedIn: 'root' })
export class KycIncomeService {
  public resourceUrl = SERVER_API_URL + 'api/kyc-incomes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/kyc-incomes';

  constructor(protected http: HttpClient) {}

  create(kycIncome: IKycIncome): Observable<EntityResponseType> {
    return this.http.post<IKycIncome>(this.resourceUrl, kycIncome, { observe: 'response' });
  }

  update(kycIncome: IKycIncome): Observable<EntityResponseType> {
    return this.http.put<IKycIncome>(this.resourceUrl, kycIncome, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKycIncome>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKycIncome[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKycIncome[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
