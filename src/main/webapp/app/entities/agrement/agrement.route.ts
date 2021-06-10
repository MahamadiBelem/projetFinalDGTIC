import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAgrement, Agrement } from 'app/shared/model/agrement.model';
import { AgrementService } from './agrement.service';
import { AgrementComponent } from './agrement.component';
import { AgrementDetailComponent } from './agrement-detail.component';
import { AgrementUpdateComponent } from './agrement-update.component';

@Injectable({ providedIn: 'root' })
export class AgrementResolve implements Resolve<IAgrement> {
  constructor(private service: AgrementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAgrement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((agrement: HttpResponse<Agrement>) => {
          if (agrement.body) {
            return of(agrement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Agrement());
  }
}

export const agrementRoute: Routes = [
  {
    path: '',
    component: AgrementComponent,
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'Agrements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgrementDetailComponent,
    resolve: {
      agrement: AgrementResolve,
    },
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Agrements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgrementUpdateComponent,
    resolve: {
      agrement: AgrementResolve,
    },
    data: {
      // authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Agrements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgrementUpdateComponent,
    resolve: {
      agrement: AgrementResolve,
    },
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Agrements',
    },
    canActivate: [UserRouteAccessService],
  },
];
