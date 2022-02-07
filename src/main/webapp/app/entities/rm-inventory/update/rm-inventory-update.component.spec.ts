import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RmInventoryService } from '../service/rm-inventory.service';
import { IRmInventory, RmInventory } from '../rm-inventory.model';

import { RmInventoryUpdateComponent } from './rm-inventory-update.component';

describe('RmInventory Management Update Component', () => {
  let comp: RmInventoryUpdateComponent;
  let fixture: ComponentFixture<RmInventoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let rmInventoryService: RmInventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RmInventoryUpdateComponent],
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
      .overrideTemplate(RmInventoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RmInventoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    rmInventoryService = TestBed.inject(RmInventoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const rmInventory: IRmInventory = { id: 456 };

      activatedRoute.data = of({ rmInventory });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(rmInventory));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RmInventory>>();
      const rmInventory = { id: 123 };
      jest.spyOn(rmInventoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rmInventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rmInventory }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(rmInventoryService.update).toHaveBeenCalledWith(rmInventory);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RmInventory>>();
      const rmInventory = new RmInventory();
      jest.spyOn(rmInventoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rmInventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: rmInventory }));
      saveSubject.complete();

      // THEN
      expect(rmInventoryService.create).toHaveBeenCalledWith(rmInventory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<RmInventory>>();
      const rmInventory = { id: 123 };
      jest.spyOn(rmInventoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ rmInventory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(rmInventoryService.update).toHaveBeenCalledWith(rmInventory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
