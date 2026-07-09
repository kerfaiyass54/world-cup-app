import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupStandings } from './group-standings';

describe('GroupStandings', () => {
  let component: GroupStandings;
  let fixture: ComponentFixture<GroupStandings>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GroupStandings],
    }).compileComponents();

    fixture = TestBed.createComponent(GroupStandings);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
