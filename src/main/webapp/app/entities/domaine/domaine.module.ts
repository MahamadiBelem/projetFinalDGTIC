import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetDgticSharedModule } from 'app/shared/shared.module';
import { DomaineComponent } from './domaine.component';
import { DomaineDetailComponent } from './domaine-detail.component';
import { DomaineUpdateComponent } from './domaine-update.component';
import { DomaineDeleteDialogComponent } from './domaine-delete-dialog.component';
import { domaineRoute } from './domaine.route';

@NgModule({
  imports: [ProjetDgticSharedModule, RouterModule.forChild(domaineRoute)],
  declarations: [DomaineComponent, DomaineDetailComponent, DomaineUpdateComponent, DomaineDeleteDialogComponent],
  entryComponents: [DomaineDeleteDialogComponent],
})
export class ProjetDgticDomaineModule {}
