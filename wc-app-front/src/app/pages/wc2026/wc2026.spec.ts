import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Wc2026 } from './wc2026';

describe('Wc2026', () => {
  let component: Wc2026;
  let fixture: ComponentFixture<Wc2026>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Wc2026],
    }).compileComponents();

    fixture = TestBed.createComponent(Wc2026);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
