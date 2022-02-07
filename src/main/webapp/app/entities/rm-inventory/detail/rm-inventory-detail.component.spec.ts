import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RmInventoryDetailComponent } from './rm-inventory-detail.component';

describe('RmInventory Management Detail Component', () => {
  let comp: RmInventoryDetailComponent;
  let fixture: ComponentFixture<RmInventoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RmInventoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ rmInventory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RmInventoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RmInventoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load rmInventory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.rmInventory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
