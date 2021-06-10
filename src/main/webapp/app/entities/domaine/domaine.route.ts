import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDomaine, Domaine } from 'app/shared/model/domaine.model';
import { DomaineService } from './domaine.service';
import { DomaineComponent } from './domaine.component';
import { DomaineDetailComponent } from './domaine-detail.component';
import { DomaineUpdateComponent } from './domaine-update.component';

@Injectable({ providedIn: 'root' })
export class DomaineResolve implements Resolve<IDomaine> {
  constructor(private service: DomaineService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDomaine> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((domaine: HttpResponse<Domaine>) => {
          if (domaine.body) {
            return of(domaine.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Domaine());
  }
}

export const domaineRoute: Routes = [
  {
    path: '',
    component: DomaineComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Domaines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DomaineDetailComponent,
    resolve: {
      domaine: DomaineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Domaines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DomaineUpdateComponent,
    resolve: {
      domaine: DomaineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Domaines',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DomaineUpdateComponent,
    resolve: {
      domaine: DomaineResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Domaines',
    },
    canActivate: [UserRouteAccessService],
  },
];
