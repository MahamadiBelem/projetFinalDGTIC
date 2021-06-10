import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQualification, Qualification } from 'app/shared/model/qualification.model';
import { QualificationService } from './qualification.service';
import { QualificationComponent } from './qualification.component';
import { QualificationDetailComponent } from './qualification-detail.component';
import { QualificationUpdateComponent } from './qualification-update.component';

@Injectable({ providedIn: 'root' })
export class QualificationResolve implements Resolve<IQualification> {
  constructor(private service: QualificationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQualification> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((qualification: HttpResponse<Qualification>) => {
          if (qualification.body) {
            return of(qualification.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Qualification());
  }
}

export const qualificationRoute: Routes = [
  {
    path: '',
    component: QualificationComponent,
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'Qualifications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QualificationDetailComponent,
    resolve: {
      qualification: QualificationResolve,
    },
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Qualifications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QualificationUpdateComponent,
    resolve: {
      qualification: QualificationResolve,
    },
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Qualifications',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QualificationUpdateComponent,
    resolve: {
      qualification: QualificationResolve,
    },
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Qualifications',
    },
    canActivate: [UserRouteAccessService],
  },
];
