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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BeeServerEntityModule {}
