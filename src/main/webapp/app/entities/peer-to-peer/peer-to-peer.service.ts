import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IPeerToPeer } from 'app/shared/model/peer-to-peer.model';

type EntityResponseType = HttpResponse<IPeerToPeer>;
type EntityArrayResponseType = HttpResponse<IPeerToPeer[]>;

@Injectable({ providedIn: 'root' })
export class PeerToPeerService {
  public resourceUrl = SERVER_API_URL + 'api/peer-to-peers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/peer-to-peers';

  constructor(protected http: HttpClient) {}

  create(peerToPeer: IPeerToPeer): Observable<EntityResponseType> {
    return this.http.post<IPeerToPeer>(this.resourceUrl, peerToPeer, { observe: 'response' });
  }

  update(peerToPeer: IPeerToPeer): Observable<EntityResponseType> {
    return this.http.put<IPeerToPeer>(this.resourceUrl, peerToPeer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPeerToPeer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPeerToPeer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPeerToPeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
