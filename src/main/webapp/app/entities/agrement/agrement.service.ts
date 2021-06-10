import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAgrement } from 'app/shared/model/agrement.model';

type EntityResponseType = HttpResponse<IAgrement>;
type EntityArrayResponseType = HttpResponse<IAgrement[]>;

@Injectable({ providedIn: 'root' })
export class AgrementService {
  public resourceUrl = SERVER_API_URL + 'api/agrements';

  constructor(protected http: HttpClient) {}

  create(agrement: IAgrement): Observable<EntityResponseType> {
    return this.http.post<IAgrement>(this.resourceUrl, agrement, { observe: 'response' });
  }

  update(agrement: IAgrement): Observable<EntityResponseType> {
    return this.http.put<IAgrement>(this.resourceUrl, agrement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgrement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgrement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
