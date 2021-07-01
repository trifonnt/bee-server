import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeasurementRecord } from 'app/shared/model/measurement-record.model';
import { MeasurementRecordService } from './measurement-record.service';

@Component({
  templateUrl: './measurement-record-delete-dialog.component.html',
})
export class MeasurementRecordDeleteDialogComponent {
  measurementRecord?: IMeasurementRecord;

  constructor(
    protected measurementRecordService: MeasurementRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.measurementRecordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('measurementRecordListModification');
      this.activeModal.close();
    });
  }
}
