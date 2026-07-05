import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckPredictions } from './check-predictions';

describe('CheckPredictions', () => {
  let component: CheckPredictions;
  let fixture: ComponentFixture<CheckPredictions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CheckPredictions],
    }).compileComponents();

    fixture = TestBed.createComponent(CheckPredictions);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
