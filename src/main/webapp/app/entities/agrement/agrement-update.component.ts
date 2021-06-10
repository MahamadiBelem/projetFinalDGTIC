import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAgrement, Agrement } from 'app/shared/model/agrement.model';
import { AgrementService } from './agrement.service';
import { IQualification } from 'app/shared/model/qualification.model';
import { QualificationService } from 'app/entities/qualification/qualification.service';
import { IArrete } from 'app/shared/model/arrete.model';
import { ArreteService } from 'app/entities/arrete/arrete.service';

type SelectableEntity = IQualification | IArrete;

@Component({
  selector: 'jhi-agrement-update',
  templateUrl: './agrement-update.component.html',
})
export class AgrementUpdateComponent implements OnInit {
  isSaving = false;
  qualifications: IQualification[] = [];
  arretes: IArrete[] = [];

  editForm = this.fb.group({
    id: [],
    codeAgrement: [null, [Validators.required]],
    qualifications: [],
    arrete: [],
  });

  constructor(
    protected agrementService: AgrementService,
    protected qualificationService: QualificationService,
    protected arreteService: ArreteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agrement }) => {
      this.updateForm(agrement);

      this.qualificationService.query().subscribe((res: HttpResponse<IQualification[]>) => (this.qualifications = res.body || []));

      this.arreteService.query().subscribe((res: HttpResponse<IArrete[]>) => (this.arretes = res.body || []));
    });
  }

  updateForm(agrement: IAgrement): void {
    this.editForm.patchValue({
      id: agrement.id,
      codeAgrement: agrement.codeAgrement,
      qualifications: agrement.qualifications,
      arrete: agrement.arrete,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agrement = this.createFromForm();
    if (agrement.id !== undefined) {
      this.subscribeToSaveResponse(this.agrementService.update(agrement));
    } else {
      this.subscribeToSaveResponse(this.agrementService.create(agrement));
    }
  }

  private createFromForm(): IAgrement {
    return {
      ...new Agrement(),
      id: this.editForm.get(['id'])!.value,
      codeAgrement: this.editForm.get(['codeAgrement'])!.value,
      qualifications: this.editForm.get(['qualifications'])!.value,
      arrete: this.editForm.get(['arrete'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgrement>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IQualification[], option: IQualification): IQualification {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
