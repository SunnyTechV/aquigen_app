import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'raw-material-order',
        data: { pageTitle: 'aquigenApp.rawMaterialOrder.home.title' },
        loadChildren: () => import('./raw-material-order/raw-material-order.module').then(m => m.RawMaterialOrderModule),
      },
      {
        path: 'raw-material-master',
        data: { pageTitle: 'aquigenApp.rawMaterialMaster.home.title' },
        loadChildren: () => import('./raw-material-master/raw-material-master.module').then(m => m.RawMaterialMasterModule),
      },
      {
        path: 'categories',
        data: { pageTitle: 'aquigenApp.categories.home.title' },
        loadChildren: () => import('./categories/categories.module').then(m => m.CategoriesModule),
      },
      {
        path: 'unit',
        data: { pageTitle: 'aquigenApp.unit.home.title' },
        loadChildren: () => import('./unit/unit.module').then(m => m.UnitModule),
      },
      {
        path: 'rm-inventory',
        data: { pageTitle: 'aquigenApp.rmInventory.home.title' },
        loadChildren: () => import('./rm-inventory/rm-inventory.module').then(m => m.RmInventoryModule),
      },
      {
        path: 'consumption-details',
        data: { pageTitle: 'aquigenApp.consumptionDetails.home.title' },
        loadChildren: () => import('./consumption-details/consumption-details.module').then(m => m.ConsumptionDetailsModule),
      },
      {
        path: 'transfer',
        data: { pageTitle: 'aquigenApp.transfer.home.title' },
        loadChildren: () => import('./transfer/transfer.module').then(m => m.TransferModule),
      },
      {
        path: 'transfer-details',
        data: { pageTitle: 'aquigenApp.transferDetails.home.title' },
        loadChildren: () => import('./transfer-details/transfer-details.module').then(m => m.TransferDetailsModule),
      },
      {
        path: 'tranfer-details-approvals',
        data: { pageTitle: 'aquigenApp.tranferDetailsApprovals.home.title' },
        loadChildren: () =>
          import('./tranfer-details-approvals/tranfer-details-approvals.module').then(m => m.TranferDetailsApprovalsModule),
      },
      {
        path: 'tranfer-recieved',
        data: { pageTitle: 'aquigenApp.tranferRecieved.home.title' },
        loadChildren: () => import('./tranfer-recieved/tranfer-recieved.module').then(m => m.TranferRecievedModule),
      },
      {
        path: 'purchase-order',
        data: { pageTitle: 'aquigenApp.purchaseOrder.home.title' },
        loadChildren: () => import('./purchase-order/purchase-order.module').then(m => m.PurchaseOrderModule),
      },
      {
        path: 'purchase-order-details',
        data: { pageTitle: 'aquigenApp.purchaseOrderDetails.home.title' },
        loadChildren: () => import('./purchase-order-details/purchase-order-details.module').then(m => m.PurchaseOrderDetailsModule),
      },
      {
        path: 'goods-recived',
        data: { pageTitle: 'aquigenApp.goodsRecived.home.title' },
        loadChildren: () => import('./goods-recived/goods-recived.module').then(m => m.GoodsRecivedModule),
      },
      {
        path: 'warehouse',
        data: { pageTitle: 'aquigenApp.warehouse.home.title' },
        loadChildren: () => import('./warehouse/warehouse.module').then(m => m.WarehouseModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'aquigenApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'product-inventory',
        data: { pageTitle: 'aquigenApp.productInventory.home.title' },
        loadChildren: () => import('./product-inventory/product-inventory.module').then(m => m.ProductInventoryModule),
      },
      {
        path: 'product-transaction',
        data: { pageTitle: 'aquigenApp.productTransaction.home.title' },
        loadChildren: () => import('./product-transaction/product-transaction.module').then(m => m.ProductTransactionModule),
      },
      {
        path: 'security-user',
        data: { pageTitle: 'aquigenApp.securityUser.home.title' },
        loadChildren: () => import('./security-user/security-user.module').then(m => m.SecurityUserModule),
      },
      {
        path: 'product-quatation',
        data: { pageTitle: 'aquigenApp.productQuatation.home.title' },
        loadChildren: () => import('./product-quatation/product-quatation.module').then(m => m.ProductQuatationModule),
      },
      {
        path: 'quatation-details',
        data: { pageTitle: 'aquigenApp.quatationDetails.home.title' },
        loadChildren: () => import('./quatation-details/quatation-details.module').then(m => m.QuatationDetailsModule),
      },
      {
        path: 'user-access',
        data: { pageTitle: 'aquigenApp.userAccess.home.title' },
        loadChildren: () => import('./user-access/user-access.module').then(m => m.UserAccessModule),
      },
      {
        path: 'security-role',
        data: { pageTitle: 'aquigenApp.securityRole.home.title' },
        loadChildren: () => import('./security-role/security-role.module').then(m => m.SecurityRoleModule),
      },
      {
        path: 'security-permission',
        data: { pageTitle: 'aquigenApp.securityPermission.home.title' },
        loadChildren: () => import('./security-permission/security-permission.module').then(m => m.SecurityPermissionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
