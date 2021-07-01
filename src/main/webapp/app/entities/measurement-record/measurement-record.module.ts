import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeeServerSharedModule } from 'app/shared/shared.module';
import { MeasurementRecordComponent } from './measurement-record.component';
import { MeasurementRecordDetailComponent } from './measurement-record-detail.component';
import { MeasurementRecordUpdateComponent } from './measurement-record-update.component';
import { MeasurementRecordDeleteDialogComponent } from './measurement-record-delete-dialog.component';
import { measurementRecordRoute } from './measurement-record.route';

@NgModule({
  imports: [BeeServerSharedModule, RouterModule.forChild(measurementRecordRoute)],
  declarations: [
    MeasurementRecordComponent,
    MeasurementRecordDetailComponent,
    MeasurementRecordUpdateComponent,
    MeasurementRecordDeleteDialogComponent,
  ],
  entryComponents: [MeasurementRecordDeleteDialogComponent],
})
export class BeeServerMeasurementRecordModule {}
