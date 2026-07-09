import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupMatches } from './group-matches';

describe('GroupMatches', () => {
  let component: GroupMatches;
  let fixture: ComponentFixture<GroupMatches>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GroupMatches],
    }).compileComponents();

    fixture = TestBed.createComponent(GroupMatches);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
