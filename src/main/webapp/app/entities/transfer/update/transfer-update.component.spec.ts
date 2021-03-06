import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TransferService } from '../service/transfer.service';
import { ITransfer, Transfer } from '../transfer.model';
import { IRmInventory } from 'app/entities/rm-inventory/rm-inventory.model';
import { RmInventoryService } from 'app/entities/rm-inventory/service/rm-inventory.service';

import { TransferUpdateComponent } from './transfer-update.component';

describe('Transfer Management Update Component', () => {
  let comp: TransferUpdateComponent;
  let fixture: ComponentFixture<TransferUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transferService: TransferService;
  let rmInventoryService: RmInventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TransferUpdateComponent],
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
      .overrideTemplate(TransferUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TransferUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transferService = TestBed.inject(TransferService);
    rmInventoryService = TestBed.inject(RmInventoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RmInventory query and add missing value', () => {
      const transfer: ITransfer = { id: 456 };
      const rmInventory: IRmInventory = { id: 13445 };
      transfer.rmInventory = rmInventory;

      const rmInventoryCollection: IRmInventory[] = [{ id: 99031 }];
      jest.spyOn(rmInventoryService, 'query').mockReturnValue(of(new HttpResponse({ body: rmInventoryCollection })));
      const additionalRmInventories = [rmInventory];
      const expectedCollection: IRmInventory[] = [...additionalRmInventories, ...rmInventoryCollection];
      jest.spyOn(rmInventoryService, 'addRmInventoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      expect(rmInventoryService.query).toHaveBeenCalled();
      expect(rmInventoryService.addRmInventoryToCollectionIfMissing).toHaveBeenCalledWith(
        rmInventoryCollection,
        ...additionalRmInventories
      );
      expect(comp.rmInventoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transfer: ITransfer = { id: 456 };
      const rmInventory: IRmInventory = { id: 32258 };
      transfer.rmInventory = rmInventory;

      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(transfer));
      expect(comp.rmInventoriesSharedCollection).toContain(rmInventory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transfer>>();
      const transfer = { id: 123 };
      jest.spyOn(transferService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transfer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(transferService.update).toHaveBeenCalledWith(transfer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transfer>>();
      const transfer = new Transfer();
      jest.spyOn(transferService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transfer }));
      saveSubject.complete();

      // THEN
      expect(transferService.create).toHaveBeenCalledWith(transfer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Transfer>>();
      const transfer = { id: 123 };
      jest.spyOn(transferService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transfer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transferService.update).toHaveBeenCalledWith(transfer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRmInventoryById', () => {
      it('Should return tracked RmInventory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRmInventoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
