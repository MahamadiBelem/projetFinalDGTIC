import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArrete, Arrete } from 'app/shared/model/arrete.model';
import { ArreteService } from './arrete.service';
import { ArreteComponent } from './arrete.component';
import { ArreteDetailComponent } from './arrete-detail.component';
import { ArreteUpdateComponent } from './arrete-update.component';

@Injectable({ providedIn: 'root' })
export class ArreteResolve implements Resolve<IArrete> {
  constructor(private service: ArreteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArrete> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((arrete: HttpResponse<Arrete>) => {
          if (arrete.body) {
            return of(arrete.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Arrete());
  }
}

export const arreteRoute: Routes = [
  {
    path: '',
    component: ArreteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Arretes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ArreteDetailComponent,
    resolve: {
      arrete: ArreteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Arretes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ArreteUpdateComponent,
    resolve: {
      arrete: ArreteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Arretes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ArreteUpdateComponent,
    resolve: {
      arrete: ArreteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Arretes',
    },
    canActivate: [UserRouteAccessService],
  },
];
