import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEntreprise, Entreprise } from 'app/shared/model/entreprise.model';
import { EntrepriseService } from './entreprise.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAgrement } from 'app/shared/model/agrement.model';
import { AgrementService } from 'app/entities/agrement/agrement.service';

@Component({
  selector: 'jhi-entreprise-update',
  templateUrl: './entreprise-update.component.html',
})
export class EntrepriseUpdateComponent implements OnInit {
  isSaving = false;
  agrements: IAgrement[] = [];

  editForm = this.fb.group({
    id: [],
    sigleEntreprise: [null, [Validators.required]],
    nomEntreprise: [null, [Validators.required]],
    numeroRCCM: [],
    numeroIFU: [],
    ville: [],
    localisation: [],
    telephone1: [],
    telephone2: [],
    agrement: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected entrepriseService: EntrepriseService,
    protected agrementService: AgrementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entreprise }) => {
      this.updateForm(entreprise);

      this.agrementService.query().subscribe((res: HttpResponse<IAgrement[]>) => (this.agrements = res.body || []));
    });
  }

  updateForm(entreprise: IEntreprise): void {
    this.editForm.patchValue({
      id: entreprise.id,
      sigleEntreprise: entreprise.sigleEntreprise,
      nomEntreprise: entreprise.nomEntreprise,
      numeroRCCM: entreprise.numeroRCCM,
      numeroIFU: entreprise.numeroIFU,
      ville: entreprise.ville,
      localisation: entreprise.localisation,
      telephone1: entreprise.telephone1,
      telephone2: entreprise.telephone2,
      agrement: entreprise.agrement,
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
    const entreprise = this.createFromForm();
    if (entreprise.id !== undefined) {
      this.subscribeToSaveResponse(this.entrepriseService.update(entreprise));
    } else {
      this.subscribeToSaveResponse(this.entrepriseService.create(entreprise));
    }
  }

  private createFromForm(): IEntreprise {
    return {
      ...new Entreprise(),
      id: this.editForm.get(['id'])!.value,
      sigleEntreprise: this.editForm.get(['sigleEntreprise'])!.value,
      nomEntreprise: this.editForm.get(['nomEntreprise'])!.value,
      numeroRCCM: this.editForm.get(['numeroRCCM'])!.value,
      numeroIFU: this.editForm.get(['numeroIFU'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      localisation: this.editForm.get(['localisation'])!.value,
      telephone1: this.editForm.get(['telephone1'])!.value,
      telephone2: this.editForm.get(['telephone2'])!.value,
      agrement: this.editForm.get(['agrement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntreprise>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.editForm.reset();
    // this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IAgrement): any {
    return item.id;
  }
}
