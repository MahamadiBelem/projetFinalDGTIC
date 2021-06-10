import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetDgticSharedModule } from 'app/shared/shared.module';
import { AgrementComponent } from './agrement.component';
import { AgrementDetailComponent } from './agrement-detail.component';
import { AgrementUpdateComponent } from './agrement-update.component';
import { AgrementDeleteDialogComponent } from './agrement-delete-dialog.component';
import { agrementRoute } from './agrement.route';

@NgModule({
  imports: [ProjetDgticSharedModule, RouterModule.forChild(agrementRoute)],
  declarations: [AgrementComponent, AgrementDetailComponent, AgrementUpdateComponent, AgrementDeleteDialogComponent],
  entryComponents: [AgrementDeleteDialogComponent],
})
export class ProjetDgticAgrementModule {}
