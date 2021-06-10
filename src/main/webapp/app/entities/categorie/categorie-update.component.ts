import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICategorie, Categorie } from 'app/shared/model/categorie.model';
import { CategorieService } from './categorie.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDomaine } from 'app/shared/model/domaine.model';
import { DomaineService } from 'app/entities/domaine/domaine.service';

@Component({
  selector: 'jhi-categorie-update',
  templateUrl: './categorie-update.component.html',
})
export class CategorieUpdateComponent implements OnInit {
  isSaving = false;
  domaines: IDomaine[] = [];

  editForm = this.fb.group({
    id: [],
    codeCategorie: [null, [Validators.required]],
    libelle: [null, [Validators.required]],
    domaine: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected categorieService: CategorieService,
    protected domaineService: DomaineService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorie }) => {
      this.updateForm(categorie);

      this.domaineService.query().subscribe((res: HttpResponse<IDomaine[]>) => (this.domaines = res.body || []));
    });
  }

  updateForm(categorie: ICategorie): void {
    this.editForm.patchValue({
      id: categorie.id,
      codeCategorie: categorie.codeCategorie,
      libelle: categorie.libelle,
      domaine: categorie.domaine,
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
    const categorie = this.createFromForm();
    if (categorie.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieService.update(categorie));
    } else {
      this.subscribeToSaveResponse(this.categorieService.create(categorie));
    }
  }

  private createFromForm(): ICategorie {
    return {
      ...new Categorie(),
      id: this.editForm.get(['id'])!.value,
      codeCategorie: this.editForm.get(['codeCategorie'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      domaine: this.editForm.get(['domaine'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorie>>): void {
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

  trackById(index: number, item: IDomaine): any {
    return item.id;
  }
}
