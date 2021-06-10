import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetDgticSharedModule } from 'app/shared/shared.module';
import { QualificationComponent } from './qualification.component';
import { QualificationDetailComponent } from './qualification-detail.component';
import { QualificationUpdateComponent } from './qualification-update.component';
import { QualificationDeleteDialogComponent } from './qualification-delete-dialog.component';
import { qualificationRoute } from './qualification.route';

@NgModule({
  imports: [ProjetDgticSharedModule, RouterModule.forChild(qualificationRoute)],
  declarations: [QualificationComponent, QualificationDetailComponent, QualificationUpdateComponent, QualificationDeleteDialogComponent],
  entryComponents: [QualificationDeleteDialogComponent],
})
export class ProjetDgticQualificationModule {}
