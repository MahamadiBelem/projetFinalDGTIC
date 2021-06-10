import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agrement',
        loadChildren: () => import('./agrement/agrement.module').then(m => m.ProjetDgticAgrementModule),
      },
      {
        path: 'qualification',
        loadChildren: () => import('./qualification/qualification.module').then(m => m.ProjetDgticQualificationModule),
      },
      {
        path: 'domaine',
        loadChildren: () => import('./domaine/domaine.module').then(m => m.ProjetDgticDomaineModule),
      },
      {
        path: 'categorie',
        loadChildren: () => import('./categorie/categorie.module').then(m => m.ProjetDgticCategorieModule),
      },
      {
        path: 'arrete',
        loadChildren: () => import('./arrete/arrete.module').then(m => m.ProjetDgticArreteModule),
      },
      {
        path: 'entreprise',
        loadChildren: () => import('./entreprise/entreprise.module').then(m => m.ProjetDgticEntrepriseModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ProjetDgticEntityModule {}
