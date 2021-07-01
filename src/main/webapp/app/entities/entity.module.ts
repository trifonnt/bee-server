import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'apiary',
        loadChildren: () => import('./apiary/apiary.module').then(m => m.BeeServerApiaryModule),
      },
      {
        path: 'beehive',
        loadChildren: () => import('./beehive/beehive.module').then(m => m.BeeServerBeehiveModule),
      },
      {
        path: 'device',
        loadChildren: () => import('./device/device.module').then(m => m.BeeServerDeviceModule),
      },
      {
        path: 'measurement-record',
        loadChildren: () => import('./measurement-record/measurement-record.module').then(m => m.BeeServerMeasurementRecordModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BeeServerEntityModule {}
