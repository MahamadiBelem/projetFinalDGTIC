import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDomaine } from 'app/shared/model/domaine.model';

type EntityResponseType = HttpResponse<IDomaine>;
type EntityArrayResponseType = HttpResponse<IDomaine[]>;

@Injectable({ providedIn: 'root' })
export class DomaineService {
  public resourceUrl = SERVER_API_URL + 'api/domaines';

  constructor(protected http: HttpClient) {}

  create(domaine: IDomaine): Observable<EntityResponseType> {
    return this.http.post<IDomaine>(this.resourceUrl, domaine, { observe: 'response' });
  }

  update(domaine: IDomaine): Observable<EntityResponseType> {
    return this.http.put<IDomaine>(this.resourceUrl, domaine, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDomaine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDomaine[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
