import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQualification } from 'app/shared/model/qualification.model';
import { QualificationService } from './qualification.service';

@Component({
  templateUrl: './qualification-delete-dialog.component.html',
})
export class QualificationDeleteDialogComponent {
  qualification?: IQualification;

  constructor(
    protected qualificationService: QualificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qualificationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('qualificationListModification');
      this.activeModal.close();
    });
  }
}
