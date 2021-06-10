import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDomaine } from 'app/shared/model/domaine.model';
import { DomaineService } from './domaine.service';

@Component({
  templateUrl: './domaine-delete-dialog.component.html',
})
export class DomaineDeleteDialogComponent {
  domaine?: IDomaine;

  constructor(protected domaineService: DomaineService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.domaineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('domaineListModification');
      this.activeModal.close();
    });
  }
}
