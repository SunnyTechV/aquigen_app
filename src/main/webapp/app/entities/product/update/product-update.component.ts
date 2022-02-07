import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProduct, Product } from '../product.model';
import { ProductService } from '../service/product.service';
import { ICategories } from 'app/entities/categories/categories.model';
import { CategoriesService } from 'app/entities/categories/service/categories.service';
import { IUnit } from 'app/entities/unit/unit.model';
import { UnitService } from 'app/entities/unit/service/unit.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;

  categoriesCollection: ICategories[] = [];
  unitsCollection: IUnit[] = [];
  securityUsersSharedCollection: ISecurityUser[] = [];

  editForm = this.fb.group({
    id: [],
    productName: [],
    alertUnits: [],
    casNumber: [],
    catlogNumber: [],
    molecularWt: [],
    molecularFormula: [],
    chemicalName: [],
    structureImg: [],
    description: [],
    qrCode: [],
    barCode: [],
    gstPercentage: [],
    lastModified: [],
    lastModifiedBy: [],
    freeField1: [],
    freeField2: [],
    freeField3: [],
    freeField4: [],
    categories: [],
    unit: [],
    securityUser: [],
  });

  constructor(
    protected productService: ProductService,
    protected categoriesService: CategoriesService,
    protected unitService: UnitService,
    protected securityUserService: SecurityUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      if (product.id === undefined) {
        const today = dayjs().startOf('day');
        product.lastModified = today;
      }

      this.updateForm(product);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  protected updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      productName: product.productName,
      alertUnits: product.alertUnits,
      casNumber: product.casNumber,
      catlogNumber: product.catlogNumber,
      molecularWt: product.molecularWt,
      molecularFormula: product.molecularFormula,
      chemicalName: product.chemicalName,
      structureImg: product.structureImg,
      description: product.description,
      qrCode: product.qrCode,
      barCode: product.barCode,
      gstPercentage: product.gstPercentage,
      lastModified: product.lastModified ? product.lastModified.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: product.lastModifiedBy,
      freeField1: product.freeField1,
      freeField2: product.freeField2,
      freeField3: product.freeField3,
      freeField4: product.freeField4,
      categories: product.categories,
      unit: product.unit,
      securityUser: product.securityUser,
    });

    this.categoriesCollection = this.categoriesService.addCategoriesToCollectionIfMissing(this.categoriesCollection, product.categories);
    this.unitsCollection = this.unitService.addUnitToCollectionIfMissing(this.unitsCollection, product.unit);
    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      product.securityUser
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categoriesService
      .query({ 'productId.specified': 'false' })
      .pipe(map((res: HttpResponse<ICategories[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategories[]) =>
          this.categoriesService.addCategoriesToCollectionIfMissing(categories, this.editForm.get('categories')!.value)
        )
      )
      .subscribe((categories: ICategories[]) => (this.categoriesCollection = categories));

    this.unitService
      .query({ 'productId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUnit[]>) => res.body ?? []))
      .pipe(map((units: IUnit[]) => this.unitService.addUnitToCollectionIfMissing(units, this.editForm.get('unit')!.value)))
      .subscribe((units: IUnit[]) => (this.unitsCollection = units));

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

  protected createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      alertUnits: this.editForm.get(['alertUnits'])!.value,
      casNumber: this.editForm.get(['casNumber'])!.value,
      catlogNumber: this.editForm.get(['catlogNumber'])!.value,
      molecularWt: this.editForm.get(['molecularWt'])!.value,
      molecularFormula: this.editForm.get(['molecularFormula'])!.value,
      chemicalName: this.editForm.get(['chemicalName'])!.value,
      structureImg: this.editForm.get(['structureImg'])!.value,
      description: this.editForm.get(['description'])!.value,
      qrCode: this.editForm.get(['qrCode'])!.value,
      barCode: this.editForm.get(['barCode'])!.value,
      gstPercentage: this.editForm.get(['gstPercentage'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? dayjs(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      freeField3: this.editForm.get(['freeField3'])!.value,
      freeField4: this.editForm.get(['freeField4'])!.value,
      categories: this.editForm.get(['categories'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
    };
  }
}
