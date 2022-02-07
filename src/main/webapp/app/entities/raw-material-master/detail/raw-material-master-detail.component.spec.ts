import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RawMaterialMasterDetailComponent } from './raw-material-master-detail.component';

describe('RawMaterialMaster Management Detail Component', () => {
  let comp: RawMaterialMasterDetailComponent;
  let fixture: ComponentFixture<RawMaterialMasterDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RawMaterialMasterDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ rawMaterialMaster: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RawMaterialMasterDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RawMaterialMasterDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load rawMaterialMaster on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.rawMaterialMaster).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
