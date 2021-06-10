import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgrement } from 'app/shared/model/agrement.model';
import { AgrementService } from './agrement.service';

@Component({
  templateUrl: './agrement-delete-dialog.component.html',
})
export class AgrementDeleteDialogComponent {
  agrement?: IAgrement;

  constructor(protected agrementService: AgrementService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agrementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('agrementListModification');
      this.activeModal.close();
    });
  }
}
