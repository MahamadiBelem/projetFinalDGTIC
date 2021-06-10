import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IQualification, Qualification } from 'app/shared/model/qualification.model';
import { QualificationService } from './qualification.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDomaine } from 'app/shared/model/domaine.model';
import { DomaineService } from 'app/entities/domaine/domaine.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie/categorie.service';

type SelectableEntity = IDomaine | ICategorie;

@Component({
  selector: 'jhi-qualification-update',
  templateUrl: './qualification-update.component.html',
})
export class QualificationUpdateComponent implements OnInit {
  isSaving = false;
  domaines: IDomaine[] = [];
  categories: ICategorie[] = [];

  editForm = this.fb.group({
    id: [],
    serviceConcerne: [null, [Validators.required]],
    domaine: [],
    categorie: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected qualificationService: QualificationService,
    protected domaineService: DomaineService,
    protected categorieService: CategorieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qualification }) => {
      this.updateForm(qualification);

      this.domaineService.query().subscribe((res: HttpResponse<IDomaine[]>) => (this.domaines = res.body || []));

      this.categorieService.query().subscribe((res: HttpResponse<ICategorie[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(qualification: IQualification): void {
    this.editForm.patchValue({
      id: qualification.id,
      serviceConcerne: qualification.serviceConcerne,
      domaine: qualification.domaine,
      categorie: qualification.categorie,
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
    const qualification = this.createFromForm();
    if (qualification.id !== undefined) {
      this.subscribeToSaveResponse(this.qualificationService.update(qualification));
    } else {
      this.subscribeToSaveResponse(this.qualificationService.create(qualification));
    }
  }

  private createFromForm(): IQualification {
    return {
      ...new Qualification(),
      id: this.editForm.get(['id'])!.value,
      serviceConcerne: this.editForm.get(['serviceConcerne'])!.value,
      domaine: this.editForm.get(['domaine'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQualification>>): void {
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
}
