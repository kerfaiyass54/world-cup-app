import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentChampion } from './tournament-champion';

describe('TournamentChampion', () => {
  let component: TournamentChampion;
  let fixture: ComponentFixture<TournamentChampion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentChampion],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentChampion);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
