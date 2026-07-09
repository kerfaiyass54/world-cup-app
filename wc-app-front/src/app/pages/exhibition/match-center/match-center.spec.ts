import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchCenter } from './match-center';

describe('MatchCenter', () => {
  let component: MatchCenter;
  let fixture: ComponentFixture<MatchCenter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MatchCenter],
    }).compileComponents();

    fixture = TestBed.createComponent(MatchCenter);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
