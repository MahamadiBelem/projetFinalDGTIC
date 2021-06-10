import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEntreprise } from 'app/shared/model/entreprise.model';

@Component({
  selector: 'jhi-entreprise-detail',
  templateUrl: './entreprise-detail.component.html',
})
export class EntrepriseDetailComponent implements OnInit {
  entreprise: IEntreprise | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entreprise }) => (this.entreprise = entreprise));
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
