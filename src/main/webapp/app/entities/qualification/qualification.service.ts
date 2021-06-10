import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQualification } from 'app/shared/model/qualification.model';

type EntityResponseType = HttpResponse<IQualification>;
type EntityArrayResponseType = HttpResponse<IQualification[]>;

@Injectable({ providedIn: 'root' })
export class QualificationService {
  public resourceUrl = SERVER_API_URL + 'api/qualifications';

  constructor(protected http: HttpClient) {}

  create(qualification: IQualification): Observable<EntityResponseType> {
    return this.http.post<IQualification>(this.resourceUrl, qualification, { observe: 'response' });
  }

  update(qualification: IQualification): Observable<EntityResponseType> {
    return this.http.put<IQualification>(this.resourceUrl, qualification, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQualification>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQualification[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
