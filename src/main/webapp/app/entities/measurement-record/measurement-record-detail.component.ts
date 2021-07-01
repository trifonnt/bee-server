import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeasurementRecord } from 'app/shared/model/measurement-record.model';

@Component({
  selector: 'jhi-measurement-record-detail',
  templateUrl: './measurement-record-detail.component.html',
})
export class MeasurementRecordDetailComponent implements OnInit {
  measurementRecord: IMeasurementRecord | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ measurementRecord }) => (this.measurementRecord = measurementRecord));
  }

  previousState(): void {
    window.history.back();
  }
}
