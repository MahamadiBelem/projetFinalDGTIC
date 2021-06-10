import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArrete } from 'app/shared/model/arrete.model';
import { ArreteService } from './arrete.service';

@Component({
  templateUrl: './arrete-delete-dialog.component.html',
})
export class ArreteDeleteDialogComponent {
  arrete?: IArrete;

  constructor(protected arreteService: ArreteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.arreteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('arreteListModification');
      this.activeModal.close();
    });
  }
}
