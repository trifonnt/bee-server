import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMeasurementRecord, MeasurementRecord } from 'app/shared/model/measurement-record.model';
import { MeasurementRecordService } from './measurement-record.service';
import { IDevice } from 'app/shared/model/device.model';
import { DeviceService } from 'app/entities/device/device.service';

@Component({
  selector: 'jhi-measurement-record-update',
  templateUrl: './measurement-record-update.component.html',
})
export class MeasurementRecordUpdateComponent implements OnInit {
  isSaving = false;
  devices: IDevice[] = [];

  editForm = this.fb.group({
    id: [],
    recordedAt: [],
    inboundCount: [],
    outboundCount: [],
    deviceId: [null, Validators.required],
  });

  constructor(
    protected measurementRecordService: MeasurementRecordService,
    protected deviceService: DeviceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ measurementRecord }) => {
      if (!measurementRecord.id) {
        const today = moment().startOf('day');
        measurementRecord.recordedAt = today;
      }

      this.updateForm(measurementRecord);

      this.deviceService.query().subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body || []));
    });
  }

  updateForm(measurementRecord: IMeasurementRecord): void {
    this.editForm.patchValue({
      id: measurementRecord.id,
      recordedAt: measurementRecord.recordedAt ? measurementRecord.recordedAt.format(DATE_TIME_FORMAT) : null,
      inboundCount: measurementRecord.inboundCount,
      outboundCount: measurementRecord.outboundCount,
      deviceId: measurementRecord.deviceId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const measurementRecord = this.createFromForm();
    if (measurementRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.measurementRecordService.update(measurementRecord));
    } else {
      this.subscribeToSaveResponse(this.measurementRecordService.create(measurementRecord));
    }
  }

  private createFromForm(): IMeasurementRecord {
    return {
      ...new MeasurementRecord(),
      id: this.editForm.get(['id'])!.value,
      recordedAt: this.editForm.get(['recordedAt'])!.value ? moment(this.editForm.get(['recordedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      inboundCount: this.editForm.get(['inboundCount'])!.value,
      outboundCount: this.editForm.get(['outboundCount'])!.value,
      deviceId: this.editForm.get(['deviceId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeasurementRecord>>): void {
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

  trackById(index: number, item: IDevice): any {
    return item.id;
  }
}
