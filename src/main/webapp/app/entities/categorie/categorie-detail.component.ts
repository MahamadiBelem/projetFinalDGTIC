import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICategorie } from 'app/shared/model/categorie.model';

@Component({
  selector: 'jhi-categorie-detail',
  templateUrl: './categorie-detail.component.html',
})
export class CategorieDetailComponent implements OnInit {
  categorie: ICategorie | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorie }) => (this.categorie = categorie));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
