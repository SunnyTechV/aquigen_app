import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RawMaterialMasterComponent } from '../list/raw-material-master.component';
import { RawMaterialMasterDetailComponent } from '../detail/raw-material-master-detail.component';
import { RawMaterialMasterUpdateComponent } from '../update/raw-material-master-update.component';
import { RawMaterialMasterRoutingResolveService } from './raw-material-master-routing-resolve.service';

const rawMaterialMasterRoute: Routes = [
  {
    path: '',
    component: RawMaterialMasterComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RawMaterialMasterDetailComponent,
    resolve: {
      rawMaterialMaster: RawMaterialMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RawMaterialMasterUpdateComponent,
    resolve: {
      rawMaterialMaster: RawMaterialMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RawMaterialMasterUpdateComponent,
    resolve: {
      rawMaterialMaster: RawMaterialMasterRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rawMaterialMasterRoute)],
  exports: [RouterModule],
})
export class RawMaterialMasterRoutingModule {}
