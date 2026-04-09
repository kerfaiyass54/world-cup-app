import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamsInfos } from './teams-infos';

describe('TeamsInfos', () => {
  let component: TeamsInfos;
  let fixture: ComponentFixture<TeamsInfos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeamsInfos],
    }).compileComponents();

    fixture = TestBed.createComponent(TeamsInfos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
