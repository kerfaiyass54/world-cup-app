import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentInfo } from './tournament-info';

describe('TournamentInfo', () => {
  let component: TournamentInfo;
  let fixture: ComponentFixture<TournamentInfo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentInfo],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentInfo);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
