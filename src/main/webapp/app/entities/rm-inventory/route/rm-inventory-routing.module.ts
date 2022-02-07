import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RmInventoryComponent } from '../list/rm-inventory.component';
import { RmInventoryDetailComponent } from '../detail/rm-inventory-detail.component';
import { RmInventoryUpdateComponent } from '../update/rm-inventory-update.component';
import { RmInventoryRoutingResolveService } from './rm-inventory-routing-resolve.service';

const rmInventoryRoute: Routes = [
  {
    path: '',
    component: RmInventoryComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RmInventoryDetailComponent,
    resolve: {
      rmInventory: RmInventoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RmInventoryUpdateComponent,
    resolve: {
      rmInventory: RmInventoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RmInventoryUpdateComponent,
    resolve: {
      rmInventory: RmInventoryRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(rmInventoryRoute)],
  exports: [RouterModule],
})
export class RmInventoryRoutingModule {}
