import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IArrete, Arrete } from 'app/shared/model/arrete.model';
import { ArreteService } from './arrete.service';

@Component({
  selector: 'jhi-arrete-update',
  templateUrl: './arrete-update.component.html',
})
export class ArreteUpdateComponent implements OnInit {
  isSaving = false;
  dateSignatureDp: any;

  editForm = this.fb.group({
    id: [],
    intituleArrete: [],
    numeroArrete: [null, [Validators.required]],
    dateSignature: [null, [Validators.required]],
    nombreAgrement: [],
  });

  constructor(protected arreteService: ArreteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arrete }) => {
      this.updateForm(arrete);
    });
  }

  updateForm(arrete: IArrete): void {
    this.editForm.patchValue({
      id: arrete.id,
      intituleArrete: arrete.intituleArrete,
      numeroArrete: arrete.numeroArrete,
      dateSignature: arrete.dateSignature,
      nombreAgrement: arrete.nombreAgrement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const arrete = this.createFromForm();
    if (arrete.id !== undefined) {
      this.subscribeToSaveResponse(this.arreteService.update(arrete));
    } else {
      this.subscribeToSaveResponse(this.arreteService.create(arrete));
    }
  }

  private createFromForm(): IArrete {
    return {
      ...new Arrete(),
      id: this.editForm.get(['id'])!.value,
      intituleArrete: this.editForm.get(['intituleArrete'])!.value,
      numeroArrete: this.editForm.get(['numeroArrete'])!.value,
      dateSignature: this.editForm.get(['dateSignature'])!.value,
      nombreAgrement: this.editForm.get(['nombreAgrement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArrete>>): void {
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
}
