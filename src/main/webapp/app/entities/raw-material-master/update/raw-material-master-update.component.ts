import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRawMaterialMaster, RawMaterialMaster } from '../raw-material-master.model';
import { RawMaterialMasterService } from '../service/raw-material-master.service';
import { IRawMaterialOrder } from 'app/entities/raw-material-order/raw-material-order.model';
import { RawMaterialOrderService } from 'app/entities/raw-material-order/service/raw-material-order.service';
import { ICategories } from 'app/entities/categories/categories.model';
import { CategoriesService } from 'app/entities/categories/service/categories.service';
import { IUnit } from 'app/entities/unit/unit.model';
import { UnitService } from 'app/entities/unit/service/unit.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

@Component({
  selector: 'jhi-raw-material-master-update',
  templateUrl: './raw-material-master-update.component.html',
})
export class RawMaterialMasterUpdateComponent implements OnInit {
  isSaving = false;

  rawMaterialOrdersSharedCollection: IRawMaterialOrder[] = [];
  categoriesSharedCollection: ICategories[] = [];
  unitsSharedCollection: IUnit[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    materialName: [],
    shortName: [],
    chemicalFormula: [],
    hsnNo: [],
    barCode: [],
    qrCode: [],
    gstPercentage: [],
    materialImage: [],
    alertUnits: [],
    casNumber: [],
    catlogNumber: [],
    description: [],
    lastModified: [],
    lastModifiedBy: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
    isDeleted: [],
    isActive: [],
    rawMaterialOrders: [],
    categories: [],
    unit: [],
    securityUser: [],
  });

  constructor(
    protected rawMaterialMasterService: RawMaterialMasterService,
    protected rawMaterialOrderService: RawMaterialOrderService,
    protected categoriesService: CategoriesService,
    protected unitService: UnitService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rawMaterialMaster }) => {
      if (rawMaterialMaster.id === undefined) {
        const today = dayjs().startOf('day');
        rawMaterialMaster.lastModified = today;
      }

      this.updateForm(rawMaterialMaster);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rawMaterialMaster = this.createFromForm();
    if (rawMaterialMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.rawMaterialMasterService.update(rawMaterialMaster));
    } else {
      this.subscribeToSaveResponse(this.rawMaterialMasterService.create(rawMaterialMaster));
    }
  }

  trackRawMaterialOrderById(index: number, item: IRawMaterialOrder): number {
    return item.id!;
  }

  trackCategoriesById(index: number, item: ICategories): number {
    return item.id!;
  }

  trackUnitById(index: number, item: IUnit): number {
    return item.id!;
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  getSelectedRawMaterialOrder(option: IRawMaterialOrder, selectedVals?: IRawMaterialOrder[]): IRawMaterialOrder {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRawMaterialMaster>>): void {
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

  protected updateForm(rawMaterialMaster: IRawMaterialMaster): void {
    this.editForm.patchValue({
      id: rawMaterialMaster.id,
      materialName: rawMaterialMaster.materialName,
      shortName: rawMaterialMaster.shortName,
      chemicalFormula: rawMaterialMaster.chemicalFormula,
      hsnNo: rawMaterialMaster.hsnNo,
      barCode: rawMaterialMaster.barCode,
      qrCode: rawMaterialMaster.qrCode,
      gstPercentage: rawMaterialMaster.gstPercentage,
      materialImage: rawMaterialMaster.materialImage,
      alertUnits: rawMaterialMaster.alertUnits,
      casNumber: rawMaterialMaster.casNumber,
      catlogNumber: rawMaterialMaster.catlogNumber,
      description: rawMaterialMaster.description,
      lastModified: rawMaterialMaster.lastModified ? rawMaterialMaster.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: rawMaterialMaster.lastModifiedBy,
      freeField1: rawMaterialMaster.freeField1,
      freeField2: rawMaterialMaster.freeField2,
      freeField3: rawMaterialMaster.freeField3,
      freeField4: rawMaterialMaster.freeField4,
      isDeleted: rawMaterialMaster.isDeleted,
      isActive: rawMaterialMaster.isActive,
      rawMaterialOrders: rawMaterialMaster.rawMaterialOrders,
      categories: rawMaterialMaster.categories,
      unit: rawMaterialMaster.unit,
      securityUser: rawMaterialMaster.securityUser,
    });

    this.rawMaterialOrdersSharedCollection = this.rawMaterialOrderService.addRawMaterialOrderToCollectionIfMissing(
      this.rawMaterialOrdersSharedCollection,
      ...(rawMaterialMaster.rawMaterialOrders ?? [])
    );
    this.categoriesSharedCollection = this.categoriesService.addCategoriesToCollectionIfMissing(
      this.categoriesSharedCollection,
      rawMaterialMaster.categories
    );
    this.unitsSharedCollection = this.unitService.addUnitToCollectionIfMissing(this.unitsSharedCollection, rawMaterialMaster.unit);
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      rawMaterialMaster.securityUser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rawMaterialOrderService
      .query()
      .pipe(map((res: HttpResponse<IRawMaterialOrder[]>) => res.body ?? []))
      .pipe(
        map((rawMaterialOrders: IRawMaterialOrder[]) =>
          this.rawMaterialOrderService.addRawMaterialOrderToCollectionIfMissing(
            rawMaterialOrders,
            ...(this.editForm.get('rawMaterialOrders')!.value ?? [])
          )
        )
      )
      .subscribe((rawMaterialOrders: IRawMaterialOrder[]) => (this.rawMaterialOrdersSharedCollection = rawMaterialOrders));

    this.categoriesService
      .query()
      .pipe(map((res: HttpResponse<ICategories[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategories[]) =>
          this.categoriesService.addCategoriesToCollectionIfMissing(categories, this.editForm.get('categories')!.value)
        )
      )
      .subscribe((categories: ICategories[]) => (this.categoriesSharedCollection = categories));

    this.unitService
      .query()
      .pipe(map((res: HttpResponse<IUnit[]>) => res.body ?? []))
      .pipe(map((units: IUnit[]) => this.unitService.addUnitToCollectionIfMissing(units, this.editForm.get('unit')!.value)))
      .subscribe((units: IUnit[]) => (this.unitsSharedCollection = units));

    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, this.editForm.get('securityUser')!.value)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));
  }

  protected createFromForm(): IRawMaterialMaster {
    return {
      ...new RawMaterialMaster(),
      id: this.editForm.get(['id'])!.value,
      materialName: this.editForm.get(['materialName'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
      chemicalFormula: this.editForm.get(['chemicalFormula'])!.value,
      hsnNo: this.editForm.get(['hsnNo'])!.value,
      barCode: this.editForm.get(['barCode'])!.value,
      qrCode: this.editForm.get(['qrCode'])!.value,
      gstPercentage: this.editForm.get(['gstPercentage'])!.value,
      materialImage: this.editForm.get(['materialImage'])!.value,
      alertUnits: this.editForm.get(['alertUnits'])!.value,
      casNumber: this.editForm.get(['casNumber'])!.value,
      catlogNumber: this.editForm.get(['catlogNumber'])!.value,
      description: this.editForm.get(['description'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
      isDeleted: this.editForm.get(['isDeleted'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
      rawMaterialOrders: this.editForm.get(['rawMaterialOrders'])!.value,
      categories: this.editForm.get(['categories'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
    };
  }
}
