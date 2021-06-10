import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IArrete } from 'app/shared/model/arrete.model';

type EntityResponseType = HttpResponse<IArrete>;
type EntityArrayResponseType = HttpResponse<IArrete[]>;

@Injectable({ providedIn: 'root' })
export class ArreteService {
  public resourceUrl = SERVER_API_URL + 'api/arretes';

  constructor(protected http: HttpClient) {}

  create(arrete: IArrete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrete);
    return this.http
      .post<IArrete>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(arrete: IArrete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(arrete);
    return this.http
      .put<IArrete>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IArrete>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IArrete[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(arrete: IArrete): IArrete {
    const copy: IArrete = Object.assign({}, arrete, {
      dateSignature: arrete.dateSignature && arrete.dateSignature.isValid() ? arrete.dateSignature.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateSignature = res.body.dateSignature ? moment(res.body.dateSignature) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((arrete: IArrete) => {
        arrete.dateSignature = arrete.dateSignature ? moment(arrete.dateSignature) : undefined;
      });
    }
    return res;
  }
}
