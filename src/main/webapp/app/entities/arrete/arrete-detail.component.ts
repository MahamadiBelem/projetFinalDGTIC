import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArrete } from 'app/shared/model/arrete.model';

@Component({
  selector: 'jhi-arrete-detail',
  templateUrl: './arrete-detail.component.html',
})
export class ArreteDetailComponent implements OnInit {
  arrete: IArrete | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ arrete }) => (this.arrete = arrete));
  }

  previousState(): void {
    window.history.back();
  }
}
