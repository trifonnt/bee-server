import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BeeServerSharedModule } from 'app/shared/shared.module';
import { DeviceComponent } from './device.component';
import { DeviceDetailComponent } from './device-detail.component';
import { DeviceUpdateComponent } from './device-update.component';
import { DeviceDeleteDialogComponent } from './device-delete-dialog.component';
import { deviceRoute } from './device.route';

@NgModule({
  imports: [BeeServerSharedModule, RouterModule.forChild(deviceRoute)],
  declarations: [DeviceComponent, DeviceDetailComponent, DeviceUpdateComponent, DeviceDeleteDialogComponent],
  entryComponents: [DeviceDeleteDialogComponent],
})
export class BeeServerDeviceModule {}
