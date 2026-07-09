import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentCreate } from './tournament-create';

describe('TournamentCreate', () => {
  let component: TournamentCreate;
  let fixture: ComponentFixture<TournamentCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TournamentCreate],
    }).compileComponents();

    fixture = TestBed.createComponent(TournamentCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
