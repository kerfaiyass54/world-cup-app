import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentsInfos } from './tournaments-infos';

describe('TournamentsInfos', () => {
  let component: TournamentsInfos;
  let fixture: ComponentFixture<TournamentsInfos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentsInfos],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentsInfos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
