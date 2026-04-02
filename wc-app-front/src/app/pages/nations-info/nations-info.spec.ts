import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NationsInfo } from './nations-info';

describe('NationsInfo', () => {
  let component: NationsInfo;
  let fixture: ComponentFixture<NationsInfo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NationsInfo],
    }).compileComponents();

    fixture = TestBed.createComponent(NationsInfo);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
