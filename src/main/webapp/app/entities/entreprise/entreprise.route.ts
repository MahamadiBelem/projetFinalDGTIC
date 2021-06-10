import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntreprise, Entreprise } from 'app/shared/model/entreprise.model';
import { EntrepriseService } from './entreprise.service';
import { EntrepriseComponent } from './entreprise.component';
import { EntrepriseDetailComponent } from './entreprise-detail.component';
import { EntrepriseUpdateComponent } from './entreprise-update.component';

@Injectable({ providedIn: 'root' })
export class EntrepriseResolve implements Resolve<IEntreprise> {
  constructor(private service: EntrepriseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntreprise> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entreprise: HttpResponse<Entreprise>) => {
          if (entreprise.body) {
            return of(entreprise.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Entreprise());
  }
}

export const entrepriseRoute: Routes = [
  {
    path: '',
    component: EntrepriseComponent,
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'Entreprises',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntrepriseDetailComponent,
    resolve: {
      entreprise: EntrepriseResolve,
    },
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Entreprises',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntrepriseUpdateComponent,
    resolve: {
      entreprise: EntrepriseResolve,
    },
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Entreprises',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntrepriseUpdateComponent,
    resolve: {
      entreprise: EntrepriseResolve,
    },
    data: {
      //authorities: [Authority.USER],
      authorities: [],
      pageTitle: 'Entreprises',
    },
    canActivate: [UserRouteAccessService],
  },
];
