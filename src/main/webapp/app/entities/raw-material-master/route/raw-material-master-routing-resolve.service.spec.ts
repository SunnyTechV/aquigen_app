import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IRawMaterialMaster, RawMaterialMaster } from '../raw-material-master.model';
import { RawMaterialMasterService } from '../service/raw-material-master.service';

import { RawMaterialMasterRoutingResolveService } from './raw-material-master-routing-resolve.service';

describe('RawMaterialMaster routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: RawMaterialMasterRoutingResolveService;
  let service: RawMaterialMasterService;
  let resultRawMaterialMaster: IRawMaterialMaster | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(RawMaterialMasterRoutingResolveService);
    service = TestBed.inject(RawMaterialMasterService);
    resultRawMaterialMaster = undefined;
  });

  describe('resolve', () => {
    it('should return IRawMaterialMaster returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRawMaterialMaster = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRawMaterialMaster).toEqual({ id: 123 });
    });

    it('should return new IRawMaterialMaster if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRawMaterialMaster = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultRawMaterialMaster).toEqual(new RawMaterialMaster());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RawMaterialMaster })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultRawMaterialMaster = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultRawMaterialMaster).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
