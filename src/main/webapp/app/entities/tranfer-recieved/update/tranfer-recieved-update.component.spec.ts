import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TranferRecievedService } from '../service/tranfer-recieved.service';
import { ITranferRecieved, TranferRecieved } from '../tranfer-recieved.model';
import { ITransferDetails } from 'app/entities/transfer-details/transfer-details.model';
import { TransferDetailsService } from 'app/entities/transfer-details/service/transfer-details.service';

import { TranferRecievedUpdateComponent } from './tranfer-recieved-update.component';

describe('TranferRecieved Management Update Component', () => {
  let comp: TranferRecievedUpdateComponent;
  let fixture: ComponentFixture<TranferRecievedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tranferRecievedService: TranferRecievedService;
  let transferDetailsService: TransferDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TranferRecievedUpdateComponent],
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
      .overrideTemplate(TranferRecievedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TranferRecievedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tranferRecievedService = TestBed.inject(TranferRecievedService);
    transferDetailsService = TestBed.inject(TransferDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TransferDetails query and add missing value', () => {
      const tranferRecieved: ITranferRecieved = { id: 456 };
      const transferDetails: ITransferDetails = { id: 84694 };
      tranferRecieved.transferDetails = transferDetails;

      const transferDetailsCollection: ITransferDetails[] = [{ id: 68516 }];
      jest.spyOn(transferDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: transferDetailsCollection })));
      const additionalTransferDetails = [transferDetails];
      const expectedCollection: ITransferDetails[] = [...additionalTransferDetails, ...transferDetailsCollection];
      jest.spyOn(transferDetailsService, 'addTransferDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      expect(transferDetailsService.query).toHaveBeenCalled();
      expect(transferDetailsService.addTransferDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        transferDetailsCollection,
        ...additionalTransferDetails
      );
      expect(comp.transferDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tranferRecieved: ITranferRecieved = { id: 456 };
      const transferDetails: ITransferDetails = { id: 35956 };
      tranferRecieved.transferDetails = transferDetails;

      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tranferRecieved));
      expect(comp.transferDetailsSharedCollection).toContain(transferDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferRecieved>>();
      const tranferRecieved = { id: 123 };
      jest.spyOn(tranferRecievedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tranferRecieved }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tranferRecievedService.update).toHaveBeenCalledWith(tranferRecieved);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferRecieved>>();
      const tranferRecieved = new TranferRecieved();
      jest.spyOn(tranferRecievedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tranferRecieved }));
      saveSubject.complete();

      // THEN
      expect(tranferRecievedService.create).toHaveBeenCalledWith(tranferRecieved);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TranferRecieved>>();
      const tranferRecieved = { id: 123 };
      jest.spyOn(tranferRecievedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tranferRecieved });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tranferRecievedService.update).toHaveBeenCalledWith(tranferRecieved);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTransferDetailsById', () => {
      it('Should return tracked TransferDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTransferDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
