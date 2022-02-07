import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPurchaseOrder, PurchaseOrder } from '../purchase-order.model';
import { PurchaseOrderService } from '../service/purchase-order.service';
import { IRmInventory } from 'app/entities/rm-inventory/rm-inventory.model';
import { RmInventoryService } from 'app/entities/rm-inventory/service/rm-inventory.service';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/service/warehouse.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IRawMaterialOrder } from 'app/entities/raw-material-order/raw-material-order.model';
import { RawMaterialOrderService } from 'app/entities/raw-material-order/service/raw-material-order.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-purchase-order-update',
  templateUrl: './purchase-order-update.component.html',
})
export class PurchaseOrderUpdateComponent implements OnInit {
  isSaving = false;
  statusValues = Object.keys(Status);

  rmInventoriesSharedCollection: IRmInventory[] = [];
  warehousesSharedCollection: IWarehouse[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];
  rawMaterialOrdersSharedCollection: IRawMaterialOrder[] = [];

  editForm = this.fb.group({
    id: [],
    totalPOAmount: [],
    totalGSTAmount: [],
    expectedDeliveryDate: [],
    poDate: [],
    orderStatus: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
    rmInventory: [],
    warehouse: [],
    securityUser: [],
    rawMaterialOrder: [],
  });

  constructor(
    protected purchaseOrderService: PurchaseOrderService,
    protected rmInventoryService: RmInventoryService,
    protected warehouseService: WarehouseService,
    protected securityUserService: SecurityUserService,
    protected rawMaterialOrderService: RawMaterialOrderService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseOrder }) => {
      if (purchaseOrder.id === undefined) {
        const today = dayjs().startOf('day');
        purchaseOrder.expectedDeliveryDate = today;
        purchaseOrder.poDate = today;
        purchaseOrder.lastModified = today;
      }

      this.updateForm(purchaseOrder);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purchaseOrder = this.createFromForm();
    if (purchaseOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseOrderService.update(purchaseOrder));
    } else {
      this.subscribeToSaveResponse(this.purchaseOrderService.create(purchaseOrder));
    }
  }

  trackRmInventoryById(index: number, item: IRmInventory): number {
    return item.id!;
  }

  trackWarehouseById(index: number, item: IWarehouse): number {
    return item.id!;
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackRawMaterialOrderById(index: number, item: IRawMaterialOrder): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrder>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(purchaseOrder: IPurchaseOrder): void {
    this.editForm.patchValue({
      id: purchaseOrder.id,
      totalPOAmount: purchaseOrder.totalPOAmount,
      totalGSTAmount: purchaseOrder.totalGSTAmount,
      expectedDeliveryDate: purchaseOrder.expectedDeliveryDate ? purchaseOrder.expectedDeliveryDate.format(DATE_TIME_FORMAT) : null,
      poDate: purchaseOrder.poDate ? purchaseOrder.poDate.format(DATE_TIME_FORMAT) : null,
      orderStatus: purchaseOrder.orderStatus,
      lastModified: purchaseOrder.lastModified ? purchaseOrder.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: purchaseOrder.lastModifiedBy,
      freeField1: purchaseOrder.freeField1,
      freeField2: purchaseOrder.freeField2,
      freeField3: purchaseOrder.freeField3,
      freeField4: purchaseOrder.freeField4,
      rmInventory: purchaseOrder.rmInventory,
      warehouse: purchaseOrder.warehouse,
      securityUser: purchaseOrder.securityUser,
      rawMaterialOrder: purchaseOrder.rawMaterialOrder,
    });

    this.rmInventoriesSharedCollection = this.rmInventoryService.addRmInventoryToCollectionIfMissing(
      this.rmInventoriesSharedCollection,
      purchaseOrder.rmInventory
    );
    this.warehousesSharedCollection = this.warehouseService.addWarehouseToCollectionIfMissing(
      this.warehousesSharedCollection,
      purchaseOrder.warehouse
    );
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      purchaseOrder.securityUser
    );
    this.rawMaterialOrdersSharedCollection = this.rawMaterialOrderService.addRawMaterialOrderToCollectionIfMissing(
      this.rawMaterialOrdersSharedCollection,
      purchaseOrder.rawMaterialOrder
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rmInventoryService
      .query()
      .pipe(map((res: HttpResponse<IRmInventory[]>) => res.body ?? []))
      .pipe(
        map((rmInventories: IRmInventory[]) =>
          this.rmInventoryService.addRmInventoryToCollectionIfMissing(rmInventories, this.editForm.get('rmInventory')!.value)
        )
      )
      .subscribe((rmInventories: IRmInventory[]) => (this.rmInventoriesSharedCollection = rmInventories));

    this.warehouseService
      .query()
      .pipe(map((res: HttpResponse<IWarehouse[]>) => res.body ?? []))
      .pipe(
        map((warehouses: IWarehouse[]) =>
          this.warehouseService.addWarehouseToCollectionIfMissing(warehouses, this.editForm.get('warehouse')!.value)
        )
      )
      .subscribe((warehouses: IWarehouse[]) => (this.warehousesSharedCollection = warehouses));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, this.editForm.get('securityUser')!.value)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.rawMaterialOrderService
      .query()
      .pipe(map((res: HttpResponse<IRawMaterialOrder[]>) => res.body ?? []))
      .pipe(
        map((rawMaterialOrders: IRawMaterialOrder[]) =>
          this.rawMaterialOrderService.addRawMaterialOrderToCollectionIfMissing(
            rawMaterialOrders,
            this.editForm.get('rawMaterialOrder')!.value
          )
        )
      )
      .subscribe((rawMaterialOrders: IRawMaterialOrder[]) => (this.rawMaterialOrdersSharedCollection = rawMaterialOrders));
  }

  protected createFromForm(): IPurchaseOrder {
    return {
      ...new PurchaseOrder(),
      id: this.editForm.get(['id'])!.value,
      totalPOAmount: this.editForm.get(['totalPOAmount'])!.value,
      totalGSTAmount: this.editForm.get(['totalGSTAmount'])!.value,
      expectedDeliveryDate: this.editForm.get(['expectedDeliveryDate'])!.value
        ? dayjs(this.editForm.get(['expectedDeliveryDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      poDate: this.editForm.get(['poDate'])!.value ? dayjs(this.editForm.get(['poDate'])!.value, DATE_TIME_FORMAT) : undefined,
      orderStatus: this.editForm.get(['orderStatus'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
      rmInventory: this.editForm.get(['rmInventory'])!.value,
      warehouse: this.editForm.get(['warehouse'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
      rawMaterialOrder: this.editForm.get(['rawMaterialOrder'])!.value,
    };
  }
}
