import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProjetDgticSharedModule } from 'app/shared/shared.module';
import { ArreteComponent } from './arrete.component';
import { ArreteDetailComponent } from './arrete-detail.component';
import { ArreteUpdateComponent } from './arrete-update.component';
import { ArreteDeleteDialogComponent } from './arrete-delete-dialog.component';
import { arreteRoute } from './arrete.route';

@NgModule({
  imports: [ProjetDgticSharedModule, RouterModule.forChild(arreteRoute)],
  declarations: [ArreteComponent, ArreteDetailComponent, ArreteUpdateComponent, ArreteDeleteDialogComponent],
  entryComponents: [ArreteDeleteDialogComponent],
})
export class ProjetDgticArreteModule {}
