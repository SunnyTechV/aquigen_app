import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RawMaterialMasterService } from '../service/raw-material-master.service';
import { IRawMaterialMaster, RawMaterialMaster } from '../raw-material-master.model';
import { IRawMaterialOrder } from 'app/entities/raw-material-order/raw-material-order.model';
import { RawMaterialOrderService } from 'app/entities/raw-material-order/service/raw-material-order.service';
import { ICategories } from 'app/entities/categories/categories.model';
import { CategoriesService } from 'app/entities/categories/service/categories.service';
import { IUnit } from 'app/entities/unit/unit.model';
import { UnitService } from 'app/entities/unit/service/unit.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';

import { RawMaterialMasterUpdateComponent } from './raw-material-master-update.component';

describe('RawMaterialMaster Management Update Component', () => {
  let comp: RawMaterialMasterUpdateComponent;
  let fixture: ComponentFixture<RawMaterialMasterUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rawMaterialMasterService: RawMaterialMasterService;
  let rawMaterialOrderService: RawMaterialOrderService;
  let categoriesService: CategoriesService;
  let unitService: UnitService;
  let securityUserService: SecurityUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RawMaterialMasterUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RawMaterialMasterUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RawMaterialMasterUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rawMaterialMasterService = TestBed.inject(RawMaterialMasterService);
    rawMaterialOrderService = TestBed.inject(RawMaterialOrderService);
    categoriesService = TestBed.inject(CategoriesService);
    unitService = TestBed.inject(UnitService);
    securityUserService = TestBed.inject(SecurityUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RawMaterialOrder query and add missing value', () => {
      const rawMaterialMaster: IRawMaterialMaster = { id: 456 };
      const rawMaterialOrders: IRawMaterialOrder[] = [{ id: 96409 }];
      rawMaterialMaster.rawMaterialOrders = rawMaterialOrders;

      const rawMaterialOrderCollection: IRawMaterialOrder[] = [{ id: 92430 }];
      jest.spyOn(rawMaterialOrderService, 'query').mockReturnValue(of(new HttpResponse({ body: rawMaterialOrderCollection })));
      const additionalRawMaterialOrders = [...rawMaterialOrders];
      const expectedCollection: IRawMaterialOrder[] = [...additionalRawMaterialOrders, ...rawMaterialOrderCollection];
      jest.spyOn(rawMaterialOrderService, 'addRawMaterialOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rawMaterialMaster });
      comp.ngOnInit();

      expect(rawMaterialOrderService.query).toHaveBeenCalled();
      expect(rawMaterialOrderService.addRawMaterialOrderToCollectionIfMissing).toHaveBeenCalledWith(
        rawMaterialOrderCollection,
        ...additionalRawMaterialOrders
      );
      expect(comp.rawMaterialOrdersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Categories query and add missing value', () => {
      const rawMaterialMaster: IRawMaterialMaster = { id: 456 };
      const categories: ICategories = { id: 88622 };
      rawMaterialMaster.categories = categories;

      const categoriesCollection: ICategories[] = [{ id: 52254 }];
      jest.spyOn(categoriesService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriesCollection })));
      const additionalCategories = [categories];
      const expectedCollection: ICategories[] = [...additionalCategories, ...categoriesCollection];
      jest.spyOn(categoriesService, 'addCategoriesToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rawMaterialMaster });
      comp.ngOnInit();

      expect(categoriesService.query).toHaveBeenCalled();
      expect(categoriesService.addCategoriesToCollectionIfMissing).toHaveBeenCalledWith(categoriesCollection, ...additionalCategories);
      expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Unit query and add missing value', () => {
      const rawMaterialMaster: IRawMaterialMaster = { id: 456 };
      const unit: IUnit = { id: 19055 };
      rawMaterialMaster.unit = unit;

      const unitCollection: IUnit[] = [{ id: 48902 }];
      jest.spyOn(unitService, 'query').mockReturnValue(of(new HttpResponse({ body: unitCollection })));
      const additionalUnits = [unit];
      const expectedCollection: IUnit[] = [...additionalUnits, ...unitCollection];
      jest.spyOn(unitService, 'addUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rawMaterialMaster });
      comp.ngOnInit();

      expect(unitService.query).toHaveBeenCalled();
      expect(unitService.addUnitToCollectionIfMissing).toHaveBeenCalledWith(unitCollection, ...additionalUnits);
      expect(comp.unitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityUser query and add missing value', () => {
      const rawMaterialMaster: IRawMaterialMaster = { id: 456 };
      const securityUser: ISecurityUser = { id: 76736 };
      rawMaterialMaster.securityUser = securityUser;

      const securityUserCollection: ISecurityUser[] = [{ id: 84263 }];
      jest.spyOn(securityUserService, 'query').mockReturnValue(of(new HttpResponse({ body: securityUserCollection })));
      const additionalSecurityUsers = [securityUser];
      const expectedCollection: ISecurityUser[] = [...additionalSecurityUsers, ...securityUserCollection];
      jest.spyOn(securityUserService, 'addSecurityUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ rawMaterialMaster });
      comp.ngOnInit();

      expect(securityUserService.query).toHaveBeenCalled();
      expect(securityUserService.addSecurityUserToCollectionIfMissing).toHaveBeenCalledWith(
        securityUserCollection,
        ...additionalSecurityUsers
      );
      expect(comp.securityUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const rawMaterialMaster: IRawMaterialMaster = { id: 456 };
      const rawMaterialOrders: IRawMaterialOrder = { id: 98954 };
      rawMaterialMaster.rawMaterialOrders = [rawMaterialOrders];
      const categories: ICategories = { id: 65863 };
      rawMaterialMaster.categories = categories;
      const unit: IUnit = { id: 86487 };
      rawMaterialMaster.unit = unit;
      const securityUser: ISecurityUser = { id: 90536 };
      rawMaterialMaster.securityUser = securityUser;

      activatedRoute.data = of({ rawMaterialMaster });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(rawMaterialMaster));
      expect(comp.rawMaterialOrdersSharedCollection).toContain(rawMaterialOrders);
      expect(comp.categoriesSharedCollection).toContain(categories);
      expect(comp.unitsSharedCollection).toContain(unit);
      expect(comp.securityUsersSharedCollection).toContain(securityUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterialMaster>>();
      const rawMaterialMaster = { id: 123 };
      jest.spyOn(rawMaterialMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterialMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rawMaterialMaster }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(rawMaterialMasterService.update).toHaveBeenCalledWith(rawMaterialMaster);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterialMaster>>();
      const rawMaterialMaster = new RawMaterialMaster();
      jest.spyOn(rawMaterialMasterService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterialMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rawMaterialMaster }));
      saveSubject.complete();

      // THEN
      expect(rawMaterialMasterService.create).toHaveBeenCalledWith(rawMaterialMaster);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RawMaterialMaster>>();
      const rawMaterialMaster = { id: 123 };
      jest.spyOn(rawMaterialMasterService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rawMaterialMaster });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rawMaterialMasterService.update).toHaveBeenCalledWith(rawMaterialMaster);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRawMaterialOrderById', () => {
      it('Should return tracked RawMaterialOrder primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRawMaterialOrderById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCategoriesById', () => {
      it('Should return tracked Categories primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCategoriesById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUnitById', () => {
      it('Should return tracked Unit primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUnitById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSecurityUserById', () => {
      it('Should return tracked SecurityUser primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSecurityUserById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });

  describe('Getting selected relationships', () => {
    describe('getSelectedRawMaterialOrder', () => {
      it('Should return option if no RawMaterialOrder is selected', () => {
        const option = { id: 123 };
        const result = comp.getSelectedRawMaterialOrder(option);
        expect(result === option).toEqual(true);
      });

      it('Should return selected RawMaterialOrder for according option', () => {
        const option = { id: 123 };
        const selected = { id: 123 };
        const selected2 = { id: 456 };
        const result = comp.getSelectedRawMaterialOrder(option, [selected2, selected]);
        expect(result === selected).toEqual(true);
        expect(result === selected2).toEqual(false);
        expect(result === option).toEqual(false);
      });

      it('Should return option if this RawMaterialOrder is not selected', () => {
        const option = { id: 123 };
        const selected = { id: 456 };
        const result = comp.getSelectedRawMaterialOrder(option, [selected]);
        expect(result === option).toEqual(true);
        expect(result === selected).toEqual(false);
      });
    });
  });
});
