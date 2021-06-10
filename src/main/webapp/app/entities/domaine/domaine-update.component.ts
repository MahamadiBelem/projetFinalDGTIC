import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDomaine, Domaine } from 'app/shared/model/domaine.model';
import { DomaineService } from './domaine.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-domaine-update',
  templateUrl: './domaine-update.component.html',
})
export class DomaineUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codeDomaine: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected domaineService: DomaineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ domaine }) => {
      this.updateForm(domaine);
    });
  }

  updateForm(domaine: IDomaine): void {
    this.editForm.patchValue({
      id: domaine.id,
      codeDomaine: domaine.codeDomaine,
      libelle: domaine.libelle,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('projetDgticApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const domaine = this.createFromForm();
    if (domaine.id !== undefined) {
      this.subscribeToSaveResponse(this.domaineService.update(domaine));
    } else {
      this.subscribeToSaveResponse(this.domaineService.create(domaine));
    }
  }

  private createFromForm(): IDomaine {
    return {
      ...new Domaine(),
      id: this.editForm.get(['id'])!.value,
      codeDomaine: this.editForm.get(['codeDomaine'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDomaine>>): void {
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
