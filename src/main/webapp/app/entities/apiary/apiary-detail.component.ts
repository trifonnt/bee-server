import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApiary } from 'app/shared/model/apiary.model';

@Component({
  selector: 'jhi-apiary-detail',
  templateUrl: './apiary-detail.component.html',
})
export class ApiaryDetailComponent implements OnInit {
  apiary: IApiary | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apiary }) => (this.apiary = apiary));
  }

  previousState(): void {
    window.history.back();
  }
}
